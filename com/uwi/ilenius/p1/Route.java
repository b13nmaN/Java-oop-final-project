/**
 * Route represents a transportation route consisting of segments and trains.
 * It implements Verifiable, Openable, and Closeable interfaces.
 */
package com.uwi.ilenius.p1;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a transportation route.
 */
public class Route implements Verifiable, Openable, Closeable {
    private String name; // The name of the route
    private boolean isRoundTrip; // Indicates if the route is a round trip
    private RSStatus status = RSStatus.Open; // The status of the route
    private LinkedList<Segment> segments; // List of segments in the route
    private LinkedList<Train> trainsForRoute; // List of trains for the route
    private LinkedList<Station> stations; // List of stations in the route
    /**
     * Constructs a Route object.
     * 
     * @param name         The name of the route.
     * @param isRoundTrip  Indicates if the route is a round trip.
     * @param status       The status of the route.
     */
    public Route(String name, boolean isRoundTrip, LinkedList<Station> stations) {
        this.name = name;
        this.isRoundTrip = isRoundTrip;
        this.stations = stations;
        this.segments = new LinkedList<>();
        this.trainsForRoute = new LinkedList<>();
    }

    /**
     * Checks if the route is a round trip.
     * 
     * @return true if the route is a round trip, false otherwise.
     */
    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    /**
     * Gets the start station of the route.
     * 
     * @return The start station of the route.
     */
    public Station getStart() {
        if (!segments.isEmpty()) {
            return segments.getFirst().getSegmentStart();
        }
        return null;
    }
    /**
     * A description of the function that returns a Station object with the given name, or null if not found.
     *
     * @param  name  the name of the station to search for
     * @return       the Station object with the given name, or null if not found
     */
    private Station getStationByName(String name) {
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
     }

    /**
     * Gets the name of the route.
     * 
     * @return The name of the route.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the status of the route.
     * 
     * @return The status of the route.
     */
    public RSStatus getStatus() {
        return status;
    }

    /**
     * Gets the segments of the route.
     * 
     * @return The segments of the route.
     */
    public LinkedList<Segment> getSegments() {
        return segments;
    }

    /**
     * Gets the stations of the route.
     * 
     * @return The stations of the route.
     */
    public LinkedList<Station> getStations() {
        return stations;
    }


    /**
     * Gets the end station of the route.
     * 
     * @return The end station of the route.
     */
    public Station getEnd() {
        if (!segments.isEmpty()) {
            return segments.getLast().getSegmentEnd();
        }
        return null;
    }

    /**
     * Gets the next station after the given station.
     * 
     * @param station  The name of the station.
     * @return The next station after the given station.
     */
    public Station getNextStation(String station) {
        ListIterator<Station> iterator = stations.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(station)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
            }
        }
        return null;
    }

    /**
     * Gets the previous station before the given station.
     * 
     * @param station    The name of the station.
     * @return The previous station before the given station.
     */
    public String getPreviousStation(String station) {
        ListIterator<Station> iterator = stations.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(station)) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getName();
                }
            }
        }
        return null;
    }
        /**
     * Retrieves a list of trains that belong to a specific route.
     *
     * @param  trainSystem  the TrainSystem containing all trains
     * @return              a list of trains that belong to the specified route
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
     * Checks if it's possible to get to the given station.
     * 
     * @param station  The name of the station.
     * @return true if it's possible to get to the station, false otherwise.
     */
    public boolean canGetTo(String sname) {
        Station stationObj = getStationByName(sname);
        return stationObj.isOpen();
    }

    /**
     * Adds a segment to the route.
     * 
     * @param segment  The segment to add.
     */
    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    /**
     * Adds multiple segments to the route.
     * 
     * @param segments  The segments to add.
     */
    public void addSegments(LinkedList<Segment> segments) {
        this.segments.addAll(segments);
    }

    /**
     * Removes a train from the route.
     * 
     * @param train  The train to remove.
     */
    public void removeTrain(Train train) {
        trainsForRoute.remove(train);
    }

    /**
     * Checks if the route contains the specified train.
     * 
     * @param train  The train to check.
     * @return true if the route contains the train, false otherwise.
     */
    public boolean containsTrain(Train train) {
        return trainsForRoute.contains(train);
    }

    /**
     * Removes a segment from the route.
     * 
     * @param segment  The segment to remove.
     */
    public void removeSegment(Segment segment) {
        segments.remove(segment);
    }

    /**
     * Checks if the route contains the specified segment.
     * 
     * @param segment  The segment to check.
     * @return true if the route contains the segment, false otherwise.
     */
    public boolean containsSegment(Segment segment) {
        return segments.contains(segment);
    }

    /**
     * Changes the light.
     * 
     * @param startOfSegment  The starting point of the segment.
     */
    public void changeLight(String startOfSegment) {
        // Not implemented for this example
    }

    /**
     * Verifies the validity of the route.
     * 
     * @return true if the route is valid, false otherwise.
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

        // C. If it's a round trip, check if start and end stations are the same
        if (isRoundTrip && !segments.getFirst().getSegmentStart().equals(segments.getLast().getSegmentEnd())) {
            return false;
        }

        // D. If it's not a round trip, check if start and end stations are different
        if (!isRoundTrip && segments.getFirst().getSegmentStart().equals(segments.getLast().getSegmentEnd())) {
            return false;
        }

        // E. Check if segments are properly sequenced
        for (int i = 1; i < segments.size(); i++) {
            if (!segments.get(i - 1).getSegmentEnd().equals(segments.get(i).getSegmentStart())) {
                return false;
            }
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

        // H. Check for loops (except for round trips)
        if (!isRoundTrip) {
            Set<String> visitedStations = new HashSet<>();
            String currentStation = segments.getFirst().getSegmentStart().getName();
            visitedStations.add(currentStation);
            for (Segment segment : segments) {
                if (!visitedStations.add(segment.getSegmentEnd().getName())) {
                    return false; // Loop detected
                }
                currentStation = segment.getSegmentEnd().getName();
            }
        }

        // All conditions passed
        return true;
    }

    /**
     * Closes the route.
     */
    public void close() {
        // Implement logic to close the route
        for (Segment segment : segments) {
            segment.close();
        }
    }

    /**
     * Opens the route.
     */
    public void open() {
        // Implement logic to open the route
        for (Segment segment : segments) {
            segment.open();
        }
    }
}
