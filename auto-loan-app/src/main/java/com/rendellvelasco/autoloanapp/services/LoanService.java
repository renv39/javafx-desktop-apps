
package com.rendellvelasco.autoloanapp.services;

import com.rendellvelasco.autoloanapp.models.Loan;
import com.rendellvelasco.autoloanapp.repositories.ILoanRepository;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;

public class LoanService {

    private final ILoanRepository dbRepo;
    private final ILoanRepository fileRepo;

    @Inject
    public LoanService(@Named("db")   ILoanRepository dbRepo,
                       @Named("file") ILoanRepository fileRepo) {
        this.dbRepo   = dbRepo;
        this.fileRepo = fileRepo;
    }

    public void saveToDatabase(Loan loan) {
        dbRepo.saveLoan(loan);
    }

    public List<Loan> loadFromDatabase() {
        return dbRepo.getAllLoans();
    }

    public void saveToFile(Loan loan) {
        fileRepo.saveLoan(loan);
    }

    public List<Loan> loadFromFile() {
        return fileRepo.getAllLoans();
    }
}
