-- Defectos ya no referencian coffees(id): solo texto (coffee_display_name, etc.).
-- Idempotente: seguro si la columna o la FK ya no existen.

SET @db = DATABASE();

SELECT CONSTRAINT_NAME INTO @fkname
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = @db
  AND TABLE_NAME = 'defects'
  AND COLUMN_NAME = 'coffee_id'
  AND REFERENCED_TABLE_NAME IS NOT NULL
LIMIT 1;

SET @q = IF(@fkname IS NOT NULL, CONCAT('ALTER TABLE defects DROP FOREIGN KEY `', @fkname, '`'), 'SELECT 1');
PREPARE stmt FROM @q;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT COUNT(*) INTO @col
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'defects' AND COLUMN_NAME = 'coffee_id';

SET @q2 = IF(@col > 0, 'ALTER TABLE defects DROP COLUMN coffee_id', 'SELECT 1');
PREPARE stmt2 FROM @q2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;
