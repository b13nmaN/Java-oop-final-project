package com.uwi.ilenius.p2.interfaces;

import com.uwi.ilenius.p2.events.CFOSEvent;;

/**
 * The Openable interface defines a contract for objects that can be opened.
 */
public interface Openable {
    /**
     * Opens the object.
     */
    CFOSEvent open();
}
