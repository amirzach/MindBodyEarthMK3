package com.example.mindbodyearthmk3.database.carbonfootprint;

public class EnergyConsumption {
    private double electricityUsage; //kWh
    private double gasUsage; //cubic centimeters
    private double electricityEmissionFactor;
    private double gasEmissionFactor;

    public EnergyConsumption(double electricityUsage, double gasUsage) {
        this.electricityUsage = electricityUsage;
        this.gasUsage = gasUsage;
    }

    public double getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(double electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public double getGasUsage() {
        return gasUsage;
    }

    public void setGasUsage(double gasUsage) {
        this.gasUsage = gasUsage;
    }

    public double getElectricityEmissionFactor() {
        return electricityEmissionFactor;
    }

    public void setElectricityEmissionFactor(double electricityEmissionFactor) {
        this.electricityEmissionFactor = electricityEmissionFactor;
    }

    public double getGasEmissionFactor() {
        return gasEmissionFactor;
    }

    public void setGasEmissionFactor(double gasEmissionFactor) {
        this.gasEmissionFactor = gasEmissionFactor;
    }
}
