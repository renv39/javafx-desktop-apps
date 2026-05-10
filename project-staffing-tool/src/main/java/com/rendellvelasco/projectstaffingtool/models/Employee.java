
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Abstract base class representing an employee in the system.
 */
public abstract class Employee {

    private final StringProperty id;
    private final StringProperty name;
    private final ObservableList<String> skills;

    public Employee(String id, String name, String email) {
        this.id     = new SimpleStringProperty(id);
        this.name   = new SimpleStringProperty(name);
        this.skills = FXCollections.observableArrayList();
    }

    // --- id ---
    public String getId() { return id.get(); }
    public void setId(String value) { id.set(value); }
    public StringProperty idProperty() { return id; }

    // --- name ---
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }


    // --- skills ---
    public ObservableList<String> getSkills() { return skills; }

    /**
     * Returns the hourly cost basis used to compute assignment cost.
     * Subclasses define how this is calculated.
     */
    public abstract double getBaseCost();

    @Override
    public String toString() { return getName(); }
}
