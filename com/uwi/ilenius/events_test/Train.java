package com.uwi.ilenius.events_test;

import java.util.List;
import java.util.ArrayList;

public class Train {
    private String id;
    private String currentStation;
    private List<EventListener> listeners = new ArrayList<>();

    public Train(String id, String startingStation) {
        this.id = id;
        this.currentStation = startingStation;
    }

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public TrainMovementEvent moveTo(String destinationStation) {
        String sourceStation = currentStation;
        currentStation = destinationStation;
        TrainMovementEvent event = new TrainMovementEvent(id, sourceStation, destinationStation);

        // Notify all registered listeners
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }

        return event;
    }
}
