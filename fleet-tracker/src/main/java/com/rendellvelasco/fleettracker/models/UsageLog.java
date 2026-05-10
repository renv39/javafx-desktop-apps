
package com.rendellvelasco.fleettracker.models;

import java.time.LocalDate;

public class UsageLog {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double distance;

    public UsageLog(LocalDate startDate, LocalDate endDate, Double distance) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
