package com.uwi.ilenius.p1;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Represents a train in the transportation system.
 */
public class Train implements Verifiable {
    private Integer id; // The ID of the train
    private Integer timeRegistered; // The time when the train was registered
    private Integer startTime; // The time when the train started
    private Station currentLocation; // The current location of the train
    private Route route; // The route of the train
    private TrainSystem trainSystem; // The train system associated with the train

    /**
     * Constructs a Train object with the given ID.
     * 
     * @param id  The ID of the train.
     */
    public Train(Integer id) {
        this.id = id;
        this.timeRegistered = -1; // Assuming -1 indicates not registered
        this.startTime = -1; // Assuming -1 indicates not started
        this.currentLocation = null;
    }

    /**
     * Gets the ID of the train.
     * 
     * @return The ID of the train.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the route of the train.
     * 
     * @return The route of the train.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Gets the current location of the train.
     * 
     * @return The current location of the train.
     */
    public Station getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Checks if the train is registered.
     * 
     * @return true if the train is registered, false otherwise.
     */
    public boolean isRegistered() {
        return timeRegistered != -1;
    }

    /**
     * Gets the time when the train was registered.
     * 
     * @return The time when the train was registered.
     */
    public Integer whenRegistered() {
        return timeRegistered;
    }

    /**
     * Registers the train at the specified time.
     * 
     * @param time  The time when the train is registered.
     */
    public void register(Integer time) {
        if (!isRegistered()) {
            this.timeRegistered = time;
        } else {
            System.out.println("Train is already registered.");
        }
    }

    /**
     * Starts the train.
     */
    public void start() {
        if (!isRegistered()) {
            System.out.println("Train must be registered before starting.");
            return;
        }

        if (startTime == -1) {
            startTime = timeRegistered;
        } else {
            System.out.println("Train is already started.");
        }
    }

    /**
     * Advances the train.
     */
    public void advance() {
        // Implement logic to advance the train
        if (currentLocation != null) {
            nextStation();
            Station nextStation = currentLocation;
            if (nextStation != null) {
                currentLocation = nextStation;
            } else {
                System.out.println("Train has reached the end of the route.");
            }
        } else {
            System.out.println("Train has not been assigned a route yet.");
        }
    }

    /**
     * Gets the name of the current station where the train is located.
     * 
     * @return The name of the current station.
     */
    public String currentStation() {
        if (currentLocation != null) {
            return currentLocation.getName();
        }
        return null;
    }

    /**
     * Gets the name of the next station in the train's route.
     * 
     * @return The name of the next station.
     */
    public String nextStation() {
        Station nexStation = route.getNextStation(currentLocation.getName());
        currentLocation = nexStation;
        if (nexStation != null) {
            return nexStation.getName();
        }
        
        return null;
    }

    /**
     * Changes the route of the train.
     * 
     * @param route  The new route of the train.
     */
    public void changeRoute(Route route) {
       setRoute(route);
    }

    /**
     * Sets the route of the train.
     * 
     * @param route  The new route of the train.
     */
    public void setRoute(Route route) {
        this.route = route;
    }


    /**
     * Verifies the validity of the train.
     * 
     * @return true if the train is valid, false otherwise.
     */
    public boolean verify() {
        return route != null && route.verify() && timeRegistered > 0;
    }
}
