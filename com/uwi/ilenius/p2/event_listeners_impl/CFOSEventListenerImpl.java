package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.CFOSEventListener;
import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.events.CFOSEvent;
class CFOSEventListenerImpl implements CFOSEventListener, EventListener {
    @Override
    public void onCFOSEvent(CFOSEvent event) {
        // Implementation logic for CFOSEvent
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof CFOSEvent) {
            onCFOSEvent((CFOSEvent) event);
        }
    }
}
