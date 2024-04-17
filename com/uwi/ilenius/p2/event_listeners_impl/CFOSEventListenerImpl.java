package com.uwi.ilenius.p2.event_listeners_impl;

import com.uwi.ilenius.p2.event_listeners.CFOSEventListener;
import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.events.CFOSEvent;

/**
 * Implementation of CFOSEventListener and EventListener interfaces.
 */
public class CFOSEventListenerImpl implements CFOSEventListener, EventListener {

    public CFOSEventListenerImpl() {
        
    }

    /**
     * Handles CFOSEvent occurrences.
     *
     * @param event The CFOSEvent instance.
     */
    @Override
    public void onCFOSEvent(CFOSEvent event) {
        switch (event.getAction()) {
            case OPEN:
                System.out.println("Opening " + event.getObject() + " at time " + event.getTime());
                break;
            case CLOSE:
                System.out.println("Closing " + event.getObject() + " at time " + event.getTime());
                break;
            case FINISH:
                System.out.println("Finished " + event.getObject() + " at time " + event.getTime());
                break;
            case START:
                System.out.println("Starting " + event.getObject() + " at time " + event.getTime());
                break;
        }

    }

    /**
     * Handles generic events by delegating CFOSEvent instances to onCFOSEvent method.
     *
     * @param event The event object.
     */
    @Override
    public void onEvent(Object event) {
        if (event instanceof CFOSEvent) {
            onCFOSEvent((CFOSEvent) event);
        }
    }
}
