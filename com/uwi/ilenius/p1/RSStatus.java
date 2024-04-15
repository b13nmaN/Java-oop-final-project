package com.uwi.ilenius.p1;

/**
 * Represents the possible status of a railway station.
 */
public enum RSStatus {
    Open("Open"), // The station is open
    ClosedForMaintenance("Closed for Maintenance"), // The station is closed for maintenance
    ;

    private String description; // The description of the station status

    /**
     * Constructs an RSStatus enum with the given description.
     * 
     * @param description The description of the station status.
     */
    RSStatus(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the station status.
     * 
     * @return The description of the station status.
     */
    public String getDescription() {
        return description;
    }
}
