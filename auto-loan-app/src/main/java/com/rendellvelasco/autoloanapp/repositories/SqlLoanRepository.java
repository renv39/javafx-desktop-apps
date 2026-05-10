
package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.*;
import com.rendellvelasco.autoloanapp.models.*;
import com.rendellvelasco.autoloanapp.utilities.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlLoanRepository implements ILoanRepository {

    @Override
    public void saveLoan(Loan loan) {
        String sql = """
            INSERT INTO loans (
                down_payment, interest_rate, duration, frequency,
                customer_name, customer_phone, customer_city, customer_province,
                vehicle_type, vehicle_age, vehicle_price
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setDouble(1, loan.getDownPayment());
            stmt.setDouble(2, loan.getInterestRate());
            stmt.setInt(3, loan.getDuration());
            stmt.setString(4, loan.getPaymentFrequency().name());

            Customer c = loan.getCustomer();
            stmt.setString(5, c != null ? c.getName() : null);
            stmt.setString(6, c != null ? c.getPhone() : null);
            stmt.setString(7, c != null ? c.getCity() : null);
            stmt.setString(8, c != null ? c.getProvince() : null);

            Vehicle v = loan.getVehicle();
            stmt.setString(9,  v != null ? v.getType().name() : null);
            stmt.setString(10, v != null ? v.getAge().name() : null);
            stmt.setDouble(11, v != null ? v.getPrice() : 0);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("saveLoan failed: " + e.getMessage());
        }
    }

    @Override
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Rebuild Customer
                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                customer.setPhone(rs.getString("customer_phone"));
                customer.setCity(rs.getString("customer_city"));
                customer.setProvince(rs.getString("customer_province"));

                // Rebuild Vehicle
                Vehicle vehicle = new Vehicle();
                vehicle.setType(VehicleType.valueOf(rs.getString("vehicle_type")));
                vehicle.setAge(VehicleAge.valueOf(rs.getString("vehicle_age")));
                vehicle.setPrice(rs.getDouble("vehicle_price"));

                // Rebuild Loan
                Loan loan = new Loan();
                loan.setDownPayment(rs.getDouble("down_payment"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setDuration(rs.getInt("duration"));
                loan.setPaymentFrequency(PaymentFrequency.valueOf(rs.getString("frequency")));
                loan.setCustomer(customer);
                loan.setVehicle(vehicle);

                loans.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("getAllLoans failed: " + e.getMessage());
        }
        return loans;
    }
}