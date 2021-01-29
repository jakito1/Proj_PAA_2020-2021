package com.pa.proj2020.adts.graph;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import smartgraph.view.graphview.SmartStylableNode;

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
    private MenuItem menuExportSerialize;
    private MenuItem menuImportSerialize;
    private MenuItem menuExportJSON;
    private final MenuBar menuBar;


    /**
     * Permite criar um novo SocialNetworkView
     *
     * @param obj objeto SocialNetwork
     */
    public SocialNetworkView(Object obj) {
        stage = new Stage(StageStyle.DECORATED);
        menuBar = new MenuBar();
        update(obj);
    }

    /**
     * Cria um Menu no SocialNetworkView com varias opcoes
     *
     * @return o menu criado no SocialNetworkView
     */
    private void createMenu() {
        Menu menuOptions = new Menu("Options");
        Menu menuOptions1 = new Menu("Save/Update");
        Menu menuOptions2 = new Menu("Stats");

        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuUndo = new MenuItem("Undo");
        MenuItem menuAddUser = new MenuItem("Add user");
        MenuItem menuAddIndirectRelationship = new MenuItem("Add indirect relationships");
        MenuItem menuDijkstra = new MenuItem("Dijkstra");

        menuExportSerialize = new MenuItem("Export to Java Serialize");
        menuImportSerialize = new MenuItem("Import from Java Serialize");
        menuExportJSON = new MenuItem("Export to JSON");

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

        menuUndo.setOnAction(e -> {
            System.out.println("Size: antes " + this.socialNetwork.getGraph().numVertices());
            this.caretaker.restoreState();
            System.out.println("Size: depois " + this.socialNetwork.getGraph().numVertices());

            pane.setLeft(this.graphView);
            this.graphView.update();
        });

        menuDijkstra.setOnAction(e -> this.addDijkstra());

        menuStats1.setOnAction(e -> this.addStatUsersAdded());

        menuStats2.setOnAction(e -> this.addStatUsersIncludedByUserAdded());

        menuStats3.setOnAction(e -> this.addStatUserWithMoreDirectRelationships());

        menuStats4.setOnAction(e -> this.addStatInterestMostShared());

        menuStats5.setOnAction(e -> this.addStatTopFiveUsersWithMostRelationships());

        menuStats6.setOnAction(e -> this.addStatTopFiveInterestsStats());

        menuBar.getMenus().addAll(menuOptions, menuOptions1, menuOptions2);
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
    private void createNodeAddUser() {
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
    private void createNodeAddIndirectRelationships() {
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD INDIRECT RELATIONSHIPS");

        ComboBox<String> texts = viewObjectCreator.createComboBoxString("Select a user to add indirect relationships",
                230, 20);

        this.socialNetwork.getGraph().vertices().forEach(user -> texts.getItems().add(user.element().toString()));

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
    private void addDijkstra() {
        Button dijkstraButton = new Button("Dijkstra");
        ArrayList<User> path = new ArrayList<>();

        ComboBox<String> textsUser1 = viewObjectCreator.createComboBoxString("Select the origin user", 230, 20);
        ComboBox<String> textsUser2 = viewObjectCreator.createComboBoxString("Select the destiny user", 230, 20);

        this.socialNetwork.getGraph().vertices().forEach(user -> {
            textsUser1.getItems().add(user.element().toString());
            textsUser2.getItems().add(user.element().toString());
        });

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

            if (getChildren().get(getChildren().size() - 1) instanceof Text) {
                getChildren().remove(getChildren().size() - 1);
            }
            if (user1 != null && user2 != null) {
                try {
                    this.socialNetwork.getGraph().minCostPath(user1, user2, path);
                    getStylableVertex(user1).setStyleClass("myVertexDijkstra");
                    for (User user : path) {
                        graphView.getStylableVertex(user).setStyleClass("myVertexDijkstra");
                    }

                    for (User user : path) {
                        for (Edge<Relationship, User> edge : socialNetwork.getGraph().outboundEdges(user1)) {
                            if (isSameUser(edge, user)) {
                                getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
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
                        if (userVertex == null || userVertex2 == null) break;

                        for (Edge<Relationship, User> edge : this.socialNetwork.getGraph().outboundEdges(userVertex)) {
                            if (isSameUser(edge, userVertex2.element())) {
                                getStylableEdge(edge).setStyleClass("myEdgeDijkstra");
                            }
                        }
                    }

                } catch (NullPointerException ex) {
                    Text text = new Text("There's no path, please try again");
                    getChildren().add(text);
                }
            }

            graphView.update();
        });

        VBox center2 = viewObjectCreator.createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);
        center2.getChildren().addAll(textsUser1, textsUser2, dijkstraButton);

        pane.setCenter(center2);
    }

    private boolean isSameUser(Edge<Relationship, User> edge, User element) {
        return edge.vertices()[1].element().equals(element);
    }

    private ObservableList<Node> getChildren() {
        return ((VBox) pane.getCenter()).getChildren();
    }

    /**
     * Cria uma janela com a informacao associada ao SocialNetworkView
     */
    private void createCenterSocialNetworkView() {
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
        pane.setTop(menuBar);
        pane.setLeft(smartGraphView);

        Scene scene = new Scene(pane, 1024, 600);

        stage.setScene(scene);
        stage.show();
        graphView.init();
    }

    /**
     * Atualiza as cores do grafo
     */
    private void updateGraphColors() {
        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();
    }

    /**
     * Cria o menu das estatisticas de utilizadores adicionados
     */
    private void addStatUsersAdded() {
        addStat(socialNetwork.addedUsersStats());
    }

    /**
     * Cria o menu das estatisticas de utilizadores incluidos
     */
    private void addStatUsersIncludedByUserAdded() {
        addStat(socialNetwork.includedUsersStats());
    }

    /**
     * Cria o menu das estatisticas de utilizadores com mais relacionamentos diretos
     */
    private void addStatUserWithMoreDirectRelationships() {
        addStat(socialNetwork.userWithMoreDirectRelationshipsStats());
    }

    /**
     * Cria o menu das estatisticas de interesse mais partilhado
     */
    private void addStatInterestMostShared() {
        addStat(socialNetwork.interestMostSharedStats());
    }

    private void addStat(String stat) {
        ListView<String> list = viewObjectCreator.createListViewString(350, 100, stat);
        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de top 5 de utilizadores com mais relacionamentos
     */
    private void addStatTopFiveUsersWithMostRelationships() {
        BarChart<String, Integer> bar = createBarChart("Users", "Relationships",
                "Top Five Users With Most Relationships");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<User, Integer> map = new HashMap<>(this.socialNetwork.topFiveUsersWithMostRelationshipsStats());

        map.keySet().forEach(user -> {
            series.getData().add(new XYChart.Data<>(user.toString(), map.get(user)));
        });

        bar.getData().add(series);
        pane.setCenter(bar);
    }

    private BarChart<String, Integer> createBarChart(String labelX, String labelY, String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(labelX);
        yAxis.setLabel(labelY);

        BarChart<String, Integer> bar = new BarChart(xAxis, yAxis);
        bar.setTitle(title);

        return bar;
    }

    /**
     * Cria o menu das estatisticas de top 5 interesses
     */
    private void addStatTopFiveInterestsStats() {
        BarChart<String, Integer> bar = createBarChart("Interests", "Users", "Top Five Interests");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<Interest, Integer> map = new HashMap<>(this.socialNetwork.topFiveInterestsStats());

        map.keySet().forEach(interest -> {
            if (interest != null) {
                series.getData().add(new XYChart.Data<>(interest.getName(), map.get(interest)));
            }
        });

        bar.getData().add(series);
        pane.setCenter(bar);
    }

    /**
     * Cria o menu com a informacao do vertice
     *
     * @param user vertice
     */
    private void addInformationVertex(Vertex<User> user) {

        ListView<String> list = viewObjectCreator.createListViewString(350, 100, user.element().toString());
        list.getItems().add(" List Of Interests");

        this.socialNetwork.interestsOfUser(user.element().getID())
                .forEach(interest -> list.getItems().add(interest.toString()));

        pane.setCenter(list);
    }

    /**
     * Cria o menu com a informacao da aresta
     *
     * @param edge aresta
     */
    private void addInformationEdge(Edge<Relationship, User> edge) {
        if (edge.element() instanceof RelationshipIndirect) {
            ListView<String> list = viewObjectCreator.createListViewString(350, 100,
                    ((RelationshipIndirect) edge.element()).getListOfInterestsString());
            pane.setCenter(list);
        }
    }

    /**
     * Cria o GraphView
     */
    private void createGraphView() {
        graphView = new SmartGraphPanel(this.socialNetwork.getGraph(), new SmartCircularSortedPlacementStrategy());

        this.updateGraphColors();

        SmartGraphDemoContainer smartGraphView = new SmartGraphDemoContainer(graphView);
        smartGraphView.setManaged(true);
        smartGraphView.setMinWidth(600);

    }

    /**
     * Atualiza as cores dos vertices
     */
    private void updateColorsVertexGraph() {
        if (this.socialNetwork.getGraph().numVertices() == 0) return;

        this.socialNetwork.getGraph().vertices().forEach(userVertex -> {
            if (userVertex.element().getType().equals(Type.INCLUIDO)) {
                getStylableVertex(userVertex).setStyleClass("myVertexIncluded");
            } else {
                getStylableVertex(userVertex).setStyleClass("myVertexAdded");
            }
        });
    }

    private SmartStylableNode getStylableVertex(Vertex<User> userVertex) {
        return graphView.getStylableVertex(userVertex);
    }

    /**
     * Atualiza as cores das arestas
     */
    private void updateColorsEdgesGraph() {
        if (this.socialNetwork.getGraph().numEdges() == 0) return;

        this.socialNetwork.getGraph().edges().forEach(relationshipEdge -> {
            if (relationshipEdge.element() instanceof RelationshipIndirect) {
                getStylableEdge(relationshipEdge).setStyleClass("myEdgeIndirect");
            } else if (relationshipEdge.element() instanceof RelationshipSimple) {
                getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectSimple");
            } else {
                getStylableEdge(relationshipEdge).setStyleClass("myEdgeDirectShared");
            }
        });
    }

    private SmartStylableNode getStylableEdge(Edge<Relationship, User> relationshipEdge) {
        return graphView.getStylableEdge(relationshipEdge);
    }

    /**
     * Inicia o SocialNetworkView
     */
    public void startCentralConsole() {
        this.createCenter();
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof SocialNetwork) {
            SocialNetwork socialNetwork = (SocialNetwork) obj;
            createMenu();
            this.socialNetwork = socialNetwork;
            this.caretaker = new Caretaker(socialNetwork);
            this.createGraphView();
        }
    }

    public void setTriggers(SocialNetworkController socialNetworkController) {

        menuExportSerialize.setOnAction(e -> socialNetworkController.exportSerialization());

        menuImportSerialize.setOnAction(e -> socialNetworkController.importSerialization());

        menuExportJSON.setOnAction(e -> socialNetworkController.exportJSON());

    }
}
