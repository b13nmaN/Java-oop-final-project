package com.uwi.ilenius.p1;

/**
 * Represents the possible status of a train system.
 */
public enum SystemStatus {
    Initialised("System is Initialised"), // The system is initialised
    Operational("System is Operational"), // The system is operational
    Deadlocked("System is Deadlocked"), // The system is deadlocked
    Finished("No More trains!"); // The system has finished operation

    private String description; // The description of the system status

    /**
     * Constructs a SystemStatus enum with the given description.
     * 
     * @param description The description of the system status.
     */
    SystemStatus(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the system status.
     * 
     * @return The description of the system status.
     */
    public String getDescription() {
        return description;
    }
}
