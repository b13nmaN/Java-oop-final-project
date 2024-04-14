package com.uwi.ilenius.p2.models;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;


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
        ListIterator<Segment> iterator = segments.listIterator();
        Segment currentSegment = null;
        while (iterator.hasNext()) {
            currentSegment = iterator.next();
            if (currentSegment.getSegmentStart().getName().equals(station)) {
                if (iterator.hasNext()) {
                    return iterator.next().getSegmentEnd();
                } else if (isRoundTrip) {
                    return segments.getFirst().getSegmentStart();
                }
            }
        }
        return null;
    }

    public String getPreviousStation(String station, boolean isAtStart) {
        // Implement logic to get the previous station by using linked list and iterators
        ListIterator<Segment> iterator = segments.listIterator();
        Segment currentSegment = null;
        while (iterator.hasNext()) {
            currentSegment = iterator.next();
            if (currentSegment.getSegmentEnd().getName().equals(station)) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSegmentStart().getName();
                } else if (!isAtStart) {
                    return segments.getLast().getSegmentEnd().getName();
                }
            }
        }
        return null;
    }
    

    public boolean canGetTo(String station) {
        // Implement logic to check if it's possible to get to the station
        ListIterator<Segment> iterator = segments.listIterator();
        Segment currentSegment = null;
        while (iterator.hasNext()) {
            currentSegment = iterator.next();
            if (currentSegment.getSegmentStart().getName().equals(station)) {
                return true;
            }
        }
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

    public RSStatus open() {
        // Implement logic to open the route
        status = RSStatus.Open;
        return status;
    }
}
