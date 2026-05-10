package com.rendellvelasco.controllers;

import com.rendellvelasco.models.*;
import com.rendellvelasco.utils.Utility;
import com.rendellvelasco.views.VehicleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleController {
    private List<Vehicle> model;
    private VehicleView view;

    public VehicleController(VehicleView vehicleView, List<Vehicle> vehicleModel) {
        this.model = vehicleModel;
        this.view = vehicleView;
    }

    public void setVehicleMileages(){
        view.printRequirementHeader(1);

        view.printMileageMessage("Sedan");
        int mileage = (Integer) Utility.takeInput(true);
        model.add(new Sedan(mileage));

        view.printMileageMessage("SUV");
        mileage = (Integer) Utility.takeInput(true);
        model.add(new Suv(mileage));

        view.printMileageMessage("Truck");
        mileage = (Integer) Utility.takeInput(true);
        model.add(new Truck(mileage));

        view.printMileageMessage("Van");
        mileage = (Integer) Utility.takeInput(true);
        model.add(new Van(mileage));

        view.printMileageMessage("Ambulance");
        mileage = (Integer) Utility.takeInput(true);
        model.add(new Ambulance(mileage));


    }

    public void mostUrgentMaintenance(){
        Collections.sort(model);
        view.printRequirementHeader(2);
        view.printUrgentVehicleMaintenanceDetails(model.getFirst());
    }

    public void descendingOrderOfPurchasePrice(){
        model.sort(Comparator.comparingDouble(Vehicle::getPurchasePrice).reversed());
        view.printRequirementHeader(3);
        view.printVehicleDescendingOrderOfPurchasingPrice(model);

    }


    public void sortByVehicleCategory(){
        view.printRequirementHeader(4);
        view.printVehicleCategoryRequest();
        String category = (String) Utility.takeInput(false);
        List<Vehicle> vehicleCategoryList = new ArrayList<>();
        for(Vehicle v: model){
            if (category.equals(v.getCategory())) {
                vehicleCategoryList.add(v);
            }

        }
        view.printVehicleCategoryList(vehicleCategoryList);
    }

    public void sortByMaintenanceUrgency(){
        Collections.sort(model);
        view.printRequirementHeader(5,6);
        List<Integer> remainingList = new ArrayList<>();
        int remaining = 0;
        for(Vehicle v : model) {
            remaining = v.getCurrentMileage() - v.getServiceInterval() ;

            if(remaining >= 0){
                remainingList.add(0);
                continue;
            }
            remainingList.add(Math.abs(remaining));
        }
        view.printVehicleMaintenanceUrgency(model, remainingList);


    }

}

