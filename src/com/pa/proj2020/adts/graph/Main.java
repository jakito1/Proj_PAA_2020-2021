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

    private volatile boolean running;

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

        //graphView.setAutomaticLayout(true);


    }




    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*ReadData a = new ReadData();
        System.out.println(a.readData("interest_names.csv"));*/

        launch(args);


    }

    private Graph<User, Relationship> build_sample_graph() {

        Graph<User, Relationship> g = new DirectGraph<>();

        User user1 = new User("Ana", 1);
        User user2 = new User("Tiago", 2);
        User user3 = new User("Chico", 3);
        User user4 = new User("Miguel", 4);

        Vertex<User> a= g.insertVertex(user1);
        Vertex<User> b=g.insertVertex(user2);
        Vertex<User> c=g.insertVertex(user3);
        Vertex<User> d=g.insertVertex(user4);


        //Vertex<User> e=g.insertVertex();
        //Vertex<User> f=g.insertVertex();

        //System.out.println(a.element().getName());


        g.insertEdge(a, b, new RelationshipSimple());
        g.insertEdge(b  , a, new RelationshipSimple());
        g.insertEdge(a, c, new RelationshipSimple());
        g.insertEdge(a, d, new RelationshipSimple());
        /*g.insertEdge(b, c, "BC");
        g.insertEdge(c, d, "CD");
        g.insertEdge(b, e, "BE");
        g.insertEdge(f, d, "DF");
        g.insertEdge(f, d, "DF2");*/

        //yep, its a loop!
        g.insertEdge(a, a, new RelationshipSimple());

        return g;
    }

    private static final Random random = new Random(/* seed to reproduce*/);



}
