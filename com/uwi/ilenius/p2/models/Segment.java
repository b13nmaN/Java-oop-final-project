package com.uwi.ilenius.p2.models;
import java.util.List;
import java.util.ArrayList;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.LightEvent;
import com.uwi.ilenius.p2.events.OccupiedEvent;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.EventListenerManager;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;
import com.uwi.ilenius.p2.event_listeners.EventListener;
// import com.uwi.ilenius.p2.enums.ObjectType;
import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.enums.Light;

/**
 * The Segment class represents a section of a railway system that connects two stations.
 * It manages the traffic light, train occupancy, and events related to opening, closing, and train movement.
 * The class implements the Verifiable, Openable, Closeable, and EventListenerManager interfaces.
 */
public class Segment extends Logable implements Verifiable, Openable, Closeable, EventListenerManager {
    private String name;
    private RSStatus status = RSStatus.Open;
    private boolean hasTrain;
    private boolean isOpen;
    private TrafficLight trafficLight = new TrafficLight(0, Light.Red);
    private Station segmentStart;
    private Station segmentEnd;
    private TrainSystem trainSystem;
    private List<EventListener> listeners = new ArrayList<>();
    private int time;
    private Train train;

    /**
     * Constructs a new Segment object with the given name, start station, and end station.
     *
     * @param name         the name of the segment
     * @param segmentStart the start station of the segment
     * @param segmentEnd   the end station of the segment
     */
    public Segment(String name, Station segmentStart, Station segmentEnd) {
        this.name = name;
        this.hasTrain = false;
        this.isOpen = true;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
        this.time = 0;
    }


        /**
     * Retrieves the name of the object.
     *
     * @return          the name of the object
     */
    public String getName() {
        return name;
    }
        /**
     * Retrieves the status of the object.
     *
     * @return the status of the object
     */
    public RSStatus getStatus() {
        return status;
    }
        /**
     * Retrieves the start station of the segment.
     *
     * @return the start station of the segment
     */
    public Station getSegmentStart() {
        return segmentStart;
    }
        /**
     * Returns the end station of the segment.
     *
     * @return the end station of the segment
     */
    public Station getSegmentEnd() {
        return segmentEnd;
    }
        /**
     * Sets the value of the 'isOpen' property.
     *
     * @param  isOpen  the new value for the 'isOpen' property
     */
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
        /**
     * Sets the value of the 'hasTrain' property.
     *
     * @param  hasTrain  the new value for the 'hasTrain' property
     */
    public void setHasTrain(boolean hasTrain) {
        this.hasTrain = hasTrain;
    }
        /**
     * Checks if the segment has a train.
     * 
     * @return true if the segment has a train, false otherwise.
     */
    public boolean hasTrain() {
        return hasTrain;
    }
        /**
     * Checks if the segment is open.
     *
     * @return true if the segment is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }
        /**
     * Sets the time value of the object to the specified time.
     *
     * @param  time  the time value to set
     */
    public void setTime(int time) {
        this.time += time;
    }

    public void setTrain(Train train) {
        this.train = train;
    }


    /**
     * Registers an event listener to receive events from this segment.
     *
     * @param listener the event listener to be registered
     */
    @Override
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters an event listener from receiving events from this segment.
     *
     * @param listener the event listener to be unregistered
     */
    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns a list of all registered event listeners.
     *
     * @return a list of registered event listeners
     */
    @Override
    public List<EventListener> getListeners() {
        return listeners;
    }

    /**
     * Notifies all registered event listeners about the given event.
     *
     * @param event the event to be notified
     */
    @Override
    public void notifyListeners(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    /**
     * Accepts a train onto the segment at the given time.
     *
     * @param train the train to be accepted
     * @param time  the time at which the train is accepted
     * @return an OccupiedEvent if the train is accepted, null otherwise
     */
    public OccupiedEvent acceptTrain(Train train, int time) {
        setTime(time);
        if (!hasTrain && isOpen) {
            setTrain(train);
            hasTrain = true;
            OccupiedEvent event = new OccupiedEvent(name, time, train, true);

            // Notify all registered listeners
            notifyListeners(event);
            addToLog(event);
            return event;
        } else {
            System.out.println("Cannot accept train. Segment is occupied or closed.");
            return null;
        }
    }

    /**
     * Releases a train from the segment at the given time.
     *
     * @param time the time at which the train is released
     * @return a CFOSEvent if a train is released, null otherwise
     */
    public CFOSEvent releaseTrain(int time) {
        setTime(time);
        if (hasTrain) {
            hasTrain = false;
            CFOSEvent event = new CFOSEvent(name, time, Action.CLOSE);

            // Notify all registered listeners
            notifyListeners(event);
            addToLog(event);

            return event;
        } else {
            System.out.println("No train to release.");
            return null;
        }
    }

    /**
     * Changes the traffic light color on the segment.
     *
     * @return a LightEvent representing the light color change
     */
    public LightEvent changeLight() {
        // Implement logic to change the light
        switch (trafficLight.getColour()) {
            case Red:
                trafficLight.change();
                LightEvent event1 = new LightEvent(name, time, Light.Red, Light.Green);
                addToLog(event1);

                // Notify all registered listeners
                notifyListeners(event1);

                return event1;
            case Green:
                trafficLight.change();
                LightEvent event2 = new LightEvent(name, time, Light.Green, Light.Red);
                addToLog(event2);

                // Notify all registered listeners
                notifyListeners(event2);

                return event2;
            default:
                System.out.println("Invalid colour " + trafficLight.getColour());
                return null;
        }
    }

    /**
     * Returns the current traffic light color on the segment.
     *
     * @return true if the light is green, false otherwise
     */
    public boolean lightColour() {
        // Implement logic to determine light color
        return trafficLight.getColour() == Light.Green;
    }

    /**
     * Closes the segment at the given time.
     *
     * @return a CFOSEvent representing the closure of the segment
     */
    public CFOSEvent close() {
        if(lightColour()) {
            changeLight();
        }
        Train train = segmentStart.getTrainInStation();
        setHasTrain(true);
        segmentStart.close();
        segmentStart.setTrainInStation(train);
        CFOSEvent event = new CFOSEvent(name, time, Action.CLOSE);
        addToLog(event);
        

        // Notify all registered listeners
        notifyListeners(event);
        return event;
    }

    /**
     * Opens the segment at the given time.
     *
     * @return a CFOSEvent representing the opening of the segment
     */
    public CFOSEvent open() {
        releaseTrain(time);
        changeLight();
        setHasTrain(false);
        segmentStart.open();
        CFOSEvent event = new CFOSEvent(name, time, Action.OPEN);
        addToLog(event);
        

        // Notify all registered listeners
        notifyListeners(event);

        return event;
    }

   /**
     * Verifies the validity of the segment's state.
     *
     * @return true if the segment is in a valid state, false otherwise
     */
    public boolean verify() {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (trafficLight == null || !trafficLight.verify() || segmentStart == null || segmentEnd == null) {
            return false;
        }

        if (segmentStart.equals(segmentEnd)) {
            return false; // Start and end stations cannot be the same
        }

        if (isOpen && (!segmentStart.isOpen() || !segmentEnd.isOpen())) {
            return false; // If segment is open, both start and end stations should be open
        }

        if (!isOpen && (segmentStart.isOpen() || segmentEnd.isOpen())) {
            return false; // If segment is closed, both start and end stations should be closed
        }

        return true;
    }

    @Override
    public boolean validate() {
        // TODO Auto-generated method stub
        return super.validate();
    }

    @Override
	public String toString() {
		return "Segment [name=" + name + ", segmentStart=" + (segmentStart == null ? "none" : segmentStart.getName())
				+ ", segmentEnd=" + (segmentEnd == null ? "none" : segmentEnd.getName()) + ", status="
				+ status.getDescription() + ", trafficLight=" + trafficLight + ", train="
				+ (train.equals("-1") ? "none" : train) + ", verified=" + (verify() ? "Yes" : "No") + "]";
	}
}



