<<<<<<< Updated upstream
=======
import com.pa.proj2020.adts.graph.SocialNetwork;
>>>>>>> Stashed changes
import com.pa.proj2020.adts.graph.SocialNetworkView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
<<<<<<< Updated upstream

=======
    private SocialNetwork socialNetwork;
>>>>>>> Stashed changes
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
<<<<<<< Updated upstream
        SocialNetworkView socialNetworkView = new SocialNetworkView();
        socialNetworkView.startCentralConsole();

        Scene scene = new Scene(socialNetworkView, 630, 350);

        primaryStage.setTitle("Projeto PA");
        primaryStage.setScene(scene);

        primaryStage.show();
=======

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
>>>>>>> Stashed changes
    }
}
