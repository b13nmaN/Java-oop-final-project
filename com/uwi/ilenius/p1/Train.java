package com.uwi.ilenius.p1;

public class Train {
    private Integer id;
    private Integer timeRegistered;
    private Integer startTime;
    private Station currentLocation;

    public Train(Integer id) {
        this.id = id;
        this.timeRegistered = -1; // Assuming -1 indicates not registered
        this.startTime = -1; // Assuming -1 indicates not started
        this.currentLocation = null;
    }

    public boolean isRegistered() {
        return timeRegistered != -1;
    }

    public Integer whenRegistered() {
        return timeRegistered;
    }

    public void register(Integer time) {
        if (!isRegistered()) {
            this.timeRegistered = time;
        } else {
            System.out.println("Train is already registered.");
        }
    }

    public void start() {
        if (!isRegistered()) {
            System.out.println("Train must be registered before starting.");
            return;
        }

        if (startTime == -1) {
            startTime = timeRegistered;
        } else {
            System.out.println("Train is already started.");
        }
    }

    public void advance() {
        // Implement logic to advance the train
    }

    public String currentStation() {
        if (currentLocation != null) {
            return currentLocation.getName();
        }
        return null;
    }

    public String nextStation() {
        // Implement logic to determine the next station
        return null;
    }

    public void changeRoute(Route route) {
        // Implement logic to change the route of the train
    }

    public boolean verify() {
        // Implement logic to verify the train
        return false;
    }
}
