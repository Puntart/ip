package bobmortimer.gui;

import java.io.IOException;

import bobmortimer.BobMortimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private BobMortimer bobMortimer = new BobMortimer("BobMortimer.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/MainWindow.fxml"));
            stage.setTitle("Bob Mortimer");
            Image image = new Image("/images/DaBob2.png");
            stage.getIcons().add(image);
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setBobMortimer(bobMortimer);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

