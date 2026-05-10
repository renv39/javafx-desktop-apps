
package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.App;
import com.rendellvelasco.autoloanapp.models.*;
import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.models.PaymentFrequency;
import com.rendellvelasco.autoloanapp.models.Vehicle;
import com.rendellvelasco.autoloanapp.services.LoanService;
import com.rendellvelasco.autoloanapp.state.LoanState;
import com.rendellvelasco.autoloanapp.utilities.LoanCalculation;
import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

public class LoanController {

    private final App app;
    private final LoanService loanService;
    private final LoanCalculation calculator;
    private final LoanState loanState;

    private Loan loan = new Loan();

    @Inject
    public LoanController(App app, LoanService loanService,
                          LoanCalculation calculator, LoanState loanState) {
        this.app = app;
        this.loanService = loanService;
        this.calculator = calculator;
        this.loanState = loanState;
    }

    @FXML private CustomerController customerComponentController;
    @FXML private VehicleController vehicleComponentController;
    @FXML private TextField downPaymentField;
    @FXML private TextField estimatedPaymentField;
    @FXML private RadioButton rate099Radio;
    @FXML private RadioButton rate199Radio;
    @FXML private RadioButton rate299Radio;
    @FXML private RadioButton rateOtherRadio;
    @FXML private TextField otherRateField;
    @FXML private Slider durationSlider;
    @FXML private RadioButton weeklyRadio;
    @FXML private RadioButton biWeeklyRadio;
    @FXML private RadioButton monthlyRadio;
    @FXML private ToggleGroup interestToggleGroup;
    @FXML private ToggleGroup frequencyToggleGroup;

    @FXML
    public void initialize() {
        setupInterestRate();
        setupPaymentFrequency();
        setupDuration();
        setupDownPaymentBinding();
        // Listen to LoanState — whenever selected loan changes, fill the form
        loanState.selectedLoanProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadLoan(newVal);
            }
        });
    }

    private void setupInterestRate() {
        rate099Radio.setUserData(0.99);
        rate199Radio.setUserData(1.99);
        rate299Radio.setUserData(2.99);
        otherRateField.setDisable(true);

        interestToggleGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            if (selected == null) return;
            if (selected == rateOtherRadio) {
                otherRateField.setDisable(false);
            } else {
                otherRateField.setDisable(true);
                loan.setInterestRate((double) selected.getUserData());
            }
        });

        otherRateField.textProperty().addListener((obs, old, value) -> {
            if (rateOtherRadio.isSelected()) {
                try {
                    loan.setInterestRate(Double.parseDouble(value));
                } catch (NumberFormatException ignored) {}
            }
        });
    }

    private void setupPaymentFrequency() {
        weeklyRadio.setUserData(PaymentFrequency.WEEKLY);
        biWeeklyRadio.setUserData(PaymentFrequency.BI_WEEKLY);
        monthlyRadio.setUserData(PaymentFrequency.MONTHLY);

        frequencyToggleGroup.selectedToggleProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                loan.setPaymentFrequency((PaymentFrequency) selected.getUserData());
            }
        });
    }

    private void setupDuration() {
        durationSlider.valueProperty().addListener((obs, old, value) ->
                loan.setDuration(value.intValue()));
        loan.setDuration((int) durationSlider.getValue());
    }

    private void setupDownPaymentBinding() {
        Bindings.bindBidirectional(
                downPaymentField.textProperty(),
                loan.downPaymentProperty(),
                new NumberStringConverter()
        );
    }

    // =========================================
    // STORAGE ACTIONS
    // =========================================

    @FXML
    public void handleWriteToDB() {
        try {
            Loan snapshot = buildLoanSnapshot();
            loanService.saveToDatabase(snapshot);
            showInfo("Saved to Database", "Loan saved to database.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            showError("Validation Error", e.getMessage());
        }
    }

    @FXML
    public void handleReadFromDB() {
        app.showSavedLoansFromDB();
    }

    @FXML
    public void handleWriteToFile() {
        try {
            Loan snapshot = buildLoanSnapshot();
            loanService.saveToFile(snapshot);
            showInfo("Saved to File", "Loan saved to file.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            showError("Validation Error", e.getMessage());
        }
    }

    @FXML
    public void handleReadFromFile() {
        app.showSavedLoansFromFile();
    }

    @FXML
    public void handleShowSavedLoans() {
        app.showSavedLoanList();
    }

    // =========================================
    // EXISTING ACTIONS
    // =========================================

    @FXML
    public void handleCalculate() {
        if (!vehicleComponentController.validateVehicle()) return;
        Vehicle vehicle = vehicleComponentController.getVehicle();
        loan.setVehicle(vehicle);
        double payment = calculator.calculatePayment(loan, vehicle);
        estimatedPaymentField.setText(String.format("$%.2f", payment));
    }

    @FXML
    public void handleClear() {
        loan.setDownPayment(0);
        loan.setInterestRate(0);
        loan.setDuration(0);
        loan.setPaymentFrequency(null);
        loan.setVehicle(null);
        loan.setCustomer(null);

        downPaymentField.clear();
        otherRateField.clear();
        estimatedPaymentField.clear();
        interestToggleGroup.selectToggle(null);
        frequencyToggleGroup.selectToggle(null);
        durationSlider.setValue(0);

        customerComponentController.resetForm();
        vehicleComponentController.resetForm();
    }

    @FXML
    public void handleShowAmortization() {
        // Push current form loan into shared state for amortization
        loanState.setSelectedLoan(buildCurrentLoan());
        app.showAmortizationSchedule();
    }

    // Fills form when a loan is selected from saved list
    public void loadLoan(Loan selected) {
        if (selected == null) return;

        loan.setDownPayment(selected.getDownPayment());
        loan.setInterestRate(selected.getInterestRate());
        loan.setDuration(selected.getDuration());
        loan.setPaymentFrequency(selected.getPaymentFrequency());
        loan.setCustomer(selected.getCustomer());
        loan.setVehicle(selected.getVehicle());

        durationSlider.setValue(selected.getDuration());

        double rate = selected.getInterestRate();
        if (rate == 0.99)      interestToggleGroup.selectToggle(rate099Radio);
        else if (rate == 1.99) interestToggleGroup.selectToggle(rate199Radio);
        else if (rate == 2.99) interestToggleGroup.selectToggle(rate299Radio);
        else {
            interestToggleGroup.selectToggle(rateOtherRadio);
            otherRateField.setText(String.valueOf(rate));
        }

        if (selected.getPaymentFrequency() != null) {
            switch (selected.getPaymentFrequency()) {
                case WEEKLY    -> frequencyToggleGroup.selectToggle(weeklyRadio);
                case BI_WEEKLY -> frequencyToggleGroup.selectToggle(biWeeklyRadio);
                case MONTHLY   -> frequencyToggleGroup.selectToggle(monthlyRadio);
            }
        }

        customerComponentController.setCustomer(selected.getCustomer());
        vehicleComponentController.setVehicle(selected.getVehicle());
    }

    // =========================================
    // HELPERS
    // =========================================

    private Loan buildLoanSnapshot() {
        customerComponentController.validateCustomer();
        vehicleComponentController.validateVehicle();
        return buildCurrentLoan();
    }

    private Loan buildCurrentLoan() {
        Loan snapshot = new Loan();
        snapshot.setDownPayment(loan.getDownPayment());
        snapshot.setInterestRate(loan.getInterestRate());
        snapshot.setDuration(loan.getDuration());
        snapshot.setPaymentFrequency(loan.getPaymentFrequency());
        snapshot.setCustomer(customerComponentController.getCustomer());
        snapshot.setVehicle(vehicleComponentController.getVehicle());
        return snapshot;
    }

    private void showInfo(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}