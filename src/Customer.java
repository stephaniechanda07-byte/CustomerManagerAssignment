package com.example.customermanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final StringProperty name;
    private final StringProperty province;

    public Customer(String name, String province) {
        this.name = new SimpleStringProperty(name == null ? "" : name);
        this.province = new SimpleStringProperty(province == null ? "" : province);
    }

    // Property accessors (used for TableView binding)
    public StringProperty nameProperty() { return name; }
    public StringProperty provinceProperty() { return province; }

    // Standard getters and setters
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name == null ? "" : name); }

    public String getProvince() { return province.get(); }
    public void setProvince(String province) { this.province.set(province == null ? "" : province); }

    @Override
    public String toString() {
        return getName() + " (" + getProvince() + ")";
    }
}