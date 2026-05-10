package com.rendellvelasco.models;

public class Ambulance extends SpecializedVehicles {
    final String primaryFunction = "Emergency medical transport, life-saving";
    final int serviceInterval = 8000;
    final double maintenanceCost = 800;
    final String fuelType = "Diesel";
    final String vehicleCategory = "Specialized";

    public Ambulance(int currentMileage) {
        super("Ambulance", 85000, currentMileage);
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
