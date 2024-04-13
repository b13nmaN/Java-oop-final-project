package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.enums.Light;

public class LightEvent extends Event {
    private Light fromColour;
    private Light toColour;

    public LightEvent(String objectName, int time, Light fromColour, Light toColour) {
        super(objectName, time);
        this.fromColour = fromColour;
        this.toColour = toColour;
    }

    public Light getFromColour() {
        return fromColour;
    }

    public Light getToColour() {
        return toColour;
    }
}
