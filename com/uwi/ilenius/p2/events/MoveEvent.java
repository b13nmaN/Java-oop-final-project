package com.uwi.ilenius.p2.events;

public class MoveEvent extends Event {
    private String fromStation;
    private String toStation;

    public MoveEvent(String objectName, int time, String fromStation, String toStation) {
        super(objectName, time);
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    @Override
    public String getObjectName() {
        // TODO Auto-generated method stub
        return super.getObjectName();
    }

    @Override
    public int getTime() {
        // TODO Auto-generated method stub
        return super.getTime();
    }
}
