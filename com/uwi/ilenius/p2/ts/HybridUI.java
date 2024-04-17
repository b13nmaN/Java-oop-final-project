package com.uwi.ilenius.p2.ts;


import com.uwi.ilenius.p2.models.Simulator;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

public class HybridUI extends Application {

    private Simulator simulator;

    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Train Simulation");

        // Create a button to start the simulation
        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(event -> {
            try {
                // Initialize the simulator
                simulator = new Simulator();
                simulator.initialise("C:\\Users\\Joel Blenman\\Desktop\\UWI\\OOP-Concepts\\project\\com\\uwi\\ilenius\\p2\\models\\sData.txt");

                // Start the simulation in a new thread
                Thread simulationThread = new Thread(() -> {
                    simulator.simulate();
                    updateStatusLabel("Simulation has finished");
                });
                simulationThread.start();

                // Update status label
                updateStatusLabel("Simulation has started");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Create label for ATMS time
        Label atmsLabel = new Label("ATMS(Automatic Train Management System)");

        // Create status label
        statusLabel = new Label("");

        // Center the button and label vertically
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(startButton, atmsLabel, statusLabel);
        root.setPrefSize(300, 200);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // Method to update status label
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }


}

