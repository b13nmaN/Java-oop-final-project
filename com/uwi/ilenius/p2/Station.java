package com.uwi.ilenius.p2;

import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;

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
        // this.hasTrain = false;
        this.isOpen = true;

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


    // get name method
    
    public String getName() {
        return name;
    }
}

