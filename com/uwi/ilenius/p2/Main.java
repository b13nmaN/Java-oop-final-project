package com.uwi.ilenius.p2;


import com.uwi.ilenius.p2.models.TrainSystem;
import com.uwi.ilenius.p2.models.Station;
import com.uwi.ilenius.p2.models.Segment;
import com.uwi.ilenius.p2.models.Simulator;
import com.uwi.ilenius.p2.models.Route;
import com.uwi.ilenius.p2.models.Train;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Create an instance of TrainSystem
        Simulator simulator = new Simulator();
        TrainSystem trainSystem = new TrainSystem(simulator);

        // Test methods
        // Add stations
        trainSystem.addStation("Station A");
        trainSystem.addStation("Station B");
        trainSystem.addStation("Station C");
        trainSystem.addStation("Station D");

        // Add segments
        trainSystem.addSegment("Segment 1", "Station A", "Station B");
        trainSystem.addSegment("Segment 2", "Station C", "Station D");

        // Add routes
        LinkedList<Station> stations = trainSystem.getStations();
        
        System.out.println("Number of stations retrieved: " + stations.size());

        trainSystem.addRoute("Route 1", true, stations);
        Route route1 = trainSystem.getRouteByName("Route 1");

        System.out.println("The stations in the route are: " + route1.getStations());

        // Add trains
        trainSystem.addTrain("Train 1");
        

        // Register train to a route
        trainSystem.registerTrain("Train 1", "Route 1");

        // Get information about stations, segments, routes, and trains
        System.out.println(trainSystem.getStationInfo("Station A"));
        System.out.println(trainSystem.getSegmentInfo("Segment 1"));
        System.out.println(trainSystem.getRouteInfo("Route 1"));
        System.out.println(trainSystem.getTrainInfo("Train 1"));

        // Verify the train system
        // System.out.println("Train system verification status: " + trainSystem.verify());
    }
}


