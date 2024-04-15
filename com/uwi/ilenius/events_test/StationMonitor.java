package com.uwi.ilenius.events_test;

public class StationMonitor implements TrainMovementListener, EventListener {

    private final String stationName;

    public StationMonitor(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public void onTrainMovement(TrainMovementEvent event) {
        if (event.getSourceStation().equals(stationName) || event.getDestinationStation().equals(stationName)) {
            System.out.println("Train " + event.getTrainId() + " " + (event.getSourceStation().equals(stationName) ? "departed from" : "arrived at") + " station " + stationName);
        }
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof TrainMovementEvent) {
            onTrainMovement((TrainMovementEvent) event);
        }
    }
}

