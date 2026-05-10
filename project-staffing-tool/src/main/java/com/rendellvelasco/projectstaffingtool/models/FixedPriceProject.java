
package com.rendellvelasco.projectstaffingtool.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * A project billed at a fixed price. Profit margin is cost * (1 + clientMarkup).
 */
public class FixedPriceProject extends Project {

    private final DoubleProperty maxBudget;
    private final DoubleProperty clientMarkup;

    public FixedPriceProject(String id, String title, Status status,
                             double maxBudget, double clientMarkup) {
        super(id, title, status);
        this.maxBudget    = new SimpleDoubleProperty(maxBudget);
        this.clientMarkup = new SimpleDoubleProperty(clientMarkup);
    }

    // --- maxBudget ---
    public double getMaxBudget() { return maxBudget.get(); }
    public void setMaxBudget(double value) { maxBudget.set(value); }
    public DoubleProperty maxBudgetProperty() { return maxBudget; }

    // --- clientMarkup ---
    public double getClientMarkup() { return clientMarkup.get(); }
    public void setClientMarkup(double value) { clientMarkup.set(value); }
    public DoubleProperty clientMarkupProperty() { return clientMarkup; }

    /** Total cost with markup applied. */
    @Override
    public double getProfitMargin() {
        return super.getProfitMargin() * (1.0 + getClientMarkup());
    }
}
