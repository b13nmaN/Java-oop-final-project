package com.uwi.ilenius.p2.models;

import com.uwi.ilenius.p2.enums.SimulatorStatus;
import com.uwi.ilenius.p2.events.Event;

import java.util.LinkedList;

/**
 * The Simulator class represents the simulation engine for the train system.
 * It manages the current time, simulation status, and provides methods for running and validating the simulation.
 */
public class Simulator extends Logable {
    private int currentTime = 100;
    private int MAX_SIMULATION_TIME = 10000;
    private SimulatorStatus status;
    private TrainSystem trainSystem;

    /**
     * Constructs a new Simulator object with the initial time set to 100 and status set to WORKING.
     */
    public Simulator() {
        this.currentTime = 100;
        this.status = SimulatorStatus.INITIALISED;
        // this.trainSystem = trainSystem;
    }

    /**
     * Returns the current time of the simulation.
     *
     * @return the current time
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets the current time of the simulation.
     *
     * @param currentTime the new current time
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime += currentTime;
    }

    /**
     * Checks if the simulation is finished.
     *
     * @return true if the simulation is finished, false otherwise
     */
    public boolean isFinished() {
        return status == SimulatorStatus.FINISHED;
    }

    // Simulate the train system
    public void simulate() {
        while (!isFinished()) {
            LinkedList<Event> events = trainSystem.advance();
            // Advance the train system and get events
            for (Event event : events) {
                addToLog(event);
                // Add events to the log
            }
            currentTime++;
            // Increment current time
            if (currentTime >= MAX_SIMULATION_TIME) {
                status = SimulatorStatus.FINISHED;
                // Set status to finished if maximum simulation time reached
            }
        }
    }

    /**
     * Validates the simulation according to custom validation logic.
     *
     * @return true if the validation passes, false otherwise
     */
    public boolean validate() {
        // Custom validation logic can be added here
        return true;
    }

    @Override
	public String toString() {
		String str = "";
		str += helperString(str) + "\n";
		str += "--- Events --\n";
		str += logSize() == 0 ? " \tno events" : "";
		for (String object : getObjects()) {
			str += "Object=[" + object + ", events=" + logSize() + "]\n";
			for (String event : getEvents(object))
				str += "\t" + event + "\n";
		}
		str = helperString2(str);
		return str;
	}

	public String toShortString() {
		String str = "";
		str = helperString(str);
		str += "There are " + logSize() + " events with " + distinctObjects() + " distinct objects.\n";
		str = helperString2(str);
		return str;
	}

	private String helperString(String str) {
		str += "The current time instant is: " + currentTime + "\n";
		str += "The current status is: " + status.getDescription() + "\n";
		return str;
	}

	private String helperString2(String str) {
		str += currentTime <= 0 ? "\nNothing to validate as yet."
				: "\n\nValidation has " + (validate() ? "passed" : "failed");
		return str;
	}
}
