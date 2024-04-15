package com.uwi.ilenius.p2.models;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;


public class Route implements Verifiable, Openable, Closeable {
    private String name;
    private boolean isRoundTrip;
    private RSStatus status = RSStatus.Open;
    private LinkedList<Segment> segments;
    private LinkedList<Train> trainsForRoute;
    private TrainSystem trainSystem;
    private LinkedList<Station> stations;

    public Route(String name, boolean isRoundTrip, LinkedList<Station> stations) {
        this.name = name;
        this.isRoundTrip = isRoundTrip;
        this.stations = stations;
        // this.segments = new LinkedList<>();
        this.trainsForRoute = new LinkedList<>();
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public Station getStart() {
        if (!stations.isEmpty()) {
            return stations.getFirst();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public RSStatus getStatus() {
        return status;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }



    // Other attributes and methods remain unchanged

    public LinkedList<Segment> getSegmentsForRoute(TrainSystem trainSystem) {
        LinkedList<Segment> routeSegments = new LinkedList<>();
        LinkedList<Segment> segments = trainSystem.getSegments(); // Assuming getSegments() returns LinkedList<Segment>
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
      
      
    // Other methods remain unchanged


    public Station getEnd() {
        if (!stations.isEmpty()) {
            return stations.getLast();
        }
        return null;
    }


    // public void getSegmentsForRoute() {
    //     List<Train> trains = getTrainsForRoute(getName());
    //     for (Train train : trains) {
    //         Segment segment = trainSystem.getSegment(train.getSegmentName());
    //         if (!segments.contains(segment)) {
    //             segments.add(segment);
    //         }
    //     }
    // }



    // Other attributes and methods remain unchanged

    public Station getNextStation(String station, boolean isAtStart) {
        // Implement logic to get the next station
        ListIterator<Station> iterator = stations.listIterator();
        while (iterator.hasNext()) {
            Station currentStation = iterator.next();
            if (currentStation.getName().equals(station)) {
                if (isAtStart && iterator.hasNext()) {
                    return iterator.next();
                } else if (!isAtStart && iterator.hasNext()) {
                    iterator.next(); // Move to the next station
                    if (iterator.hasNext()) {
                        return iterator.next();
                    }
                }
            }
        }
        return null;
    }

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

    // Other methods remain unchanged


    



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


    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public void addSegments(LinkedList<Segment> segments) {
        // Implement logic to add multiple segments
        this.segments.addAll(segments);
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
    
        // E. Check if segments are properly sequenced
        // for (int i = 1; i < segments.size(); i++) {
        //     if (!segments.get(i - 1).getSegmentEnd().equals(segments.get(i).getSegmentStart())) {
        //         return false;
        //     }
        // }
    
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
        // if (!isRoundTrip) {
        //     Set<String> visitedStations = new HashSet<>();
        //     String currentStation = segments.getFirst().getSegmentStart().getName();
        //     visitedStations.add(currentStation);
        //     for (Segment segment : segments) {
        //         if (!visitedStations.add(segment.getSegmentEnd().getName())) {
        //             return false; // Loop detected
        //         }
        //         currentStation = segment.getSegmentEnd().getName();
        //     }
        // }
    
        // All conditions passed
        return true;
    }

    public CFOSEvent close() {
        int time = trainSystem.getCurrentTime();
        return new CFOSEvent("Route", time, Action.CLOSE);
    }

    public CFOSEvent open() {

        int time = trainSystem.getCurrentTime();
        return new CFOSEvent("Route", time, Action.OPEN);
    }

}
