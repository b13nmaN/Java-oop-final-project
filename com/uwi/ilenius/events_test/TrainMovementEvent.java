package com.uwi.ilenius.events_test;

// Event class representing a train movement
public class TrainMovementEvent {
    private final String trainId;
    private final String sourceStation;
    private final String destinationStation;

    public TrainMovementEvent(String trainId, String sourceStation, String destinationStation) {
        this.trainId = trainId;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }
}
