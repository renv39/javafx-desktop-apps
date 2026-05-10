
package com.rendellvelasco.autoloanapp.state;

import com.rendellvelasco.autoloanapp.models.Loan;
import com.google.inject.Singleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Singleton
public class LoanState {
    private final ObjectProperty<Loan> selectedLoan = new SimpleObjectProperty<>();

    public Loan getSelectedLoan() { return selectedLoan.get(); }
    public void setSelectedLoan(Loan loan) { selectedLoan.set(loan); }
    public ObjectProperty<Loan> selectedLoanProperty() { return selectedLoan; }
}
