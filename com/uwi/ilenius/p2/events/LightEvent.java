package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.enums.Light;

/**
 * Represents an event related to lights.
 */
public class LightEvent extends Event {
    private Light fromColour;
    private Light toColour;

    /**
     * Constructor for LightEvent.
     *
     * @param objectName The name of the object related to the event.
     * @param time       The time at which the event occurs.
     * @param fromColour The initial color of the light.
     * @param toColour   The color to which the light changes.
     */
    public LightEvent(String objectName, int time, Light fromColour, Light toColour) {
        super(objectName, time);
        this.fromColour = fromColour;
        this.toColour = toColour;
    }

    /**
     * Retrieves the initial color of the light.
     *
     * @return The initial color of the light.
     */
    public Light getFromColour() {
        return fromColour;
    }

    /**
     * Retrieves the color to which the light changes.
     *
     * @return The color to which the light changes.
     */
    public Light getToColour() {
        return toColour;
    }

    /**
     * Retrieves the name of the object related to the event.
     *
     * @return The name of the object related to the event.
     */
    @Override
    public String getObject() {
        return super.getObject();
    }

    /**
     * Retrieves the time at which the event occurs.
     *
     * @return The time at which the event occurs.
     */
    @Override
    public int getTime() {
        return super.getTime();
    }
}
