package com.uwi.ilenius.p1;

/**
 * Represents a traffic light associated with a segment.
 */
public class TrafficLight implements Verifiable {
    private Integer id; // The identifier of the traffic light
    private Light colour; // The color of the traffic light
    private Segment segment; // The segment associated with the traffic light

    /**
     * Constructs a TrafficLight object with the given ID and color.
     * 
     * @param id     The identifier of the traffic light.
     * @param colour The color of the traffic light.
     */
    public TrafficLight(Integer id, Light colour) {
        this.id = id;
        this.colour = colour;
    }


    /**
     * Gets the color of the traffic light.
     * 
     * @return The color of the traffic light.
     */
    public Light getColour() {
        return colour;
    }
    /**
     * Changes the color of the traffic light from red to green or vice versa.
     */
    public void change() {
        if (colour == Light.Red) {
            colour = Light.Green;
        } else if (colour == Light.Green) {
            colour = Light.Red;
        }
    }

    /**
     * Verifies the validity of the traffic light.
     * 
     * @return true if the traffic light is valid, false otherwise.
     */
    public boolean verify() {
        return colour != null;
    }
}
