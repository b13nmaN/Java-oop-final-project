package com.uwi.ilenius.p1;

public class Station {
    private String name;
    private RSStatus status;
    private boolean hasTrain;
    private boolean isOpen;

    public Station(String name, RSStatus status) {
        this.name = name;
        this.status = status;
        this.hasTrain = false;
        this.isOpen = true;
    }

    public boolean hasTrain() {
        return hasTrain;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean verify() {
        // Implement logic to verify the station
        return false;
    }

    public void close() {
        isOpen = false;
        // Implement logic to close the station
    }

    public void open() {
        isOpen = true;
        // Implement logic to open the station
    }

    public void acceptTrain(Train train) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            // Logic to handle train acceptance
        } else {
            System.out.println("Cannot accept train. Station is occupied or closed.");
        }
    }

    public void releaseTrain() {
        if (hasTrain) {
            hasTrain = false;
            // Logic to handle train release
        } else {
            System.out.println("No train to release.");
        }
    }
}

