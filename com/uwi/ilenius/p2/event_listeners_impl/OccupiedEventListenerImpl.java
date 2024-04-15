package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.OccupiedEventListener;
import com.uwi.ilenius.p2.events.OccupiedEvent;
class OccupiedEventListenerImpl implements OccupiedEventListener, EventListener {
    @Override
    public void onOccupiedEvent(OccupiedEvent event) {
        // Implementation logic for OccupiedEvent
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof OccupiedEvent) {
            onOccupiedEvent((OccupiedEvent) event);
        }
    }
}
