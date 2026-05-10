package com.rendellvelasco.views;

import com.rendellvelasco.models.Vehicle;

import java.util.List;

public class VehicleView {
    public void printRequirementHeader(int number) {
        System.out.printf("--: Requirement %d :--%n", number);
    }

    public void printRequirementHeader(int number, int number2) {
        System.out.printf("--: Requirement %d & %d :--%n", number, number2);
    }

    public void printMileageMessage(String carName) {
        System.out.printf("Enter the current mileage for %s (km): ", carName);
    }

    public void printUrgentVehicleMaintenanceDetails(Vehicle v) {
        System.out.println("The vehicle requiring the most urgent maintenance is: " + v.getClass().getSimpleName());
        System.out.printf("%s's purchase price is: $%,.1f%n", v.getClass().getSimpleName(), v.getPurchasePrice());
        System.out.println(v.getClass().getSimpleName()+ "'s primary function: " + v.getPrimaryFunction());
        System.out.printf("%s's service interval: Every %,d%n",v.getClass().getSimpleName(), v.getServiceInterval());
        System.out.printf("%s's maintenance cost: $%.1f%n", v.getClass().getSimpleName(), v.getMaintenanceCost());
    }

    public void printVehicleDescendingOrderOfPurchasingPrice(List<Vehicle> vehicleList) {
        System.out.println("Vehicles in Descending Order of Purchase Price:");
        for (Vehicle v : vehicleList) {
            System.out.println(v.toString());
        }
    }

    public void printVehicleCategoryRequest() {
        System.out.println("Enter a vehicle category (PassengerVehicles, CommercialVehicles, SpecializedVehicles): ");
    }

    public void printVehicleCategoryList(List<Vehicle> vehicleList) {
        System.out.println("Vehicles in " + vehicleList.getClass().getSimpleName() + "Category:");
        for (Vehicle v : vehicleList) {
            System.out.println(v.getClass().getSimpleName() + " - Primary Function: " + v.getPrimaryFunction() + " - Fuel Type: " + v.getFuelType());
        }
    }

    public void printVehicleMaintenanceUrgency(List<Vehicle> vehicleList, List<Integer> remaining) {
        System.out.println("Vehicles sorted by maintenance urgency (closest to service interval first):");
        for (int i = 0; i < vehicleList.size(); i++) {
            System.out.printf("%s (%,d km / %,d km - %d km remaining)%n", vehicleList.get(i).toString(), vehicleList.get(i).getCurrentMileage(), vehicleList.get(i).getServiceInterval(), remaining.get(i));
        }
    }
}
