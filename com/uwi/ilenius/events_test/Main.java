package com.uwi.ilenius.events_test;

public class Main {
    public static void main(String[] args) {
        Train train_1 = new Train("T1", "Station A");
        Train train_2 = new Train("T1", "Station A");

        // Create listeners
        StationMonitor stationMonitorA = new StationMonitor("Station A");
        StationMonitor stationMonitorB = new StationMonitor("Station B");
        
        // Register listeners with the train (assuming Train has a method for this)
        train_1.registerListener(stationMonitorA);
        train_1.registerListener(stationMonitorB);
        
        TrainMovementEvent event = train_1.moveTo("Station B");

        System.out.println(event.getTrainId() + " " + event.getSourceStation() + " " + event.getDestinationStation());

    }
}
