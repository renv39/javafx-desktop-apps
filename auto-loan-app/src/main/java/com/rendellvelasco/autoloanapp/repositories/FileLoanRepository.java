
package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.Loan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoanRepository implements ILoanRepository {

    private static final String FILE_PATH = "loans.dat";

    @Override
    public void saveLoan(Loan loan) {
        List<Loan> existing = getAllLoans(); // load what's already there
        existing.add(loan);                 // append new loan

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH))) {
            out.writeObject(existing);
        } catch (IOException e) {
            System.err.println("saveLoan to file failed: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Loan> getAllLoans() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(FILE_PATH))) {
            return (List<Loan>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("getAllLoans from file failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
