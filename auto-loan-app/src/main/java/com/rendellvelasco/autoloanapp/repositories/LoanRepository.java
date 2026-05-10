

package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LoanRepository {
    private ObservableList<Loan> loans = FXCollections.observableArrayList();

    public void saveLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
