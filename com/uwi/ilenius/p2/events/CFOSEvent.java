package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.enums.Action;

/**
 * Represents an event related to CFOs (Chief Financial Officers).
 */
public class CFOSEvent extends Event {
    private Action action;

    /**
     * Constructor for CFOSEvent.
     *
     * @param objectName The name of the object related to the event.
     * @param time       The time at which the event occurs.
     * @param action     The action associated with the CFO event.
     */
    public CFOSEvent(String objectName, int time, Action action) {
        super(objectName, time);
        this.action = action;
    }

    /**
     * Retrieves the action associated with the CFO event.
     *
     * @return The action associated with the CFO event.
     */
    public Action getAction() {
        return action;
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
