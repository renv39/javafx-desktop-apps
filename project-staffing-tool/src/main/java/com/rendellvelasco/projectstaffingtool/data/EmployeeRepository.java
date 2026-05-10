
package com.rendellvelasco.projectstaffingtool.data;

import com.rendellvelasco.projectstaffingtool.models.Employee;
import com.rendellvelasco.projectstaffingtool.models.ExternalConsultant;
import com.rendellvelasco.projectstaffingtool.models.InternalStaff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Repository for all Employee records.
 * @Singleton is managed by Guice — no manual getInstance() needed.
 */
@Singleton
public class EmployeeRepository {

    private final ObservableList<Employee> allEmployees;

    @Inject
    public EmployeeRepository() {
        allEmployees = FXCollections.observableArrayList();
        seed();
    }

    private void seed() {
        InternalStaff alice = new InternalStaff("E001", "Alice Chen", "alice@nexus.com", 95_000);
        alice.getSkills().addAll("Java", "Spring", "SQL");

        InternalStaff bob = new InternalStaff("E002", "Bob Martinez", "bob@nexus.com", 85_000);
        bob.getSkills().addAll("Python", "Machine Learning", "Docker");

        InternalStaff carol = new InternalStaff("E003", "Carol Singh", "carol@nexus.com", 78_000);
        carol.getSkills().addAll("React", "TypeScript", "CSS");

        ExternalConsultant dan = new ExternalConsultant("E004", "Dan Wu", "dan@techforce.com",
                120.0, "TechForce Agency");
        dan.getSkills().addAll("DevOps", "Kubernetes", "Terraform");

        ExternalConsultant eve = new ExternalConsultant("E005", "Eve Okafor", "eve@consult.io",
                95.0, "Consult.io");
        eve.getSkills().addAll("Java", "React", "Agile");

        allEmployees.addAll(alice, bob, carol, dan, eve);
    }

    public ObservableList<Employee> findAll() {
        return allEmployees;
    }

    public FilteredList<Employee> filterBySkill(String skill) {
        FilteredList<Employee> filtered = new FilteredList<>(allEmployees);
        if (skill == null || skill.isBlank()) {
            filtered.setPredicate(e -> true);
        } else {
            filtered.setPredicate(e -> e.getSkills().stream()
                    .anyMatch(s -> s.equalsIgnoreCase(skill)));
        }
        return filtered;
    }
}
