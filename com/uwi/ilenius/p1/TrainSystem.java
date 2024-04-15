package com.uwi.ilenius.p1;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

/**
* The TrainSystem class represents the overall train system and manages stations, segments, routes, and trains.
* It implements the Verifiable interface.
*/
public class TrainSystem implements Verifiable {
    private SystemStatus status;
    private LinkedList<Station> stations;
    private LinkedList<Segment> segments;
    private LinkedList<Route> routes;
    private LinkedList<Train> trains;
 
    /**
     * Constructs a new TrainSystem object with an initial status of "Initialised"
     * and empty lists for stations, segments, routes, and trains.
     */
    public TrainSystem() {
        this.status = SystemStatus.Initialised;
        this.stations = new LinkedList<>();
        this.segments = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.trains = new LinkedList<>();
    }
 
    /**
     * Adds a new station to the system with the given name.
     * If a station with the same name already exists, no new station is added.
     *
     * @param sname the name of the station to add
     */
    public void addStation(String sname) {
        if (!containsStation(sname)) {
            stations.add(new Station(sname));
        }
    }
 
    /**
     * Removes a station from the system with the given name.
     *
     * @param sname the name of the station to remove
     */
    public void removeStation(String sname) {
        stations.remove(new Station(sname));
    }
    /**
     * Opens a station with the given name.
     *
     * @param sname the name of the station to open
     */
    public void openStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.open();
        }
    }
 
    /**
     * Closes a station with the given name.
     *
     * @param sname the name of the station to close
     */
    public void closeStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.close();
        }
    }
 
    /**
     * Adds a new segment to the system with the given name, start station, and end station.
     * If a segment with the same name already exists, no new segment is added.
     *
     * @param sname the name of the segment to add
     * @param start the name of the start station for the segment
     * @param sEnd  the name of the end station for the segment
     */
    public void addSegment(String sname, String start, String sEnd) {
        if (!containsSegment(sname)) {
            Station startStation = getStationByName(start);
            Station endStation = getStationByName(sEnd);
            if (startStation != null && endStation != null) {
                segments.add(new Segment(sname, startStation, endStation));
            }
        }
    }
 
    /**
     * Removes a segment from the system with the given name.
     *
     * @param sname the name of the segment to remove
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
     * Opens a segment with the given name.
     *
     * @param sname the name of the segment to open
     */
    public void openSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.open();
        }
    }
 
    /**
     * Closes a segment with the given name.
     *
     * @param sname the name of the segment to close
     */
    public void closeSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.close();
        }
    }
 
    /**
     * Adds a new route to the system with the given name, round trip flag, and status.
     * If a route with the same name already exists, no new route is added.
     *
     * @param rName       the name of the route to add
     * @param isRoundTrip a flag indicating whether the route is a round trip or not
     * @param stations      ordered list of stations in the route
     */
    public void addRoute(String rName, boolean isRoundTrip, LinkedList<Station> stations) {
        if (!containsRoute(rName)) {
            routes.add(new Route(rName, isRoundTrip, stations));
        }
    }
 
    /**
     * Removes a route from the system with the given name.
     *
     * @param rName the name of the route to remove
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
     * Opens a route with the given name.
     *
     * @param rName the name of the route to open
     */
    public void openRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.open();
        }
    }
 
    /**
     * Closes a route with the given name.
     *
     * @param rName the name of the route to close
     */
    public void closeRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.close();
        }
    }
 
    /**
     * Adds a new train to the system.
     *
     * @param train the train to add
     */
    public void addTrain() {
        int nextId = (int) (Math.random() * Integer.MAX_VALUE);
        while (containsTrain(nextId)) {
            nextId = (int) (Math.random() * Integer.MAX_VALUE);
        }
        Train train = new Train(nextId);
        trains.add(train); 
        
    }
 
    /**
     * Removes a train from the system with the given ID.
     *
     * @param id the ID of the train to remove
     */
    public void removeTrain(Integer id) {
        // iterate through trains List and remove if found
        for (ListIterator<Train> iterator = trains.listIterator(); iterator.hasNext(); ) {
            Train train = iterator.next();
            if (train.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
 
    /**
     * Registers a train to a route at a specific start time.
     *
     * @param trainId   the ID of the train to register
     * @param routeName the name of the route to register the train to
     * @param startTime the start time for the train on the route
     */
    public void registerTrain(Integer trainId, String routeName, int startTime) {
        Train train = getTrainById(trainId);
        Route route = getRouteByName(routeName);
        if (train != null && route != null) {
            train.register(startTime);
        }
    }
 
    /**
     * Deregisters a train from the system and removes it.
     *
     * @param trainId the ID of the train to deregister
     */
    public void deRegisterTrain(Integer trainId) {
        trains.remove(getTrainById(trainId));
        removeTrain(trainId);
    }
 
    /**
     * Checks if a station with the given name exists in the system.
     *
     * @param station the name of the station to check
     * @return true if the station exists, false otherwise
     */
    public boolean containsStation(String station) {
        return getStationByName(station) != null;
    }
 
    /**
     * Checks if a segment with the given name exists in the system.
     *
     * @param segment the name of the segment to check
     * @return true if the segment exists, false otherwise
     */
    public boolean containsSegment(String segment) {
        return getSegmentByName(segment) != null;
     }
     
     /**
     * Checks if a route with the given name exists in the system.
     *
     * @param route the name of the route to check
     * @return true if the route exists, false otherwise
     */
     public boolean containsRoute(String route) {
        return getRouteByName(route) != null;
     }
     
     /**
     * Checks if a train with the given ID exists in the system.
     *
     * @param trainId the ID of the train to check
     * @return true if the train exists, false otherwise
     */
     public boolean containsTrain(Integer trainId) {
        return getTrainById(trainId) != null;
     }
     
     /**
     * Returns information about a station with the given name.
     *
     * @param station the name of the station
     * @return a string containing the station's name and status, or null if the station doesn't exist
     */
     public String getStationInfo(String station) {
        Station s = getStationByName(station);
        if (s != null) {
            return "Station Name: " + s.getName() + "\n" +
                    "Status: " + s.getStatus().toString() + "\n";
        }
        return null;
     }
     
     /**
     * Returns information about a segment with the given name.
     *
     * @param segmentName the name of the segment
     * @return a string containing the segment's name, status, start station, and end station,
     *         or null if the segment doesn't exist
     */
     public String getSegmentInfo(String segmentName) {
        Segment s = getSegmentByName(segmentName);
        if (s != null) {
            return "Segment Name: " + s.getName() + "\n" +
                    "Status: " + s.getStatus().toString() + "\n" +
                    "Start Station: " + s.getSegmentStart().getName() + "\n" +
                    "End Station: " + s.getSegmentEnd().getName() + "\n";
        }
        return null;
     }
     
     /**
     * Returns information about a route with the given name.
     *
     * @param routeName the name of the route
     * @return a string containing the route's name, round trip flag, status, and list of segments,
     *         or null if the route doesn't exist
     */
     public String getRouteInfo(String routeName) {
        Route r = getRouteByName(routeName);
        if (r != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Route Name: ").append(r.getName()).append("\n");
            sb.append("Is Round Trip: ").append(r.isRoundTrip()).append("\n");
            sb.append("Status: ").append(r.getStatus().toString()).append("\n");
            sb.append("Segments:\n");
     
            // Iterate through the segments using entrySet() to access both key and value
            for (Segment segment : r.getSegments()) {
                sb.append("- ").append(segment.getName()).append("\n");
            }
            return sb.toString();
        }
        return null;
     }
     
     /**
     * Returns information about a train with the given ID.
     *
     * @param trainId the ID of the train
     * @return a string containing the train's ID, current location, and assigned route,
     *         or null if the train doesn't exist
     */
     public String getTrainInfo(Integer trainId) {
        Train t = getTrainById(trainId);
        if (t != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Train ID: ").append(t.getId()).append("\n");
            sb.append("Current Location: ").append(t.getCurrentLocation() != null ? t.getCurrentLocation().getName() : "Unknown").append("\n");
            sb.append("Route: ").append(t.getRoute() != null ? t.getRoute().getName() : "None").append("\n");
            return sb.toString();
        }
        return null;
     }
     
     /**
     * Sets the status of the train system to "Initialised".
     */
     public void setToWorking() {
        status = SystemStatus.Initialised;
     }
     
     /**
     * Sets the status of the train system to "Deadlocked".
     */
     public void setPaused() {
        status = SystemStatus.Deadlocked;
     }
     
     /**
     * Sets the status of the train system to "Finished".
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
     * Verifies the integrity of the train system by checking each object for validity and ensuring
     * no duplicates exist.
     *
     * @return true if the train system is valid, false otherwise
     */
     
     //\\\\\\\\\\\\\\\\\\\\\\\\\\\\ START OF GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
           /**
       * Returns the list of routes in the train system.
       *
       * @return the list of routes in the train system
       */
      public LinkedList<Route> getRoutes() {
          return routes;
      }
            /**
       * Returns the list of stations in the train system.
       *
       * @return the list of stations in the train system
       */
      public LinkedList<Station> getStations() {
          return stations;
      }
            /**
       * Returns the list of segments in the train system.
       *
       * @return the list of segments in the train system
       */
      public LinkedList<Segment> getSegments() {
          return segments;
      }
            /**
       * Returns the status of the train system.
       *
       * @return the status of the train system
       */
      public SystemStatus getStatus() {
          return status;
      }

            /**
       * Returns the list of trains in the train system.
       *
       * @return the list of trains in the train system
       */
      public LinkedList<Train> getTrains() {
          return trains;
      }
     /**
     * Returns the Station object with the given name, or null if not found.
     *
     * @param name the name of the station
     * @return the Station object with the given name, or null if not found
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
     * Returns the Segment object with the given name, or null if not found.
     *
     * @param name the name of the segment
     * @return the Segment object with the given name, or null if not found
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
     * Returns the Route object with the given name, or null if not found.
     *
     * @param name the name of the route
     * @return the Route object with the given name, or null if not found
     */
     public Route getRouteByName(String name) {
        for (Route route : routes) {
            if (route.getName().equals(name)) {
                return route;
            }
        }
        return null;
     }
     
    //\\\\\\\\\\\\\\\\\\\\\\\ END OF GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     /**
     * Returns the Train object with the given ID, or null if not found.
     *
     * @param id the ID of the train
     * @return the Train object with the given ID, or null if not found
     */
     private Train getTrainById(Integer id) {
        for (Train train : trains) {
            if (train.getId().equals(id)) {
                return train;
            }
        }
        return null;
     }

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
     * Advances the train system to the next state.
     */
     public void advance() {
        status = SystemStatus.Operational;
     }

}
