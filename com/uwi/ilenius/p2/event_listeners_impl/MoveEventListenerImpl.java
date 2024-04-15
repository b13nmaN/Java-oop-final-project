package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.MoveEventListener;
import com.uwi.ilenius.p2.events.MoveEvent;
public class MoveEventListenerImpl implements MoveEventListener, EventListener {
    @Override
    public void onMoveEvent(MoveEvent event) {
        System.out.println("Train move from " + event.getFromStation() + " to " + event.getToStation() + "event time: " + event.getTime());
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof MoveEvent) {
            onMoveEvent((MoveEvent) event);
        }
    }
}
