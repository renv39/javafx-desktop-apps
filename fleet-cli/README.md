# Fleet CLI

A console-based Java application for managing a commercial vehicle fleet, demonstrating class hierarchies, interfaces, the Comparable interface for sorting, and the MVC design pattern.

## Demo

> _Demo video coming soon_

## Learning Outcomes

- Designing a **multi-level class hierarchy** with abstract and concrete classes in Java
- Implementing and using **Java interfaces** (`IVehicleMaintenance`, `IVehicleOperations`)
- Using the **Comparable interface** to sort objects by custom criteria (mileage proximity to service interval)
- Working with **arrays of objects** and polymorphism for fleet-wide operations
- Applying the **MVC design pattern** to separate data, display, and business logic
- **Input validation** for user-provided data (negative values, non-numeric input, invalid categories)

## Technologies

- Java
- MVC Design Pattern
- Comparable Interface

## My Responsibilities

- Designed and implemented the full **class hierarchy**: `Vehicle` (abstract) → `PassengerVehicles`, `CommercialVehicles`, `SpecializedVehicles` (abstract) → `Sedan`, `SUV`, `Truck`, `Van`, `Ambulance` (concrete)
- Implemented two interfaces: `IVehicleMaintenance` (service interval, maintenance cost) and `IVehicleOperations` (primary function, fuel type)
- Built **mileage-based maintenance urgency detection** — identifies the vehicle closest to or exceeding its service interval using `compareTo()`
- Implemented **sorting by purchase price** (descending) with overridden `toString()`
- Built **category filtering** — user enters a vehicle category and the app displays all vehicles in that category with their function and fuel type
- Applied **MVC separation**: Model (vehicle classes and fleet data), View (output formatting), Controller (input handling and orchestration)
- Handled edge cases: vehicles at/exceeding service interval, zero mileage, multiple vehicles at similar urgency

## Provided Code

- Vehicle fleet data table (purchase prices, service intervals, maintenance costs, fuel types) — class structure and all logic were my responsibility
- UML class hierarchy diagram

## Disclaimer

This repository contains my original implementations only, created as part of coursework at Seneca Polytechnic. Assignment specifications, starter code, and any materials authored by the course instructor are not included. This work is shared for portfolio and archival purposes and is not intended for redistribution or reuse as academic submissions.

---

*Built as part of coursework at Seneca Polytechnic — Computer Programming & Analysis*
