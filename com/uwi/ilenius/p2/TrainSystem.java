package com.uwi.ilenius.p2;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Set;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.enums.SystemStatus;
import com.uwi.ilenius.p2.interfaces.Verifiable;

public class TrainSystem implements Verifiable {
    private SystemStatus status;
    private LinkedList<Station> stations;
    private LinkedList<Segment> segments;
    private LinkedList<Route> routes;
    private LinkedList<Train> trains;

    public TrainSystem() {
        this.status = SystemStatus.Initialised;
        this.stations = new LinkedList<>();
        this.segments = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.trains = new LinkedList<>();
    }

    public void addStation(String sname) {
        if (!containsStation(sname)) {
            stations.add(new Station(sname));
        }
    }

    public void removeStation(String sname) {
        stations.remove(new Station(sname));
    }

    public void openStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.open();
        }
    }

    public void closeStation(String sname) {
        Station station = getStationByName(sname);
        if (station != null) {
            station.close();
        }
    }

    public void addSegment(String sname, String start, String sEnd) {
        if (!containsSegment(sname)) {
            Station startStation = getStationByName(start);
            Station endStation = getStationByName(sEnd);
            if (startStation != null && endStation != null) {
                segments.add(new Segment(sname, startStation, endStation));
            }
        }
    }

    public void removeSegment(String sname) {
        // iterate through segments List and remove if found
        for (ListIterator<Segment> iterator = segments.listIterator(); iterator.hasNext();) {
            Segment segment = iterator.next();
            if (segment.getName().equals(sname)) {
                iterator.remove();
                break;
            }
        }
    }

    public void openSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.open();
        }
    }

    public void closeSegment(String sname) {
        Segment segment = getSegmentByName(sname);
        if (segment != null) {
            segment.close();
        }
    }

    public void addRoute(String rName, boolean isRoundTrip, RSStatus status) {
        if (!containsRoute(rName)) {
            routes.add(new Route(rName, isRoundTrip, status));
        }
    }

    public void removeRoute(String rName) {
        // iterate through routes List and remove if found
        for (ListIterator<Route> iterator = routes.listIterator(); iterator.hasNext();) {
            Route route = iterator.next();
            if (route.getName().equals(rName)) {
                iterator.remove();
                break;
            }
        }
    }

    public void openRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.open();
        }
    }

    public void closeRoute(String rName) {
        Route route = getRouteByName(rName);
        if (route != null) {
            route.close();
        }
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void removeTrain(Integer id) {
        // iterate through trains List and remove if found
        for (ListIterator<Train> iterator = trains.listIterator(); iterator.hasNext();) {
            Train train = iterator.next();
            if (train.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }

    public void registerTrain(Integer trainId, String routeName, int startTime) {
        Train train = getTrainById(trainId);
        Route route = getRouteByName(routeName);
        if (train != null && route != null) {
            train.register(startTime);
        }
    }

    public void deRegisterTrain(Integer trainId) {
        trains.remove(getTrainById(trainId));
        removeTrain(trainId);   
    }

    public boolean containsStation(String station) {
        return getStationByName(station) != null;
    }

    public boolean containsSegment(String segment) {
        return getSegmentByName(segment) != null;
    }

    public boolean containsRoute(String route) {
        return getRouteByName(route) != null;
    }

    public boolean containsTrain(Integer trainId) {
        return getTrainById(trainId) != null;
    }

    public String getStationInfo(String station) {
        Station s = getStationByName(station);
        if (s != null) {
            return "Station Name: " + s.getName() + "\n" +
                    "Status: " + s.getStatus().toString() + "\n";
        }
        return null;
    }

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


    public void setToWorking() {
        status = SystemStatus.Initialised;
    }


    public void setStopped() {
        status = SystemStatus.Finished;
    }

    public SystemStatus currentStatus() {
        return status;
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
    

    public void advance() {
        // Implement logic to advance the train system
    }

    private Station getStationByName(String name) {
        for (Station station : stations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    private Segment getSegmentByName(String name) {
        for (Segment segment : segments) {
            if (segment.getName().equals(name)) {
                return segment;
            }
        }
        return null;
    }

    private Route getRouteByName(String name) {
        for (Route route : routes) {
            if (route.getName().equals(name)) {
                return route;
            }
        }
        return null;
    }

    private Train getTrainById(Integer id) {
        for (Train train : trains) {
            if (train.getId().equals(id)) {
                return train;
            }
        }
        return null;
    }
}
