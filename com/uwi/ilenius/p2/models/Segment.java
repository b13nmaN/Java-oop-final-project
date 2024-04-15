package com.uwi.ilenius.p2.models;
import java.util.List;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.LightEvent;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;
// import com.uwi.ilenius.p2.enums.ObjectType;
import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.enums.Light;

public class Segment implements Verifiable, Openable, Closeable{
    private String name;
    private RSStatus status = RSStatus.Open;
    private boolean hasTrain;
    private boolean isOpen;
    private TrafficLight trafficLight;
    private Station segmentStart;
    private Station segmentEnd;
    // private List<Train> trainsForRoute;
    private TrainSystem trainSystem;
    // private ObjectType type = ObjectType.Segment_;

    // the segment class has a compostion relationship with the TrafficLight class
    public Segment(String name, Station segmentStart, Station segmentEnd) {
        this.name = name;
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

    public CFOSEvent acceptTrain(Train train, int time) {
        if (!hasTrain && isOpen) {
            hasTrain = true;
            return new CFOSEvent("Segment", time, Action.OPEN);
        } else {
            System.out.println("Cannot accept train. Segment is occupied or closed.");
            return null;
        }
    }

    public CFOSEvent releaseTrain(int time) {
        if (hasTrain) {
            hasTrain = false;
            return new CFOSEvent("Segment", time, Action.CLOSE);
        } else {
            System.out.println("No train to release.");
            return null;
        }
    }

    public LightEvent changeLight() {
        // Implement logic to change the light
        switch (trafficLight.getColour()) {
            case Red:
                trafficLight.change();
                return new LightEvent("Segment", trainSystem.getCurrentTime(), Light.Red, Light.Green);
            case Green:
                trafficLight.change();
                return new LightEvent("Segment", trainSystem.getCurrentTime(), Light.Green, Light.Red);
            default:
                System.out.println("Invalid colour " + trafficLight.getColour());
                return null;
        }
        
    }

    public CFOSEvent close() {
        isOpen = false;
        int time = trainSystem.getCurrentTime();
        return new CFOSEvent("Segment", time, Action.CLOSE);
    }

    public CFOSEvent open() {
        isOpen = true;

        int time = trainSystem.getCurrentTime();
        
        return new CFOSEvent("Segment", time, Action.OPEN);
    }

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

}

