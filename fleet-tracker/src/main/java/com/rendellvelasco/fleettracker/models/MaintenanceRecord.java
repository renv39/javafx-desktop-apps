package com.rendellvelasco.fleettracker.models;

import java.time.LocalDate;

public class MaintenanceRecord {
    private LocalDate date;
    private Double cost;
    private String description;

    public MaintenanceRecord(LocalDate date, Double cost, String description) {
        this.date = date;
        this.cost = cost;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
