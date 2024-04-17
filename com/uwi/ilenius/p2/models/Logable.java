package com.uwi.ilenius.p2.models;

import java.util.*;

import com.uwi.ilenius.p2.events.Event;

/**
 * Represents a loggable entity capable of logging events.
 */
abstract public class Logable {
    private LinkedList<Event> events;
    private LinkedList<String> stringEvents;

    /**
     * Constructor for Logable.
     * Initializes the log with an empty list of events.
     */
    public Logable() {
        this.events = new LinkedList<>();
    }

    /**
     * Adds an event to the log.
     *
     * @param event The event to be added to the log.
     */
    public void addToLog(Event event) {
        // Check if the log already contains the event
        if (!events.contains(event)) {
            events.add(event);
            // System.out.println("Added event: " + event);
        } 
    }
    

    /**
     * Retrieves the size of the log.
     *
     * @return The size of the log.
     */
    public int logSize() {
        return events.size();
    }

    /**
     * Checks if the log contains a sequence of events.
     *
     * @param events The sequence of events to check for.
     * @return true if the log contains all the events in the sequence, false otherwise.
     */
    public boolean contains(List<Event> events) {
        return this.events.containsAll(events);
    }

    /**
     * Checks if the log contains a sequence of events in order.
     *
     * @param events The sequence of events to check for.
     * @return true if the log contains all the events in the sequence in order, false otherwise.
     */
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

    /**
     * Validates the log.
     * This method should be implemented with custom validation logic.
     *
     * @return true if the log is valid, false otherwise.
     */
    public boolean validate() {
        boolean isValid = true;

        // Check if events list is not null
        if (events == null) {
            System.out.println("Error: Events list cannot be null.");
            isValid = false;
        }

        // Check if stringEvents list is not null
        if (stringEvents == null) {
            System.out.println("Error: String events list cannot be null.");
            isValid = false;
        }

        // Add more validation rules here if needed

        return isValid;
    }

    public List<Event> getEventsAsTypeEvent() {
        return new ArrayList<>(events);
    }

    /**
     * Retrieves all events in the log.
     *
     * @return A list containing all events in the log.
     */
    public LinkedList<String> getEvents() {
	    LinkedList<String> evnts = new LinkedList<String>();;
		if (events == null || events.size() == 0)
			return evnts;
		else {
			for (Event e : events)
				evnts.add(e.toString());
		}
		return evnts;
	}

	public LinkedList<String> getEvents(int time) {
		LinkedList<String> evnts = new LinkedList<String>();

		if (events == null || events.size() == 0)
			return evnts;
		else {

			ArrayList<Event> ets = new ArrayList<Event>();
			for (Event e : events)
				if (e.getTime() == time)
					ets.add(e);

			for (Event e : ets)
				evnts.add(e.toString());

			return evnts;
		}
	}

	public LinkedList<String> getEvents(String object) {
		LinkedList<String> evnts = new LinkedList<String>();

		if (events == null || events.size() == 0)
			return evnts;
		else {

			LinkedList<Event> ets = new LinkedList<Event>();
			for (Event e : events)
				if (e.getObject().equals(object))
					ets.add(e);

			for (Event e : ets)
				evnts.add(e.toString());

			return evnts;
		}
	}

	public LinkedList<String> getObjects() {
		LinkedList<String> objects = new LinkedList<String>();
		for (Event e : events)
			if (!objects.contains(e.getObject()))
				objects.add(e.getObject());
		return objects;
	}


    /**
     * Retrieves distinct objects involved in the log events.
     *
     * @return A set containing distinct object names involved in the log events.
     */
    public Set<String> distinctObjects() {
        Set<String> objects = new HashSet<>();
        for (Event event : events) {
            objects.add(event.getObject());
        }
        return objects;
    }

    @Override
	public String toString() {

		String evnts = "Events Log[";
		if (events == null || events.size() == 0)
			evnts += "no events]";
		else {
			for (Event e : events)
				evnts += (events.indexOf(e) == 0 ? "\n\t" : "\t") + e
						+ (events.indexOf(e) != events.size() - 1 ? "\n" : "\n\t]");
		}
		return evnts;
    }
}
