
package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.Loan;
import java.util.List;

public interface ILoanRepository {
    void saveLoan(Loan loan);
    List<Loan> getAllLoans();
}