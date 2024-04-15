package com.uwi.ilenius.p1;

/**
 * Represents the possible states of a traffic light.
 */
public enum Light {
    Red("Red"), // The red light state
    Green("Green"), // The green light state
    ;

    private String description; // The description of the light state

    /**
     * Constructs a Light enum with the given description.
     * 
     * @param description The description of the light state.
     */
    Light(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the light state.
     * 
     * @return The description of the light state.
     */
    public String getDescription() {
        return description;
    }
}
