package com.uwi.ilenius.p2.interfaces;

import java.util.List;

import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.event_listeners.EventListener;

public interface EventListenerManager {
    void registerListener(EventListener listener);
    void unregisterListener(EventListener listener);
    List<EventListener> getListeners();
    void notifyListeners(Event event);
}


