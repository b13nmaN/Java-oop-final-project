package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.LightEventListener;
import com.uwi.ilenius.p2.events.LightEvent;
class LightEventListenerImpl implements LightEventListener, EventListener {
    @Override
    public void onLightEvent(LightEvent event) {
        // Implementation logic for LightEvent
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof LightEvent) {
            onLightEvent((LightEvent) event);
        }
    }
}
