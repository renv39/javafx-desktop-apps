# Fleet Tracker

A multi-window JavaFX desktop application for managing vehicle information, maintenance schedules, and usage logs. Built with MVC architecture and a dedicated Service Layer to separate business logic from the UI.

## Demo

> _Demo video coming soon_

## Learning Outcomes

- Building a **multi-window JavaFX application** with separate FXML files and controllers per screen
- Implementing the **Service Layer pattern** to separate data storage from UI logic
- Managing **one-to-many relationships** (one Vehicle → many MaintenanceRecords / UsageLogs) using `Map<Vehicle, List<...>>`
- **Passing service instances between controllers** — the SummaryController receives shared services from the MainController rather than creating its own
- Designing **POJO model classes** with proper encapsulation (private fields, public getters/setters)
- Working with **JavaFX TableView** and dynamic data display

## Technologies

- Java
- JavaFX
- FXML
- MVC + Service Layer

## My Responsibilities

- Designed and built **two FXML views**: MainView (primary input screen) and SummaryView (data display popup)
- Implemented **three model classes**: `Vehicle` (make, model, year, type), `MaintenanceRecord` (date, description, cost), `UsageLog` (start date, end date, kilometers)
- Built the **Service Layer** with three service classes:
  - `VehicleService` — manages the vehicle list (`ArrayList<Vehicle>`)
  - `MaintenanceService` — manages records linked to vehicles via `Map<Vehicle, List<MaintenanceRecord>>`
  - `UsageService` — manages usage logs linked to vehicles via `Map<Vehicle, List<UsageLog>>`
- Implemented **MainController**: creates service instances, handles vehicle creation, maintenance/usage record entry, and opens the summary window while passing service objects
- Implemented **SummaryController**: receives service objects via `setServices()`, populates TableView/TextArea based on selected data type (Vehicles, Maintenance, or Usage)
- Ensured controllers **never store data directly** — all data operations go through the service layer

## Provided Code

- UML class diagram showing the Controller, Service, and Model layers
- Assignment specification with architectural constraints
- JavaFX and FXML libraries

## Disclaimer

This repository contains my original implementations only, created as part of coursework at Seneca Polytechnic. Assignment specifications, starter code, and any materials authored by the course instructor are not included. This work is shared for portfolio and archival purposes and is not intended for redistribution or reuse as academic submissions.

---

*Built as part of coursework at Seneca Polytechnic — Computer Programming & Analysis*
