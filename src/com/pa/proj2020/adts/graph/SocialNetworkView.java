package com.pa.proj2020.adts.graph;





import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
     *
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
        MenuItem menuStats6 = new MenuItem("Bar chart with the number of relationships of\n the 5 users with the most relationships");

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

        menuUndo.setOnAction(e -> {

            System.out.println("Size: antes " + this.socialNetwork.getGraph().numVertices());

            this.caretaker.restoreState();

            System.out.println("Size: depois " + this.socialNetwork.getGraph().numVertices());
            this.graphView.update();
            pane.setLeft(this.graphView);

        });



//        menuUndo.setOnAction(e -> {
//            caretaker.requestRestore(this.webGenerator);
//            this.graphView.update();
//
//        });

//        menuSave.setOnAction(e -> {
//
//            this.webdaojson = new WebDAOJSON(this.webGenerator.getName());
//
//            webdaojson.printGraph(this.webGenerator);
//
//        });
//
//        menuUpdate.setOnAction(e -> {
//            this.webdaojson = new WebDAOJSON(this.webGenerator.getName());
//            WebDAOJSON test = (WebDAOJSON) this.webdaojson;
//            WebGenerator web1 = null;
//            web1 = ((WebGenerator) test.updateGraph());
//
//        });

        menuBar.getMenus().addAll(menuOptions, menuOptions1, menuOptions2);

        return menuBar;
    }

    /**
     * Cria a janela inicial do WebGeneratorView
     *
     * @return a janela criada
     */
    private void createCenter() {
        Image img = new Image("images/SocialNetworkImage.jpg");

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(850.0);
        imageView.setImage(img);


        VBox center = new VBox();
        center.setPadding(new Insets(-10, 12, 15, 12));
        center.setSpacing(15);
        center.setAlignment(Pos.CENTER);

        Font sanSerif = Font.font("SanSerif", 35);
        Font sanSerif1 = Font.font("SanSerif", 15);
        Font sanSerif2 = Font.font("SanSerif", 10);

        Text welcomeText = new Text("Social Network");
        Text welcomeText1 = new Text("Insert name of file with relationships");
        Text welcomeText2 = new Text("Insert name of file with interests");
        Text welcomeText3 = new Text("Insert name of file with user_names");
        Text welcomeText4 = new Text("Insert name of file with interest_names");
        Text welcomeText5 = new Text("Note: leaving in blank will set default values");

        welcomeText.setFill(Color.BLUE);
        welcomeText.setFont(sanSerif);

        welcomeText1.setFill(Color.BLUE);
        welcomeText1.setFont(sanSerif1);

        welcomeText2.setFill(Color.BLUE);
        welcomeText2.setFont(sanSerif1);

        welcomeText3.setFill(Color.BLUE);
        welcomeText3.setFont(sanSerif1);

        welcomeText4.setFill(Color.BLUE);
        welcomeText4.setFont(sanSerif1);

        welcomeText5.setFill(Color.BLACK);
        welcomeText5.setFont(sanSerif2);

        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(10));
        hBox1.setSpacing(10);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);


        center.getChildren().addAll(welcomeText, imageView);

        TextField relationshipsField = new TextField();
        relationshipsField.setText("interest_names.csv");
        relationshipsField.setMaxWidth(250);
        relationshipsField.setMinWidth(250);

        center.getChildren().add(relationshipsField);

        TextField interestsField = new TextField();
        interestsField.setText("interests.csv");
        interestsField.setMaxWidth(250);
        interestsField.setMinWidth(250);

        Label label1 = new Label();
        label1.setText("Relationships: ");
        label1.setLabelFor(relationshipsField);
        label1.setTextFill(Color.BLUE);
        label1.setFont(sanSerif1);

        Label label2 = new Label();
        label2.setText("Interests: ");
        label2.setLabelFor(relationshipsField);
        label2.setTextFill(Color.BLUE);
        label2.setFont(sanSerif1);

        hBox1.getChildren().addAll(label1, relationshipsField, label2, interestsField);
        hBox1.setAlignment(Pos.CENTER);

        center.getChildren().addAll(hBox1);

        TextField userNamesField = new TextField();
        userNamesField.setText("relationships.csv");
        userNamesField.setMaxWidth(250);
        userNamesField.setMinWidth(250);

        TextField interestNamesField = new TextField();
        interestNamesField.setText("user_names.csv");
        interestNamesField.setMaxWidth(250);
        interestNamesField.setMinWidth(250);

        Label label3 = new Label();
        label3.setText("User Names: ");
        label3.setLabelFor(userNamesField);
        label3.setTextFill(Color.BLUE);
        label3.setFont(sanSerif1);

        Label label4 = new Label();
        label4.setText("Interest Names: ");
        label4.setLabelFor(interestNamesField);
        label4.setTextFill(Color.BLUE);
        label4.setFont(sanSerif1);

        hBox2.getChildren().addAll(label3, userNamesField, label4, interestNamesField);
        hBox2.setAlignment(Pos.CENTER);

        center.getChildren().addAll(hBox2);

        TextField nameField = new TextField();

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        nameField.setText("Model Name");
        nameField.setMaxWidth(250);
        nameField.setMinWidth(250);
        center.getChildren().add(nameField);

        Button okIteractiveButton = new Button("START ITERATIVE");

        okIteractiveButton.setOnAction(e -> {
            stage.close();
            this.createCenterSocialNetworkView();
            this.graphView.setAutomaticLayout(true);

        });

        Button okTotalButton = new Button("START TOTAL");

        okTotalButton.setOnAction(e -> {
            stage.close();
            this.socialNetwork.constructModelTotal();
            this.createCenterSocialNetworkView();

        });

        Label label5 = new Label();
        label5.setText("Model Name: ");
        label5.setLabelFor(nameField);
        label5.setTextFill(Color.BLUE);
        label5.setFont(sanSerif1);

        hBox.getChildren().addAll(label5, nameField, okIteractiveButton, okTotalButton);
        hBox.setAlignment(Pos.CENTER);
        center.getChildren().addAll(hBox, welcomeText5);
        BorderPane pane = new BorderPane();
        pane.setCenter(center);

        Scene scene = new Scene(pane, 850, 840);

        stage.setTitle("SocialNetwork Controller");
        stage.setScene(scene);
        stage.show();

    }

    public void createNodeAddUser(){
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD USER");

        ComboBox<String> texts = new ComboBox<String>();
        texts.getItems().addAll(this.socialNetwork.getUsersNotInserted());

        texts.getItems().setAll(texts.getItems().sorted());

        texts.setPromptText("Select a user to add");
        texts.setPrefSize(230, 20);

        addUserButton.setOnAction(e -> {
            this.socialNetwork.constructModelIterative(Integer.parseInt(((String)texts.getValue()).split(" ")[0]));
            texts.getItems().remove(texts.getValue());

            this.caretaker.saveState();

            graphView.update();
        });

        updateButton.setOnAction(e -> {
            this.updateGraphColors();

        });

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        hBox2.getChildren().addAll(addUserButton, updateButton);

        VBox center2 = new VBox();
        center2.setPadding(new Insets(-10, 12, 15, 12));
        center2.setSpacing(15);
        center2.setAlignment(Pos.CENTER);
        center2.getChildren().addAll(texts, hBox2);

        pane.setCenter(center2);

    }



    public void createNodeAddIndirectRelationships(){
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD INDIRECT RELATIONSHIPS");

        ComboBox<String> texts = new ComboBox<String>();
        for(Vertex<User> user : this.socialNetwork.getGraph().vertices()){
            texts.getItems().add(user.element().toString());
        }

        texts.getItems().setAll(texts.getItems().sorted());

        texts.setPromptText("Select a user to add indirect relationships");
        texts.setPrefSize(230, 20);

        addUserButton.setOnAction(e -> {
            this.socialNetwork.addIndirectRelationships(Integer.parseInt(((String)texts.getValue()).split(" ")[0]));
            texts.getItems().remove(texts.getValue());
            graphView.update();
        });

        updateButton.setOnAction(e -> {
            this.updateGraphColors();

        });

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        hBox2.getChildren().addAll(addUserButton, updateButton);

        VBox center2 = new VBox();
        center2.setPadding(new Insets(-10, 12, 15, 12));
        center2.setSpacing(15);
        center2.setAlignment(Pos.CENTER);
        center2.getChildren().addAll(texts, hBox2);

        pane.setCenter(center2);

    }




    public void addDijkstra(){

        Button dijkstraButton = new Button("Dijkstra");
        ArrayList<User> path = new ArrayList<>();

        ComboBox<String> textsUser1 = new ComboBox<String>();
        ComboBox<String> textsUser2 = new ComboBox<String>();

        for(Vertex<User> user : this.socialNetwork.getGraph().vertices()){
            textsUser1.getItems().add(user.element().toString());
            textsUser2.getItems().add(user.element().toString());
        }

        textsUser2.getItems().setAll(textsUser2.getItems().sorted());
        textsUser1.getItems().setAll(textsUser1.getItems().sorted());

        textsUser1.setPromptText("Select the origin user");
        textsUser1.setPrefSize(230, 20);

        textsUser2.setPromptText("Select the destiny user");
        textsUser2.setPrefSize(230, 20);


        dijkstraButton.setOnAction(e -> {
            Vertex<User> user1 = null;
            Vertex<User> user2 = null;



                    for(Vertex<User> userVertex : this.socialNetwork.getGraph().vertices()){
                        if(userVertex.element().getID() == Integer.parseInt(((String)textsUser1.getValue()).split(" ")[0])){
                            user1 = userVertex;
                        } else if(userVertex.element().getID() == Integer.parseInt(((String)textsUser2.getValue()).split(" ")[0])){
                            user2 = userVertex;
                        }
                    }

                    this.updateGraphColors();

            if(((VBox)this.pane.getCenter()).getChildren().get(((VBox)this.pane.getCenter()).getChildren().size()-1) instanceof Text ){
                ((VBox)this.pane.getCenter()).getChildren().remove(((VBox)this.pane.getCenter()).getChildren().size()-1);
            }
                    if(user1 != null && user2 != null){
                        try {
                            this.socialNetwork.getGraph().minCostPath(user1, user2, path);
                            graphView.getStylableVertex(user1).setStyleClass("myVertexDijkstra");
                            for(User user : path){
                                graphView.getStylableVertex(user).setStyleClass("myVertexDijkstra");
                            }

                            for(User user : path){
                                for(Edge<Relationship, User> edge : this.socialNetwork.getGraph().outboundEdges(user1)){
                                    if(edge.vertices()[1].element().equals(user)){
                                        graphView.getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
                                    }
                                }
                            }

                                for(int i=0; i< path.size()-1; i++) {
                                    Vertex<User> userVertex = null;
                                    Vertex<User> userVertex2 = null;

                                    for(Vertex<User> userVertex1 : this.socialNetwork.getGraph().vertices()){
                                        if(userVertex1.element().getID() == path.get(i).getID()){
                                            userVertex = userVertex1;
                                        } else if(userVertex1.element().getID() == path.get(i+1).getID()){
                                            userVertex2 = userVertex1;
                                        }
                                    }
                                    if(userVertex == null || userVertex2 == null){
                                        break;
                                    }

                                    for (Edge<Relationship, User> edge : this.socialNetwork.getGraph().outboundEdges(userVertex)) {
                                        if (edge.vertices()[1].element().equals(userVertex2.element())) {
                                            graphView.getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
                                        }
                                    }
                                }

                        }catch (NullPointerException ex){
                            Text text = new Text("There's no path, please try again");
                            ((VBox)this.pane.getCenter()).getChildren().add(text);
                        }
                    }

            graphView.update();
        });



//        HBox hBox2 = new HBox();
//        hBox2.setPadding(new Insets(10));
//        hBox2.setSpacing(10);
//        hBox2.setAlignment(Pos.CENTER);
//
//        hBox2.getChildren().addAll(addUserButton, updateButton);

        VBox center2 = new VBox();
        center2.setPadding(new Insets(-10, 12, 15, 12));
        center2.setSpacing(15);
        center2.setAlignment(Pos.CENTER);
        center2.getChildren().addAll(textsUser1, textsUser2, dijkstraButton);

        pane.setCenter(center2);

    }


    /**
     * Cria uma janela com a informacao associadao ao SocialNetworkView
     */
    public void createCenterSocialNetworkView() {
        if(this.socialNetwork == null){
            this.socialNetwork = new SocialNetwork();
            this.socialNetwork.initializeData();
        }

        graphView = new SmartGraphPanel(this.socialNetwork.getGraph(), strategy);

        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();

        SmartGraphDemoContainer smartGraphView = new SmartGraphDemoContainer(this.graphView);
        smartGraphView.setManaged(true);
        smartGraphView.setMinWidth(600);
        smartGraphView.setMaxWidth(650);

        pane = new BorderPane();
        pane.setTop(this.createMenu());
        pane.setLeft(smartGraphView);

        Scene scene = new Scene(pane, 1024, 600);

        stage.setScene(scene);
        stage.show();
        graphView.init();
    }

    public void updateGraphColors(){
        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();
    }


    public void addStatUsersAdded(){
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 500);
        list.getItems().add(this.socialNetwork.addedUsersStats());

        pane.setCenter(list);
    }

    public void addStatUsersIncludedByUserAdded(){
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 500);
        list.getItems().add(this.socialNetwork.includedUsersStats());


        pane.setCenter(list);
    }

    public void addStatUserWithMoreDirectRelationships(){
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 100);
        list.getItems().add(this.socialNetwork.userWithMoreDirectRelationshipsStats());

        pane.setCenter(list);
    }

    public void addStatInterestMostShared(){
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 100);
        list.getItems().add(this.socialNetwork.interestMostSharedStats());

        pane.setCenter(list);
    }



    public Node createGraphView(){
        graphView = new SmartGraphPanel(graph, strategy);

        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();

        SmartGraphDemoContainer smartGraphView = new SmartGraphDemoContainer(graphView);
        smartGraphView.setManaged(true);
        smartGraphView.setMinWidth(600);

        return smartGraphView;
    }

    public void updateColorsVertexGraph(){
        if(this.socialNetwork.getGraph().numVertices() == 0){
            return;
        }

        for(Vertex<User> userVertex : this.socialNetwork.getGraph().vertices()){
            if(userVertex.element().getType().equals(Type.INCLUIDO)){
                System.out.println(userVertex.element());
                System.out.println(userVertex);
                System.out.println(graphView.getChildren());
                this.graphView.getStylableVertex(userVertex).setStyleClass("myVertexIncluded");
            } else {
                this.graphView.getStylableVertex(userVertex).setStyleClass("myVertexAdded");
            }
        }
    }

    public void updateColorsEdgesGraph(){
        if(this.socialNetwork.getGraph().numEdges() == 0){
            return;
        }

        for(Edge<Relationship, User> relationshipEdge : this.socialNetwork.getGraph().edges()){

            if(relationshipEdge.element() instanceof  RelationshipIndirect ){
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeIndirect");
            }else if(relationshipEdge.element() instanceof  RelationshipSimple) {
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectSimple");
            }
            else {
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectShared");
            }
        }
    }

}
