package com.uwi.ilenius.p1;

import java.util.List;

/**
 * Represents a segment of a transportation route.
 */
public class Segment implements Verifiable, Openable, Closeable {
    private String name; // The name of the segment
    private RSStatus status = RSStatus.Open; // The status of the segment
    private boolean hasTrain = false; // Indicates if the segment has a train
    private boolean isOpen = true; // Indicates if the segment is open
    private TrafficLight trafficLight = new TrafficLight(0, Light.Red); // The traffic light associated with the segment
    private Station segmentStart; // The start station of the segment
    private Station segmentEnd; // The end station of the segment
    private Train trainInSegment; // The train in the segment

    /**
     * Constructs a Segment object.
     * 
     * @param name          The name of the segment.
     * @param segmentStart  The start station of the segment.
     * @param segmentEnd    The end station of the segment.
     */
    public Segment(String name, Station segmentStart, Station segmentEnd) {
        this.name = name;
        this.status = status; // The status is not initialized in the constructor
        this.hasTrain = false;
        this.isOpen = true;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
    }

    /**
     * Gets the name of the segment.
     * 
     * @return The name of the segment.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the status of the segment.
     * 
     * @return The status of the segment.
     */
    public RSStatus getStatus() {
        return status;
    }

    /**
     * Gets the start station of the segment.
     * 
     * @return The start station of the segment.
     */
    public Station getSegmentStart() {
        return segmentStart;
    }

    /**
     * Gets the end station of the segment.
     * 
     * @return The end station of the segment.
     */
    public Station getSegmentEnd() {
        return segmentEnd;
    }


    /**
     * Checks if the segment has a train.
     * 
     * @return true if the segment has a train, false otherwise.
     */
    public boolean hasTrain() {
        return hasTrain;
    }

    /**
     * Checks if the segment is open.
     * 
     * @return true if the segment is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Accepts a train into the segment.
     * 
     * @param train  The train to accept.
     */
    public void acceptTrain(Train train) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            trainInSegment = train;
        } else {
            System.out.println("Cannot accept train. Segment is occupied or closed.");
        }
    }

    /**
     * Releases the train from the segment.
     */
    public void releaseTrain() {
        if (hasTrain) {
            hasTrain = false;
            trainInSegment = null;
        } else {
            System.out.println("No train to release.");
        }
    }

    /**
     * Changes the traffic light of the segment.
     */
    public void changeLight() {
        if(hasTrain && isOpen && lightColour()) {
            trafficLight.change();
        }
    }

    /**
     * Determines the color of the traffic light.
     * 
     * @return true if the light color is green, false otherwise.
     */
    public boolean lightColour() {
        // Implement logic to determine light color
        return trafficLight.getColour() == Light.Green;
    }

    /**
     * Verifies the validity of the segment.
     * 
     * @return true if the segment is valid, false otherwise.
     */
    public boolean verify() {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (trafficLight == null || !trafficLight.verify() || segmentStart == null || segmentEnd == null) {
            return false;
        }

        if (segmentStart.equals(segmentEnd)) {
            return false; // Start and end stations cannot be the same
        }

        if (isOpen && (!segmentStart.isOpen() || !segmentEnd.isOpen())) {
            return false; // If segment is open, both start and end stations should be open
        }

        if (!isOpen && (segmentStart.isOpen() || segmentEnd.isOpen())) {
            return false; // If segment is closed, both start and end stations should be closed
        }

        return true;
    }

    /**
     * Closes the segment.
     */
    public void close() {
        isOpen = false;
    }

    /**
     * Opens the segment.
     */
    public void open() {
        isOpen = true;
    }
}
