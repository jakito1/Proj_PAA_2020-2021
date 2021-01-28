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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import smartgraph.view.containers.SmartGraphDemoContainer;
import smartgraph.view.graphview.SmartCircularSortedPlacementStrategy;
import smartgraph.view.graphview.SmartGraphPanel;
import smartgraph.view.graphview.SmartPlacementStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SocialNetworkView {

    private final Stage stage;
    SmartPlacementStrategy strategy;
    private SocialNetwork socialNetwork;
    private SmartGraphPanel<User, Relationship> graphView;
    private Graph<User, Relationship> graph;
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
     *
     * @param socialNetwork objeto SocialNetwork
     */
    public SocialNetworkView(SocialNetwork socialNetwork) {
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

        MenuItem menuExportSerialize = new MenuItem("Export to Java Serialize");
        MenuItem menuImportSerialize = new MenuItem("Import from Java Serialize");
        MenuItem menuExportJSON = new MenuItem("Export to JSON");

        MenuItem menuStats1 = new MenuItem("Statistic Users Added");
        MenuItem menuStats2 = new MenuItem("Statistic Users Included by User Added");
        MenuItem menuStats3 = new MenuItem("Statistic User With More Direct Relationships");
        MenuItem menuStats4 = new MenuItem("Statistic Interest Most Shared");
        MenuItem menuStats5 = new MenuItem("Bar chart with the number of relationships of\n the 5 users with the most relationships");
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

    private ImageView createImageView(Image img){
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(850.0);
        imageView.setImage(img);

        return imageView;
    }

    private Text createText(String fontName, int size, String innerText, Color color){

        if(fontName == null || size <= 0 || innerText == null || color == null){
            return new Text("");
        }

        Font font = createFont(fontName, size);
        Text text = new Text(innerText);

        text.setFill(color);
        text.setFont(font);

        return(text);
    }

    private VBox createVBox(Insets insets, int spacing, Pos alignment){
        if(insets == null || spacing <= 0){
            return null;
        }

        VBox vbox = new VBox();
        vbox.setPadding(insets);
        vbox.setSpacing(spacing);

        if(alignment != null){
            vbox.setAlignment(alignment);
        }

        return vbox;
    }

    private HBox createHBox(Insets insets, int spacing, Pos alignment){
        if(insets == null || spacing <= 0){
            return null;
        }

        HBox hbox = new HBox();
        hbox.setPadding(insets);
        hbox.setSpacing(spacing);

        if(alignment != null){
            hbox.setAlignment(alignment);
        }

        return hbox;
    }

    private TextField createTextField(String innerText, int maxWidth, int minWidth){
        TextField textField = new TextField();

        if(innerText == null || maxWidth <=0 || minWidth <0){
            return textField;
        }

        textField.setText(innerText);
        textField.setMaxWidth(maxWidth);
        textField.setMinWidth(minWidth);

        return textField;
    }

    private Font createFont(String fontName, int size){
        if(fontName == null || size <= 0){
            return null;
        }

        Font font = Font.font(fontName, size);
        return font;
    }

    private Label createLabel(String innerText, Color color, String fontName, int size){
        if(innerText == null || color == null || fontName == null){
            return null;
        }

        Label label = new Label();
        Font font = createFont(fontName, size);

        label.setText(innerText);
        label.setTextFill(color);
        label.setFont(font);

        return label;
    }


    /**
     * Cria a janela inicial do SocialNetworkView
     */
    private void createCenter() {
        Image img = new Image("images/SocialNetworkImage.jpg");
        ImageView imageView = createImageView(img);

        VBox center = createVBox(new Insets(-10, 12, 15, 12), 15, Pos.CENTER);

        Text welcomeText = this.createText("SanSerif", 35,
                "Social Network", Color.BLUE);
        Text welcomeText1 = this.createText("SanSerif", 15,
                "Insert name of file with relationships", Color.BLUE);
        Text welcomeText2 = this.createText("SanSerif", 15,
                "Insert name of file with interests", Color.BLUE);
        Text welcomeText3 = this.createText("SanSerif", 15,
                "Insert name of file with user_names", Color.BLUE);
        Text welcomeText4 = this.createText("SanSerif", 15,
                "Insert name of file with interest_names", Color.BLUE);
        Text welcomeText5 = this.createText("SanSerif", 10,
                "Note: leaving in blank will set default values", Color.BLACK);

        HBox hBox1 = createHBox(new Insets(10), 10, null);
        HBox hBox2 = createHBox(new Insets(10), 10, null);

        center.getChildren().addAll(welcomeText, imageView);

        TextField relationshipsField = createTextField("relationships.csv", 250, 250);

        center.getChildren().add(relationshipsField);

        TextField interestsField = createTextField("interests.csv", 250, 250);

        Label label1 = createLabel("Relationships: ", Color.BLUE, "SanSerif", 15);
        label1.setLabelFor(relationshipsField);

        Label label2 = createLabel("Interests: ", Color.BLUE, "SanSerif", 15);
        label2.setLabelFor(relationshipsField);

        hBox1.getChildren().addAll(label1, relationshipsField, label2, interestsField);
        hBox1.setAlignment(Pos.CENTER);

        center.getChildren().addAll(hBox1);

        TextField userNamesField = createTextField("user_names.csv", 250, 250);
        TextField interestNamesField = createTextField("interest_names.csv", 250, 250);


        Label label3 = createLabel("User Names: ", Color.BLUE, "SanSerif", 15);
        label3.setLabelFor(userNamesField);

        Label label4 = createLabel("Interest Names: ", Color.BLUE, "SanSerif", 15);
        label4.setLabelFor(interestNamesField);

        hBox2.getChildren().addAll(label3, userNamesField, label4, interestNamesField);
        hBox2.setAlignment(Pos.CENTER);

        center.getChildren().addAll(hBox2);

        TextField nameField = createTextField("Model Name", 250, 250);

        HBox hBox = createHBox(new Insets(10), 10, Pos.CENTER);

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

        Label label5 = createLabel("Model Name: ", Color.BLUE, "SanSerif", 15);
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
     * Método que inicializa os nomes dos ficheiros
     *
     * @param userNamesFile     representa o ficheiro com os nomes dos utilizadores
     * @param relationshipsFile representa o ficheiro com relationships
     * @param interestNamesFile representa o ficheiro com o nome dos interesses
     * @param interestsFile     representa o ficheiro com os interesses
     */
    public void setFilenames(String userNamesFile, String relationshipsFile,
                             String interestNamesFile, String interestsFile) {
        this.socialNetwork.setFileNames(userNamesFile, relationshipsFile, interestNamesFile, interestsFile);
    }


    /**
     * Cria o menu de adicao de utilizador
     */
    public void createNodeAddUser() {
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD USER");

        ComboBox<String> texts = new ComboBox<>();
        texts.getItems().addAll(this.socialNetwork.getUsersNotInserted());

        texts.getItems().setAll(texts.getItems().sorted());

        texts.setPromptText("Select a user to add");
        texts.setPrefSize(230, 20);

        addUserButton.setOnAction(e -> {

            this.caretaker.saveState();

            this.socialNetwork.constructModelIterative(Integer.parseInt(texts.getValue().split(" ")[0]));
            texts.getItems().remove(texts.getValue());
            graphView.update();
        });

        updateButton.setOnAction(e -> this.updateGraphColors());

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


    /**
     * Cria o menu de adicao de relacionamentos indiretos
     */
    public void createNodeAddIndirectRelationships() {
        Button updateButton = new Button("UPDATE COLORS");
        Button addUserButton = new Button("ADD INDIRECT RELATIONSHIPS");

        ComboBox<String> texts = new ComboBox<>();
        for (Vertex<User> user : this.socialNetwork.getGraph().vertices()) {
            texts.getItems().add(user.element().toString());
        }

        texts.getItems().setAll(texts.getItems().sorted());

        texts.setPromptText("Select a user to add indirect relationships");
        texts.setPrefSize(230, 20);

        addUserButton.setOnAction(e -> {
            this.socialNetwork.addIndirectRelationships(Integer.parseInt(texts.getValue().split(" ")[0]));
            texts.getItems().remove(texts.getValue());
            graphView.update();
        });

        updateButton.setOnAction(e -> this.updateGraphColors());

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


    /**
     * Cria o menu de Dijkstra
     */
    public void addDijkstra() {

        Button dijkstraButton = new Button("Dijkstra");
        ArrayList<User> path = new ArrayList<>();

        ComboBox<String> textsUser1 = new ComboBox<>();
        ComboBox<String> textsUser2 = new ComboBox<>();

        for (Vertex<User> user : this.socialNetwork.getGraph().vertices()) {
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
        if (this.socialNetwork == null) {
            this.socialNetwork = new SocialNetwork();
            this.socialNetwork.initializeData();
        }

        graphView = new SmartGraphPanel(this.socialNetwork.getGraph(), strategy);

        graphView.setVertexDoubleClickAction(graphVertex ->
                this.addInformationVertex(graphVertex.getUnderlyingVertex()));

        graphView.setEdgeDoubleClickAction(graphEdge -> this.addInformationEdge(graphEdge.getUnderlyingEdge()));

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
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 500);
        list.getItems().add(this.socialNetwork.addedUsersStats());

        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de utilizadores incluidos
     */
    public void addStatUsersIncludedByUserAdded() {
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 500);
        list.getItems().add(this.socialNetwork.includedUsersStats());


        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de utilizadores com mais relacionamentos diretos
     */
    public void addStatUserWithMoreDirectRelationships() {
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 100);
        list.getItems().add(this.socialNetwork.userWithMoreDirectRelationshipsStats());

        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de interesse mais partilhado
     */
    public void addStatInterestMostShared() {
        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 100);
        list.getItems().add(this.socialNetwork.interestMostSharedStats());

        pane.setCenter(list);
    }

    /**
     * Cria o menu das estatisticas de top 5 de utilizadores com mais relacionamentos
     */
    public void addStatTopFiveUsersWithMostRelationships() {

        CategoryAxis xaxis = new CategoryAxis();
        NumberAxis yaxis = new NumberAxis();
        xaxis.setLabel("Users");
        yaxis.setLabel("Relationships");

        BarChart<String, Integer> bar = new BarChart(xaxis, yaxis);
        bar.setTitle("Top Five Users With Most Relationships");


        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<User, Integer> map = new HashMap<>(this.socialNetwork.topFiveUsersWithMostRelationshipsStats());

        for (User user : map.keySet()) {
            series.getData().add(new XYChart.Data<>(user.toString(), map.get(user)));
            System.out.println("User: " + user.toString() + " Relationships: " + map.get(user));
        }

        bar.getData().add(series);

        pane.setCenter(bar);
    }


    /**
     * Cria o menu das estatisticas de top 5 interesses
     */
    public void addStatTopFiveInterestsStats() {

        CategoryAxis xaxis = new CategoryAxis();
        NumberAxis yaxis = new NumberAxis();
        xaxis.setLabel("Interests");
        yaxis.setLabel("Users");

        BarChart<String, Integer> bar = new BarChart(xaxis, yaxis);
        bar.setTitle("Top Five Interests");


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

        ListView<String> list = new ListView<>();
        list.setMaxSize(350, 100);

        list.getItems().add(user.element().toString());
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
            ListView<String> list = new ListView<>();
            list.setMaxSize(350, 100);
            list.getItems().add(((RelationshipIndirect) edge.element()).getListOfInterestsString());
            pane.setCenter(list);
        }

    }

    /**
     * Cria o GraphView
     */
    public void createGraphView() {
        graphView = new SmartGraphPanel(graph, strategy);

        this.updateColorsVertexGraph();
        this.updateColorsEdgesGraph();

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

}
