package com.uwi.ilenius.p2.models;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.MoveEvent;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.interfaces.EventListenerManager;
import com.uwi.ilenius.p2.interfaces.Verifiable;
import com.uwi.ilenius.p2.interfaces.TimeObserver;

/**
 * The Train class represents a train in a train system.
 * It implements the Verifiable interface for verification functionality and EventListenerManager for event management.
 */
public class Train extends Logable implements Verifiable, EventListenerManager, TimeObserver {
    private Integer id;
    private String name;
    private Integer timeRegistered;
    private Integer startTime;
    private Station currentStation;
    private Boolean isAtStart = true;
    private Integer waitTimeRemaining;
    private Route route;
    private LinkedList<String> stops;
    private Station stopsAt;
    private int time;
    private List<EventListener> listeners = new ArrayList<>();

    /**
     * Constructs a Train object with a given ID and name.
     * @param id The ID of the train.
     * @param name The name of the train.
     */
    public Train(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.timeRegistered = -1; // Assuming -1 indicates not registered
        this.startTime = -1; // Assuming -1 indicates not started
        this.currentStation = null;
        this.isAtStart = true; // Assuming train starts at the beginning of the route
        this.waitTimeRemaining = -1; // No wait time initially
        this.stops = new LinkedList<>();
        this.time = 0;
    }

    // Getters
    /**
     * Retrieves the ID of the train.
     * @return The ID of the train.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the name of the train.
     * @return The name of the train.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the route of the train.
     * @return The route of the train.
     */
    public Route getRoute() {
        return route;
    }

    
    /**
     * Retrieves the current location of the train.
     * @return The current location of the train.
     */
    public Station getcurrentStation() {
        return currentStation;
    }


    /**
     * Retrieves the remaining wait time of the train.
     * @return The remaining wait time of the train.
     */
    public Integer getWaitTimeRemaining() {
        return waitTimeRemaining;
    }

    /**
     * Checks if the train has stops.
     * @return True if the train has stops, false otherwise.
     */
    public boolean hasStops() {
        return stops.size() > 0;
    }

    /**
     * Retrieves the stops of the train.
     * @return The stops of the train.
     */
    public LinkedList<String> getStops() {
        return stops;
    }

    @Override
    public void updateTime(int currentTime) {
        this.time = currentTime;
    }

    //Setters
        /**
     * Sets the start time of the train.
     *
     * @param  startTime  the new start time for the train
     */
    public void setTimeRegistered(Integer timeRegistered) {
        this.timeRegistered = timeRegistered;
    }
        /**
     * Sets the current location of the train to the specified station.
     *
     * @param  currentStation  the station to set as the current location
     */
    public void setcurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }
        /**
     * Sets the start time of the train.
     *
     * @param  startTime  the new start time for the train
     */
    public void setStartTime(Integer startTime) {
        this.startTime += startTime;
    }

    public void setWaitTimeRemaining(Integer waitTimeRemaining) {
        this.waitTimeRemaining = (timeRegistered + startTime) - time;
    }

    /**
     * Checks if the train is registered.
     * @return True if the train is registered, false otherwise.
     */
    public boolean isRegistered() {
        return timeRegistered != -1;
    }

    /**
     * Retrieves the time when the train was registered.
     * @return The time when the train was registered.
     */
    public Integer whenRegistered() {
        return timeRegistered;
    }

    /**
     * Registers the train with the given time.
     * @param time The time to register the train.
     */
    public void register(Integer time) {
        if (!isRegistered()) {
            this.timeRegistered = time;
            this.startTime = time; // Set start time when registered
        } else {
            System.out.println("Train is already registered.");
        }
    }

    /**
     * Deregisters the train.
     */
    public void deregister() {
        this.timeRegistered = -1;
        this.startTime = -1;
        this.currentStation = null;
        this.isAtStart = true;
        this.waitTimeRemaining = 0;
        this.stops.clear();
        route = null;
    }

    /**
     * Checks if the train is waiting.
     * @return True if the train is waiting, false otherwise.
     */
    public boolean isWaiting() {
        return waitTimeRemaining == time;
    }

    @Override
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public List<EventListener> getListeners() {
        return listeners;
    }

    @Override
    public void notifyListeners(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    /**
     * Starts the train.
     * @return The CFOSEvent representing the train start.
     * @throws IllegalStateException If the train is already registered or started.
     */
    public CFOSEvent start() {
        if (!isRegistered())
            System.out.println("Train must be registered before starting.");

        if (startTime == 0) {
            startTime = timeRegistered;
            isAtStart = false;
            CFOSEvent event = new CFOSEvent(name, startTime, Action.START);
            notifyListeners(event);
            addToLog(event);

            return event;
        } else {
            System.out.println("Train is already started.");
            return null;
        }
    }

    /**
     * Finishes the train.
     * @return The CFOSEvent representing the train finish.
     * @throws IllegalStateException If the train has already finished.
     */
    public CFOSEvent finish() {
        if (currentStation == route.getEnd() && !isAtStart)
            throw new IllegalStateException("Train has already finished.");

        CFOSEvent event = new CFOSEvent(name, startTime, Action.FINISH);
        currentStation = route.getEnd(); // Move the train to the end station
        addToLog(event);

        return event;
    }

    /**
     * Advances the train to the next station.
     * @param time The time of the advancement.
     * @return The MoveEvent representing the train movement.
     */
    public MoveEvent advance(int time) {
        String sourceStation = currentStation.getName();
        String destinationStation = nextStation();
    
        start();
    
        // Calculate time taken to traverse the segment
        int segmentTime; // Time for traversing a segment
    
        if (isAtStart || stopsAt == null || !stopsAt.equals(currentStation)) {
            // Train does not need to stop at the end station or it's not a stop station
            segmentTime = 1; // Train takes 1 unit of time to traverse the segment
        } else {
            // Train needs to stop at the end station
            segmentTime = 2; // Train takes 2 units of time to traverse the segment
        }
    
        // Check if the current station is in the stops list
        if (stops.contains(currentStation)) {
            // Move event when the train stops at a stop station
            MoveEvent stopEvent = new MoveEvent(name, time, sourceStation, destinationStation);
            addToLog(stopEvent);
            notifyListeners(stopEvent);
    
            // Update waiting time
            waitTimeRemaining = 1;
        }
    
        // Move event when the train passes through a non-stop station
        MoveEvent event;
        if (!isWaiting()) {
            // Train is not waiting, so it can advance normally
            int arrivalTime = time + segmentTime;
            event = new MoveEvent(name, arrivalTime, sourceStation, destinationStation);
        } else {
            // Train is waiting, so it does not move yet
            event = new MoveEvent(name, time + 1, sourceStation, destinationStation);
            waitTimeRemaining--; // Decrement wait time remaining
        }
    
        // Update the current station
        currentStation = route.getStationByName(destinationStation);
    
        // Add event to log
        addToLog(event);
    
        // Notify all registered listeners
        notifyListeners(event);
        return event;
    }
    
    

    /**
     * Retrieves the name of the next station.
     * @return The name of the next station.
     */
    public String nextStation() {
        String currentStationName = currentStation.getName();
        return isAtStart ? route.getNextStation(currentStationName, isAtStart).getName() : route.getPreviousStation(currentStationName, isAtStart).getName();
    }

    /**
     * Adds a stop to the train route.
     * @param stop The name of the stop to add.
     */
    public void addStop(String stop) {
        stops.add(stop);
    }

    /**
     * Changes the route of the train.
     * @param route The new route for the train.
     */
    public void changeRoute(Route route) {
        this.route = route;
    }

    /**
     * Verifies if the train is valid.
     * @return True if the train is valid, false otherwise.
     */
    public boolean verify() {
        return route != null && route.verify() && timeRegistered > 0;
    }

    /**
     * Validates the train.
     * @return True if the train is valid, false otherwise.
     */
    public boolean validate() {
        // Implement validation logic
        return true; // Placeholder, implement your validation logic
    }

    // @Override
	// public String toString() {
	// 	return "Train [id=" + id + ", name=" + name + ", " + "timeRegistered="
	// 			+ (timeRegistered <= 0 ? "unregistered" : timeRegistered) + ", startTime="
	// 			+ (timeRegistered >= 0 ? getStartTime() : "unregistered") + ", currentStation="
	// 			+ (currentStation == null ? "none" : currentStation) + ", route="
	// 			+ (route == null ? "none" : route.getName()) + ", stopsAt="
	// 			+ (stopsAt.size() > 0 ? stopsAt.toString() : "All") + ", status=" + status.getDescription()
	// 			+ ", verified=" + (verify() ? "Yes" : "No") + "]";
	// }

}
