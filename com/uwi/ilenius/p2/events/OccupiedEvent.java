package com.uwi.ilenius.p2.events;

public class OccupiedEvent extends Event {
    private String train;
    private boolean isEntry;

    public OccupiedEvent(String objectName, int time, String train, boolean isEntry) {
        super(objectName, time);
        this.train = train;
        this.isEntry = isEntry;
    }

    public String getTrain() {
        return train;
    }

    public boolean isEntry() {
        return isEntry;
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
