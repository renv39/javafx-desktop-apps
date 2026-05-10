
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * A full-time internal employee whose cost is derived from annual salary.
 */
public class InternalStaff extends Employee {

    private final DoubleProperty annualSalary;

    public InternalStaff(String id, String name, String email, double annualSalary) {
        super(id, name, email);
        this.annualSalary = new SimpleDoubleProperty(annualSalary);
    }

    // --- annualSalary ---
    public double getAnnualSalary() { return annualSalary.get(); }
    public void setAnnualSalary(double value) { annualSalary.set(value); }
    public DoubleProperty annualSalaryProperty() { return annualSalary; }

    /**
     * Hourly rate = annual salary / (52 weeks * 40 hrs).
     */
    @Override
    public double getBaseCost() {
        return getAnnualSalary() / (52.0 * 40.0);
    }
}
