package com.uwi.ilenius.p2.models;

import com.uwi.ilenius.p2.enums.Light;
import com.uwi.ilenius.p2.interfaces.Verifiable;

/**
 * The TrafficLight class represents a traffic light.
 * It implements the Verifiable interface for verification functionality.
 */
public class TrafficLight implements Verifiable {
    private Integer id;
    private Light colour;

    /**
     * Constructs a TrafficLight object with a given ID and initial color.
     * @param id The ID of the traffic light.
     * @param colour The initial color of the traffic light.
     */
    public TrafficLight(Integer id, Light colour) {
        this.id = id;
        this.colour = colour;
    }

    /**
     * Retrieves the current color of the traffic light.
     * @return The current color of the traffic light.
     */
    public Light getColour() {
        return colour;
    }

    /**
     * Retrieves the ID of the traffic light.
     * @return The ID of the traffic light.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Changes the color of the traffic light.
     * If the current color is Red, changes it to Green; if it's Green, changes it to Red.
     */
    public void change() {
        if (colour == Light.Red) {
            colour = Light.Green;
        } else if (colour == Light.Green) {
            colour = Light.Red;
        }
    }

    /**
     * Verifies if the traffic light color is valid.
     * @return True if the color is not null, false otherwise.
     */
    public boolean verify() {
        return colour != null;
    }

    public boolean validate() {
        // Custom validation logic can be added here
        return true;
    }
}
