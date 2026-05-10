

package com.rendellvelasco.autoloanapp.services;

import com.rendellvelasco.autoloanapp.models.AmortizationEntry;
import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.models.PaymentFrequency;
import com.rendellvelasco.autoloanapp.utilities.FixedRateLoan;
import com.rendellvelasco.autoloanapp.utilities.LoanCalculation;

import java.util.ArrayList;
import java.util.List;

public class AmortizationService {

    private final LoanCalculation calculator = new FixedRateLoan();

    public List<AmortizationEntry> generateSchedule(Loan loan) {

        List<AmortizationEntry> entries = new ArrayList<>();

        if (loan == null || loan.getVehicle() == null) return entries;

        double principal = loan.getVehicle().getPrice() - loan.getDownPayment();
        double annualRate = loan.getInterestRate() / 100.0;
        int durationMonths = loan.getDuration();
        PaymentFrequency frequency = loan.getPaymentFrequency();

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

        double payment = calculator.calculatePayment(loan, loan.getVehicle());
        double balance = principal;

        for (int i = 1; i <= totalPayments; i++) {
            double interestPortion  = balance * ratePerPeriod;
            double principalPortion = payment - interestPortion;
            balance -= principalPortion;

            if (i == totalPayments) balance = 0.0;

            AmortizationEntry entry = new AmortizationEntry();
            entry.setMonth(i);
            entry.setPrincipal(Math.round(principalPortion * 100.0) / 100.0);
            entry.setInterest(Math.round(interestPortion * 100.0) / 100.0);
            entry.setBalance(Math.round(balance * 100.0) / 100.0);

            entries.add(entry);
        }

        return entries;
    }
}