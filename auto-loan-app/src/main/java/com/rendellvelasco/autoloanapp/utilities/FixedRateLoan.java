
package com.rendellvelasco.autoloanapp.utilities;

import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.models.PaymentFrequency;
import com.rendellvelasco.autoloanapp.models.Vehicle;

public class FixedRateLoan implements LoanCalculation {
    @Override
    public double calculatePayment(Loan loan, Vehicle vehicle) {

        if (loan == null || vehicle == null) return 0.0;

        double principal = vehicle.getPrice() - loan.getDownPayment();
        double annualRate = loan.getInterestRate() / 100.0;

        if (principal <= 0 || annualRate <= 0) return 0.0;

        PaymentFrequency frequency = loan.getPaymentFrequency();
        int durationMonths = loan.getDuration(); // duration stored in months

        double ratePerPeriod;
        int totalPayments;

        switch (frequency) {

            case WEEKLY -> {
                ratePerPeriod = annualRate / 52.0;
                totalPayments = durationMonths * 52 / 12;
            }

            case BI_WEEKLY -> {
                ratePerPeriod = annualRate / 26.0;
                totalPayments = durationMonths * 26 / 12;
            }

            default -> { // MONTHLY
                ratePerPeriod = annualRate / 12.0;
                totalPayments = durationMonths;
            }
        }

        if (totalPayments <= 0) return 0.0;

        return (principal * ratePerPeriod) /
                (1 - Math.pow(1 + ratePerPeriod, -totalPayments));
    }
}

