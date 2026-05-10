
package com.rendellvelasco.autoloanapp.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AmortizationEntry {
    private final IntegerProperty month = new SimpleIntegerProperty();
    private final DoubleProperty principal = new SimpleDoubleProperty();
    private final DoubleProperty interest = new SimpleDoubleProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();

    public int getMonth() {
        return month.get();
    }

    public IntegerProperty monthProperty() {
        return month;
    }

    public void setMonth(int month) {
        this.month.set(month);
    }

    public double getPrincipal() {
        return principal.get();
    }

    public DoubleProperty principalProperty() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal.set(principal);
    }

    public double getInterest() {
        return interest.get();
    }

    public DoubleProperty interestProperty() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest.set(interest);
    }

    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }
}
