package com.uwi.ilenius.p1;

public class Station implements Verifiable, Openable, Closeable{
    private String name;
    private RSStatus status = RSStatus.Open;
    private boolean hasTrain;
    private boolean isOpen;
    private Train trainInStation = null;
    // private TrainSystem trainSystem;

    public Station(String name) {
        this.name = name;
        this.status = status;
        this.hasTrain = false;
        this.isOpen = true;

    }

    public boolean hasTrain() {
        return hasTrain;
    }

    public Train getTrain() {
        return trainInStation;
    }

    public RSStatus getStatus() {
        return status;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean verify() {
        return name != null && !name.trim().isEmpty();
    }
    

    public void close() {
        isOpen = false;
        // status = RSStatus.ClosedForMaintenance;
    }

    public void open() {
        isOpen = true;
        status = RSStatus.Open;
        // Implement logic to open the station
    }

    public void acceptTrain(Train train) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            trainInStation = train;
            // Logic to handle train acceptance
        } else {
            System.out.println("Cannot accept train. Station is occupied or closed.");
        }
    }

    public void releaseTrain() {
        if (hasTrain) {
            hasTrain = false;
            trainInStation = null;
            // Logic to handle train release
        } else {
            System.out.println("No train to release.");
        }
    }

    // get name method
    
    public String getName() {
        return name;
    }
}

