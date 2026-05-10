


package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.models.Vehicle;
import com.rendellvelasco.autoloanapp.models.VehicleAge;
import com.rendellvelasco.autoloanapp.models.VehicleType;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class VehicleController {
    private Vehicle vehicle = new Vehicle();

    // --- Vehicle Type (CAR / TRUCK / FAMILY_VAN) ---
    @FXML
    private RadioButton carRadio;

    @FXML
    private RadioButton truckRadio;

    @FXML
    private RadioButton familyVanRadio;

    @FXML
    private ToggleGroup typeToggleGroup;

    // --- Vehicle Age (NEW / USED) ---
    @FXML
    private RadioButton newRadio;

    @FXML
    private RadioButton usedRadio;

    @FXML
    private ToggleGroup ageToggleGroup;

    // --- Price ---
    @FXML
    private TextField priceTextField;

    @FXML
    public void initialize() {

        // -------- TYPE BINDING --------
        typeToggleGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            if (selected == carRadio) {
                vehicle.setType(VehicleType.CAR);
            } else if (selected == truckRadio) {
                vehicle.setType(VehicleType.TRUCK);
            } else if (selected == familyVanRadio) {
                vehicle.setType(VehicleType.FAMILY_VAN);
            }
        });

        // -------- AGE BINDING --------
        ageToggleGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            if (selected == newRadio) {
                vehicle.setAge(VehicleAge.NEW);
            } else if (selected == usedRadio) {
                vehicle.setAge(VehicleAge.USED);
            }
        });

        // -------- PRICE BINDING --------
        priceTextField.textProperty().addListener((obs, old, value) -> {
            try {
                if (!value.isEmpty()) {
                    vehicle.setPrice(Double.parseDouble(value));
                }
            } catch (NumberFormatException e) {
                vehicle.setPrice(0);
            }
        });
    }


    public Vehicle getVehicle() {
        Vehicle copy = new Vehicle();
        copy.setType(vehicle.getType());
        copy.setAge(vehicle.getAge());
        copy.setPrice(vehicle.getPrice());
        return copy;
    }

    public boolean validateVehicle() {

        if (vehicle.getType() == null) {
            throw new IllegalStateException("Please select a vehicle type.");
        }

        if (vehicle.getAge() == null) {
            throw new IllegalStateException("Please select whether the vehicle is new or used.");
        }

        if (vehicle.getPrice() <= 0) {
            throw new IllegalArgumentException("Vehicle price must be greater than 0.");
        }

        return true;
    }

    public void resetForm() {

        typeToggleGroup.selectToggle(null);
        ageToggleGroup.selectToggle(null);
        priceTextField.clear();

        vehicle.setType(null);
        vehicle.setAge(null);
        vehicle.setPrice(0);
    }

    public void setVehicle(Vehicle newVehicle) {
        this.vehicle = newVehicle;

        // Sync UI to reflect the loaded vehicle
        priceTextField.setText(newVehicle.getPrice() > 0 ? String.valueOf(newVehicle.getPrice()) : "");

        if (newVehicle.getType() != null) {
            switch (newVehicle.getType()) {
                case CAR        -> typeToggleGroup.selectToggle(carRadio);
                case TRUCK      -> typeToggleGroup.selectToggle(truckRadio);
                case FAMILY_VAN -> typeToggleGroup.selectToggle(familyVanRadio);
            }
        } else {
            typeToggleGroup.selectToggle(null);
        }

        if (newVehicle.getAge() != null) {
            switch (newVehicle.getAge()) {
                case NEW  -> ageToggleGroup.selectToggle(newRadio);
                case USED -> ageToggleGroup.selectToggle(usedRadio);
            }
        } else {
            ageToggleGroup.selectToggle(null);
        }
    }
}
