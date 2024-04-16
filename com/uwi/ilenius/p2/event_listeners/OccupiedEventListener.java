/**
 * Interface for listening to OccupiedEvents.
 */
package com.uwi.ilenius.p2.event_listeners;

import com.uwi.ilenius.p2.events.OccupiedEvent;

/**
 * Defines the contract for classes that want to listen for OccupiedEvents.
 */
public interface OccupiedEventListener {
    
    /**
     * This method is called when an OccupiedEvent occurs.
     *
     * @param event The OccupiedEvent that occurred.
     */
    void onOccupiedEvent(OccupiedEvent event);
}
