
package com.rendellvelasco.projectstaffingtool.data;

import com.rendellvelasco.projectstaffingtool.models.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rendellvelasco.projectstaffingtool.models.FixedPriceProject;
import com.rendellvelasco.projectstaffingtool.models.Project;
import com.rendellvelasco.projectstaffingtool.models.Status;
import com.rendellvelasco.projectstaffingtool.models.TimeMaterialProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Repository for all Project records.
 * @Singleton is managed by Guice — no manual getInstance() needed.
 */
@Singleton
public class ProjectRepository {

    private final ObservableList<Project> allProjects;

    @Inject
    public ProjectRepository() {
        allProjects = FXCollections.observableArrayList();
        seed();
    }

    private void seed() {
        allProjects.addAll(
            new FixedPriceProject("P001", "AI Upgrade",           Status.ACTIVE,   250_000, 0.15),
            new FixedPriceProject("P002", "HR Portal Redesign",   Status.ACTIVE,   120_000, 0.10),
            new FixedPriceProject("P003", "Legacy Migration",     Status.PLANNING,  80_000, 0.20),
            new FixedPriceProject("P004", "BI Dashboard",         Status.CLOSED,   150_000, 0.12),
            new FixedPriceProject("P005", "Mobile App v2",        Status.ACTIVE,   200_000, 0.18),
            new TimeMaterialProject("P006", "Web Revamp",          Status.ACTIVE,   500),
            new TimeMaterialProject("P007", "Cloud Infrastructure",Status.ACTIVE,   800),
            new TimeMaterialProject("P008", "Security Audit",      Status.PLANNING, 200),
            new TimeMaterialProject("P009", "Data Pipeline",       Status.ACTIVE,   600),
            new TimeMaterialProject("P010", "DevOps Automation",   Status.CLOSED,   400),
            new TimeMaterialProject("P011", "Chatbot Integration", Status.PLANNING, 300)
        );


    }

    public ObservableList<Project> findAll() {
        return allProjects;
    }
}
