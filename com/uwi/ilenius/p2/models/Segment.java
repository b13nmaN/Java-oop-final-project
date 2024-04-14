package com.uwi.ilenius.p2.models;
import java.util.List;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.LightEvent;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;

public class Segment implements Verifiable, Openable, Closeable{
    private String name;
    private RSStatus status;
    private boolean hasTrain;
    private boolean isOpen;
    private TrafficLight trafficLight;
    private Station segmentStart;
    private Station segmentEnd;
    private List<Train> trainsForRoute;
    private TrainSystem trainSystem;

    // the segment class has a compostion relationship with the TrafficLight class
    public Segment(String name, Station segmentStart, Station segmentEnd) {
        this.name = name;
        this.status = status;
        this.hasTrain = false;
        this.isOpen = true;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
    }



    public String getName() {
        return name;
    }

    public RSStatus getStatus() {
        return status;
    }

    public Station getSegmentStart() {
        return segmentStart;
    }

    public Station getSegmentEnd() {
        return segmentEnd;
    }

    public void setSegmentStart(Station segmentStart) {
        this.segmentStart = segmentStart;
    }


    public void setSegmentEnd(Station segmentEnd) {
        this.segmentEnd = segmentEnd;
    }

    public boolean hasTrain() {
        return hasTrain;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void acceptTrain(Train train) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            // Logic to handle train acceptance
        } else {
            System.out.println("Cannot accept train. Segment is occupied or closed.");
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

    // public LightEvent changeLight() {
    //     // Implement logic to change the light
        
    // }

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
    

    public void close() {
        isOpen = false;
        // Implement logic to close the segment
        
    }

    public RSStatus open() {
        isOpen = true;
        RSStatus status = RSStatus.Open;
        return status;
        // Implement logic to open the segment
    }

}

