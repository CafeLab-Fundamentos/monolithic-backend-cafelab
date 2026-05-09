package com.cafemetrix.cafelab.shared.infrastructure.persistence.schema;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * La tabla {@code defects} puede conservar la columna NOT NULL {@code coffee_id} de un esquema antiguo
 * mientras la entidad JPA ya no la mapea; MySQL rechaza el INSERT. Esta reparación es idempotente y
 * equivale a {@code db/migration/V1_2__defects_drop_coffee_id.sql} cuando Flyway está desactivado.
 */
@Component
@Order
public class DefectsLegacyCoffeeIdRepair implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DefectsLegacyCoffeeIdRepair.class);
    private static final Pattern SAFE_IDENT = Pattern.compile("^[A-Za-z0-9_]+$");

    private final JdbcTemplate jdbc;

    public DefectsLegacyCoffeeIdRepair(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            repairIfNeeded();
        } catch (Exception e) {
            log.warn(
                    "No se pudo eliminar la columna legacy coffee_id de defects; se reintentará al reiniciar: {}",
                    e.getMessage());
        }
    }

    private void repairIfNeeded() {
        String db = jdbc.queryForObject("SELECT DATABASE()", String.class);
        if (db == null || db.isBlank()) {
            return;
        }

        List<String> fkNames = jdbc.query(
                """
                SELECT CONSTRAINT_NAME FROM information_schema.KEY_COLUMN_USAGE
                WHERE TABLE_SCHEMA = ? AND TABLE_NAME = 'defects' AND COLUMN_NAME = 'coffee_id'
                  AND REFERENCED_TABLE_NAME IS NOT NULL
                LIMIT 1
                """,
                (rs, rowNum) -> rs.getString(1),
                db);
        if (!fkNames.isEmpty()) {
            String fk = fkNames.get(0);
            if (fk != null && SAFE_IDENT.matcher(fk).matches()) {
                jdbc.execute("ALTER TABLE defects DROP FOREIGN KEY `" + fk + "`");
            }
        }

        Integer colCount = jdbc.queryForObject(
                """
                SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = ? AND TABLE_NAME = 'defects' AND COLUMN_NAME = 'coffee_id'
                """,
                Integer.class,
                db);
        if (colCount != null && colCount > 0) {
            jdbc.execute("ALTER TABLE defects DROP COLUMN coffee_id");
            log.info("Columna legacy coffee_id eliminada de la tabla defects.");
        }
    }
}
