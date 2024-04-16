package com.uwi.ilenius.p2.interfaces;

import java.util.List;

import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.event_listeners.EventListener;

/**
 * Defines the interface for managing event listeners.
 */
public interface EventListenerManager {
    
    /**
     * Registers an event listener.
     *
     * @param listener The event listener to register.
     */
    void registerListener(EventListener listener);
    
    /**
     * Unregisters an event listener.
     *
     * @param listener The event listener to unregister.
     */
    void unregisterListener(EventListener listener);
    
    /**
     * Retrieves the list of registered event listeners.
     *
     * @return The list of registered event listeners.
     */
    List<EventListener> getListeners();
    
    /**
     * Notifies all registered event listeners about the occurrence of an event.
     *
     * @param event The event to notify listeners about.
     */
    void notifyListeners(Event event);
}
