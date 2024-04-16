/**
 * Interface for listening to CFOSEvents.
 */
package com.uwi.ilenius.p2.event_listeners;

import com.uwi.ilenius.p2.events.CFOSEvent;

/**
 * Defines the contract for classes that want to listen for CFOSEvents.
 */
public interface CFOSEventListener {
    
    /**
     * This method is called when a CFOSEvent occurs.
     *
     * @param event The CFOSEvent that occurred.
     */
    void onCFOSEvent(CFOSEvent event);
}
