package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.models.Train;

/**
 * Represents an event related to the occupation of a location by a train.
 */
public class OccupiedEvent extends Event {
    private Train train;
    private boolean isEntry;

    /**
     * Constructor for OccupiedEvent.
     *
     * @param objectName The name of the object related to the event.
     * @param time       The time at which the event occurs.
     * @param train      The train involved in the event.
     * @param isEntry    Indicates whether the event represents an entry or exit.
     */
    public OccupiedEvent(String objectName, int time, Train train, boolean isEntry) {
        super(objectName, time);
        this.train = train;
        this.isEntry = isEntry;
    }

    /**
     * Retrieves the train involved in the event.
     *
     * @return The train involved in the event.
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Checks whether the event represents an entry.
     *
     * @return true if the event represents an entry, false otherwise.
     */
    public boolean isEntry() {
        return isEntry;
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
