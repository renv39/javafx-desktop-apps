
package com.rendellvelasco.projectstaffingtool.services;

import com.rendellvelasco.projectstaffingtool.data.ProjectRepository;
import com.rendellvelasco.projectstaffingtool.exceptions.OverAllocationException;
import com.rendellvelasco.projectstaffingtool.models.Assignment;
import com.rendellvelasco.projectstaffingtool.models.Employee;
import com.rendellvelasco.projectstaffingtool.models.Project;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Core business logic for resource management.
 * ProjectRepository is injected by Guice — no manual instantiation.
 */
@Singleton
public class ResourceService {

    private static final double MAX_WEEKLY_HOURS = 40.0;

    private final ProjectRepository projectRepository;

    @Inject
    public ResourceService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ObservableList<Assignment> getAssignmentsByEmployee(Employee e) {
        ObservableList<Assignment> result = FXCollections.observableArrayList();
        for (Project p : projectRepository.findAll()) {
            for (Assignment a : p.getAssignments()) {
                if (a.getEmployee().equals(e)) {
                    result.add(a);
                }
            }
        }
        return result;
    }

    public void assignTeamMember(Project p, Employee e, String role, double hours)
            throws OverAllocationException {
        validateOverAllocation(e, hours);
        p.getAssignments().add(new Assignment(role, hours, e));
    }

    public void updateAssignmentHours(Assignment a, double newHours)
            throws OverAllocationException {
        Employee e = a.getEmployee();
        double existingHours = getAssignmentsByEmployee(e).stream()
                .filter(x -> x != a)
                .mapToDouble(Assignment::getAllocatedHours)
                .sum();
        double totalLoad = existingHours + newHours;
        if (totalLoad > MAX_WEEKLY_HOURS) {
            throw new OverAllocationException(
                    String.format("Update rejected: %s would be %.1f h/week (max %.0f).",
                            e.getName(), totalLoad, MAX_WEEKLY_HOURS));
        }
        a.setAllocatedHours(newHours);
    }

    private void validateOverAllocation(Employee e, double newHours)
            throws OverAllocationException {
        double existingHours = getAssignmentsByEmployee(e).stream()
                .mapToDouble(Assignment::getAllocatedHours)
                .sum();
        double totalLoad = existingHours + newHours;
        if (totalLoad > MAX_WEEKLY_HOURS) {
            throw new OverAllocationException(
                    String.format("Over-allocation: %s would have %.1f h/week (max %.0f).",
                            e.getName(), totalLoad, MAX_WEEKLY_HOURS));
        }
    }
}
