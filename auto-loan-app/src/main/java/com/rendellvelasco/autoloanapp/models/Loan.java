
package com.rendellvelasco.autoloanapp.models;

import javafx.beans.property.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient DoubleProperty downPayment                    = new SimpleDoubleProperty();
    private transient DoubleProperty interestRate                   = new SimpleDoubleProperty();
    private transient IntegerProperty duration                      = new SimpleIntegerProperty();
    private transient ObjectProperty<PaymentFrequency> frequency    = new SimpleObjectProperty<>();
    private transient ObjectProperty<Customer> customer             = new SimpleObjectProperty<>();
    private transient ObjectProperty<Vehicle> vehicle               = new SimpleObjectProperty<>();

    public Loan() {}

    public Loan(Customer customer) {
        this.customer.set(customer);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(downPayment.get());
        out.writeDouble(interestRate.get());
        out.writeInt(duration.get());
        out.writeObject(frequency.get());   // enum - serializable
        out.writeObject(customer.get());    // Customer - serializable
        out.writeObject(vehicle.get());     // Vehicle - serializable
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        downPayment  = new SimpleDoubleProperty(in.readDouble());
        interestRate = new SimpleDoubleProperty(in.readDouble());
        duration     = new SimpleIntegerProperty(in.readInt());
        frequency    = new SimpleObjectProperty<>((PaymentFrequency) in.readObject());
        customer     = new SimpleObjectProperty<>((Customer) in.readObject());
        vehicle      = new SimpleObjectProperty<>((Vehicle) in.readObject());
    }

    public double getDownPayment() { return downPayment.get(); }
    public DoubleProperty downPaymentProperty() { return downPayment; }
    public void setDownPayment(double v) { downPayment.set(v); }

    public double getInterestRate() { return interestRate.get(); }
    public DoubleProperty interestRateProperty() { return interestRate; }
    public void setInterestRate(double v) { interestRate.set(v); }

    public int getDuration() { return duration.get(); }
    public IntegerProperty durationProperty() { return duration; }
    public void setDuration(int v) { duration.set(v); }

    public PaymentFrequency getPaymentFrequency() { return frequency.get(); }
    public ObjectProperty<PaymentFrequency> paymentFrequencyProperty() { return frequency; }
    public void setPaymentFrequency(PaymentFrequency v) { frequency.set(v); }

    public Customer getCustomer() { return customer.get(); }
    public ObjectProperty<Customer> customerProperty() { return customer; }
    public void setCustomer(Customer v) { customer.set(v); }

    public Vehicle getVehicle() { return vehicle.get(); }
    public ObjectProperty<Vehicle> vehicleProperty() { return vehicle; }
    public void setVehicle(Vehicle v) { vehicle.set(v); }

    @Override
    public String toString() {
        return "Loan{downPayment=" + downPayment.get() +
                ", interestRate=" + interestRate.get() +
                ", duration=" + duration.get() +
                ", frequency=" + frequency.get() +
                ", customer=" + customer.get() +
                ", vehicle=" + vehicle.get() + '}';
    }
}