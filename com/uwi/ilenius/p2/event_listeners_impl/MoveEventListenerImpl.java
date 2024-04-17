package com.uwi.ilenius.p2.event_listeners_impl;

import java.util.LinkedList;

import com.uwi.ilenius.p2.event_listeners.EventListener;
import com.uwi.ilenius.p2.event_listeners.MoveEventListener;
import com.uwi.ilenius.p2.events.MoveEvent;
import com.uwi.ilenius.p2.models.Route;
import com.uwi.ilenius.p2.models.Train;
import com.uwi.ilenius.p2.models.Station;
import com.uwi.ilenius.p2.models.Segment;

/**
 * Implementation of MoveEventListener and EventListener interfaces.
 */
public class MoveEventListenerImpl implements MoveEventListener, EventListener {
    private Route route;

    /**
     * Constructor for MoveEventListenerImpl.
     *
     * @param route The route for which this event listener is associated.
     */
    public MoveEventListenerImpl(Route route) {
        this.route = route;
    }

    /**
     * Handles MoveEvent occurrences.
     *
     * @param event The MoveEvent instance.
     */
    @Override
    public void onMoveEvent(MoveEvent event) {
        int time = event.getTime();
        String trainName = event.getObject();
        String nextStationName = event.getToStation();
        Station nextStation = route.getStationByName(nextStationName);

        Train train = route.geTrainByName(trainName);
        // Update train's current location
        train.setcurrentStation(nextStation);
        
        
        Segment segment = route.getSegmentByStartStation(nextStation);

        // Check if the next segment and station are available for the train to move
        if (nextStation.isOpen() && !segment.hasTrain()) {

            if (!train.hasStops()) {
                // Move the train to the next station
                nextStation.setTrainInStation(train);

                // Update segment's status
                segment.acceptTrain(train, time);
                segment.close(); // Close the segment after the train moves

                // Log the movement of the train
                // train.setStartTime(time + 1);
                segment.open();
            } else {
                for (Station stop : train.getStops()) {
                    if (stop.getName().equals(event.getToStation())) {
                        // Update train's current location
                        train.setcurrentStation(nextStation);
                        // Move the train to the next station
                        nextStation.setTrainInStation(train);

                        // Update segment's status
                        segment.acceptTrain(train, time);
                        segment.close(); // Close the segment after the train moves
                        nextStation.close();
                        segment.setTime(time);

                        // Log the movement of the train
                        // train.setStartTime(time + 2);
                        segment.setTime(time);
                        segment.open();
                        nextStation.setTrainInStation(null);
                        nextStation.open();
                        break; // Exit the loop once the stop is found
                    }
                }
            }
            // Check if train reached the end of the route
            if (train.getcurrentStation() == route.getEnd()) {
                // Trigger a "finish" event for the train
                train.finish(); // Call the train's finish method
                // You can further process or notify listeners about the finish event here
                System.out.println("Train " + trainName + " finished its journey at " + time);
            }

            System.out.println("Train Name: " + trainName + " moved from " + event.getFromStation() + " to " + event.getToStation() + " at time: " + time);
        } else {
            // The segment or station is occupied, handle accordingly
            System.out.println("Train " + trainName + " cannot move to " + nextStation.getName() + ". Segment or station is occupied.");
        }
    }

    /**
     * Handles generic events by delegating MoveEvent instances to onMoveEvent method.
     *
     * @param event The event object.
     */
    @Override
    public void onEvent(Object event) {
        if (event instanceof MoveEvent) {
            onMoveEvent((MoveEvent) event);
        }
    }
}
