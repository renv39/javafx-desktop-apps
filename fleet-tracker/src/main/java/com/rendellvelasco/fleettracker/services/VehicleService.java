
package com.rendellvelasco.fleettracker.services;

import com.rendellvelasco.fleettracker.models.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleService {
    private List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle v){
        vehicles.add(v);
    }

    public List<Vehicle> getAllVehicles(){
        return Collections.unmodifiableList(vehicles);
    }
}
