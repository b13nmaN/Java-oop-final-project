package com.uwi.ilenius.p2.events;

/**
 * Represents an event related to the movement of an object from one station to another.
 */
public class MoveEvent extends Event {
    private String fromStation;
    private String toStation;

    /**
     * Constructor for MoveEvent.
     *
     * @param objectName  The name of the object related to the event.
     * @param time        The time at which the event occurs.
     * @param fromStation The station from which the object is moving.
     * @param toStation   The station to which the object is moving.
     */
    public MoveEvent(String objectName, int time, String fromStation, String toStation) {
        super(objectName, time);
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    /**
     * Retrieves the station from which the object is moving.
     *
     * @return The station from which the object is moving.
     */
    public String getFromStation() {
        return fromStation;
    }

    /**
     * Retrieves the station to which the object is moving.
     *
     * @return The station to which the object is moving.
     */
    public String getToStation() {
        return toStation;
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
