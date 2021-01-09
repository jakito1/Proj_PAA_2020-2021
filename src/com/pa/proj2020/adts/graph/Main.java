package com.pa.proj2020.adts.graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import smartgraph.view.containers.SmartGraphDemoContainer;
import smartgraph.view.graphview.SmartCircularSortedPlacementStrategy;
import smartgraph.view.graphview.SmartGraphPanel;
import smartgraph.view.graphview.SmartPlacementStrategy;

public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ignored) {
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.initializeData();

        socialNetwork.constructModelIteractive(1);

        socialNetwork.constructModelIteractive(9);
        socialNetwork.constructModelIteractive(11);
        socialNetwork.constructModelIteractive(14);
        System.out.println(socialNetwork.toString());

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(socialNetwork.addedUsersStats());
        System.out.println(socialNetwork.includedUsersStats());
        System.out.println(socialNetwork.interestMostSharedStats());
        System.out.println(socialNetwork.userWithMoreDirectRelationshipsStats());
        System.out.println("---------------------------------------------------------------------------------");

        Graph<User, Relationship> g = socialNetwork.getGraph();

        //Graph<User, Relationship> g = socialNetwork.constructModelTotal(); //Build Graph automatico

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
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(socialNetwork.getLog().toString());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        graphView.init();
    }

}