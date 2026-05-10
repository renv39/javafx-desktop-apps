package com.rendellvelasco.models;

public class Suv extends PassengerVehicles {
    final String primaryFunction = "Family transport, off-road capability";
    final int serviceInterval = 12000;
    final double maintenanceCost = 450;
    final String fuelType = "Hybrid";
    final String vehicleCategory = "Premium";

    public Suv(int currentMileage) {
        super("Suv", 45000, currentMileage);
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
