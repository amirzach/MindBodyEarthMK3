package com.example.mindbodyearthmk3.database.carbonfootprint;

public class Waste {
    //both in kg
    private double wasteProduced;
    private double wasteRecycled;
    private double wasteEmissionFactor;

    public Waste(double wasteProduced, double wasteRecycled) {
        this.wasteProduced = wasteProduced;
        this.wasteRecycled = wasteRecycled;
    }

    public double getWasteProduced() {
        return wasteProduced;
    }

    public void setWasteProduced(double wasteProduced) {
        this.wasteProduced = wasteProduced;
    }

    public double getWasteRecycled() {
        return wasteRecycled;
    }

    public void setWasteRecycled(double wasteRecycled) {
        this.wasteRecycled = wasteRecycled;
    }

    public double getWasteEmissionFactor() {
        return wasteEmissionFactor;
    }

    public void setWasteEmissionFactor(double wasteEmissionFactor) {
        this.wasteEmissionFactor = wasteEmissionFactor;
    }
}
