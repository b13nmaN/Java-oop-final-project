package com.uwi.ilenius.p1;

/**
 * The Verifiable interface defines a contract for objects that can be verified.
 * It provides a single method, verify(), which performs the verification logic.
 */
public interface Verifiable {
    /**
     * Verifies the object's state or properties.
     *
     * @return true if the object is in a valid state, false otherwise.
     */
    boolean verify();
}
