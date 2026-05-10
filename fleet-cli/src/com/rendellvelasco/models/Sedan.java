package com.rendellvelasco.models;

public class Sedan extends PassengerVehicles {
    final String primaryFunction = "Executive transportation, client visits";
    final int serviceInterval = 10000;
    final double maintenanceCost = 350;
    final String fuelType = "Gasoline";
    final String vehicleCategory = "Economy";

    public Sedan(int currentMileage) {
        super("Sedan", 28500, currentMileage);
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
