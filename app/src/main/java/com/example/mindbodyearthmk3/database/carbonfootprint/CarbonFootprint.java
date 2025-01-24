package com.example.mindbodyearthmk3.database.carbonfootprint;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mindbodyearthmk3.database.MealPlan;

//Emissions = E

@Entity
public class CarbonFootprint {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "carbon_footprint_id")
    private long id;
//    private double totalCarbonFootprint;
//    private double energyFootprint;
//    private double transportationFootprint;
//    private double wasteFootprint;
//    private double mealPlanFootprint;

    @Embedded
    private final EnergyConsumption energyConsumption;
    @Embedded
    private final Transportation transportation;
    @Embedded
    private final MealPlan mealPlan;
    private double mealEmissionFactor;
    @Embedded
    private final Waste waste;


    public CarbonFootprint(long id, EnergyConsumption energyConsumption, Transportation transportation, MealPlan mealPlan, Waste waste) {
        this.id = id;
        this.energyConsumption = energyConsumption;
        this.transportation = transportation;
        this.mealPlan = mealPlan;
        this.waste = waste;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnergyConsumption getEnergyConsumption() {
        return energyConsumption;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public double getMealEmissionFactor() {
        return mealEmissionFactor;
    }

    public void setMealEmissionFactor(double mealEmissionFactor) {
        this.mealEmissionFactor = mealEmissionFactor;
    }

    public Waste getWaste() {
        return waste;
    }
}
