import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML private TextField entitle;
    @FXML private AnchorPane scenePane;
    @FXML private Label myLabel;
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private GridPane gridPane;
    @FXML private Button button;
    @FXML private Button button10;
    @FXML private Button button11;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Button button5;
    @FXML private Button button6;
    @FXML private Button button7;
    @FXML private Button button8;
    @FXML private Button button9;
    @FXML private Label under;
    private Button[][] grid = new Button[3][3];
    char currentP1 = 'X';
    private void intGrid() {
        grid[0][0] = button3;
        grid[0][1] = button4;
        grid[0][2] = button5;
        grid[1][0] = button6;
        grid[1][1] = button7;
        grid[1][2] = button8;
        grid[2][0] = button9;
        grid[2][1] = button10;
        grid[2][2] = button11;
    }
    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            mainWindow.setScene(scene);
            mainWindow.show();
        } catch (IOException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error Loading Scene");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("error  loading the scene.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
    @FXML
    public void switchScene1(ActionEvent event) {
        loadScene("Scenebuild.fxml", event); 
    }
    @FXML
    public void switchScene2(ActionEvent event) {
        loadScene("SceneBuild2.fxml", event); 
    }
    @FXML
    public void switchScene3(ActionEvent event) {
        loadScene("Scenebuild3.fxml", event);
    }
    
    @FXML 
    public void logOutButton(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Image greenIcon = new Image(App.class.getResource("freepik-logo-.png").toExternalForm());
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(greenIcon);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit");
        alert.setContentText("Do you want to save before quitting?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("application.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");
        for (ButtonType buttonType : alert.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(buttonType);
            if (button != null) {
                button.getStyleClass().add("alert-button");
            }
        }
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage mainWindow = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logged out");
            mainWindow.close();
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            String currentText = clickedButton.getText();
    
            // Check the button is empty before process
            if (currentText.isEmpty()) {
                // Set the text of the button to the current player's symbol (X or O)
                clickedButton.setText(getCurrPlayer() == 'X' ? "X" : "O");
                
                //the style of the button
                clickedButton.setStyle("-fx-text-fill: green; -fx-font-size: 40px;");
                
                // Togglecurrent player for the next turn
                togglPlayer();
                playerunder();
            }
        }
        if (checkWin()) {
            displayResult("Player " + getCurrPlayer() + " wins");
            resetGame();
            return;
        }
        if (checkDraw()) {
            displayResult("It's a draw");
            resetGame();
            return;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intGrid();
    }

    private char getCurrPlayer() {
        return currentP1;
    }
    @FXML
    private void playerunder(){
        if(currentP1 == 'X'){
             under.setText("<--");
        }else{
            under.setText("-->");
        }
    }
    private void togglPlayer() {
        currentP1 = (currentP1 == 'X') ? 'O' : 'X';
    }
    private boolean checkWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (checkRow(row)) {
                return true;
            }
        }
    
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (checkColumn(col)) {
                return true;
            }
        }
    
        // Check diagonals
        if (checkDiagonal() || checkAntiDiagonal()) {
            return true;
        }
    
        return false;
    }
    private boolean checkRow(int row) {
        return grid[row][0].getText().equals(grid[row][1].getText()) &&
               grid[row][0].getText().equals(grid[row][2].getText()) &&
               !grid[row][0].getText().isEmpty();
    }
    private boolean checkColumn(int col) {
        return grid[0][col].getText().equals(grid[1][col].getText()) &&
               grid[0][col].getText().equals(grid[2][col].getText()) &&
               !grid[0][col].getText().isEmpty();
    }
    private boolean checkDiagonal() {
        return grid[0][0].getText().equals(grid[1][1].getText()) &&
               grid[0][0].getText().equals(grid[2][2].getText()) &&
               !grid[0][0].getText().isEmpty();
    }
    private boolean checkAntiDiagonal() {
        return grid[0][2].getText().equals(grid[1][1].getText()) &&
               grid[0][2].getText().equals(grid[2][0].getText()) &&
               !grid[0][2].getText().isEmpty();
    }
    private boolean checkDraw() {
        for (Button[] row : grid) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        
        // If all buttons are filled so there is no winner, it's a draw
        return !checkWin();
    }
    private void displayResult(String result) {
        // Show an alert dialog with the result
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Image greenIcon = new Image(App.class.getResource("freepik-logo-.png").toExternalForm());
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(greenIcon);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(result);
        
        // Apply custom styling using CSS to alert 
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("application.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");
        
        alert.showAndWait();
    }
    
    private void resetGame() {
        // Clear the text of all buttons
        for (Button[] row : grid) {
            for (Button button : row) {
                button.setText("");
            }
        }
        currentP1 = 'X';
    }
}