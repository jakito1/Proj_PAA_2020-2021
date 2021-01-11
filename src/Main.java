import com.pa.proj2020.adts.graph.SocialNetwork;
import com.pa.proj2020.adts.graph.SocialNetworkView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private SocialNetwork socialNetwork;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.initializeData();
//
//        socialNetwork.constructModelIteractive(1);
//        socialNetwork.constructModelIteractive(9);
//        socialNetwork.constructModelIteractive(11);
//        socialNetwork.constructModelIteractive(14);
//
//        socialNetwork.addIndirectRelationships(1);

        SocialNetworkView socialNetworkView = new SocialNetworkView(socialNetwork);
        socialNetworkView.startCentralConsole();

//        Scene scene = new Scene(socialNetworkView, 1024, 850);
//
//        primaryStage.setTitle("Projeto PA");
//        primaryStage.setScene(scene);
//
//        primaryStage.show();
    }
}

