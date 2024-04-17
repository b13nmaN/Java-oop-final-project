package com.uwi.ilenius.p2.models;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

import com.uwi.ilenius.p2.enums.SystemStatus;
import com.uwi.ilenius.p2.interfaces.Verifiable;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.enums.ObjectType;

/**
 * The TrainSystem class represents the entire train system, managing stations, segments, routes, and trains.
 * It implements the Verifiable interface to ensure the validity of the system's components.
 */
public class TrainSystem implements Verifiable {
    private SystemStatus status;
    private LinkedList<Station> stations;
    private LinkedList<Segment> segments;
    private LinkedList<Route> routes;
    private LinkedList<Train> trains;
    private Simulator simulator;
    private LinkedList<Event> events;
    private LinkedList<TrafficLight> trafficLights;

    /**
     * Constructs a new TrainSystem object with the given simulator.
     *
     * @param simulator the simulator object used to manage the simulation time
     */
    public TrainSystem() {
        this.status = SystemStatus.Initialised;
        this.stations = new LinkedList<>();
        this.segments = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.trains = new LinkedList<>();
        this.trafficLights = new LinkedList<>();
    }


    public void setEvents(LinkedList<Event> events) {
        this.events = events;
    }
    /**
 * Retrieves an object of the given type by name.
 *
 * @param type the type of object to retrieve
 * @param name the name of the object to retrieve
 * @return the object of the given type with the given name, or null if no such object exists
 */
public Object getObjectByName(ObjectType type, String name) {
    Object obj = null;
    switch (type) {
        case Station_:
            obj = stations.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
            break;
        case Segment_:
            obj = segments.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
            break;
        case Route_:
            obj = routes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
            break;
        case Train_:
            obj = trains.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
            break;
        case TrafficLight_:
            break;
    }
    return obj;
}


        /**
     * Retrieves all events from the stations, segments, routes, and trains in the TrainSystem.
     *
     * @return a LinkedList containing all the events from the TrainSystem
     */
    public LinkedList<Event> getAllEvents() {
        LinkedList<Event> allEvents = new LinkedList<>();
    
        // Iterate through each station and retrieve events
        for (Station station : stations) {
            Logable logable = (Logable) getObjectByName(ObjectType.Station_, station.getName());
            if (logable != null) {
                allEvents.addAll(logable.getEventsAsTypeEvent());
            }
        }
    
        // Iterate through each segment and retrieve events
        for (Segment segment : segments) {
            Logable logable = (Logable) getObjectByName(ObjectType.Segment_, segment.getName());
            if (logable != null) {
                allEvents.addAll(logable.getEventsAsTypeEvent());
            }
        }
    
        // Iterate through each route and retrieve events
        for (Route route : routes) {
            Logable logable = (Logable) getObjectByName(ObjectType.Route_, route.getName());
            if (logable != null) {
                allEvents.addAll(logable.getEventsAsTypeEvent());
            }
        }
    
        // Iterate through each train and retrieve events
        for (Train train : trains) {
            Logable logable = (Logable) getObjectByName(ObjectType.Train_, train.getName());
            if (logable != null) {
                allEvents.addAll(logable.getEventsAsTypeEvent());
            }
        }
    
        return allEvents;
    }
    
        /**
     * Displays all the events in the TrainSystem.
     *
     * @return void
     */
    public void displayEvents() {
        LinkedList<Event> allEvents = getAllEvents();
        System.out.println("List of Events:");
        for (Event event : allEvents) {
            System.out.println(event.toString());
        }
    }


    
    /**
     * Adds a new station with the given name to the train system.
     *
     * @param sname the name of the station to be added
     */
    public void addStation(String sname) {
        if (!containsStation(sname)) {
            stations.add(new Station(sname));
        }
    }

    /**
     * Removes the station with the given name from the train system.
     *
     * @param sname the name of the station to be removed
     */
    public void removeStation(String sname) {
        stations.remove(new Station(sname));
    }

    /**
     * Opens the station with the given name.
     *
     * @param sname the name of the station to be opened
     */
    public void openStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.open();
        }
    }

    /**
     * Closes the station with the given name.
     *
     * @param sname the name of the station to be closed
     */
    public void closeStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.close();
        }
    }

    /**
     * Adds a new segment with the given name, start station, and end station to the train system.
     *
     * @param sname the name of the segment to be added
     * @param start the name of the start station for the segment
     * @param sEnd  the name of the end station for the segment
     */
    public void addSegment(String sname, String start, String sEnd) {
        if (containsSegment(sname)) {
            System.out.println("Segment with name " + sname + " already exists.");
            return;
        }
    
        Station startStation = getStationByName(start);
        if (startStation == null) {
            System.out.println("Start station with name " + start + " not found.");
            return;
        }
    
        Station endStation = getStationByName(sEnd);
        if (endStation == null) {
            System.out.println("End station with name " + sEnd + " not found.");
            return;
        }
    
        // If startStation and endStation are both found, add the segment
        segments.add(new Segment(sname, startStation, endStation));
    }
    

    /**
     * Removes the segment with the given name from the train system.
     *
     * @param sname the name of the segment to be removed
     */
    public void removeSegment(String sname) {
        // iterate through segments List and remove if found
        for (ListIterator<Segment> iterator = segments.listIterator(); iterator.hasNext(); ) {
            Segment segment = iterator.next();
            if (segment.getName().equals(sname)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Opens the segment with the given name.
     *
     * @param sname the name of the segment to be opened
     */
    public void openSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.open();
        }
    }

    /**
     * Closes the segment with the given name.
     *
     * @param sname the name of the segment to be closed
     */
    public void closeSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.close();
        }
    }

    /**
     * Adds a new route with the given name, round-trip status, and a list of stations to the train system.
     *
     * @param rName     the name of the route to be added
     * @param isRoundTrip whether the route is a round-trip or not
     * @param stations  the list of stations for the route
     */
    public void addRoute(String rName, boolean isRoundTrip, LinkedList<Station> stations) {
        if (!containsRoute(rName)) {
            routes.add(new Route(rName, isRoundTrip, stations));
        }
    }

    /**
     * Removes the route with the given name from the train system.
     *
     * @param rName the name of the route to be removed
     */
    public void removeRoute(String rName) {
        // iterate through routes List and remove if found
        for (ListIterator<Route> iterator = routes.listIterator(); iterator.hasNext(); ) {
            Route route = iterator.next();
            if (route.getName().equals(rName)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Opens the route with the given name.
     *
     * @param rName the name of the route to be opened
     */
    public void openRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.open();
        }
    }

    /**
     * Closes the route with the given name.
     *
     * @param rName the name of the route to be closed
     */
    public void closeRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.close();
        }
    }

    /**
     * Adds a new train with the given name to the train system.
     *
     * @param tname the name of the train to be added
     */
    public void addTrain(String tname) {
        int nextId = 1;
        while (containsTrain(tname)) {
            nextId = (int) (Math.random() * Integer.MAX_VALUE);
        }
        Train train = new Train(nextId, tname);
        trains.add(train);
    }

    /**
     * Removes the train with the given name from the train system.
     *
     * @param tname the name of the train to be removed
     */
    public void removeTrain(String tname) {
        // iterate through trains List and remove if found
        for (ListIterator<Train> iterator = trains.listIterator(); iterator.hasNext(); ) {
            Train train = iterator.next();
            if (train.getName().equals(tname)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Registers the train with the given name to the route with the given name.
     *
     * @param tname the name of the train to be registered
     * @param rName the name of the route to register the train on
     */
    public void registerTrain(String tname, String rName) {
        for (ListIterator<Train> iterator = trains.listIterator(); iterator.hasNext(); ) {
            Train train = iterator.next();
            if (train.getName().equals(tname)) {
                train.changeRoute(getRouteByName(rName));
                train.setTimeRegistered(100); // get the current time of the simulation
                break;
            }
        }
    }

    /**
     * Deregisters the train with the given name from its current route and removes it from the train system.
     *
     * @param tname the name of the train to be deregistered
     */
    public void deRegisterTrain(String tname) {
        Train train = getTrainByName(tname);
        train.deregister();
        removeTrain(tname);   
    }

    /**
     * Returns the current time of the simulation.
     *
     * @return the current time of the simulation
     */
    public int getCurrentTime() {
        return simulator.getCurrentTime();
    }

    /**
     * Sets the current time of the simulation.
     *
     * @param currentTime the new current time of the simulation
     */
    public void setCurrentTime(int currentTime) {
        simulator.setCurrentTime(currentTime);
    }

    /**
     * Checks if the train system contains a station with the given name.
     *
     * @param station the name of the station to check for
     * @return true if the station is found, false otherwise
     */
    public boolean containsStation(String station) {
        return getStationByName(station) != null;
    }

    /**
     * Checks if the train system contains a segment with the given name.
     *
     * @param segment the name of the segment to check for
     * @return true if the segment is found, false otherwise
     */
    public boolean containsSegment(String segment) {
        return getSegmentByName(segment) != null;
    }

    /**
     * Checks if the train system contains a route with the given name.
     *
     * @param route the name of the route to check for
     * @return true if the route is found, false otherwise
     */
    public boolean containsRoute(String route) {
        return getRouteByName(route) != null;
    }

    /**
     * Checks if the train system contains a train with the given name.
     *
     * @param tname the name of the train to check for
     * @return true if the train is found, false otherwise
     */
    public boolean containsTrain(String tname) {
        return getTrainByName(tname) != null;
    }


    /**
     * Sets the train system's status to WORKING.
     */
    public void setToWorking() {
        status = SystemStatus.Initialised;
    }

    /**
     * Sets the train system's status to FINISHED.
     */
    public void setStopped() {
        status = SystemStatus.Finished;
    }

    /**
     * Returns the current status of the train system.
     *
     * @return the current status of the train system
     */
    public SystemStatus currentStatus() {
        return status;
    }

    /**
     * Verifies the validity of the train system's components.
     *
     * @return true if all components are valid, false otherwise
     */
    public boolean verify() {
        // A. Verify each object in stations
        Set<String> stationNames = new HashSet<>();
        for (Station station : stations) {
            if (!station.verify() || !stationNames.add(station.getName())) {
                return false; // Verification failed or duplicate station found
            }
        }
    
        // B. Verify each object in segments
        Set<String> segmentNames = new HashSet<>();
        for (Segment segment : segments) {
            if (!segment.verify() || !segmentNames.add(segment.getName())) {
                return false; // Verification failed or duplicate segment found
            }
        }
    
        // C. Verify each object in routes
        Set<String> routeNames = new HashSet<>();
        for (Route route : routes) {
            if (!route.verify() || !routeNames.add(route.getName())) {
                return false; // Verification failed or duplicate route found
            }
        }
    
        // D. Verify each object in trains
        Set<Integer> trainIds = new HashSet<>();
        for (Train train : trains) {
            if (!train.verify() || !trainIds.add(train.getId())) {
                return false; // Verification failed or duplicate train found
            }
        }
    
        // All objects verified and no duplicates found
        return true;
    }

    /**
     * Advances the train system's simulation.
     */
    public LinkedList<Event> advance() {
        LinkedList<Event> events = getAllEvents();
        return events;
    }

    /**
     * Returns the station object with the given name.
     *
     * @param name the name of the station
     * @return the station object, or null if not found
     */
    public Station getStationByName(String name) {
        for (Station station : stations) {
            String station2 = station.getName();
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Returns the segment object with the given name.
     *
     * @param name the name of the segment
     * @return the segment object, or null if not found
     */
    public Segment getSegmentByName(String name) {
        for (Segment segment : segments) {
            if (segment.getName().equals(name)) {

                return segment;
            }
        }
        return null;
    }

    /**
     * Returns the route object with the given name.
     *
     * @param name the name of the route
     * @return the route object, or null if not found
     */
    public Route getRouteByName(String name) {
        for (Route route : routes) {
            if (route.getName().equals(name)) {
                return route;
            }
        }
        return null;
    }

    /**
     * Returns the train object with the given name.
     *
     * @param tname the name of the train
     * @return the train object, or null if not found
     */
    public Train getTrainByName(String tname) {
        for (Train train : trains) {
            if (train.getName().equals(tname)) {
                return train;
            }
        }
        return null;
    }

    /**
     * Returns the list of trains in the train system.
     *
     * @return the list of trains
     */
    public LinkedList<Train> getTrains() {
        return trains;
    }

    /**
     * Returns the list of stations in the train system.
     *
     * @return the list of stations
     */
    public LinkedList<Station> getStations() {
        return stations;
    }

    /**
     * Returns the list of segments in the train system.
     *
     * @return the list of segments
     */
    public LinkedList<Segment> getSegments() {
        return segments;
    }

    /**
     * Returns a string representation of the train system, including its stations, segments, routes, and trains.
     *
     * @return a string representation of the train system
     */
   public void validate(){
  
   }
}
