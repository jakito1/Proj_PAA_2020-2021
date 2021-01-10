package com.pa.proj2020.adts.graph;





import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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


public class SocialNetworkView extends BorderPane{


    private SocialNetwork socialNetwork;
    private SmartGraphPanel<User, Relationship> graphView;
    SmartPlacementStrategy strategy;

    /**
     * Permite criar um novo WebGeneratorView
     */
    public SocialNetworkView() {
        strategy = new SmartCircularSortedPlacementStrategy();
        this.startCentralConsole();

    }

    /**
     * Inicia o WebGeneratorView
     */
    public void startCentralConsole() {
        //this.setCenter(createCenter());
        this.createCenterWebGenerator();
    }

    /**
     * Cria um Menu no WebGeneratorView com varias opcoes
     *
     * @return o menu criado no WebGeneratorView
     */
    private Node createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuOptions = new Menu("Options");
        Menu menuOptions1 = new Menu("Save/Update");
        Menu menuOptions2 = new Menu("Stats");

        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuUndo = new MenuItem("Undo");
        MenuItem menuSave = new MenuItem("SaveFile");
        MenuItem menuUpdate = new MenuItem("UpdateFile");
        MenuItem menuStats1 = new MenuItem("Number of internal pages");
        MenuItem menuStats2 = new MenuItem("Number of external pages");
        MenuItem menuStats3 = new MenuItem("Number of links");
        MenuItem menuStats4 = new MenuItem("Bar chart indicating\n the 5 pages with the most links");
        MenuItem menuStats5 = new MenuItem("Bar graph indicating\n the 5 most referenced pages");

        menuOptions.getItems().addAll(menuExit, menuUndo);
        menuOptions1.getItems().addAll(menuSave, menuUpdate);
        menuOptions2.getItems().addAll(menuStats1, menuStats2, menuStats3, menuStats4, menuStats5);
        menuExit.setOnAction(e -> Platform.exit());

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

//    /**
//     * Cria a janela inicial do WebGeneratorView
//     *
//     * @return a janela criada
//     */
//    private Node createCenter() {
//        Image img = new Image("images/img1.jpg");
//
//        BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
//                        false, false, true, false));
//
//        Background imageBackGround = new Background(bgImg);
//        this.setBackground(imageBackGround);
//
//        VBox center = new VBox();
//        center.setPadding(new Insets(-10, 12, 15, 12));
//        center.setSpacing(15);
//        center.setAlignment(Pos.CENTER);
//
//        Font sanSerif = Font.font("SanSerif", 75);
//        Font sanSerif1 = Font.font("SanSerif", 25);
//
//        Text welcomeText = new Text("Web Generator");
//        Text welcomeText1 = new Text("Insira o link");
//
//        welcomeText.setFill(Color.WHITE);
//        welcomeText.setFont(sanSerif);
//        welcomeText1.setFill(Color.WHITE);
//        welcomeText1.setFont(sanSerif1);
//
//        center.getChildren().addAll(welcomeText, welcomeText1);
//
//        TextField urlField = new TextField();
//        urlField.setText("http://www.brunomnsilva.com/sandbox/index.html");
//        urlField.setMaxWidth(500);
//        urlField.setMinWidth(500);
//        center.getChildren().add(urlField);
//        TextField nameField = new TextField();
//
//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(10));
//        hBox.setSpacing(10);
//
//        nameField.setText("Model Name");
//        nameField.setMaxWidth(430);
//        nameField.setMinWidth(430);
//        center.getChildren().add(nameField);
//
//        Button okButton = new Button("INICIAR");
//
//        okButton.setOnAction(e -> {
//            try {
//                this.webGenerator = new WebGenerator(urlField.getText());
//                this.webGenerator.setName(nameField.getText());
//            } catch (IOException ex) {
//                Logger.getLogger(WebGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            this.createCenterWebGenerator();
//
//        });
//
//        hBox.getChildren().addAll(nameField, okButton);
//        hBox.setAlignment(Pos.CENTER);
//        center.getChildren().add(hBox);
//
//        return center;
//    }

    /**
     * Cria uma janela com a informacao associadao ao WebGeneratorView
     */
    public void createCenterWebGenerator() {
        BorderPane pane = new BorderPane();

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);

        VBox center = new VBox();
        center.setPadding(new Insets(-10, 12, 15, 12));
        center.setSpacing(15);
        center.setAlignment(Pos.CENTER);

        Button runButton = new Button("RUN");

        ListView<String> list = new ListView<>();
        list.setPrefSize(400, 400);


        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.initializeData();

        socialNetwork.constructModelIteractive(1);

        socialNetwork.constructModelIteractive(9);
        socialNetwork.constructModelIteractive(11);
        socialNetwork.constructModelIteractive(14);


        Graph<User, Relationship> g = socialNetwork.getGraph();


        graphView = new SmartGraphPanel(g, strategy);

        SmartGraphDemoContainer smartGraphView = new SmartGraphDemoContainer(graphView);
        smartGraphView.setPrefWidth(600);
        smartGraphView.setManaged(true);

//        list.getItems().addAll(webGenerator.splitText());

        ComboBox texts = new ComboBox();

//        texts.getItems().addAll(this.webGenerator.getDigraph().outboundEdges(this.webGenerator.getCurrentWebsite()));

//        texts.setPromptText("Selecione o texto da hiperligação");
//        texts.setPrefSize(230, 20);
//        if (this.webGenerator.getDigraph().numVertices() > 0) {
//            graphView.getStylableVertex(this.webGenerator.getCurrentWebsite()).setStyle("-fx-fill: gold; -fx-stroke: brown;");
//        }

//        runButton.setOnAction(e -> {
//            this.caretaker.requestSave(this.webGenerator);
//
//            ArrayList<Edge> edgesList = new ArrayList<>(this.webGenerator.getDigraph().edges());
//
//            String newUrl = (String) edgesList.get(edgesList.indexOf(texts.getSelectionModel().getSelectedItem())).element();
//
//            if (newUrl != null && !newUrl.isEmpty()) {
//
//                try {
//                    this.webGenerator.addNewSite(newUrl);
//                } catch (IOException ex) {
//                    Logger.getLogger(WebGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//
//            list.getItems().clear();
//            list.getItems().addAll(webGenerator.splitText());
//            texts.getItems().clear();
//
//            texts.getItems().addAll(this.webGenerator.getDigraph().outboundEdges(this.webGenerator.getCurrentWebsite()));
//            graphView.update();
//
//            if (this.webGenerator.getDigraph().numVertices() > 0) {
//                graphView.getStylableVertex(this.webGenerator.getCurrentWebsite()).setStyle("-fx-fill: blue; -fx-stroke: brown;");
//            }
//
//        });

        hBox2.getChildren().addAll(texts);
        center.getChildren().addAll(hBox2, list);

        pane.setTop(this.createMenu());

        hBox.getChildren().addAll(smartGraphView, center);
        pane.setCenter(hBox);
        Scene scene = new Scene(pane, 850, 600);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("ProjetoRecursoPA");
        stage.setScene(scene);
        stage.show();

        graphView.init();

    }











}
