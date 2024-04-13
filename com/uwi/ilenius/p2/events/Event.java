package com.uwi.ilenius.p2.events;

public class Event {
    private String objectName;
    private int time;

    public Event(String objectName, int time) {
        this.objectName = objectName;
        this.time = time;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getTime() {
        return time;
    }
}
