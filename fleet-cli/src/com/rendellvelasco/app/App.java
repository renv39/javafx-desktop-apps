package com.rendellvelasco.app;

import com.rendellvelasco.controllers.VehicleController;
import com.rendellvelasco.models.Vehicle;
import com.rendellvelasco.views.VehicleView;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Vehicle> model = new ArrayList<>();
        VehicleView view = new VehicleView();
        VehicleController controller = new VehicleController(view, model);
        controller.setVehicleMileages();
        controller.mostUrgentMaintenance();
        controller.descendingOrderOfPurchasePrice();
        controller.sortByVehicleCategory();
        controller.sortByMaintenanceUrgency();

    }
}
