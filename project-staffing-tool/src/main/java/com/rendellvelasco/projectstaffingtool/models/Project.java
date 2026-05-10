
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Abstract base class for all project types.
 */
public abstract class Project {

    private final StringProperty id;
    private final StringProperty title;
    private final ObjectProperty<Status> status;
    private final ObservableList<Assignment> assignments;

    public Project(String id, String title, Status status) {
        this.id          = new SimpleStringProperty(id);
        this.title       = new SimpleStringProperty(title);
        this.status      = new SimpleObjectProperty<>(status);
        this.assignments = FXCollections.observableArrayList();
    }

    // --- id ---
    public String getId() { return id.get(); }
    public void setId(String value) { id.set(value); }
    public StringProperty idProperty() { return id; }

    // --- title ---
    public String getTitle() { return title.get(); }
    public void setTitle(String value) { title.set(value); }
    public StringProperty titleProperty() { return title; }

    // --- status ---
    public Status getStatus() { return status.get(); }
    public void setStatus(Status value) { status.set(value); }
    public ObjectProperty<Status> statusProperty() { return status; }

    // --- assignments ---
    public ObservableList<Assignment> getAssignments() { return assignments; }

    /**
     * Computes the total cost of all assignments on this project.
     * Subclasses may override to apply markups or caps.
     */
    public double getProfitMargin() {
        return assignments.stream()
                .mapToDouble(a -> a.getCost().get())
                .sum();
    }

    @Override
    public String toString() { return getTitle(); }
}
