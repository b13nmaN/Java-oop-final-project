package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.OccupiedEventListener;
import com.uwi.ilenius.p2.events.OccupiedEvent;
public class OccupiedEventListenerImpl implements OccupiedEventListener, EventListener {
    @Override
    public void onOccupiedEvent(OccupiedEvent event) {
        System.out.println(event.getObject() + " is occupied with " + event.getTrain().getName() + " at " + event.getTime());
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof OccupiedEvent) {
            onOccupiedEvent((OccupiedEvent) event);
        }
    }
}
