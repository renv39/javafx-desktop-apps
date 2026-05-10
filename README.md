# JavaFX Desktop Applications

A collection of workshops and a capstone project built throughout an Application Development course at Seneca Polytechnic, focused on building full MVC desktop applications in Java with JavaFX, dependency injection, JDBC database access, and enterprise design patterns.

## Workshops

| # | Workshop | Description |
|---|----------|-------------|
| 1 | [fleet-cli](./fleet-cli/) | Java class hierarchy with interfaces, Comparable sorting, MVC (console) |
| 2 | [fleet-tracker](./fleet-tracker/) | Multi-window JavaFX app with Service Layer, one-to-many data associations |
| 4&5 | [project-staffing-tool](./project-staffing-tool/) | Decision support system with DI framework, live reactive bindings, over-allocation detection |
| 3,6&7 | [auto-loan-app](./auto-loan-app/) | Full loan application with Guice IoC, SQLite, BCrypt, file I/O, 3-tier architecture |

> Each workshop folder has its own README with a demo, learning outcomes, technologies used, my responsibilities, and what was provided.

## Tech Stack

Java · JavaFX · FXML · Scene Builder · Google Guice · SQLite · JDBC · BCrypt · Git

## Skills Demonstrated

- Building **desktop GUI applications** from scratch using **JavaFX** with FXML and Scene Builder
- Applying the **MVC (Model-View-Controller)** pattern with dedicated **Service** and **Repository** layers
- **Inversion of Control (IoC)** with Google Guice — configuring controller factories and binding interfaces to implementations
- **Object-Relational Mapping** — designing Java classes that map to relational database tables via JDBC
- Implementing **dual data persistence**: SQLite database storage and local file I/O (serialization)
- **Password security** with BCrypt hashing — plain-text passwords are never stored
- **Reactive UI bindings** with JavaFX Properties, DoubleBinding, FilteredList, and change listeners
- Implementing **design patterns**: Repository, Service Layer, Factory, Dependency Injection, Association Class
- **Java OOP fundamentals**: abstract classes, interfaces, Comparable, generics, collections
- Designing from **UML class diagrams** — translating provided diagrams into working code

## Workshop Progression

Workshops 1–2 build **foundational Java and JavaFX skills**: OOP hierarchies, interfaces, multi-window apps, and the service layer pattern.

Workshops 3, 6-7 develop a **complete auto loan application** across two phases: first with manual DI, MVC, and in-memory storage, then upgraded with Guice IoC, SQLite persistence, BCrypt security, and file I/O.

Workshops 4–5 tackle a **separate domain** (project staffing) with a DI framework, abstract models, reactive bindings, and business rule enforcement.

---

*Built as part of coursework at Seneca Polytechnic — Computer Programming & Analysis*
