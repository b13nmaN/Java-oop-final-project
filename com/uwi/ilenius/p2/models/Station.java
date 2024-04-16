package com.uwi.ilenius.p2.models;

import java.util.List;
import java.util.ArrayList;

import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;
import com.uwi.ilenius.p2.interfaces.EventListenerManager;
import com.uwi.ilenius.p2.event_listeners.EventListener;

/**
 * The Station class represents a station in a train system.
 * It implements various interfaces for functionality and event management.
 */
public class Station extends Logable implements Verifiable, Openable, Closeable, EventListenerManager {
    private String name;
    private RSStatus status = RSStatus.Open;
    private boolean hasTrain = false;
    private boolean isOpen;
    private Train trainInStation = null;
    private int time = 0;
    private List<EventListener> listeners = new ArrayList<>();

    /**
     * Constructs a Station object with a given name.
     * @param name The name of the station.
     */
    public Station(String name) {
        this.name = name;
        this.isOpen = true;
    }

    /**
     * Retrieves the train currently in the station.
     * @return The train in the station.
     */
    public Train getTrain() {
        return trainInStation;
    }

    /**
     * Retrieves the status of the station.
     * @return The status of the station.
     */
    public RSStatus getStatus() {
        return status;
    }

    /**
     * Sets the current time.
     * @param time The current time to set.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Retrieves the train currently in the station.
     * @return The train in the station.
     */
    public Train getTrainInStation() {
        return trainInStation;
    }

    /**
     * Sets the train currently in the station.
     * @param trainInStation The train to set in the station.
     */
    public void setTrainInStation(Train trainInStation) {
        this.hasTrain = true;
        isOpen = false;
        this.trainInStation = trainInStation;
    }

    /**
     * Checks if the station is open.
     * @return True if the station is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Verifies if the station name is valid.
     * @return True if the name is not null or empty, false otherwise.
     */
    public boolean verify() {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Closes the station.
     * @return The CFOSEvent representing the station closure.
     */
    public CFOSEvent close() {
        isOpen = false;
        hasTrain = true;
        CFOSEvent event = new CFOSEvent(name, time, Action.CLOSE);
        addToLog(event);

        // Notify all registered listeners
        notifyListeners(event);
        return event;
    }

    /**
     * Opens the station.
     * @return The CFOSEvent representing the station opening.
     */
    public CFOSEvent open() {
        hasTrain = false;
        isOpen = true;
        CFOSEvent event = new CFOSEvent(name, time, Action.OPEN);
        addToLog(event);


        // Notify all registered listeners
        notifyListeners(event);

        return event;
    }

    /**
     * Retrieves the name of the station.
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }
        /**
     * Registers an event listener.
     *
     * @param  listener  the event listener to register
     */
    @Override
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }
        /**
     * Unregisters an event listener.
     *
     * @param  listener  the event listener to unregister
     */
    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }
        /**
     * Retrieves a list of all registered event listeners.
     *
     * @return a list of registered event listeners
     */
    @Override
    public List<EventListener> getListeners() {
        return listeners;
    }
        /**
     * Notifies all registered event listeners about the occurrence of an event.
     *
     * @param  event  the event to notify listeners about
     */
    @Override
    public void notifyListeners(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
        /**
     * Validates the station according to custom validation logic.
     *
     * @return true if the validation passes, false otherwise
     */
    public boolean validate() {
        // Custom validation logic can be added here
        return true;
    }
}
