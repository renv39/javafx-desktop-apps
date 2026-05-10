
package com.rendellvelasco.autoloanapp.models;

import javafx.beans.property.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient ObjectProperty<VehicleType> type = new SimpleObjectProperty<>();
    private transient ObjectProperty<VehicleAge> age   = new SimpleObjectProperty<>();
    private transient DoubleProperty price             = new SimpleDoubleProperty();

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(type.get());   // enums are serializable
        out.writeObject(age.get());
        out.writeDouble(price.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        type  = new SimpleObjectProperty<>((VehicleType) in.readObject());
        age   = new SimpleObjectProperty<>((VehicleAge) in.readObject());
        price = new SimpleDoubleProperty(in.readDouble());
    }

    public VehicleType getType() { return type.get(); }
    public ObjectProperty<VehicleType> typeProperty() { return type; }
    public void setType(VehicleType type) { this.type.set(type); }

    public VehicleAge getAge() { return age.get(); }
    public ObjectProperty<VehicleAge> ageProperty() { return age; }
    public void setAge(VehicleAge age) { this.age.set(age); }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }
    public void setPrice(double price) { this.price.set(price); }

    @Override
    public String toString() {
        return "Vehicle{type=" + type.get() + ", age=" + age.get() + ", price=" + price.get() + '}';
    }
}