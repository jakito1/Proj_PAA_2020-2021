package com.pa.proj2020.adts.graph;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import observer.Observer;
import smartgraph.view.containers.SmartGraphDemoContainer;
import smartgraph.view.graphview.SmartCircularSortedPlacementStrategy;
import smartgraph.view.graphview.SmartGraphPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SocialNetworkView implements Observer {

    private final Stage stage;
    private final ViewObjectCreator viewObjectCreator = ViewObjectCreator.getInstance();
    private SocialNetwork socialNetwork;
    private SmartGraphPanel<User, Relationship> graphView;
    private BorderPane pane;
    private Caretaker caretaker;


    /**
     * Permite criar um novo SocialNetworkView
     */
    public SocialNetworkView() {
        stage = new Stage(StageStyle.DECORATED);
    }

    /**
     * Permite criar um novo SocialNetworkView
     *
     * @param socialNetwork objeto SocialNetwork
     */
    public SocialNetworkView(SocialNetwork socialNetwork) {
        this();
        this.socialNetwork = socialNetwork;
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

        MenuItem menuExportSerialize = new MenuItem("Export to Java Serialize");
        MenuItem menuImportSerialize = new MenuItem("Import from Java Serialize");
        MenuItem menuExportJSON = new MenuItem("Export to JSON");

        MenuItem menuStats1 = new MenuItem("Statistic Users Added");
        MenuItem menuStats2 = new MenuItem("Statistic Users Included by User Added");
        MenuItem menuStats3 = new MenuItem("Statistic User With More Direct Relationships");
        MenuItem menuStats4 = new MenuItem("Statistic Interest Most Shared");
        MenuItem menuStats5 = new MenuItem("Bar chart with the number of relationships of\n " +
                "the 5 users with the most relationships");
        MenuItem menuStats6 = new MenuItem("Bar chart with the top 5 interest");

        menuOptions.getItems().addAll(menuExit, menuUndo, menuAddUser, menuAddIndirectRelationship, menuDijkstra);
        menuOptions1.getItems().addAll(menuExportSerialize, menuImportSerialize, menuExportJSON);
        menuOptions2.getItems().addAll(menuStats1, menuStats2, menuStats3, menuStats4, menuStats5, menuStats6);

        menuExit.setOnAction(e -> Platform.exit());

        menuAddUser.setOnAction(e -> this.createNodeAddUser());

        menuAddIndirectRelationship.setOnAction(e -> this.createNodeAddIndirectRelationships());

        menuDijkstra.setOnAction(e -> this.addDijkstra());

        menuExportSerialize.setOnAction(e -> this.socialNetwork.exportSerialization());

        menuImportSerialize.setOnAction(e -> {
            this.socialNetwork.importSerialization();
            this.graphView.update();
        });

        menuExportJSON.setOnAction(e -> this.socialNetwork.exportJSON());

        menuStats1.setOnAction(e -> this.addStatUsersAdded());

        menuStats2.setOnAction(e -> this.addStatUsersIncludedByUserAdded());

        menuStats3.setOnAction(e -> this.addStatUserWithMoreDirectRelationships());

        menuStats4.setOnAction(e -> this.addStatInterestMostShared());

        menuStats5.setOnAction(e -> this.addStatTopFiveUsersWithMostRelationships());

        menuStats6.setOnAction(e -> this.addStatTopFiveInterestsStats());

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

    /**
     * Cria a janela inicial do SocialNetworkView
     */
    private void createCenter() {
        Image img = new Image("images/SocialNetworkImage.jpg");
        ImageView imageView = viewObjectCreator.createImageView(img);

        VBox center = viewObjectCreator.createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);
        Text welcomeText = viewObjectCreator.createText("SanSerif", 35,
                "Social Network", Color.BLUE);
        Text welcomeText5 = viewObjectCreator.createText("SanSerif", 10,
                "Note: leaving in blank will set default values", Color.BLACK);
        HBox hBox1 = viewObjectCreator.createHBox(new Insets(10), 10, null);
        HBox hBox2 = viewObjectCreator.createHBox(new Insets(10), 10, null);
        center.getChildren().addAll(welcomeText, imageView);
        TextField relationshipsField = viewObjectCreator.createTextField("relationships.csv", 250, 250);
        center.getChildren().add(relationshipsField);
        TextField interestsField = viewObjectCreator.createTextField("interests.csv", 250, 250);
        Label label1 = viewObjectCreator.createLabel("Relationships: ", Color.BLUE, "SanSerif", 15);
        label1.setLabelFor(relationshipsField);
        Label label2 = viewObjectCreator.createLabel("Interests: ", Color.BLUE, "SanSerif", 15);
        label2.setLabelFor(relationshipsField);
        hBox1.getChildren().addAll(label1, relationshipsField, label2, interestsField);
        hBox1.setAlignment(Pos.CENTER);
        center.getChildren().addAll(hBox1);
        TextField userNamesField = viewObjectCreator.createTextField("user_names.csv", 250, 250);
        TextField interestNamesField = viewObjectCreator.createTextField("interest_names.csv", 250, 250);
        Label label3 = viewObjectCreator.createLabel("User Names: ", Color.BLUE, "SanSerif", 15);
        label3.setLabelFor(userNamesField);
        Label label4 = viewObjectCreator.createLabel("Interest Names: ", Color.BLUE, "SanSerif", 15);
        label4.setLabelFor(interestNamesField);
        hBox2.getChildren().addAll(label3, userNamesField, label4, interestNamesField);
        hBox2.setAlignment(Pos.CENTER);
        center.getChildren().addAll(hBox2);
        TextField nameField = viewObjectCreator.createTextField("Model Name", 250, 250);
        HBox hBox = viewObjectCreator.createHBox(new Insets(10), 10, Pos.CENTER);
        center.getChildren().add(nameField);

        Button okInteractiveButton = new Button("START ITERATIVE");
        okInteractiveButton.setOnAction(e -> {
            stage.close();
            this.socialNetwork.setFileNames(userNamesField.getText(), relationshipsField.getText(),
                    interestNamesField.getText(), interestsField.getText());
            this.createCenterSocialNetworkView();
            this.graphView.setAutomaticLayout(true);

        });

        Button okTotalButton = new Button("START TOTAL");
        okTotalButton.setOnAction(e -> {
            stage.close();
            this.socialNetwork.setFileNames(userNamesField.getText(), relationshipsField.getText(),
                    interestNamesField.getText(), interestsField.getText());
            this.socialNetwork.constructModelTotal();
            this.createCenterSocialNetworkView();

        });

        Label label5 = viewObjectCreator.createLabel("Model Name: ", Color.BLUE, "SanSerif", 15);
        label5.setLabelFor(nameField);
        hBox.getChildren().addAll(label5, nameField, okInteractiveButton, okTotalButton);
        center.getChildren().addAll(hBox, welcomeText5);

        BorderPane pane = new BorderPane();
        pane.setCenter(center);
        Scene scene = new Scene(pane, 850, 840);
        stage.setTitle("SocialNetwork Controller");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Cria o menu de adicao de utilizador
     */
    public void createNodeAddUser() {
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD USER");

        ComboBox<String> texts = viewObjectCreator.createComboBoxString("Select a user to add", 230, 20);
        texts.getItems().addAll(this.socialNetwork.getUsersNotInserted());
        texts.getItems().setAll(texts.getItems().sorted());

        addUserButton.setOnAction(e -> {
            this.caretaker.saveState();

            this.socialNetwork.constructModelIterative(Integer.parseInt(texts.getValue().split(" ")[0]));
            texts.getItems().remove(texts.getValue());
            graphView.update();
        });

        updateButton.setOnAction(e -> this.updateGraphColors());

        HBox hBox2 = viewObjectCreator.createHBox(new Insets(10), 10, Pos.CENTER);
        hBox2.getChildren().addAll(addUserButton, updateButton);

        VBox center2 = viewObjectCreator.createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);
        center2.getChildren().addAll(texts, hBox2);

        pane.setCenter(center2);
    }

    /**
     * Cria o menu de adicao de relacionamentos indiretos
     */
    public void createNodeAddIndirectRelationships() {
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD INDIRECT RELATIONSHIPS");

        ComboBox<String> texts = viewObjectCreator.createComboBoxString("Select a user to add indirect relationships",
                230, 20);

        for (Vertex<User> user : this.socialNetwork.getGraph().vertices()) {
            texts.getItems().add(user.element().toString());
        }

        texts.getItems().setAll(texts.getItems().sorted());

        addUserButton.setOnAction(e -> {
            this.socialNetwork.addIndirectRelationships(Integer.parseInt(texts.getValue().split(" ")[0]));
            texts.getItems().remove(texts.getValue());
            graphView.update();
        });

        updateButton.setOnAction(e -> this.updateGraphColors());

        HBox hBox2 = viewObjectCreator.createHBox(new Insets(10), 10, Pos.CENTER);
        hBox2.getChildren().addAll(addUserButton, updateButton);

        VBox center2 = viewObjectCreator.createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);
        center2.getChildren().addAll(texts, hBox2);

        pane.setCenter(center2);
    }

    /**
     * Cria o menu de Dijkstra
     */
    public void addDijkstra() {
        Button dijkstraButton = new Button("Dijkstra");
        ArrayList<User> path = new ArrayList<>();

        ComboBox<String> textsUser1 = viewObjectCreator.createComboBoxString("Select the origin user", 230, 20);
        ComboBox<String> textsUser2 = viewObjectCreator.createComboBoxString("Select the destiny user", 230, 20);

        for (Vertex<User> user : this.socialNetwork.getGraph().vertices()) {
            textsUser1.getItems().add(user.element().toString());
            textsUser2.getItems().add(user.element().toString());
        }

        textsUser2.getItems().setAll(textsUser2.getItems().sorted());
        textsUser1.getItems().setAll(textsUser1.getItems().sorted());

        dijkstraButton.setOnAction(e -> {
            Vertex<User> user1 = null;
            Vertex<User> user2 = null;

            for (Vertex<User> userVertex : this.socialNetwork.getGraph().vertices()) {
                if (userVertex.element().getID() == Integer.parseInt(textsUser1.getValue().split(" ")[0])) {
                    user1 = userVertex;
                } else if (userVertex.element().getID() == Integer.parseInt(textsUser2.getValue().split(" ")[0])) {
                    user2 = userVertex;
                }
            }

            this.updateGraphColors();

            if (((VBox) this.pane.getCenter()).getChildren().get(((VBox) this.pane.getCenter()).getChildren().size() - 1) instanceof Text) {
                ((VBox) this.pane.getCenter()).getChildren().remove(((VBox) this.pane.getCenter()).getChildren().size() - 1);
            }
            if (user1 != null && user2 != null) {
                try {
                    this.socialNetwork.getGraph().minCostPath(user1, user2, path);
                    graphView.getStylableVertex(user1).setStyleClass("myVertexDijkstra");
                    for (User user : path) {
                        graphView.getStylableVertex(user).setStyleClass("myVertexDijkstra");
                    }

                    for (User user : path) {
                        for (Edge<Relationship, User> edge : this.socialNetwork.getGraph().outboundEdges(user1)) {
                            if (edge.vertices()[1].element().equals(user)) {
                                graphView.getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
                            }
                        }
                    }

                    for (int i = 0; i < path.size() - 1; i++) {
                        Vertex<User> userVertex = null;
                        Vertex<User> userVertex2 = null;

                        for (Vertex<User> userVertex1 : this.socialNetwork.getGraph().vertices()) {
                            if (userVertex1.element().getID() == path.get(i).getID()) {
                                userVertex = userVertex1;
                            } else if (userVertex1.element().getID() == path.get(i + 1).getID()) {
                                userVertex2 = userVertex1;
                            }
                        }
                        if (userVertex == null || userVertex2 == null) {
                            break;
                        }

                        for (Edge<Relationship, User> edge : this.socialNetwork.getGraph().outboundEdges(userVertex)) {
                            if (edge.vertices()[1].element().equals(userVertex2.element())) {
                                graphView.getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
                            }
                        }
                    }

                } catch (NullPointerException ex) {
                    Text text = new Text("There's no path, please try again");
                    ((VBox) this.pane.getCenter()).getChildren().add(text);
                }
            }

            graphView.update();
        });

        VBox center2 = viewObjectCreator.createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);
        center2.getChildren().addAll(textsUser1, textsUser2, dijkstraButton);

        pane.setCenter(center2);
    }

    /**
     * Cria uma janela com a informacao associada ao SocialNetworkView
     */
    public void createCenterSocialNetworkView() {
        if (this.socialNetwork == null) {
            this.socialNetwork = new SocialNetwork();
            this.socialNetwork.initializeData();
        }

        graphView = new SmartGraphPanel(this.socialNetwork.getGraph(), new SmartCircularSortedPlacementStrategy());

        graphView.setVertexDoubleClickAction(graphVertex ->
                this.addInformationVertex(graphVertex.getUnderlyingVertex()));

        graphView.setEdgeDoubleClickAction(graphEdge -> this.addInformationEdge(graphEdge.getUnderlyingEdge()));

        this.updateGraphColors();

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

    /**
     * Atualiza as cores do grafo
     */
    public void updateGraphColors() {
        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();
    }

    /**
     * Cria o menu das estatisticas de utilizadores adicionados
     */
    public void addStatUsersAdded() {
        ListView<String> list = viewObjectCreator.createListViewString(350, 500, this.socialNetwork.addedUsersStats());
        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de utilizadores incluidos
     */
    public void addStatUsersIncludedByUserAdded() {
        ListView<String> list = viewObjectCreator.createListViewString(350, 500, this.socialNetwork.includedUsersStats());
        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de utilizadores com mais relacionamentos diretos
     */
    public void addStatUserWithMoreDirectRelationships() {
        ListView<String> list = viewObjectCreator.createListViewString(350, 100,
                this.socialNetwork.userWithMoreDirectRelationshipsStats());
        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de interesse mais partilhado
     */
    public void addStatInterestMostShared() {
        ListView<String> list = viewObjectCreator.createListViewString(350, 100, this.socialNetwork.interestMostSharedStats());
        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de top 5 de utilizadores com mais relacionamentos
     */
    public void addStatTopFiveUsersWithMostRelationships() {
        BarChart<String, Integer> bar = createBarChart("Users", "Relationships",
                "Top Five Users With Most Relationships");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<User, Integer> map = new HashMap<>(this.socialNetwork.topFiveUsersWithMostRelationshipsStats());

        for (User user : map.keySet()) {
            series.getData().add(new XYChart.Data<>(user.toString(), map.get(user)));
            System.out.println("User: " + user.toString() + " Relationships: " + map.get(user));
        }

        bar.getData().add(series);
        pane.setCenter(bar);
    }

    public BarChart<String, Integer> createBarChart(String labelX, String labelY, String title) {
        CategoryAxis xaxis = new CategoryAxis();
        NumberAxis yaxis = new NumberAxis();
        xaxis.setLabel(labelX);
        yaxis.setLabel(labelY);

        BarChart<String, Integer> bar = new BarChart(xaxis, yaxis);
        bar.setTitle(title);

        return bar;
    }

    /**
     * Cria o menu das estatisticas de top 5 interesses
     */
    public void addStatTopFiveInterestsStats() {
        BarChart<String, Integer> bar = createBarChart("Interests", "Users", "Top Five Interests");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<Interest, Integer> map = new HashMap<>(this.socialNetwork.topFiveInterestsStats());

        for (Interest interest : map.keySet()) {
            series.getData().add(new XYChart.Data<>(interest.getName(), map.get(interest)));
            System.out.println("User: " + interest.toString() + " Relationships: " + map.get(interest));
        }

        bar.getData().add(series);
        pane.setCenter(bar);
    }

    /**
     * Cria o menu com a informacao do vertice
     *
     * @param user vertice
     */
    public void addInformationVertex(Vertex<User> user) {

        ListView<String> list = viewObjectCreator.createListViewString(350, 100, user.element().toString());
        list.getItems().add(" ");
        list.getItems().add("List Of Interests");

        for (Interest interest : this.socialNetwork.interestsOfUser(user.element().getID())) {
            list.getItems().add(interest.toString());
        }

        pane.setCenter(list);
    }

    /**
     * Cria o menu com a informacao da aresta
     *
     * @param edge aresta
     */
    public void addInformationEdge(Edge<Relationship, User> edge) {
        if (edge.element() instanceof RelationshipIndirect) {
            ListView<String> list = viewObjectCreator.createListViewString(350, 100,
                    ((RelationshipIndirect) edge.element()).getListOfInterestsString());
            pane.setCenter(list);
        }
    }

    /**
     * Cria o GraphView
     */
    public void createGraphView() {
        graphView = new SmartGraphPanel(this.socialNetwork.getGraph(), new SmartCircularSortedPlacementStrategy());

        this.updateGraphColors();

        SmartGraphDemoContainer smartGraphView = new SmartGraphDemoContainer(graphView);
        smartGraphView.setManaged(true);
        smartGraphView.setMinWidth(600);

    }

    /**
     * Atualiza as cores dos vertices
     */
    public void updateColorsVertexGraph() {
        if (this.socialNetwork.getGraph().numVertices() == 0) {
            return;
        }

        for (Vertex<User> userVertex : this.socialNetwork.getGraph().vertices()) {
            if (userVertex.element().getType().equals(Type.INCLUIDO)) {
                this.graphView.getStylableVertex(userVertex).setStyleClass("myVertexIncluded");
            } else {
                this.graphView.getStylableVertex(userVertex).setStyleClass("myVertexAdded");
            }
        }
    }

    /**
     * Atualiza as cores das arestas
     */
    public void updateColorsEdgesGraph() {
        if (this.socialNetwork.getGraph().numEdges() == 0) {
            return;
        }

        for (Edge<Relationship, User> relationshipEdge : this.socialNetwork.getGraph().edges()) {

            if (relationshipEdge.element() instanceof RelationshipIndirect) {
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeIndirect");
            } else if (relationshipEdge.element() instanceof RelationshipSimple) {
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectSimple");
            } else {
                this.graphView.getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectShared");
            }
        }
    }

    @Override
    public void update(Object obj) {

    }
}
