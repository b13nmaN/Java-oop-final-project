// package com.uwi.ilenius.p2;

// import java.util.List;

// import com.uwi.ilenius.p2.models.TrainSystem;
// import com.uwi.ilenius.p2.models.Station;
// import com.uwi.ilenius.p2.models.Segment;
// import com.uwi.ilenius.p2.models.Simulator;
// import com.uwi.ilenius.p2.event_listeners.MoveEventListener;
// import com.uwi.ilenius.p2.event_listeners_impl.MoveEventListenerImpl;
// import com.uwi.ilenius.p2.event_listeners_impl.CFOSEventListenerImpl;
// import com.uwi.ilenius.p2.event_listeners_impl.LightEventListenerImpl;
// import com.uwi.ilenius.p2.event_listeners_impl.OccupiedEventListenerImpl;
// import com.uwi.ilenius.p2.events.MoveEvent;
// import com.uwi.ilenius.p2.events.OccupiedEvent;
// import com.uwi.ilenius.p2.models.Route;
// import com.uwi.ilenius.p2.models.Train;
// import java.util.LinkedList;

// import com.uwi.ilenius.p2.events.CFOSEvent;
// import com.uwi.ilenius.p2.events.Event;


// public class Main {
//     public static void main(String[] args) {
//         // Create an instance of TrainSystem
//         // Simulator simulator = new Simulator();
//         TrainSystem trainSystem = new TrainSystem(simulator);

//         // Test methods
//         // Add stations
//         trainSystem.addStation("Station A");
//         trainSystem.addStation("Station B");
//         trainSystem.addStation("Station C");

//         Station stationA = trainSystem.getStationByName("Station A");
//         Station stationB = trainSystem.getStationByName("Station B");
//         Station stationC = trainSystem.getStationByName("Station C");


//         // Add segments
//         trainSystem.addSegment("Segment 1", "Station A", "Station B");
//         trainSystem.addSegment("Segment 2", "Station B", "Station C");

//         Segment segment1 = trainSystem.getSegmentByName("Segment 1");
//         Segment segment2 = trainSystem.getSegmentByName("Segment 2");
        
//         // Add trains
//         trainSystem.addTrain("Train 1");
//         trainSystem.addTrain("Train 2");

//         // get train by name
//         Train train1 = trainSystem.getTrainByName("Train 1");
//         Train train2 = trainSystem.getTrainByName("Train 2");
    

//         //get all segments
//         LinkedList<Segment> segments = trainSystem.getSegments();

//         // Add routes
//         LinkedList<Station> stations = trainSystem.getStations();
        
//         System.out.println("Number of stations retrieved: " + stations.size());


//         //Route
//         trainSystem.addRoute("Route 1", false, stations);

//         // Register train to a route
//         trainSystem.registerTrain("Train 1", "Route 1");
//         trainSystem.registerTrain("Train 2", "Route 1");

//         Route route1 = trainSystem.getRouteByName("Route 1");

//         route1.setSegments(segments);
//         route1.getSegmentsForRoute();
//         route1.getTrainsForRoute(trainSystem);

//         System.out.println("The segments in the route are: " + route1.toString());

       
//         // System.out.println("The stations in the route are: " + route1.getStations());

        

    

//         // Set the current Station of the trains

//         Station rss = route1.getStart();

//         train1.setcurrentStation(rss);
//         train2.setcurrentStation(rss);

//         System.out.println("The current Station of the train is: " + train1.getcurrentStation());
//         System.out.println("The current Station of the train is: " + train2.getcurrentStation());


       
//         //print station
//         // System.out.println(stationA);
//         // System.out.println(stationB);
//         // Set the current Station of the trains
        


        

//         //create train listener
//         MoveEventListenerImpl trainListener = new MoveEventListenerImpl(route1);
//         CFOSEventListenerImpl cfosListener = new CFOSEventListenerImpl();
//         LightEventListenerImpl lightListener = new LightEventListenerImpl();
//         OccupiedEventListenerImpl occupiedListener = new OccupiedEventListenerImpl();

//         // Register train listener
//         train1.registerListener(trainListener);
//         train2.registerListener(trainListener);

//         // Register cfos listener
//         segment1.registerListener(cfosListener);
//         segment2.registerListener(cfosListener);
//         stationA.registerListener(cfosListener);
//         stationB.registerListener(cfosListener);
//         stationC.registerListener(cfosListener);

//         // Register light listener
//         segment1.registerListener(lightListener);
//         segment2.registerListener(lightListener);
//         stationA.registerListener(lightListener);
//         stationB.registerListener(lightListener);
//         stationC.registerListener(lightListener);

//         // Register occupied listener
//         segment1.registerListener(occupiedListener);
//         segment2.registerListener(occupiedListener);

//         // Move the train
//         train1.advance(10);
//         segment2.close();
//         train2.advance(20);
//         segment2.open();
//         train2.advance(20);        


//         List<String> trainEvents = train1.getEvents();
//         List<String> train2Events = train2.getEvents();

//         // Print events
//         printEvents(trainEvents);
//         printEvents(train2Events);
//         trainSystem.getAllEvents();
//         trainSystem.displayEvents();

//         // Verify the train system
//         // System.out.println("Train system verification status: " + trainSystem.verify());
//     }

//     public static void printEvents(List<String> events) {
//         for (String event : events) {
//             System.out.println(event);
//         }
//     }

    
// }


