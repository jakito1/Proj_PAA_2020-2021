package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import smartgraph.view.containers.SmartGraphDemoContainer;
import smartgraph.view.graphview.SmartCircularSortedPlacementStrategy;
import smartgraph.view.graphview.SmartGraphPanel;
import smartgraph.view.graphview.SmartPlacementStrategy;


public class SocialNetworkView{

    private SocialNetwork socialNetwork;
    private SmartGraphPanel<User, Relationship> graphView;
    private Graph<User, Relationship> graph;
    SmartPlacementStrategy strategy;
    private final Stage stage;
    private BorderPane pane;
    private Caretaker caretaker;


    /**
     * Permite criar um novo SocialNetworkView
     */
    public SocialNetworkView() {
        strategy = new SmartCircularSortedPlacementStrategy();
        stage = new Stage(StageStyle.DECORATED);

    }

    /**
     * Permite criar um novo SocialNetworkView
     * @param socialNetwork objeto SocialNetwork
     */
    public SocialNetworkView(SocialNetwork socialNetwork){
        this();
        this.socialNetwork = socialNetwork;
        this.graph = this.socialNetwork.getGraph();
        this.caretaker = new Caretaker(socialNetwork);

        this.createGraphView();
    }


    /**
     * Inicia o SocialNetworkView
     */
    public void startCentralConsole() {
        this.createCenter();
    }


    /**
     * Cria um Menu no SocialNetworkView com varias opcoes
     * @return o menu criado no SocialNetworkView
     */
    private Node createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuOptions = new Menu("Options");
        Menu menuOptions1 = new Menu("Save/Update");
        Menu menuOptions2 = new Menu("Stats");

        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuUndo = new MenuItem("Undo");
        MenuItem menuAddUser = new MenuItem("Add user");
        MenuItem menuAddIndirectRelationship = new MenuItem("Add indirect relationships");
        MenuItem menuDijkstra = new MenuItem("Dijkstra");

        MenuItem menuSaveGraph = new MenuItem("SaveGraph");
        MenuItem menuUpdateGraph = new MenuItem("UpdateGraph");
        MenuItem menuSaveLog = new MenuItem("SaveLog");
        MenuItem menuUpdateLog = new MenuItem("UpdateLog");

        MenuItem menuStats1 = new MenuItem("Statistic Users Added");
        MenuItem menuStats2 = new MenuItem("Statistic Users Included by User Added");
        MenuItem menuStats3 = new MenuItem("Statistic User With More Direct Relationships");
        MenuItem menuStats4 = new MenuItem("Statistic Interest Most Shared");
        MenuItem menuStats5 = new MenuItem("Bar chart with the number of relationships of\n the 5 users with the most relationships");
        MenuItem menuStats6 = new MenuItem("Bar chart with the top 5 interest");

        menuOptions.getItems().addAll(menuExit, menuUndo, menuAddUser, menuAddIndirectRelationship, menuDijkstra);
        menuOptions1.getItems().addAll(menuSaveGraph, menuUpdateGraph, menuSaveLog, menuUpdateLog);
        menuOptions2.getItems().addAll(menuStats1, menuStats2, menuStats3, menuStats4, menuStats5, menuStats6);

        menuExit.setOnAction(e -> Platform.exit());

        menuAddUser.setOnAction(e -> {
            this.createNodeAddUser();

        });

        menuAddIndirectRelationship.setOnAction(e -> {
            this.createNodeAddIndirectRelationships();

        });

        menuDijkstra.setOnAction(e -> {
            this.addDijkstra();

        });

        menuStats1.setOnAction(e -> {
            this.addStatUsersAdded();

        });

        menuStats2.setOnAction(e -> {
            this.addStatUsersIncludedByUserAdded();

        });

        menuStats3.setOnAction(e -> {
            this.addStatUserWithMoreDirectRelationships();

        });

        menuStats4.setOnAction(e -> {
            this.addStatInterestMostShared();

        });

        menuStats5.setOnAction(e -> {
            this.addStatTopFiveUsersWithMostRelationships();

        });

        menuStats6.setOnAction(e -> {
            this.addStatTopFiveInterestsStats();
        });


        menuUndo.setOnAction(e -> {

            System.out.println("Size: antes " + this.socialNetwork.getGraph().numVertices());

            this.caretaker.restoreState();

            System.out.println("Size: depois " + this.socialNetwork.getGraph().numVertices());

            pane.setLeft(this.graphView);
            this.graphView.update();
        });


        menuBar.getMenus().addAll(menuOptions, menuOptions1, menuOptions2);

        return menuBar;
    }


}
