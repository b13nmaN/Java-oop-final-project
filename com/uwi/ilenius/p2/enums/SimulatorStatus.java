package com.uwi.ilenius.p2.enums;

public enum SimulatorStatus {
    INITIALISED("Initialised"),
    OPERATIONAL("Operational"),
    DEADLOCKED("Deadlocked"),
    FINISHED("Finished");

    private String description;

    SimulatorStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

