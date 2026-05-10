
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * A project billed on time-and-materials. Actual cost is tracked against an
 * hourly cap (burnRate = current total hours / hourlyCap).
 */
public class TimeMaterialProject extends Project {

    private final DoubleProperty hourlyCap;
    private final DoubleBinding  burnRate;

    public TimeMaterialProject(String id, String title, Status status, double hourlyCap) {
        super(id, title, status);
        this.hourlyCap = new SimpleDoubleProperty(hourlyCap);

        // burnRate = totalAllocatedHours / hourlyCap
        this.burnRate = new DoubleBinding() {
            {
                super.bind(TimeMaterialProject.this.hourlyCap,
                           TimeMaterialProject.this.getAssignments());
            }
            @Override
            protected double computeValue() {
                double totalHours = getAssignments().stream()
                        .mapToDouble(Assignment::getAllocatedHours)
                        .sum();
                double cap = TimeMaterialProject.this.hourlyCap.get();
                return cap == 0 ? 0 : totalHours / cap;
            }
        };
    }

    // --- hourlyCap ---
    public double getHourlyCap() { return hourlyCap.get(); }
    public void setHourlyCap(double value) { hourlyCap.set(value); }
    public DoubleProperty hourlyCapProperty() { return hourlyCap; }

    // --- burnRate ---
    public DoubleBinding burnRateProperty() { return burnRate; }
    public double getBurnRate() { return burnRate.get(); }
}
