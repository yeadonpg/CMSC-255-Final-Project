package Final_Project;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

public class MainMenu {
    public static boolean DONE = false;

    // userName and userDifficulty are currently set to default values: null, and -1 respectively
    // userName must be set to a string, entered by the user
    // userDifficulty must be set by the user to a valid number between 0 and 2, inclusively
    public static String userName;
    public static int userDifficulty = -1;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * - For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {

        GridPane gridPane = new GridPane();
        Button done = new Button("Done");

        Label gameTitle = new Label("Typing Contest:\n\n" +
                "By: Patrick Yeadon, Yusuf Niamati, Austin Escobedo, & Lokendra Tamang\n\n" +
                "Type a randomly generated sentence as fast as you can\n" +
                "Compete to get to the top of the leaderboard!\n\n" +
                "Choose a Difficulty:\n");

        HBox radioButtons = new HBox(8);

        RadioButton easy = new RadioButton("Easy");
        easy.setTextFill(Color.GREEN);
        radioButtons.getChildren().add(easy);

        RadioButton medium = new RadioButton("Medium");
        medium.setTextFill(Color.ORANGE);
        radioButtons.getChildren().add(medium);

        RadioButton hard = new RadioButton("Hard");
        hard.setTextFill(Color.RED);
        radioButtons.getChildren().add(hard);

        easy.setOnAction(e -> {
            if (easy.isSelected()) {
                userDifficulty = 0;
                medium.setSelected(false);
                hard.setSelected(false);
            }
        });

        medium.setOnAction(e -> {
            if (medium.isSelected()) {
                userDifficulty = 1;
                easy.setSelected(false);
                hard.setSelected(false);
            }
        });

        hard.setOnAction(e -> {
            if (hard.isSelected()) {
                userDifficulty = 2;
                easy.setSelected(false);
                medium.setSelected(false);
            }
        });

        TextField userTextBox = new TextField("Enter Your Username");
        userTextBox.setFocusTraversable(false);

        userTextBox.setOnMouseClicked(e -> {
            userTextBox.setEditable(true);
            userTextBox.setText("");
        });

        userTextBox.setOnAction(e -> {
            userTextBox.setEditable(false);
        });

        done.setOnAction(e -> {
            userName = userTextBox.getText().trim();
            sceneDone();
        });

        Node[][] nodes = new Node[][] {
                {gameTitle},
                {new Label()},
                {userTextBox},
                {new Label()},
                {radioButtons, done}
        };

        // Adding created nodes to the GridPane, at their specified
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                gridPane.add(nodes[i][j], j, i);
            }
        }
        finish(Main.STAGE, gridPane);
    }

    /** {@code finish} completes the JavaFX scene, sets the scene to the stage **/
    private static void finish(Stage stage, GridPane root) {
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
    }


    /** {@code sceneDone} allows the scene handler to move onward to the next scene **/
    private static void sceneDone() {
        DONE = true;
    }
}
