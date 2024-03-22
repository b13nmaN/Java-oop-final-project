package com.uwi.ilenius.p1;

import java.util.HashMap;
import java.util.Map;

public class Route {
    private String name;
    private boolean isRoundTrip;
    private RSStatus status;
    private Map<String, Segment> segments;

    public Route(String name, boolean isRoundTrip, RSStatus status) {
        this.name = name;
        this.isRoundTrip = isRoundTrip;
        this.status = status;
        this.segments = new HashMap<>();
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public Station getStart() {
        if (!segments.isEmpty()) {
            return segments.get(segments.keySet().iterator().next()).getStart();
        }
        return null;
    }

    public Station getEnd() {
        if (!segments.isEmpty()) {
            return segments.get(segments.keySet().iterator().next()).getEnd();
        }
        return null;
    }

    public Station getNextStation(String station) {
        // Implement logic to get the next station
        return null;
    }

    public Station getPreviousStation(String station) {
        // Implement logic to get the previous station
        return null;
    }

    public boolean canGetTo(String station) {
        // Implement logic to check if it's possible to get to the station
        return false;
    }

    public void addSegment(Segment segment) {
        segments.put(segment.getName(), segment);
    }

    public void addSegments(OrderedSet<Segment> segments) {
        // Implement logic to add multiple segments
    }

    public void removeSegment(String segment) {
        segments.remove(segment);
    }

    public boolean containsSegment(String segment) {
        return segments.containsKey(segment);
    }

    public void changeLight(String startOfSegment) {
        // Implement logic to change the light
    }

    public boolean verify() {
        // Implement logic to verify the route
        return false;
    }

    public void close() {
        // Implement logic to close the route
    }

    public void open() {
        // Implement logic to open the route
    }
}

class Segment {
    private String name;
    private Station start;
    private Station end;

    public Segment(String name, Station start, Station end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public Station getStart() {
        return start;
    }

    public Station getEnd() {
        return end;
    }
}

class Station {
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class RSStatus {
    // Define RSStatus properties and methods
}

class OrderedSet<T> {
    // Define OrderedSet properties and methods
}
