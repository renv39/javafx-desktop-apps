package com.rendellvelasco.models;

public class Van extends CommercialVehicles {
    final String primaryFunction = "Passenger group transport, deliveries";
    final int serviceInterval = 10000;
    final double maintenanceCost = 400;
    final String fuelType = "Gasoline";
    final String vehicleCategory = "Commercial";

    public Van(int currentMileage) {
        super("Van", 38500, currentMileage);
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
