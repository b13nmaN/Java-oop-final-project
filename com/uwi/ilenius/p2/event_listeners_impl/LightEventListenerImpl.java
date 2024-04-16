package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.LightEventListener;
import com.uwi.ilenius.p2.events.LightEvent;

/**
 * Implementation of LightEventListener and EventListener interfaces.
 */
public class LightEventListenerImpl implements LightEventListener, EventListener {

    /**
     * Handles LightEvent occurrences.
     *
     * @param event The LightEvent instance.
     */
    @Override
    public void onLightEvent(LightEvent event) {
        // Implementation logic for LightEvent
        System.out.println("Lights changes from " + event.getFromColour() + " to " + event.getToColour());
    }

    /**
     * Handles generic events by delegating LightEvent instances to onLightEvent method.
     *
     * @param event The event object.
     */
    @Override
    public void onEvent(Object event) {
        if (event instanceof LightEvent) {
            onLightEvent((LightEvent) event);
        }
    }
}
