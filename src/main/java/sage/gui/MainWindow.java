package sage.gui;

import sage.Sage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.stage.Stage;
import sage.SageException;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sage sage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image sageImage = new Image(this.getClass().getResourceAsStream("/images/sage.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Sage instance */
    public void setSage(Sage s) {
        sage = s;

        dialogContainer.getChildren().add(
                DialogBox.getSageDialog(s.getHello(), sageImage));

        // Prints error response only if loadTasks() fails
        try {
            sage.loadTasks();
        } catch (SageException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getSageDialog(e.getMessage(), sageImage));
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Sage's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = sage.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSageDialog(response, sageImage)
        );
        userInput.clear();

        if (Objects.equals(response, "Goodbye. Have a beautiful day.")) {
            exitProgram();
        }
    }

    @FXML
    private void exitProgram() {
        // Close the application after a short delay to allow the goodbye message to be visible
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(
                javafx.util.Duration.seconds(1)
        );
        delay.setOnFinished(event -> {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        });
        delay.play();
    }
}