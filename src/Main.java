import javafx.application.Application;
import javafx.stage.Stage;
import sample.Images;
import sample.Musics;
import screen.Game;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bomber Man");
        stage.setScene(new Game().getScene(stage));
        stage.show();
    }

    public static void main(String[] args) {
        runGame();
        launch(args);
    }

    public static void runGame() {
        Images.initImages();
        Musics.initMusics();
    }
}
