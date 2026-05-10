
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Association class linking a Project to an Employee with a specific role
 * and allocated hours. Cost is reactively computed via a DoubleBinding.
 */
public class Assignment {

    private final StringProperty role;
    private final DoubleProperty allocatedHours;
    private final Employee employee;
    private final DoubleBinding cost;

    public Assignment(String role, double allocatedHours, Employee employee) {
        this.role           = new SimpleStringProperty(role);
        this.allocatedHours = new SimpleDoubleProperty(allocatedHours);
        this.employee       = employee;

        // Reactive binding: cost updates whenever allocatedHours changes
        this.cost = new DoubleBinding() {
            {
                super.bind(Assignment.this.allocatedHours);
            }
            @Override
            protected double computeValue() {
                return Assignment.this.allocatedHours.get() * employee.getBaseCost();
            }
        };
    }

    // --- role ---
    public String getRole() { return role.get(); }
    public void setRole(String value) { role.set(value); }
    public StringProperty roleProperty() { return role; }

    // --- allocatedHours ---
    public double getAllocatedHours() { return allocatedHours.get(); }
    public void setAllocatedHours(double value) { allocatedHours.set(value); }
    public DoubleProperty allocatedHoursProperty() { return allocatedHours; }

    // --- employee (read-only reference) ---
    public Employee getEmployee() { return employee; }

    // --- cost (computed binding) ---
    public DoubleBinding getCost() { return cost; }

    /** Convenience accessor for TableView column binding. */
    public DoubleProperty hoursProperty() { return allocatedHours; }
}
