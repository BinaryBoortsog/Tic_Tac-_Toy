import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // get fxml file 
            Parent root = FXMLLoader.load(getClass().getResource("Scenebuild.fxml"));
            Scene scene = new Scene(root);

            // get CSS file
            String csscost = getClass().getResource("application.css").toExternalForm();
            if (csscost != null) {
                scene.getStylesheets().add(csscost);
            } else {
                System.err.println("Error loading CSS file.");
            }

            // Load icon
            Image icon = new Image(getClass().getResourceAsStream("freepik-logo-.png"));
            if (!icon.isError()) {
                stage.getIcons().add(icon);
            } else {
                System.err.println("Error loading icon.");
            }

            stage.setTitle("Tic-Tac");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                logOutButton(stage);
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading application resources.");
        }
    }

    // logout confirmation i used confirmation alert . in future i will make save option.
    public static void logOutButton(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit");
        alert.setContentText("Do you want to save before quitting?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image greenIcon = new Image(App.class.getResourceAsStream("freepik-logo-.png"));
        if (!greenIcon.isError()) {
            alertStage.getIcons().add(greenIcon);
        } else {
            System.err.println("Error loading alert icon.");
        }

        // get css file and apply to code
        DialogPane dialogPane = alert.getDialogPane();
        String css = App.class.getResource("application.css").toExternalForm();
        if (css != null) {
            dialogPane.getStylesheets().add(css);
            dialogPane.getStyleClass().add("alert");
        } else {
            System.err.println("Error loading CSS file for alert dialog.");
        }

        // Show alert 
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            System.out.println("You successfully logged out");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
