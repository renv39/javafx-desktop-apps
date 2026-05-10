
package com.rendellvelasco.fleettracker.services;

import com.rendellvelasco.fleettracker.models.MaintenanceRecord;
import com.rendellvelasco.fleettracker.models.Vehicle;

import java.util.*;

public class MaintenanceService {
    private Map<Vehicle, List<MaintenanceRecord>> dataStore = new HashMap<>();

    public void addRecord(Vehicle v, MaintenanceRecord record){
        // If v is not already in the map, create a new ArrayList
        this.dataStore
                .computeIfAbsent(v, key -> new ArrayList<>())
                .add(record);
    }

    public List<MaintenanceRecord> getRecords(Vehicle v){
        return dataStore.getOrDefault(v, Collections.emptyList());
    }
}

