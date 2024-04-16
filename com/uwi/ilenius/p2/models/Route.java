package com.uwi.ilenius.p2.models;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

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
 * Represents a route in the train system.
 */
public class Route extends Logable implements Verifiable, Openable, Closeable, EventListenerManager {
    private String name;
    private boolean isRoundTrip;
    private RSStatus status = RSStatus.Open;
    private LinkedList<Segment> segments;
    private LinkedList<Train> trainsForRoute;
    private TrainSystem trainSystem;
    private LinkedList<Station> stations;
    private List<EventListener> listeners = new ArrayList<>();
    private int time = 0;

    /**
     * Constructor for Route.
     *
     * @param name         The name of the route.
     * @param isRoundTrip  Indicates whether the route is a round trip.
     * @param stations     The stations included in the route.
     */
    public Route(String name, boolean isRoundTrip, LinkedList<Station> stations) {        
        this.name = name;
        this.isRoundTrip = isRoundTrip;
        this.stations = stations;
        this.trainsForRoute = new LinkedList<>();
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    /**
     * Returns a boolean indicating whether the route is a round trip or not.
     *
     * @return true if the route is a round trip, false otherwise.
     */
    public Station getStart() {
        if (!stations.isEmpty()) {
            return stations.getFirst();
        }
        return null;
    }
        /**
     * Returns the name of this object.
     *
     * @return          the name of this object
     */
    public String getName() {
        return name;
    }

    public RSStatus getStatus() {
        return status;
    }
        /**
     * Gets the status of the route.
     *
     * @return          The status of the route
     */
    public LinkedList<Station> getStations() {
        return stations;
    }
        /**
     * Retrieves the segments of the object.
     *
     * @return the segments of the object
     */
    public LinkedList<Segment> getSegments() {
        return segments;
    }
        /**
     * Returns the Station object with the given name, or null if not found.
     *
     * @param  name  the name of the station
     * @return       the Station object with the given name, or null if not found
     */
    public Station getStationByName(String name) {
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }
    /**
     * Returns the Train object with the given name, or null if not found.
     *
     * @param  name  the name of the train
     * @return       the Train object with the given name, or null if not found
     */
    public Train geTrainByName(String name) {
        for (Train train : trainsForRoute) {
            if (train.getName().equals(name)) {
                return train;
            }
        }
        return null;
    }

        /**
     * Retrieves a Segment object from the given list of segments that starts at the specified station.
     *
     * @param  startStation  the station to search for in the list of segments
     * @return               the Segment object that starts at the specified station, or null if not found
     */
    public Segment getSegmentByStartStation(Station startStation) {
        // System.out.println("segments for route: " + segments);
        for (Segment segment : segments) {
            Station segmentStart = segment.getSegmentStart();
            if (segment.getSegmentStart().equals(startStation)) {
                return segment;
            }
        }
        return null;
    }
        /**
     * Sets the list of segments for the route.
     *
     * @param  segments  the list of segments to set
     */
    public void setSegments(LinkedList<Segment> segments) {
        this.segments = segments;
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
     * @return         	description of return value
     */
    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }
        /**
     * Retrieves the list of registered event listeners.
     *
     * @return The list of registered event listeners.
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

    // Other attributes and methods remain unchanged
        /**
     * Retrieves the list of segments for the current route based on the given train system.
     *
     * @param  trainSystem  the train system used to retrieve the segments
     * @return              a LinkedList of Segment objects representing the segments for the route
     */
    public LinkedList<Segment> getSegmentsForRoute() {
        LinkedList<Segment> routeSegments = new LinkedList<>();
        ListIterator<Station> stationIterator = stations.listIterator();
      
        Station previousStation = null; // Keep track of the previous station
      
        while (stationIterator.hasNext()) {
          Station currentStation = stationIterator.next();
      
          if (previousStation != null) {
            // Check segments only if there's a previous station (no segment for the first station)
            for (Segment segment : segments) {
              if (segment.getSegmentStart().equals(previousStation) && segment.getSegmentEnd().equals(currentStation)) {
                routeSegments.add(segment);
                break; // Move to the next segment after finding a match
              }
            }
          }
          previousStation = currentStation; // Update previous station for next iteration
        }
      
        return routeSegments;
      }


          /**
     * Retrieves the end station of the route.
     *
     * @return  the end station of the route, or null if the route has no stations
     */
    public Station getEnd() {
        if (!stations.isEmpty()) {
            return stations.getLast();
        }
        return null;
    }

    // Other attributes and methods remain unchanged
        /**
     * Retrieves the next station in the route after the given station.
     *
     * @param  station     the name of the current station
     * @param  isAtStart   indicates if the current station is at the start of the route
     * @return             the next station in the route, or null if the given station is not found or there is no next station
     */
    public Station getNextStation(String station, boolean isAtStart) {
        ListIterator<Station> iterator = stations.listIterator();
        while (iterator.hasNext()) {
            Station currentStation = iterator.next();
            if (currentStation.getName().equals(station)) {
                if (isAtStart && iterator.hasNext()) {
                    return iterator.next();
                } else if (!isAtStart) {
                    if (iterator.hasNext()) {
                        return iterator.next();
                    }
                }
            }
        }
        return null;
    }
    
        /**
     * A description of the entire Java function.
     *
     * @param  station    description of parameter
     * @param  isAtStart  description of parameter
     * @return            description of return value
     */
    public Station getPreviousStation(String station, boolean isAtStart) {
        // Implement logic to get the previous station by using linked list and iterators
        ListIterator<Station> iterator = stations.listIterator();
        Station previousStation = null;
        while (iterator.hasNext()) {
            Station currentStation = iterator.next();
            if (currentStation.getName().equals(station)) {
                if (!isAtStart && previousStation != null) {
                    return previousStation;
                }
                if (iterator.hasPrevious()) {
                    previousStation = iterator.previous(); // Move to the previous station
                    if (iterator.hasPrevious()) {
                        return iterator.previous();
                    }
                }
            }
            previousStation = currentStation;
        }
        return null;
    }

        /**
     * Retrieves a list of trains that belong to a specific route.
     *
     * @param  trainSystem  the train system containing the trains
     * @return              a list of trains for the specified route
     */
    public LinkedList<Train> getTrainsForRoute(TrainSystem trainSystem) {
        ListIterator<Train> trainIterator = trainSystem.getTrains().listIterator();
        while (trainIterator.hasNext()) {
            Train train = trainIterator.next();
            if (train.getRoute().getName().equals(name)) {
                trainsForRoute.add(train);
            }
        }
        return trainsForRoute;
    }

        /**
     * Adds a segment to the function.
     *
     * @param  segment  the segment to be added
     */
    public void addSegment(Segment segment) {
        segments.add(segment);
    }
        /**
     * Adds multiple segments to the list of segments.
     *
     * @param segments  the list of segments to be added
     */
    public void addSegments(LinkedList<Segment> segments) {
        // Implement logic to add multiple segments
        this.segments.addAll(segments);
    }

        /**
     * Removes a segment from the list of segments.
     *
     * @param  segment  the segment to be removed
     */
    public void removeSegment(Segment segment) {
        segments.remove(segment);
    }
        /**
     * Checks if the given segment is present in the list of segments.
     *
     * @param  segment  the segment to check for presence
     * @return         true if the segment is present, false otherwise
     */
    public boolean containsSegment(Segment segment) {
        return segments.contains(segment);
    }

        /**
     * Verifies the validity of the route.
     *
     * @return true if the route is valid, false otherwise.
     *         Validation checks include:
     *         - Name is not null or empty
     *         - Segments are not empty
     *         - Route contains at least two stations
     *         - Start and end stations are the same for round trips
     *         - Start and end stations are different for non-round trips
     *         - Segments are properly sequenced
     *         - No duplicate segments
     *         - Each segment is valid
     *         - No loops detected (except for round trips)
     */
    public boolean verify() {
        // A. Check if the name is valid (not null or empty)
        if (name == null || name.isEmpty()) {
            return false;
        }
    
        // B. Check if segments are non-empty
        if (segments.isEmpty()) {
            return false;
        }
        // Check if the route contains at least two stations
        if (stations.size() < 2) {
            return false;
        }

        // Check if it's a round trip, start and end stations are the same
        if (isRoundTrip && !stations.getFirst().equals(stations.getLast())) {
            return false;
        }

        // Check if it's not a round trip, start and end stations are different
        if (!isRoundTrip && stations.getFirst().equals(stations.getLast())) {
            return false;
        }
    
        // F. Check for duplicate segments
        Set<String> segmentNames = new HashSet<>();
        for (Segment segment : segments) {
            if (!segmentNames.add(segment.getName())) {
                return false; // Duplicate found
            }
        }
    
        // G. Verify each segment
        for (Segment segment : segments) {
            if (!segment.verify()) {
                return false; // Segment verification failed
            }
        }
    
        // All conditions passed
        return true;
    }
        /**
     * Creates a CFOSEvent to close an action.
     *
     * @return         the CFOSEvent created for the close action
     */
    public CFOSEvent close() {
        CFOSEvent event = new CFOSEvent(name, time, Action.CLOSE);

        // Notify all registered listeners
        notifyListeners(event);
        return event;
    }
        /**
     * Creates a CFOSEvent to open an action.
     *
     * @return         the CFOSEvent created for the open action
     */
    public CFOSEvent open() {
        CFOSEvent event = new CFOSEvent(name, time, Action.OPEN);

        // Notify all registered listeners
        notifyListeners(event);

        return event;
    }

    @Override
	public String toString() {
		String str = "[";
		for (Segment s : segments)
			str += s.getName() + (segments.get(segments.size() - 1) == s ? "]" : ", ");

		return "Route [name=" + name + ", isRoundTrip=" + isRoundTrip + ", status=" + status + ", segments="
				+ (segments == null || segments.size() == 0 ? "none" : str) + ", verified=" + (verify() ? "Yes" : "No")
				+ "]";
	}

}
