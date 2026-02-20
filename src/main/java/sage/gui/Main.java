package sage.gui;

import sage.Sage;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Sage using FXML.
 */
public class Main extends Application {

    private Sage sage = new Sage();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSage(sage);  // inject the Sage instance
            stage.setTitle("Sage");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
