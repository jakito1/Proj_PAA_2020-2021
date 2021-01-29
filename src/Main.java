import com.pa.proj2020.adts.graph.SocialNetwork;
import com.pa.proj2020.adts.graph.SocialNetworkController;
import com.pa.proj2020.adts.graph.SocialNetworkView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.initializeData();

        SocialNetworkView socialNetworkView = new SocialNetworkView(socialNetwork);
        new SocialNetworkController(socialNetworkView, socialNetwork);
        socialNetworkView.startCentralConsole();
    }
}

