
package com.rendellvelasco.fleettracker.services;

import com.rendellvelasco.fleettracker.models.UsageLog;
import com.rendellvelasco.fleettracker.models.Vehicle;

import java.util.*;

public class UsageService {
    private Map<Vehicle, List<UsageLog>> dataStore = new HashMap<>();

    public void addLog(Vehicle v, UsageLog log) {
        this.dataStore
                .computeIfAbsent(v, key -> new ArrayList<>())
                .add(log);
    }

    public List<UsageLog> getLogs(Vehicle v) {
        return this.dataStore.getOrDefault(v, Collections.emptyList());
    }
}
