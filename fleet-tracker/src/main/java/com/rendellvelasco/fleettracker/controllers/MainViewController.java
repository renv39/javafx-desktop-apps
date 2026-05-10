
package com.rendellvelasco.fleettracker.controllers;

import com.rendellvelasco.fleettracker.models.MaintenanceRecord;
import com.rendellvelasco.fleettracker.models.UsageLog;
import com.rendellvelasco.fleettracker.models.Vehicle;
import com.rendellvelasco.fleettracker.services.MaintenanceService;
import com.rendellvelasco.fleettracker.services.UsageService;
import com.rendellvelasco.fleettracker.services.VehicleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {
    private VehicleService vService;
    private UsageService uService;
    private MaintenanceService mService;

    public void initData() {
        this.vService = new VehicleService();
        this.uService = new UsageService();
        this.mService = new MaintenanceService();
    }

    // Vehicle input fields
    @FXML
    private TextField makeTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField typeTextField;

    // Usage Log input fields
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField distanceTextField;

    // Maintenance Record input fields
    @FXML
    private DatePicker maintenanceDatePicker;

    @FXML
    private TextField costTextField;

    @FXML
    private TextArea descriptionTextArea;

    // Save buttons
    @FXML
    private Button saveVehicleButton;

    @FXML
    private Button saveUsageLogButton;

    @FXML
    private Button saveMaintenanceRecordButton;

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML
    private Button showSummaryButton;


    @FXML
    private void onSaveVehicle() {
        // Create new vehicle from inputs
        Vehicle vehicle = new Vehicle(
                modelTextField.getText(),
                makeTextField.getText(),
                Integer.parseInt(yearTextField.getText()),
                typeTextField.getText()
        );

        // Add vehicle to the service
        vService.addVehicle(vehicle);

        vehicleComboBox.setDisable(false);

        // Populate ComboBox with all vehicles
        populateVehicleComboBox();

        // Clear vehicle input fields (optional)
        makeTextField.clear();
        modelTextField.clear();
        yearTextField.clear();
        typeTextField.clear();

        // Disable vehicle save button until next valid input
        saveVehicleButton.setDisable(true);
        showSummaryButton.setDisable(false);
    }

    @FXML
    private void onSaveUsage() {
        Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
        if (selectedVehicle == null) return;

        UsageLog usage = new UsageLog(
                startDatePicker.getValue(),
                endDatePicker.getValue(),
                Double.parseDouble(distanceTextField.getText())
        );

        uService.addLog(selectedVehicle, usage);

        // Clear inputs
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        distanceTextField.clear();

        saveUsageLogButton.setDisable(true);
    }

    @FXML
    private void onSaveMaintenance() {
        Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
        if (selectedVehicle == null) return;

        MaintenanceRecord maintenance = new MaintenanceRecord(
                maintenanceDatePicker.getValue(),
                Double.parseDouble(costTextField.getText()),
                descriptionTextArea.getText()
        );

        mService.addRecord(selectedVehicle, maintenance);

        // Clear inputs
        maintenanceDatePicker.setValue(null);
        costTextField.clear();
        descriptionTextArea.clear();

        saveMaintenanceRecordButton.setDisable(true);
    }

    @FXML
    private void onOpenSummary() {
        try {
            // Load the summary FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rendellvelasco/fleettracker/views/summary-view.fxml"));
            Parent root = loader.load();

            // Get the controller
            SummaryController controller = loader.getController();

            // Pass the existing service instances
            controller.initData(vService, uService, mService);

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Summary");
            stage.setScene(new Scene(root));

            // Make it modal (blocks the main window until closed)
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVehicleCombo() {
        Vehicle selected = vehicleComboBox.getSelectionModel().getSelectedItem();

        // Only enable inputs if a vehicle is actually selected
        boolean vehicleSelected = selected != null;

        // Enable/disable Usage inputs
        startDatePicker.setDisable(!vehicleSelected);
        endDatePicker.setDisable(!vehicleSelected);
        distanceTextField.setDisable(!vehicleSelected);

        // Enable/disable Maintenance inputs
        maintenanceDatePicker.setDisable(!vehicleSelected);
        costTextField.setDisable(!vehicleSelected);
        descriptionTextArea.setDisable(!vehicleSelected);

        // Optionally: disable the Save buttons until all fields are valid
        saveUsageLogButton.setDisable(true);
        saveMaintenanceRecordButton.setDisable(true);
    }

    @FXML
    private void initialize() {

        // Vehicle inputs
        saveVehicleButton.setDisable(true);

        makeTextField.textProperty().addListener((obs, o, n) -> validateVehicleInputs());
        modelTextField.textProperty().addListener((obs, o, n) -> validateVehicleInputs());
        yearTextField.textProperty().addListener((obs, o, n) -> validateVehicleInputs());
        typeTextField.textProperty().addListener((obs, o, n) -> validateVehicleInputs());

        vehicleComboBox.setDisable(true);

        // --- Disable usage inputs initially ---
        startDatePicker.setDisable(true);
        endDatePicker.setDisable(true);
        distanceTextField.setDisable(true);
        saveUsageLogButton.setDisable(true);

        // Usage Log inputs
        saveUsageLogButton.setDisable(true);

        startDatePicker.valueProperty().addListener((obs, o, n) -> validateUsageInputs());
        endDatePicker.valueProperty().addListener((obs, o, n) -> validateUsageInputs());
        distanceTextField.textProperty().addListener((obs, o, n) -> validateUsageInputs());

        // --- Disable maintenance inputs initially ---
        maintenanceDatePicker.setDisable(true);
        costTextField.setDisable(true);
        descriptionTextArea.setDisable(true);
        saveMaintenanceRecordButton.setDisable(true);

        // Maintenance Record inputs
        saveMaintenanceRecordButton.setDisable(true);

        maintenanceDatePicker.valueProperty().addListener((obs, o, n) -> validateMaintenanceInputs());
        costTextField.textProperty().addListener((obs, o, n) -> validateMaintenanceInputs());
        descriptionTextArea.textProperty().addListener((obs, o, n) -> validateMaintenanceInputs());

        showSummaryButton.setDisable(true);

    }

    private boolean isNotEmpty(TextField field) {
        return field.getText() != null && !field.getText().trim().isEmpty();
    }

    private void validateVehicleInputs() {
        boolean allFilled =
                isNotEmpty(makeTextField) &&
                        isNotEmpty(modelTextField) &&
                        isNotEmpty(yearTextField) &&
                        isNotEmpty(typeTextField);

        saveVehicleButton.setDisable(!allFilled);
    }


    private void validateUsageInputs() {
        boolean allFilled =
                startDatePicker.getValue() != null &&
                        endDatePicker.getValue() != null &&
                        isNotEmpty(distanceTextField);

        saveUsageLogButton.setDisable(!allFilled);
    }

    private void validateMaintenanceInputs() {
        boolean allFilled =
                maintenanceDatePicker.getValue() != null &&
                        isNotEmpty(costTextField) &&
                        !descriptionTextArea.getText().trim().isEmpty();

        saveMaintenanceRecordButton.setDisable(!allFilled);
    }


    private void populateVehicleComboBox() {
        vehicleComboBox.getItems().clear();
        vehicleComboBox.getItems().addAll(vService.getAllVehicles());
    }


}
