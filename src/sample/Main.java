package sample;

import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Calculator(new Rectangle(700, 700));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
