package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.models.Logable;

/**
 * Represents a generic event.
 */
abstract public class Event {
    private String objectName;
    private int time;

    /**
     * Constructor for Event.
     *
     * @param objectName The name of the object related to the event.
     * @param time       The time at which the event occurs.
     */
    public Event(String object, int time) {
        this.objectName = object;
        this.time = time;
    }

    /**
     * Retrieves the name of the object related to the event.
     *
     * @return The name of the object related to the event.
     */
    public String getObject() {
        return objectName;
    }

    /**
     * Retrieves the time at which the event occurs.
     *
     * @return The time at which the event occurs.
     */
    public int getTime() {
        return time;
    }

    @Override
	public boolean equals(Object evnt) {
		if (evnt instanceof Event)
			return objectName.equals(((Event) evnt).objectName) && time == (((Event) evnt).time);
		return false;
	}


	
	@Override
	public String toString() {
		return "Object=" + objectName + ", Time()=" + time;
	}
}
