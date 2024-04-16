/**
 * Interface for listening to MoveEvents.
 */
package com.uwi.ilenius.p2.event_listeners;

import com.uwi.ilenius.p2.events.MoveEvent;

/**
 * Defines the contract for classes that want to listen for MoveEvents.
 */
public interface MoveEventListener {
    
    /**
     * This method is called when a MoveEvent occurs.
     *
     * @param event The MoveEvent that occurred.
     */
    void onMoveEvent(MoveEvent event);
}
