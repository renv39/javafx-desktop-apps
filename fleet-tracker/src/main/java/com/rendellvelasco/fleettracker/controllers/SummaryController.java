
package com.rendellvelasco.fleettracker.controllers;

import com.rendellvelasco.fleettracker.models.MaintenanceRecord;
import com.rendellvelasco.fleettracker.models.UsageLog;
import com.rendellvelasco.fleettracker.models.Vehicle;
import com.rendellvelasco.fleettracker.services.MaintenanceService;
import com.rendellvelasco.fleettracker.services.UsageService;
import com.rendellvelasco.fleettracker.services.VehicleService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryController {
    private VehicleService vService;
    private UsageService uService;
    private MaintenanceService mService;


    // For TableView mapping and display purposes
    private final List<UsageLog> allUsageLogs = new ArrayList<>();
    private final Map<UsageLog, Vehicle> usageMap = new HashMap<>();

    private final List<MaintenanceRecord> allMaintenanceRecords = new ArrayList<>();
    private final Map<MaintenanceRecord, Vehicle> maintenanceMap = new HashMap<>();

    public void buildDataMaps() {
        allUsageLogs.clear();
        usageMap.clear();
        allMaintenanceRecords.clear();
        maintenanceMap.clear();

        for (Vehicle v : vService.getAllVehicles()) {
            // Map usage logs
            for (UsageLog log : uService.getLogs(v)) {
                allUsageLogs.add(log);
                usageMap.put(log, v);
            }
            // Map maintenance records
            for (MaintenanceRecord rec : mService.getRecords(v)) {
                allMaintenanceRecords.add(rec);
                maintenanceMap.put(rec, v);
            }
        }
    }


    @FXML
    private ChoiceBox<String> summaryChoiceBox;
    @FXML
    private Label summaryLabel;
    @FXML
    private Button summaryButton;
    @FXML
    private ListView<String> summaryListView;

    @FXML
    private TableView<Vehicle> vehicleTableView;
    @FXML
    private TableColumn<Vehicle, String> makeColumn;
    @FXML
    private TableColumn<Vehicle, String> modelColumn;
    @FXML
    private TableColumn<Vehicle, Integer> yearColumn;
    @FXML
    private TableColumn<Vehicle, String> typeColumn;

    @FXML
    private TableView<UsageLog> usageTableView;
    @FXML
    private TableColumn<Vehicle, String> usageVehicleColumn;
    @FXML
    private TableColumn<UsageLog, String> startDateColumn;
    @FXML
    private TableColumn<UsageLog, String> endDateColumn;
    @FXML
    private TableColumn<UsageLog, Double> distanceColumn;

    @FXML
    private TableView<MaintenanceRecord> maintenanceTableView;
    @FXML
    private TableColumn<Vehicle, String> maintenanceVehicleColumn;
    @FXML
    private TableColumn<MaintenanceRecord, String> maintenanceDateColumn;
    @FXML
    private TableColumn<MaintenanceRecord, Double> costColumn;
    @FXML
    private TableColumn<MaintenanceRecord, String> descriptionColumn;

    // ObservableList bound to ListView
    private final ObservableList<String> summaryItems = FXCollections.observableArrayList();

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initData(VehicleService vService, UsageService uService, MaintenanceService mService) {
        this.vService = vService;
        this.mService = mService;
        this.uService = uService;
        buildDataMaps();
    }

    @FXML
    public void initialize() {
        // ------------------- Vehicles -------------------
        makeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMake()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
        yearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

        // ------------------- Usage -------------------
        usageVehicleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(usageMap.get(cellData.getValue()).toString()));
        startDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().format(dtf)));
        endDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().format(dtf)));
        distanceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDistance()).asObject());

        // ------------------- Maintenance -------------------
        maintenanceVehicleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(maintenanceMap.get(cellData.getValue()).toString()));
        maintenanceDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().format(dtf)));
        costColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCost()).asObject());
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        summaryLabel.setText("Summary");
        summaryChoiceBox.getItems().addAll("Vehicles", "Maintenance", "Usage");

        summaryChoiceBox.setOnAction(e -> {
            String selected = summaryChoiceBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                summaryLabel.setText(selected); // update label
                showSelectedData();             // update table
            }
        });

        vehicleTableView.setVisible(false);
        usageTableView.setVisible(false);
        maintenanceTableView.setVisible(false);

        summaryButton.setOnAction(e -> {
            Stage currentStage = (Stage) summaryButton.getScene().getWindow();
            currentStage.close();
        });
    }

    private void showSelectedData() {
        String selectedType = summaryChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedType == null) return;

        // Hide all first
        vehicleTableView.setVisible(false);
        usageTableView.setVisible(false);
        maintenanceTableView.setVisible(false);

        switch (selectedType) {
            case "Vehicles":
                vehicleTableView.setItems(FXCollections.observableArrayList(vService.getAllVehicles()));
                vehicleTableView.setVisible(true);
                break;

            case "Usage":
                usageTableView.setItems(FXCollections.observableArrayList(allUsageLogs));
                usageTableView.setVisible(true);
                break;

            case "Maintenance":
                maintenanceTableView.setItems(FXCollections.observableArrayList(allMaintenanceRecords));
                maintenanceTableView.setVisible(true);
                break;

        }
    }



}
