

package com.rendellvelasco.autoloanapp.utilities;

import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.models.Vehicle;

public interface LoanCalculation {
    public double calculatePayment(Loan loan, Vehicle vehicle);
}
