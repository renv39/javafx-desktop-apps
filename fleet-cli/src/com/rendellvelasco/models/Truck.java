package com.rendellvelasco.models;

public class Truck extends CommercialVehicles {
    final String primaryFunction = "Heavy cargo, long-distance hauling";
    final int serviceInterval = 15000;
    final double maintenanceCost = 600;
    final String fuelType = "Diesel";
    final String vehicleCategory = "Commercial";

    public Truck(int currentMileage) {
        super("Truck", 62000, currentMileage);
        this.setServiceInterval(serviceInterval);
        this.setMaintenanceCost(maintenanceCost);
        this.setPrimaryFunction(primaryFunction);
        this.setFuelType(fuelType);
    }

    @Override
    public String getCategory() {
        return this.getClass().getSuperclass().getSimpleName();
    }
}
