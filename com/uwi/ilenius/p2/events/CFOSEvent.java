package com.uwi.ilenius.p2.events;

import com.uwi.ilenius.p2.enums.Action;

public class CFOSEvent extends Event {
    private Action action;

    public CFOSEvent(String objectName, int time, Action action) {
        super(objectName, time);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
