
package com.rendellvelasco.autoloanapp.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient StringProperty name = new SimpleStringProperty();
    private transient StringProperty phone = new SimpleStringProperty();
    private transient StringProperty city = new SimpleStringProperty();
    private transient StringProperty province = new SimpleStringProperty();

    public Customer() {}

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
        out.writeObject(phone.get());
        out.writeObject(city.get());
        out.writeObject(province.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name     = new SimpleStringProperty((String) in.readObject());
        phone    = new SimpleStringProperty((String) in.readObject());
        city     = new SimpleStringProperty((String) in.readObject());
        province = new SimpleStringProperty((String) in.readObject());
    }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }

    public String getCity() { return city.get(); }
    public StringProperty cityProperty() { return city; }
    public void setCity(String city) { this.city.set(city); }

    public String getProvince() { return province.get(); }
    public StringProperty provinceProperty() { return province; }
    public void setProvince(String province) { this.province.set(province); }

    @Override
    public String toString() {
        return "Customer{name=" + name.get() + ", phone=" + phone.get() +
                ", city=" + city.get() + ", province=" + province.get() + '}';
    }
}