/**
 * Interface for listening to events.
 */
package com.uwi.ilenius.p2.event_listeners;

/**
 * Defines the contract for classes that want to listen for events.
 */
public interface EventListener {

    /**
     * This method is called when an event occurs.
     *
     * @param event The event object that occurred.
     */
    void onEvent(Object event);
}
