package com.pa.proj2020.adts.graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import smartgraph.view.containers.SmartGraphDemoContainer;
import smartgraph.view.graphview.SmartCircularSortedPlacementStrategy;
import smartgraph.view.graphview.SmartGraphPanel;
import smartgraph.view.graphview.SmartPlacementStrategy;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage ignored) {
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.initializeData();
        Graph<User, Relationship> g = socialNetwork.constructModel(); //Build Graph

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<User, Relationship> graphView = new SmartGraphPanel(g, strategy);

        SmartGraphDemoContainer container = new SmartGraphDemoContainer(graphView);

        Scene scene = new Scene(container, 1024, 768);
        
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Projeto PA 2021/2022");
        stage.setResizable(false);
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();

        graphView.init();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}