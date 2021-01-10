import com.pa.proj2020.adts.graph.SocialNetworkView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SocialNetworkView socialNetworkView = new SocialNetworkView();
        socialNetworkView.startCentralConsole();

        Scene scene = new Scene(socialNetworkView, 630, 350);

        primaryStage.setTitle("Projeto PA");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
