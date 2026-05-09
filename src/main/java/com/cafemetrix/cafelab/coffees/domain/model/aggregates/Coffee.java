package com.cafemetrix.cafelab.coffees.domain.model.aggregates;

import com.cafemetrix.cafelab.coffees.domain.model.commands.CreateCoffeeCommand;
import com.cafemetrix.cafelab.coffees.domain.model.valueobjects.CoffeeName;
import com.cafemetrix.cafelab.coffees.domain.model.valueobjects.CoffeeRegion;
import com.cafemetrix.cafelab.coffees.domain.model.valueobjects.CoffeeVariety;
import com.cafemetrix.cafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Coffee extends AuditableAbstractAggregateRoot<Coffee> {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private CoffeeName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "region"))
    private CoffeeRegion region;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "variety"))
    private CoffeeVariety variety;

    
    @Getter
    @Column(nullable = false)
    private Double totalWeight;

    
    public Coffee(String name, String region, String variety, Double totalWeight) {
        this.name = new CoffeeName(name);
        this.region = new CoffeeRegion(region);
        this.variety = new CoffeeVariety(variety);
        this.totalWeight = totalWeight;
    }

    
    public Coffee() {}

    
    public Coffee(CreateCoffeeCommand command) {
        this.name = new CoffeeName(command.name());
        this.region = new CoffeeRegion(command.region());
        this.variety = new CoffeeVariety(command.variety());
        this.totalWeight = command.totalWeight();
    }

    
    public String getName() {
        return name.value();
    }

    
    public String getRegion() {
        return region.value();
    }

    
    public String getVariety() {
        return variety.value();
    }

    
    public void updateName(String name) {
        this.name = new CoffeeName(name);
    }

    
    public void updateRegion(String region) {
        this.region = new CoffeeRegion(region);
    }

    
    public void updateVariety(String variety) {
        this.variety = new CoffeeVariety(variety);
    }

    
    public void updateTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }
}
