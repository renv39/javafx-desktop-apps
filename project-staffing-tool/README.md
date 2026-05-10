# Project Staffing Tool

A JavaFX desktop decision support system for managing employee-to-project assignments and resolving scheduling conflicts. Built for a consulting firm scenario where managers need to prevent staff over-allocation (exceeding 40 hours/week). Features a portfolio dashboard with live cost tracking and a resource allocation tool with real-time workload validation.

## Demo

> _Demo video coming soon_

## Learning Outcomes

- Building a **multi-screen business application** with Login, Portfolio Dashboard, and Resource Allocator views
- Implementing **Dependency Injection** using a DI framework (Guice) — no manual instantiation of dependencies
- Designing **abstract domain models** with JavaFX Properties: `Project` (abstract → `FixedPriceProject`, `TimeMaterialProject`), `Employee` (abstract → `InternalStaff`, `ExternalConsultant`)
- Using an **Association class** (`Assignment`) to model many-to-many relationships between Projects and Employees
- Implementing **live reactive bindings** — total cost labels update instantly when assignment hours change, workload indicators turn red when over 40 hours
- Using **FilteredList** for real-time search and status filtering on the dashboard
- Implementing **DoubleBinding** for computed properties (assignment cost = hours × employee base cost)
- Throwing and handling **custom exceptions** (`OverAllocationException`) for business rule enforcement
- Seeding **hardcoded test data** in repositories for realistic demo scenarios

## Technologies

- Java
- JavaFX
- FXML
- Google Guice (Dependency Injection)
- MVC + Service Layer + Repository Pattern

## My Responsibilities

### Screen 1: Login
- Built login UI with credential validation via `AuthenticationService`
- Error handling with visual feedback ("Invalid Credentials" label)

### Screen 2: Portfolio Dashboard (Master-Detail)
- **BorderPane layout** with search bar, status filter, project list, and detail panel
- Left panel: `ListView` of all projects
- Center panel: project title, type, and **live total cost label** (green, updates reactively when hours change)
- `TableView` of assignments showing employee name, role, allocated hours, and computed cost
- "Add Team Member" button opens the Resource Allocator
- Implemented **FilteredList** for title search and status filtering (Active/Closed)

### Screen 3: Resource Allocator (Split-View)
- **SplitPane layout** — left panel for new assignment, right panel for current workload
- Left: skill filter (ComboBox) → employee selection → role and hours input
- **Projected Load label**: displays current hours + new hours, turns **red** if over 40 and disables Confirm button, **green** if within limit
- Right: editable `TableView` of the selected employee's assignments across all projects — managers can lower hours on other projects to free up time
- On confirm: calls `ResourceService.assignTeamMember()` which validates the 40-hour cap and throws `OverAllocationException` if exceeded

### Backend Architecture
- Implemented **domain models** with JavaFX Properties: `Project` (abstract), `FixedPriceProject` (maxBudget, clientMarkup), `TimeMaterialProject` (hourlyCap), `Employee` (abstract), `InternalStaff` (annualSalary), `ExternalConsultant` (hourlyRate, agencyName), `Assignment` (role, allocatedHours, `getCost()` as DoubleBinding)
- Built **repositories** as singletons: `ProjectRepository` (seeded with 10+ projects), `EmployeeRepository` (seeded with 5+ employees with different skills, including `filterBySkill()`)
- Built `ResourceService` with `getAssignmentsByEmployee()`, `assignTeamMember()`, `updateAssignmentHours()`, and `validateOverAllocation()`
- Configured all wiring through the DI framework — controllers receive services via injection, services receive repositories via injection

## Provided Code

- UML class diagram showing the full architecture: Controller layer, Service layer, Model layer (with inheritance), Data layer (repositories)
- Assignment specification with screen layouts, reactive requirements, and business rules
- DI framework libraries

## Disclaimer

This repository contains my original implementations only, created as part of coursework at Seneca Polytechnic. Assignment specifications, starter code, and any materials authored by the course instructor are not included. This work is shared for portfolio and archival purposes and is not intended for redistribution or reuse as academic submissions.

---

*Built as part of coursework at Seneca Polytechnic — Computer Programming & Analysis*
