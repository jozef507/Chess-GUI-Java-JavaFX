package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Trieda main pre spustenie grafickej aplikácie.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("gui/gamemanager.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 600, 435));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
