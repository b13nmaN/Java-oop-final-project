package com.uwi.ilenius.p2;


import com.uwi.ilenius.p2.models.TrainSystem;
import com.uwi.ilenius.p2.models.Station;
import com.uwi.ilenius.p2.models.Segment;
import com.uwi.ilenius.p2.models.Simulator;
import com.uwi.ilenius.p2.event_listeners.MoveEventListener;
import com.uwi.ilenius.p2.event_listeners_impl.MoveEventListenerImpl;
import com.uwi.ilenius.p2.events.MoveEvent;
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


        // Add segments
        trainSystem.addSegment("Segment 1", "Station A", "Station B");
        trainSystem.addSegment("Segment 2", "Station B", "Station C");

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

        // get train by name
        Train train1 = trainSystem.getTrainByName("Train 1");
        train1.setCurrentLocation(trainSystem.getStationByName("Station A"));

        //create train listener
        MoveEventListenerImpl trainListener = new MoveEventListenerImpl();

        // Register train listener
        train1.registerListener(trainListener);

        // Move the train
        MoveEvent moveEvent = train1.advance(10);

        // Print the move event
        System.out.println("Train moved from " + moveEvent.getFromStation() + " " + moveEvent.getToStation() + " " + moveEvent.getTime());

        // Verify the train system
        // System.out.println("Train system verification status: " + trainSystem.verify());
    }
}


