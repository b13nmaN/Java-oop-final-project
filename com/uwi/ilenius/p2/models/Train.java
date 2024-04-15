package com.uwi.ilenius.p2.models;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.MoveEvent;
import com.uwi.ilenius.p2.interfaces.Verifiable;

public class Train extends Logable implements Verifiable {
    private Integer id;
    private String name;
    private Integer timeRegistered;
    private Integer startTime;
    private Station currentLocation;
    private Boolean isAtStart;
    private Integer waitTimeRemaining;
    private Route route;
    private LinkedList<String> stops;

    public Train(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.timeRegistered = -1; // Assuming -1 indicates not registered
        this.startTime = -1; // Assuming -1 indicates not started
        this.currentLocation = null;
        this.isAtStart = true; // Assuming train starts at the beginning of the route
        this.waitTimeRemaining = 0; // No wait time initially
        this.stops = new LinkedList<>();
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Route getRoute() {
        return route;
    }

    public Station getCurrentLocation() {
        return currentLocation;
    }

    public Boolean isAtStart() {
        // Check if the train is at the start of the route
        return isAtStart && stops.isEmpty();
    }

    public Integer getWaitTimeRemaining() {
        return waitTimeRemaining;
    }

    //Setters
    public void setTimeRegistered(Integer timeRegistered) {
        this.timeRegistered = timeRegistered;
    }


    public boolean isRegistered() {
        return timeRegistered != -1;
    }

    public Integer whenRegistered() {
        return timeRegistered;
    }

    // Register the train with the given time
    public void register(Integer time) {
        if (!isRegistered()) {
            this.timeRegistered = time;
            this.startTime = time; // Set start time when registered
        } else {
            System.out.println("Train is already registered.");
        }
    }



    // Deregister the train
    public void deregister() {
        this.timeRegistered = -1;
        this.startTime = -1;
        this.currentLocation = null;
        this.isAtStart = true;
        this.waitTimeRemaining = 0;
        this.stops.clear();
        route = null;
    }

    // Check if the train is waiting
    public boolean isWaiting() {
        return waitTimeRemaining > 0;
    }

    // public CFOSEvent start() {
    //     if (!isRegistered())
    //         throw new IllegalStateException("Train must be registered before starting.");
    
    //     if (startTime == -1) {
    //         startTime = timeRegistered;
    //         CFOSEvent event = new CFOSEvent(currentLocation.getName(), startTime, Action.START);
    //         advance(startTime); // Move the train to the next station
    //         return event;
    //     } else {
    //         throw new IllegalStateException("Train is already started.");
    //     }
    // }
    

    // Method to finish the train
    public CFOSEvent finish() {
        if (currentLocation == route.getEnd() && !isAtStart)
            throw new IllegalStateException("Train has already finished.");

        CFOSEvent event = new CFOSEvent(currentLocation.getName(), startTime, Action.FINISH);
        currentLocation = route.getEnd(); // Move the train to the end station
        isAtStart = false;
        return event;
    }


    // Advance the train by the given time// Method to advance the train
    // public MoveEvent advance(int time) {
    // }

    // Get the name of the next station
    // Method to get the next station of the train
    // public String nextStation() {
    //     String currentStationName = currentLocation.getName();
    //     return isAtStart ? route.getNextStation(currentStationName, true).getName() : route.getPreviousStation(currentStationName, false).getName();
    // }

    // Add a stop to the train route
    public void addStop(String stop) {
        stops.add(stop);
    }

    // Change the route of the train
    public void changeRoute(Route route) {
        this.route = route;
    }

    // Verify if the train is valid
    public boolean verify() {
        return route != null && route.verify() && timeRegistered > 0;
    }

    // Validate the train
    public boolean validate() {
        // Implement validation logic
        return true; // Placeholder, implement your validation logic
    }
}

