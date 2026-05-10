
package com.rendellvelasco.autoloanapp.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:autoloan.db";
    private static Connection connection;

    // Private constructor - singleton, no instantiation
    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initialize() {
        try (Statement stmt = getConnection().createStatement()) {
            // Users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT    NOT NULL UNIQUE,
                    password TEXT    NOT NULL,
                    email    TEXT    NOT NULL
                )
            """);

            // Loans table - stores flat values, Customer + Vehicle embedded
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS loans (
                    id               INTEGER PRIMARY KEY AUTOINCREMENT,
                    down_payment     REAL    NOT NULL,
                    interest_rate    REAL    NOT NULL,
                    duration         INTEGER NOT NULL,
                    frequency        TEXT    NOT NULL,
                    customer_name    TEXT,
                    customer_phone   TEXT,
                    customer_city    TEXT,
                    customer_province TEXT,
                    vehicle_type     TEXT,
                    vehicle_age      TEXT,
                    vehicle_price    REAL
                )
            """);

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
