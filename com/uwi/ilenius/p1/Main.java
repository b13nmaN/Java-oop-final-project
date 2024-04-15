package com.uwi.ilenius.p1;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        TrainSystem trainSystem = new TrainSystem();
    
        // Add stations
        trainSystem.addStation("Station A");
        trainSystem.addStation("Station B");
        trainSystem.addStation("Station C");
    
        // Add route with stations
        LinkedList<Station> routeStations = new LinkedList<>();
        routeStations.add(trainSystem.getStationByName("Station A"));
        routeStations.add(trainSystem.getStationByName("Station B"));
        routeStations.add(trainSystem.getStationByName("Station C"));
        trainSystem.addRoute("Route 1", true, routeStations);
    
        // Add train
        trainSystem.addTrain();
    
        // Register train to route
        trainSystem.registerTrain(1, "Route 1", 8); // Start time: 8 AM
    
        // Get information
        System.out.println("Station Information: " + trainSystem.getStationInfo("Station A"));
        System.out.println("Route Information: " + trainSystem.getRouteInfo("Route 1"));
        // System.out.println("Train Information: " + trainSystem.getTrainInfo(1));
    
        // Verify the train system
        System.out.println("Train System Verification: " + trainSystem.verify());
    
        // Advance the train system (not implemented)
        trainSystem.advance();
    }
}


