# Auto Loan Application

A multi-screen JavaFX desktop application for calculating auto loan payments with user authentication, customer and vehicle management, amortization schedule generation, and dual data persistence (SQLite + file I/O). Built with Google Guice IoC, a 3-tier MVC architecture, and BCrypt password security.

## Demo

> _Demo video coming soon_

## Learning Outcomes

- Implementing **Inversion of Control (IoC)** with Google Guice — custom `FXMLLoader` controller factory powered by Guice injection
- Building a **3-tier architecture**: Controllers (UI) → Services (business logic) → Repositories (data access)
- **Password security with BCrypt** — plain-text passwords are never stored
- **Dual data persistence** — SQLite database via JDBC and local file I/O via Java serialization
- Designing **repository interfaces** (`IUserRepository`, `ILoanRepository`) with multiple concrete implementations, swappable via Guice bindings
- Using **JavaFX Properties** (`StringProperty`, `DoubleProperty`, `ObjectProperty`) for reactive data binding
- Building a **loan calculation engine** using interfaces (`LoanCalculation`) and concrete implementations (`FixedRateLoan`)
- Generating **amortization schedules** with principal, interest, and balance breakdowns
- **Multi-screen JavaFX navigation** with FXML — Homepage, Signup, Login, Loan Application, Amortization
- **Form validation** with real-time alerts across customer, vehicle, and loan input sections

## Technologies

- Java
- JavaFX / FXML / Scene Builder
- Google Guice (IoC / Dependency Injection)
- SQLite (JDBC)
- BCrypt (password hashing)
- Java Serialization (File I/O)
- JUnit 5
- Maven
- MVC + Service Layer + Repository Pattern

## My Responsibilities

### Application Architecture
- Configured **Google Guice** `AppModule` to bind repository interfaces to implementations
- Wired `FXMLLoader` with a **Guice-powered controller factory** so all controllers receive dependencies via `@Inject`
- Implemented the **Service Layer** (`UserService`, `LoanService`) between controllers and repositories — controllers never interact with data access directly

### Authentication & Security
- Built **signup flow**: `SignupController` → `UserService` → hashes password with BCrypt → `SqlUserRepository` saves to SQLite
- Built **login flow**: fetches user from SQLite → BCrypt verification against stored hash
- All user credentials stored securely — plain-text passwords never persisted

### Loan Application (Main Form)
- **Customer info**: name, phone, city, province dropdown with real-time validation
- **Vehicle info**: type (Car/Truck/Van via radio buttons), age (New/Used), price input
- **Loan info**: down payment, interest rate (0.99%/1.99%/2.99%/custom), duration slider (12–96 months), payment frequency (weekly/bi-weekly/monthly)
- **Estimated payment**: formatted as currency, updates reactively via property bindings and change listeners
- Built `LoanCalculation` interface and `FixedRateLoan` implementation for payment computation
- Built `LoanAmortizationController` to generate and display amortization schedules

### Data Persistence (Dual Storage)
- Implemented **4 data actions** in the UI:
  - **Write to DB**: saves current form data (customer, vehicle, loan) to SQLite
  - **Read from DB**: fetches saved loans from SQLite into the ListView
  - **Write to File**: serializes current form data to a local file
  - **Read from File**: deserializes saved loans from file into the ListView
- Created `ILoanRepository` interface with two implementations:
  - `SqlLoanRepository` — CRUD operations via JDBC
  - `FileLoanRepository` — Java serialization to/from local files
- Created `IUserRepository` interface with `SqlUserRepository` implementation
- Guice binds the correct implementation based on the storage action
- Selecting a saved loan from the ListView repopulates all form fields (customer, vehicle, loan data)

### UI Design
- Designed and built **5 screens** with FXML: Homepage (navigation), Signup (registration), Login (authentication), Loan Application (main form), Amortization Schedule (payment breakdown)
- Used JavaFX layouts: `HBox`, `GridPane`, `AnchorPane`
- Added radio buttons, sliders, dropdowns, and currency-formatted labels

## Development Progression

This application was built in two phases:

1. **Core Application** — multi-screen JavaFX app with manual dependency injection, MVC architecture, in-memory repository pattern, user authentication with hard-coded default users, and loan calculation with amortization
2. **Enterprise Upgrade** — replaced manual DI with Google Guice IoC, added 3-tier architecture with a service layer, replaced in-memory storage with SQLite persistence and file I/O, added BCrypt password hashing, and introduced repository interfaces with swappable implementations

## Provided Code

- UML class diagrams for both phases showing the full architecture: controllers, services, repositories, models, and DI configuration
- Assignment specifications with architectural constraints and feature requirements
- JavaFX, Google Guice, SQLite JDBC, and BCrypt libraries

## Disclaimer

This repository contains my original implementations only, created as part of coursework at Seneca Polytechnic. Assignment specifications, starter code, and any materials authored by the course instructor are not included. This work is shared for portfolio and archival purposes and is not intended for redistribution or reuse as academic submissions.

---

*Built as part of coursework at Seneca Polytechnic — Computer Programming & Analysis*
