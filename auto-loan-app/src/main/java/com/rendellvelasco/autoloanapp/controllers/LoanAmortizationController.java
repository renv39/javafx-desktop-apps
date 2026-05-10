
package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.models.AmortizationEntry;
import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.services.AmortizationService;
import com.rendellvelasco.autoloanapp.state.LoanState;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class LoanAmortizationController {

    private final AmortizationService amortizationService;
    private final LoanState loanState;

    @Inject
    public LoanAmortizationController(AmortizationService amortizationService, LoanState loanState) {
        this.amortizationService = amortizationService;
        this.loanState = loanState;
    }

    @FXML private TableView<AmortizationEntry> amortizationTable;
    @FXML private TableColumn<AmortizationEntry, Integer> monthCol;
    @FXML private TableColumn<AmortizationEntry, Double> principalCol;
    @FXML private TableColumn<AmortizationEntry, Double> interestCol;
    @FXML private TableColumn<AmortizationEntry, Double> balanceCol;

    @FXML
    public void initialize() {
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        principalCol.setCellValueFactory(new PropertyValueFactory<>("principal"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void displayAmortization() {
        Loan loan = loanState.getSelectedLoan();
        if (loan == null) return;
        List<AmortizationEntry> schedule = amortizationService.generateSchedule(loan);
        amortizationTable.setItems(FXCollections.observableArrayList(schedule));
    }
}