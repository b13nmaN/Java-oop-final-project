package com.uwi.ilenius.p2.interfaces;

import com.uwi.ilenius.p2.events.CFOSEvent;

/**
 * The Closeable interface defines a contract for objects that can be closed.
 */
public interface Closeable {
    /**
     * Closes the object.
     */
    CFOSEvent close();
}
