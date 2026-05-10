


package com.rendellvelasco.autoloanapp.controllers;


import com.rendellvelasco.autoloanapp.models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CustomerController {
    private Customer customer = new Customer();

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private ChoiceBox<String> provinceChoiceBox;

    @FXML
    public void initialize() {
        // Bind text fields to Customer properties
        nameTextField.requestFocus();
        nameTextField.textProperty().bindBidirectional(customer.nameProperty());
        phoneTextField.textProperty().bindBidirectional(customer.phoneProperty());
        cityTextField.textProperty().bindBidirectional(customer.cityProperty());

        // Bind ChoiceBox selection to Customer province property
        provinceChoiceBox.valueProperty().bindBidirectional(customer.provinceProperty());

        // Populate ChoiceBox with provinces (example)
        provinceChoiceBox.getItems().addAll(
                "Ontario", "Quebec", "British Columbia", "Alberta", "Manitoba",
                "Saskatchewan", "Nova Scotia", "New Brunswick", "Newfoundland and Labrador",
                "Prince Edward Island"
        );

    }

    public Customer getCustomer() {
        Customer copy = new Customer();
        copy.setName(customer.getName());
        copy.setPhone(customer.getPhone());
        copy.setCity(customer.getCity());
        copy.setProvince(customer.getProvince());
        return copy;
    }

    public boolean validateCustomer() {
        if (customer.getName().isEmpty()
                || customer.getPhone().isEmpty()
                || customer.getCity().isEmpty()
                || customer.getProvince() == null
                || customer.getProvince().isEmpty()) {
            throw new IllegalStateException("All customer fields must be filled.");
        }

        if (!customer.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        return true;
    }

    public void resetForm() {
        // Clear customer properties
        customer.setName("");
        customer.setPhone("");
        customer.setCity("");
        customer.setProvince("");

        // Optional: Reset UI focus
        nameTextField.requestFocus();
    }

    public void setCustomer(Customer newCustomer) {

        // Unbind old bindings first
        nameTextField.textProperty().unbindBidirectional(customer.nameProperty());
        phoneTextField.textProperty().unbindBidirectional(customer.phoneProperty());
        cityTextField.textProperty().unbindBidirectional(customer.cityProperty());
        provinceChoiceBox.valueProperty().unbindBidirectional(customer.provinceProperty());

        // Replace customer reference
        this.customer = newCustomer;

        // Bind to the new customer
        nameTextField.textProperty().bindBidirectional(customer.nameProperty());
        phoneTextField.textProperty().bindBidirectional(customer.phoneProperty());
        cityTextField.textProperty().bindBidirectional(customer.cityProperty());
        provinceChoiceBox.valueProperty().bindBidirectional(customer.provinceProperty());
    }
}
