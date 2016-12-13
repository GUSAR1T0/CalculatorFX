package sources;

import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Initializer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont("file:src/assets/PFSquareSansPro-Regular.ttf", 1);
        new Calculator(new Rectangle(700, 700));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
