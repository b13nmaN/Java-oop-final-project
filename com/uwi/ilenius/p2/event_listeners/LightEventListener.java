/**
 * Interface for listening to LightEvents.
 */
package com.uwi.ilenius.p2.event_listeners;

import com.uwi.ilenius.p2.events.LightEvent;

/**
 * Defines the contract for classes that want to listen for LightEvents.
 */
public interface LightEventListener {
    
    /**
     * This method is called when a LightEvent occurs.
     *
     * @param event The LightEvent that occurred.
     */
    void onLightEvent(LightEvent event);
}
