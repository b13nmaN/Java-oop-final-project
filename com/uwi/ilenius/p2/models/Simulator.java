package com.uwi.ilenius.p2.models;

import java.io.FileNotFoundException;

import com.uwi.ilenius.p2.enums.SimulatorStatus;
import com.uwi.ilenius.p2.events.Event;
import com.uwi.ilenius.p2.interfaces.TimeObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.uwi.ilenius.p2.event_listeners_impl.MoveEventListenerImpl;
import com.uwi.ilenius.p2.event_listeners_impl.CFOSEventListenerImpl;
import com.uwi.ilenius.p2.event_listeners_impl.LightEventListenerImpl;
import com.uwi.ilenius.p2.event_listeners_impl.OccupiedEventListenerImpl;



/**
 * The Simulator class represents the simulation engine for the train system.
 * It manages the current time, simulation status, and provides methods for running and validating the simulation.
 */
public class Simulator extends Logable {


    private int currentTime =0;
    private int MAX_SIMULATION_TIME = 10;
    private SimulatorStatus status = SimulatorStatus.UNINITIALISED;
    private TrainSystem trainSystem = new TrainSystem();
    private LinkedList<TimeObserver> observers = new LinkedList<>();
    private String initialisationFile;
    private List<List<String>> eventsLog = new ArrayList<>();
    private int eventEventsCounter = 0;
    private boolean isRegistered = false;
    /**
     * Constructs a new Simulator object with the initial time set to 100 and status set to WORKING.
     */
    public Simulator(String initialisationFile) throws FileNotFoundException {
        setInitialised();
        this.currentTime = 0;
        this.status = SimulatorStatus.INITIALISED;
        this.initialisationFile = initialisationFile;
        // this.trainSystem = trainSystem;
    }



    public Simulator() throws FileNotFoundException {
		// initialiseFixed();
		setInitialised();
	}

    public void getFileInfo(String initialisationFile) {
       
          File myObj = new File(initialisationFile);
          if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
          } else {
            System.out.println("The file does not exist.");
          }

      }

      // Method to execute events based on their type and associated objects
    private void executeEvent(String eventType, String objectType, String objectID, String time) {
        if (!isRegistered) {
            registerEventListeners();
        }
        notifyObservers(); // Notify observers of time update
        switch (objectType) {
            case "Segment":
                
                Segment segment = trainSystem.getSegmentByName(objectID);
                if (segment != null) {
                    switch (eventType) {
                        case "Close":
                            segment.close();
                            break;
                        case "Open":
                            segment.open();
                            break;
                    }
                }
                break;
            case "Train":
                Train train = trainSystem.getTrainByName(objectID);
                if (train != null) {
                    switch (eventType) {
                        case "Start":
                            train.start();
                            break;
                        case "Finish":
                            train.finish();
                            break;
                        case "Move":
                            train.advance(currentTime);
                            break;
                        // Add other train event types here
                    }
                }
                break;
            case "Route":
                Route route = trainSystem.getRouteByName(objectID);
                if (route != null) {
                    switch (eventType) {
                        case "Close":
                            route.close();
                            break;
                        case "Open":
                            route.open();
                            break;
                        // Add other route event types here
                    }
                }
                break;
            case "Station":
                Station station = trainSystem.getStationByName(objectID);
                if (station != null) {
                    switch (eventType) {
                        case "Close":
                            station.close();
                            break;
                        case "Open":
                            station.open();
                            break;
                        // Add other station event types here
                    }
                }
                break;
            // Add cases for other object types here
        }
    }

      // Method to register event listeners
    private void registerEventListeners() {
        isRegistered = true;
        // Register segment listeners
        for (Segment segment : trainSystem.getSegments()) {
            segment.registerListener(new CFOSEventListenerImpl());
            segment.registerListener(new LightEventListenerImpl());
            segment.registerListener(new OccupiedEventListenerImpl());
            registerObserver(segment);
        }
        for (Route route : trainSystem.getRoutes()) {
            route.registerListener(new CFOSEventListenerImpl());
            route.registerListener(new LightEventListenerImpl());
            route.registerListener(new OccupiedEventListenerImpl());
            registerObserver(route);
        }

        // Register train listeners
        for (Train train : trainSystem.getTrains()) {
            train.registerListener(new MoveEventListenerImpl(train.getRoute()));
            registerObserver(train);
        }

        // Register station listeners (assuming stations are also objects that can have events)
        for (Station station : trainSystem.getStations()) {
            station.registerListener(new CFOSEventListenerImpl());
            station.registerListener(new LightEventListenerImpl());
            registerObserver(station);
            // Add other station listeners if needed
        }
    }

    // Method to add events to the events log during parsing
    private void addEventToLog(String eventType, String objectType, String objectID, String time) {
        eventsLog.add(Arrays.asList(eventType, objectType, objectID, time));
    }
        /**
     * Initialises the simulator based on the information provided in the specified file.
     *
     * @param  initialisationFile  the file containing the setup information
     * @throws FileNotFoundException if the specified file is not found
     */
    public void initialise(String initialisationFile) throws FileNotFoundException {
		if (status == SimulatorStatus.UNINITIALISED || status == SimulatorStatus.INITIALISED) {
        File file = new File(initialisationFile);
        //TODO: add current time in file to be
			// create scanner to read from file
            try (Scanner scanner = new Scanner(file)) {
                boolean isFirstLine = false;
                String executionTime = "";
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    // System.out.println(line);
                    if (line.matches("\\d+") && !isFirstLine) { // Check if the line is an integer
                        currentTime = Integer.parseInt(line); // Set the class variable currentTime
                        isFirstLine = true;
                        continue; // Skip to the next line
                    }
                    if (line.matches("\\d+")) { // Check if the line is an integer and executionTime is not yet found
                        executionTime = line;
                        continue;
                    }
                    if (line.startsWith("Stations:")) {
                        int numberOfStations = Integer.parseInt(line.split(":")[1].trim());
                        for (int i = 0; i < numberOfStations; i++) {
                            line = scanner.nextLine().trim();
                            trainSystem.addStation(line);
                        }
                        
                    } else if (line.startsWith("Segments:")) {
                        int numberOfSegments = Integer.parseInt(line.split(":")[1].trim());
                        for (int i = 0; i < numberOfSegments; i++) {
                            line = scanner.nextLine().trim();
                            String[] parts = line.split(":");
                            String segmentName = parts[0].trim();
                            String stationA = parts[1].trim();
                            String stationB = parts[2].trim();
                            // Validate the segment instance
                            trainSystem.addSegment(segmentName, stationA, stationB);
                            Segment segment = trainSystem.getSegmentByName(segmentName);

                            if ( !segment.verify() && !segment.validate()) {
                                System.out.println("Error: Segment instance is not valid.");
                                trainSystem.removeSegment(segmentName);
                                continue;
                            }
                        }

                    } else if (line.startsWith("Routes:")) {
                        int numberOfRoutes = Integer.parseInt(line.split(":")[1].trim());
                        for (int i = 0; i < numberOfRoutes; i++) {
                            line = scanner.nextLine().trim();
                            String[] parts = line.split(":");
                            String routeName = parts[0].trim();
                            boolean isRoundTrip = Boolean.parseBoolean(parts[1].trim());
                            String[] segments = parts[2].trim().split(";");
                            LinkedList<Station> routeSegments = new LinkedList<>();
                            for (String segmentName : segments) {
                                Station segment = trainSystem.getStationByName(segmentName.trim());
                                if (segment != null) {
                                    routeSegments.add(segment);
                                }
                            }
                            
                        trainSystem.addRoute(routeName, isRoundTrip, routeSegments);
                        Route route = trainSystem.getRouteByName(routeName);
                        // get segments
                        LinkedList<Segment> segments_ = trainSystem.getSegments();
                        route.setSegments(segments_);
                        route.getSegmentsForRoute();

                            // Validate the train instance
                            if (!route.validate() && !route.verify()) {
                                System.out.println("Error: Route instance is not valid.");
                                trainSystem.removeRoute(routeName);
                                continue;
                            }
                        }
                    } else if (line.startsWith("Trains:")) {
                        int numberOfTrains = Integer.parseInt(line.split(":")[1].trim());
                        for (int i = 0; i < numberOfTrains; i++) {
                            line = scanner.nextLine().trim();
                            String[] parts = line.split(":");
                            String trainName = parts[0].trim();

                            // Add train
                            trainSystem.addTrain(trainName);
                            Train train = trainSystem.getTrainByName(trainName);
                            
                            // Validate the train instance
                        if (!train.validate() && !train.verify()) {
                            System.out.println("Error: Train instance is not valid.");
                            trainSystem.removeTrain(trainName);
                            continue;
                        }


                            int startTime = Integer.parseInt(parts[1].trim());
                            String routeName = parts[2].trim();
                            String[] stopTokens = parts[3].trim().split(";");

                            LinkedList<Station> routeStations = new LinkedList<>();
                            // If "all" is present in stopTokens, add all stations, otherwise, add specified stations
                            if (Arrays.asList(stopTokens).contains("all")) {
                                routeStations.addAll(trainSystem.getStations());
                            } else {
                                for (String stationName : stopTokens) {
                                    if (stationName != null) {
                                        train.addStop(stationName);
                                    }
                                }
                            }
                            train.register(startTime);
                            trainSystem.registerTrain(trainName, routeName);
                        }
                    } else if (line.startsWith("Events:")) {
                        int numberOfEvents = Integer.parseInt(line.split(":")[1].trim());
                        for (int i = 0; i < numberOfEvents; i++) {
                            line = scanner.nextLine().trim();
                            String[] parts = line.split(":");
                            String eventType = parts[0].trim();
                            String objectType = parts[1].trim();
                            String objectID = parts[2].trim();
                            // Add executionTime to the event before logging
                            addEventToLog(eventType, objectType, objectID, executionTime);
                        }
                    }
                }
            }
      }

    }
        /**
     * Executes all the events in the events log.
     *
     * This function iterates over each event in the events log and retrieves the event type, object type,
     * object ID, and the current time. It then calls the executeEvent function with these parameters.
     *
     * @param  None
     * @return None
     */
    public void executeEvents() {
        // System.out.println(eventsLog.toArray().toString());
        for (List<String> event_ : eventsLog) {
            String eventType = event_.get(0);
            String objectType = event_.get(1);
            String objectID = event_.get(2);
            String currentTime = event_.get(3); // Assuming getCurrentTime() returns the current simulation time
            int currentTimeInt = Integer.parseInt(currentTime);
            System.out.println("This is the current time of the event: " + currentTimeInt);
            System.out.println("This is the current time of the simulator: " + getCurrentTime());
            if (currentTimeInt == getCurrentTime()) {
                executeEvent(eventType, objectType, objectID, currentTime);
            }
        }
    }

    private void initialiseFixed() {

		if (status == SimulatorStatus.UNINITIALISED || status == SimulatorStatus.INITIALISED) {
            // Test methods
        // Add stations
        trainSystem.addStation("Station A");
        trainSystem.addStation("Station B");
        trainSystem.addStation("Station C");

        Station stationA = trainSystem.getStationByName("Station A");
        Station stationB = trainSystem.getStationByName("Station B");
        Station stationC = trainSystem.getStationByName("Station C");


        // Add segments
        trainSystem.addSegment("Segment 1", "Station A", "Station B");
        trainSystem.addSegment("Segment 2", "Station B", "Station C");

        Segment segment1 = trainSystem.getSegmentByName("Segment 1");
        Segment segment2 = trainSystem.getSegmentByName("Segment 2");
        
        // Add trains
        trainSystem.addTrain("Train 1");
        trainSystem.addTrain("Train 2");

        // get train by name
        Train train1 = trainSystem.getTrainByName("Train 1");
        Train train2 = trainSystem.getTrainByName("Train 2");
    

        //get all segments
        LinkedList<Segment> segments = trainSystem.getSegments();

        // Add routes
        LinkedList<Station> stations = trainSystem.getStations();
        
        System.out.println("Number of stations retrieved: " + stations.size());


        //Route
        trainSystem.addRoute("Route 1", false, stations);

        // Register train to a route
        trainSystem.registerTrain("Train 1", "Route 1");
        trainSystem.registerTrain("Train 2", "Route 1");

        Route route1 = trainSystem.getRouteByName("Route 1");

        route1.setSegments(segments);
        route1.getSegmentsForRoute();
        route1.getTrainsForRoute(trainSystem);

        System.out.println("The segments in the route are: " + route1.toString());

       
        // System.out.println("The stations in the route are: " + route1.getStations());

        

    

        // Set the current Station of the trains

        Station rss = route1.getStart();

        train1.setcurrentStation(rss);
        train2.setcurrentStation(rss);

        System.out.println("The current Station of the train is: " + train1.getcurrentStation());
        System.out.println("The current Station of the train is: " + train2.getcurrentStation());


       
        //print station
        // System.out.println(stationA);
        // System.out.println(stationB);
        // Set the current Station of the trains
        


        

        //create train listener
        MoveEventListenerImpl trainListener = new MoveEventListenerImpl(route1);
        CFOSEventListenerImpl cfosListener = new CFOSEventListenerImpl();
        LightEventListenerImpl lightListener = new LightEventListenerImpl();
        OccupiedEventListenerImpl occupiedListener = new OccupiedEventListenerImpl();

        // Register train listener
        train1.registerListener(trainListener);
        train2.registerListener(trainListener);

        // Register cfos listener
        segment1.registerListener(cfosListener);
        segment2.registerListener(cfosListener);
        stationA.registerListener(cfosListener);
        stationB.registerListener(cfosListener);
        stationC.registerListener(cfosListener);

        // Register light listener
        segment1.registerListener(lightListener);
        segment2.registerListener(lightListener);
        stationA.registerListener(lightListener);
        stationB.registerListener(lightListener);
        stationC.registerListener(lightListener);

        // Register occupied listener
        segment1.registerListener(occupiedListener);
        segment2.registerListener(occupiedListener);

        // Move the train
        train1.advance(currentTime);
        segment2.close();
        train2.advance(currentTime);
        segment2.open();
        train2.advance(currentTime);        


        // List<String> trainEvents = train1.getEvents();
        // List<String> train2Events = train2.getEvents();

        // Print events
        // printEvents(trainEvents);
        // printEvents(train2Events);
        trainSystem.getAllEvents();
        // trainSystem.displayEvents();

        // Verify the train system
        // System.out.println("Train system verification status: " + trainSystem.verify());
		}

		// otherwise do nothing as the simulation has started.
	}
        /**
     * Sets the simulation status to INITIALISED.
     *
     */
    private void setInitialised() {
		this.status = SimulatorStatus.INITIALISED;
	}

    // otherwise do nothing as the simulation has started.


    // Method to register observers
    public void registerObserver(TimeObserver observer) {
        observers.add(observer);
    }

    // Method to update simulator time


    // Method to notify observers
    private void notifyObservers() {
        for (TimeObserver observer : observers) {
            observer.updateTime(currentTime);
        }
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
        this.currentTime = currentTime;
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
    // Variable to keep track of the number of times executeEvents() function runs


public void simulate() {
    while (!isFinished()) {
        currentTime++; // Increment current time
        // Check if the eventEventsCounter is greater than or equal to the length of eventsLog
        // if (eventEventsCounter < eventsLog.size()) {
            executeEvents();
        //     continue; // Stop calling executeEvents() if condition is met
        // }


        LinkedList<Event> events = trainSystem.advance(); // Advance the train system and get events

        for (Event event : events) {
            addToLog(event); // Add events to the log
        }

        if (currentTime >= MAX_SIMULATION_TIME) {
            status = SimulatorStatus.FINISHED; // Set status to finished if maximum simulation time reached
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

    public static void main(String[] args) throws FileNotFoundException {
		Simulator simulator;

		if (args.length > 0)
			simulator = new Simulator(args[0]);
		else
		simulator = new Simulator();
        simulator.initialise("C:\\Users\\Joel Blenman\\Desktop\\UWI\\OOP-Concepts\\project\\com\\uwi\\ilenius\\p2\\models\\sData.txt");

		simulator.simulate();
		simulator.validate();
		System.out.println(simulator.toShortString());
		// System.out.println(simulator); // uncomment to print all the events
	}
}
