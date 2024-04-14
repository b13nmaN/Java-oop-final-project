package com.uwi.ilenius.p2.models;
import java.util.*;

import com.uwi.ilenius.p2.events.Event;

public class Logable {
    private LinkedList<Event> events;

    public Logable() {
        this.events = new LinkedList<>();
    }

    // Add an event to the log
    public void addToLog(Event event) {
        events.add(event);
    }

    // Get the size of the log
    public int logSize() {
        return events.size();
    }

    // Check if the log contains a sequence of events
    public boolean contains(List<Event> events) {
        return this.events.containsAll(events);
    }

    // Check if the log contains a sequence of events in order
    public boolean containsInSequence(List<Event> events) {
        List<Event> logCopy = new ArrayList<>(this.events);
        for (Event event : events) {
            if (!logCopy.contains(event)) {
                return false;
            }
            logCopy = logCopy.subList(logCopy.indexOf(event) + 1, logCopy.size());
        }
        return true;
    }

    // Validate the log
    public boolean validate() {
        // TODO Implement validation logic
        // Custom validation logic can be added here
        return true;
    }

    // Get all events in the log
    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    // Get all unique objects involved in the log events
    public Set<String> getObjects() {
        Set<String> objects = new HashSet<>();
        for (Event event : events) {
            objects.add(event.getObjectName());
        }
        return objects;
    }

    // Get distinct objects involved in the log events
    public Set<String> distinctObjects() {
        Set<String> objects = new HashSet<>();
        for (Event event : events) {
            objects.add(event.getObjectName());
        }
        return objects;
    }
}


