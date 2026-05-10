
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * An external contractor whose cost is a direct hourly rate.
 */
public class ExternalConsultant extends Employee {

    private final DoubleProperty hourlyRate;
    private final StringProperty agencyName;

    public ExternalConsultant(String id, String name, String email,
                              double hourlyRate, String agencyName) {
        super(id, name, email);
        this.hourlyRate = new SimpleDoubleProperty(hourlyRate);
        this.agencyName = new SimpleStringProperty(agencyName);
    }

    // --- hourlyRate ---
    public double getHourlyRate() { return hourlyRate.get(); }
    public void setHourlyRate(double value) { hourlyRate.set(value); }
    public DoubleProperty hourlyRateProperty() { return hourlyRate; }

    // --- agencyName ---
    public String getAgencyName() { return agencyName.get(); }
    public void setAgencyName(String value) { agencyName.set(value); }
    public StringProperty agencyNameProperty() { return agencyName; }

    @Override
    public double getBaseCost() { return getHourlyRate(); }
}
