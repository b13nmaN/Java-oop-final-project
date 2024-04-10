package com.uwi.ilenius.p1;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;


public class Route implements Verifiable, Openable, Closeable {
    private String name;
    private boolean isRoundTrip;
    private RSStatus status;
    private LinkedList<Segment> segments;
    private LinkedList<Train> trainsForRoute;

    public Route(String name, boolean isRoundTrip, RSStatus status) {
        this.name = name;
        this.isRoundTrip = isRoundTrip;
        this.status = status;
        this.segments = new LinkedList<>();
        this.trainsForRoute = new LinkedList<>();
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public Station getStart() {
        if (!segments.isEmpty()) {
            return segments.getFirst().getSegmentStart();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public RSStatus getStatus() {
        return status;
    }

    public LinkedList<Segment> getSegments() {
        return segments;
    }

    public Station getEnd() {
        if (!segments.isEmpty()) {
            return segments.getLast().getSegmentEnd();
        }
        return null;
    }

    public Station getNextStation(String station) {
        // Implement logic to get the next station
        return null;
    }

    public Station getPreviousStation(String station) {
        // Implement logic to get the previous station
        // Not implemented for this example
        return null;
    }

    public boolean canGetTo(String station) {
        // Implement logic to check if it's possible to get to the station
        // Not implemented for this example
        return false;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public void addSegments(LinkedList<Segment> segments) {
        // Implement logic to add multiple segments
        this.segments.addAll(segments);
    }

    public void addTrain(Train train) {
        trainsForRoute.add(train);
    }

    public void addTrains(LinkedList<Train> trains) {
        // Implement logic to add multiple trains
        trainsForRoute.addAll(trains);
    }

    public void removeTrain(Train train) {
        trainsForRoute.remove(train);
    }

    public boolean containsTrain(Train train) {
        return trainsForRoute.contains(train);
    }

    public void removeSegment(Segment segment) {
        segments.remove(segment);
    }

    public boolean containsSegment(Segment segment) {
        return segments.contains(segment);
    }

    public void changeLight(String startOfSegment) {
        // Implement logic to change the light
        // Not implemented for this example
    }

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
    

    public void close() {
        // Implement logic to close the route
        for (Segment segment : segments) {
            segment.close();
        }
    }

    public void open() {
        // Implement logic to open the route
        for (Segment segment : segments) {
            segment.open();
        }
    }
}
