package com.cafemetrix.cafelab.production.domain.model.aggregates;

import com.cafemetrix.cafelab.production.domain.model.commands.CreateRoastProfileCommand;
import com.cafemetrix.cafelab.production.domain.model.commands.UpdateRoastProfileCommand;
import com.cafemetrix.cafelab.production.domain.model.valueobjects.Duration;
import com.cafemetrix.cafelab.production.domain.model.valueobjects.RoastProfileName;
import com.cafemetrix.cafelab.production.domain.model.valueobjects.RoastType;
import com.cafemetrix.cafelab.production.domain.model.valueobjects.Temperature;
import com.cafemetrix.cafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/** Perfil de tueste; {@code userId} persiste en {@code user_id} (FK a profiles.id). */
@Entity
@Table(name = "roast_profiles")
public class RoastProfile extends AuditableAbstractAggregateRoot<RoastProfile> {
    
    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", length = 50))
    private RoastProfileName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "type", length = 20))
    private RoastType type;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "duration"))
    private Duration duration;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "temp_start"))
    private Temperature tempStart;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "temp_end"))
    private Temperature tempEnd;

    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite;

    @Getter
    @Column(name = "coffee_lot_id", nullable = false)
    private Long coffeeLotId;

    
    public RoastProfile() {
        this.isFavorite = false;
    }

    
    public RoastProfile(Long userId, String name, String type, Integer duration, 
                       Double tempStart, Double tempEnd, Long coffeeLotId) {
        this.userId = userId;
        this.name = new RoastProfileName(name);
        this.type = new RoastType(type);
        this.duration = new Duration(duration);
        this.tempStart = new Temperature(tempStart);
        this.tempEnd = new Temperature(tempEnd);
        this.coffeeLotId = coffeeLotId;
        this.isFavorite = false;
    }

    
    public RoastProfile(CreateRoastProfileCommand command) {
        this.userId = command.userId();
        this.name = new RoastProfileName(command.name());
        this.type = new RoastType(command.type());
        this.duration = new Duration(command.duration());
        this.tempStart = new Temperature(command.tempStart());
        this.tempEnd = new Temperature(command.tempEnd());
        this.coffeeLotId = command.coffeeLotId();
        this.isFavorite = command.isFavorite() != null ? command.isFavorite() : false;
    }

    
    public RoastProfile update(UpdateRoastProfileCommand command) {
        this.name = new RoastProfileName(command.name());
        this.type = new RoastType(command.type());
        this.duration = new Duration(command.duration());
        this.tempStart = new Temperature(command.tempStart());
        this.tempEnd = new Temperature(command.tempEnd());
        this.coffeeLotId = command.coffeeLotId();
        this.isFavorite = command.isFavorite();
        return this;
    }

    
    public void toggleFavorite() {
        this.isFavorite = !this.isFavorite;
    }

    public String getName() { return name.value(); }
    public String getType() { return type.value(); }
    public Integer getDuration() { return duration.value(); }
    public Double getTempStart() { return tempStart.value(); }
    public Double getTempEnd() { return tempEnd.value(); }
    public Boolean getIsFavorite() { return isFavorite; }
}
