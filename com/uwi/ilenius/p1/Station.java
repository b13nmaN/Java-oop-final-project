package com.uwi.ilenius.p1;

/**
 * Represents a station in the transportation system.
 */
public class Station implements Verifiable, Openable, Closeable {
    private String name; // The name of the station
    private RSStatus status = RSStatus.Open; // The status of the station, default is Open
    private boolean hasTrain; // Indicates if the station has a train
    private boolean isOpen; // Indicates if the station is open
    private Train trainInStation; // The train currently in the station

    /**
     * Constructs a Station object with the given name.
     * 
     * @param name  The name of the station.
     */
    public Station(String name) {
        this.name = name;
        this.hasTrain = false;
        this.isOpen = true;
    }

    /**
     * Checks if the station has a train.
     * 
     * @return true if the station has a train, false otherwise.
     */
    public boolean hasTrain() {
        return hasTrain;
    }

    /**
     * Gets the train currently in the station.
     * 
     * @return The train currently in the station.
     */
    public Train getTrain() {
        return trainInStation;
    }

    /**
     * Gets the status of the station.
     * 
     * @return The status of the station.
     */
    public RSStatus getStatus() {
        return status;
    }

    /**
     * Checks if the station is open.
     * 
     * @return true if the station is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Verifies the validity of the station.
     * 
     * @return true if the station is valid, false otherwise.
     */
    public boolean verify() {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Closes the station.
     */
    public void close() {
        isOpen = false;
        // status = RSStatus.ClosedForMaintenance;
    }

    /**
     * Opens the station.
     */
    public void open() {
        isOpen = true;
        // Implement logic to open the station
    }

    /**
     * Accepts a train into the station.
     * 
     * @param train  The train to accept.
     */
    public void acceptTrain(Train train) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            trainInStation = train;
            // Logic to handle train acceptance
        } else {
            System.out.println("Cannot accept train. Station is occupied or closed.");
        }
    }

    /**
     * Releases the train from the station.
     */
    public void releaseTrain() {
        if (hasTrain) {
            hasTrain = false;
            trainInStation = null;
            // Logic to handle train release
        } else {
            System.out.println("No train to release.");
        }
    }

    /**
     * Gets the name of the station.
     * 
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }
}
