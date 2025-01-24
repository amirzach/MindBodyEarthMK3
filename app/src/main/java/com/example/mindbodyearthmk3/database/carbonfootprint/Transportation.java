package com.example.mindbodyearthmk3.database.carbonfootprint;

public class Transportation {
    private String modeOfTransport; //public, private (petroleum), private (electric) - put in a spinner
    private double distanceTravelled; //km
    private double fuelEfficiency; //percentage

    private double transportationEmissionFactor;

    public Transportation(String modeOfTransport, double distanceTravelled, double fuelEfficiency) {
        this.modeOfTransport = modeOfTransport;
        this.distanceTravelled = distanceTravelled;
        this.fuelEfficiency = fuelEfficiency;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public double getTransportationEmissionFactor() {
        return transportationEmissionFactor;
    }

    public void setTransportationEmissionFactor(double transportationEmissionFactor) {
        this.transportationEmissionFactor = transportationEmissionFactor;
    }
}
