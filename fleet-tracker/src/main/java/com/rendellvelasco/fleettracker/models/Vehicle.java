
package com.rendellvelasco.fleettracker.models;

public class Vehicle {
    private String make;
    private String model;
    private Integer year;
    private String type;

    public Vehicle(String model, String make, Integer year, String type) {
        this.model = model;
        this.make = make;
        this.year = year;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.make + " " + this.model + " (" + this.year + ") - " + this.type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
