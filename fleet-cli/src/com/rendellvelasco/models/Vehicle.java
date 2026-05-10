package com.rendellvelasco.models;

public abstract class Vehicle implements IVehicleMaintenance, IVehicleOperations, Comparable<Vehicle> {
    private final String name;
    private final double purchasePrice;
    private final int currentMileage;
    private int serviceInterval;
    private double maintenanceCost;
    private String primaryFunction;
    private String fuelType;

    public Vehicle(String name, double purchasePrice, int currentMileage) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.currentMileage = currentMileage;
    }

    public abstract String getCategory();

    public int getCurrentMileage() {
        return this.currentMileage;
    }


    public double getPurchasePrice() {
        return purchasePrice;
    }

    public String toString() {
        return String.format("%s - $%,.1f", this.name, this.purchasePrice);
    }

    public void setServiceInterval(int serviceInterval) {
        this.serviceInterval = serviceInterval;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public void setPrimaryFunction(String primaryFunction) {
        this.primaryFunction = primaryFunction;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public int getServiceInterval() {
        return this.serviceInterval;
    }

    @Override
    public double getMaintenanceCost() {
        return this.maintenanceCost;
    }

    @Override
    public String getPrimaryFunction() {
        return this.primaryFunction;
    }

    @Override
    public String getFuelType() {
        return this.fuelType;
    }

    @Override
    public int compareTo(Vehicle v) {
        int currentRemaining =this.getCurrentMileage() - this.getServiceInterval();
        int nextRemaining = v.getCurrentMileage() - v.getServiceInterval();
        boolean thisOverdue = currentRemaining > 0;
        boolean otherOverdue = nextRemaining > 0;
        if (thisOverdue && !otherOverdue) {
            return -1; // this comes first
        } else if (!thisOverdue && otherOverdue) {
            return 1;  // other comes first
        }


        return Integer.compare(Math.abs((currentRemaining)), Math.abs((nextRemaining)));


    }

}
