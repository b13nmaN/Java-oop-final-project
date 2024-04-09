package com.uwi.ilenius.p1;

import java.util.HashMap;
import java.util.Map;


public class TrainSystem {
    private SystemStatus status;
    private Map<String, Station> stations;
    private Map<String, Segment> segments;
    private Map<String, Route> routes;
    private Map<Integer, Train> trains;

    public TrainSystem() {
        this.status = SystemStatus.Initialised;
        this.stations = new HashMap<>();
        this.segments = new HashMap<>();
        this.routes = new HashMap<>();
        this.trains = new HashMap<>();
    }

    public void addStation(String sname) {
        if (!stations.containsKey(sname)) {
            stations.put(sname, new Station(sname));
        }
    }

    public void removeStation(String sname) {
        stations.remove(sname);
    }

    public void openStation(String sname) {
        Station station = stations.get(sname);
        if (station != null) {
            station.open();
        }
    }

    public void closeStation(String sname) {
        Station station = stations.get(sname);
        if (station != null) {
            station.close();
        }
    }

    public void addSegment(String sname, String start, String sEnd) {
        if (!segments.containsKey(sname)) {
            Station startStation = stations.get(start);
            Station endStation = stations.get(sEnd);
            if (startStation != null && endStation != null) {
                segments.put(sname, new Segment(sname, startStation, endStation));
            }
        }
    }

    public void removeSegment(String sname) {
        segments.remove(sname);
    }

    public void openSegment(String sname) {
        Segment segment = segments.get(sname);
        if (segment != null) {
            segment.open();
        }
    }

    public void closeSegment(String sname) {
        Segment segment = segments.get(sname);
        if (segment != null) {
            segment.close();
        }
    }

    public void addRoute(String rName, boolean isRoundTrip, OrderedSet<Station> rStations) {
        if (!routes.containsKey(rName)) {
            Route route = new Route(rName, isRoundTrip, rStations);
            routes.put(rName, route);
        }
    }

    public void removeRoute(String rName) {
        routes.remove(rName);
    }

    public void openRoute(String rName) {
        Route route = routes.get(rName);
        if (route != null) {
            route.open();
        }
    }

    public void closeRoute(String rName) {
        Route route = routes.get(rName);
        if (route != null) {
            route.close();
        }
    }

    public void addTrain(Train train) {
        trains.put(train.getId(), train);
    }

    public void removeTrain(Integer id) {
        trains.remove(id);
    }

    public void registerTrain(Integer trainId, String routeName, int startTime) {
        Train train = trains.get(trainId);
        Route route = routes.get(routeName);
        if (train != null && route != null) {
            train.register( startTime);
        }
    }

    public void deRegisterTrain(Integer trainId) {
        Train train = trains.get(trainId);
        if (train != null) {
            train.deRegisterJourney();
        }
    }

    public boolean containsStation(String station) {
        return stations.containsKey(station);
    }
    
    public boolean containsSegment(String segment) {
        return segments.containsKey(segment);
    }
    
    public boolean containsRoute(String route) {
        return routes.containsKey(route);
    }
    
    public boolean containsTrain(Integer trainId) {
        return trains.containsKey(trainId);
    }
    
    public String getStationInfo(String station) {
        Station s = stations.get(station);
        if (s != null) {
            return "Station Name: " + s.getName() + "\n" +
                   "Status: " + s.getStatus().toString() + "\n";
        }
        return null;
    }
    
    public String getSegmentInfo(String segmentName) {
        Segment s = segments.get(segmentName);
        if (s != null) {
            return "Segment Name: " + s.getName() + "\n" +
                   "Status: " + s.getStatus().toString() + "\n" +
                   "Start Station: " + s.getStart().getName() + "\n" +
                   "End Station: " + s.getEnd().getName() + "\n";
        }
        return null;
    }
    
    public String getRouteInfo(String routeName) {
        Route r = routes.get(routeName);
        if (r != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Route Name: ").append(r.getName()).append("\n");
            sb.append("Is Round Trip: ").append(r.isRoundTrip()).append("\n");
            sb.append("Status: ").append(r.getStatus().toString()).append("\n");
            sb.append("Segments:\n");
            for (Segment segment : r.getSegments()) {
                sb.append("- ").append(segment.getName()).append("\n");
            }
            return sb.toString();
        }
        return null;
    }
    
    public String getTrainInfo(Integer trainId) {
        Train t = trains.get(trainId);
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

    public void setPaused() {
        status = SystemStatus.Deadlocked;
    }

    public void setStopped() {
        status = SystemStatus.Finished;
    }

    public SystemStatus currentStatus() {
        return status;
    }

    public boolean verify() {
        // Implement logic to verify the train system
        return false;
    }

    public void advance() {
        // Implement logic to advance the train system
    }
}
