package com.uwi.ilenius.p2.models;

// import List and ArrayList
import java.util.List;
import java.util.ArrayList;

import com.uwi.ilenius.p2.enums.Action;
import com.uwi.ilenius.p2.enums.RSStatus;
import com.uwi.ilenius.p2.events.CFOSEvent;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.interfaces.Closeable;
import com.uwi.ilenius.p2.interfaces.Openable;
import com.uwi.ilenius.p2.interfaces.Verifiable;
import com.uwi.ilenius.p2.interfaces.EventListenerManager;
import com.uwi.ilenius.p2.event_listeners.EventListener;

public class Station extends Logable  implements Verifiable, Openable, Closeable, EventListenerManager{
    private String name;
    private RSStatus status = RSStatus.Open;
    private boolean hasTrain;
    private boolean isOpen;
    private Train trainInStation = null;
    private int time = 0;
    private List<EventListener> listeners = new ArrayList<>();
    // private TrainSystem trainSystem;

    public Station(String name) {
        this.name = name;
        this.isOpen = true;

    }

    public Train getTrain() {
        return trainInStation;
    }

    public RSStatus getStatus() {
        return status;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean verify() {
        return name != null && !name.trim().isEmpty();
    }
    

    public CFOSEvent close() {
        isOpen = false;
        return new CFOSEvent("Station", time, Action.CLOSE);
        // status = RSStatus.ClosedForMaintenance;
    }

    public CFOSEvent open() {
        isOpen = true;
        return new CFOSEvent("Station", time, Action.OPEN);
        // Implement logic to open the station
    }
    // get name method
    
    public String getName() {
        return name;
    }

    @Override
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public List<EventListener> getListeners() {
        return listeners;
    }

    @Override
    public void notifyListeners(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}

