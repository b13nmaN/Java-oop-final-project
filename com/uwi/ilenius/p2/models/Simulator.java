package com.uwi.ilenius.p2.models;

import java.util.List;

import com.uwi.ilenius.p2.enums.SimulatorStatus;

public class Simulator extends Logable {
    private int currentTime;
    private SimulatorStatus status;
    private TrainSystem trainSystem;

    public Simulator(TrainSystem trainSystem) {
        this.currentTime = 0;
        this.status = SimulatorStatus.WORKING;
        this.trainSystem = trainSystem;
    }


    
    // Get the current time of the simulation
    public int getCurrentTime() {
        return currentTime;
    }

    
    // Set current time of the simulation
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    // Check if the simulation is finished
    public boolean isFinished() {
        return status == SimulatorStatus.FINISHED;
    }

    // Simulate the train system
    // public void simulate() {
    //     while (!isFinished()) {
    //         List<Event> events = trainSystem.advance(); // Advance the train system and get events
    //         for (Event event : events) {
    //             addToLog(event.log()); // Add events to the log
    //         }
    //         currentTime++; // Increment current time
    //         if (currentTime >= MAX_SIMULATION_TIME) {
    //             status = SimulatorStatus.FINISHED; // Set status to finished if maximum simulation time reached
    //         }
    //     }
    // }

    // Validate the simulation
    public boolean validate() {
        // Custom validation logic can be added here
        return true;
    }
}

