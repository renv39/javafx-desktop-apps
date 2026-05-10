
package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.services.LoanService;
import com.rendellvelasco.autoloanapp.state.LoanState;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.List;

public class LoanSavedListController {

    private final LoanService loanService;
    private final LoanState loanState;

    @Inject
    public LoanSavedListController(LoanService loanService, LoanState loanState) {
        this.loanService = loanService;
        this.loanState = loanState;
    }

    @FXML
    private ListView<Loan> loanListView;

    @FXML
    public void initialize() {
        loanListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        // Push selected loan into shared state
                        loanState.setSelectedLoan(newVal);
                    }
                });
    }

    public void loadFromDB() {
        List<Loan> loans = loanService.loadFromDatabase();
        loanListView.setItems(FXCollections.observableArrayList(loans));
    }

    public void loadFromFile() {
        List<Loan> loans = loanService.loadFromFile();
        loanListView.setItems(FXCollections.observableArrayList(loans));
    }
}