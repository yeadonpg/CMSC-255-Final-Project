package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndMenu {
    public static boolean DONE = false;
    public static boolean userQuit;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * - For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {
        GridPane root = new GridPane();

        Text highScoreTitle = new Text("Typing Contest");
        highScoreTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 40));
        highScoreTitle.setFill(Color.BLACK);
        root.add(highScoreTitle, 0, 1, 3, 1);

        Text highScoreSubTitle = new Text("High Scores");
        highScoreSubTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 30));
        highScoreSubTitle.setFill(Color.GREEN);
        root.add(highScoreSubTitle, 0, 2, 3, 1);

        Text difficultyEasy = new Text("Easy");
        difficultyEasy.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 30));
        difficultyEasy.setFill(Color.GREEN);
        root.add(difficultyEasy, 0, 3);

        Text difficultyIntermediate = new Text("Intermediate");
        difficultyIntermediate.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 30));
        difficultyIntermediate.setFill(Color.ORANGE);
        root.add(difficultyIntermediate, 1, 3);

        Text difficultyHard = new Text("Hard");
        difficultyHard.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 30));
        difficultyHard.setFill(Color.RED);
        root.add(difficultyHard, 2, 3);


        Button btnRestart = new Button("Try Again?");
        Button btnQuit = new Button("End Program");
        btnRestart.setOnAction(e -> sceneDone(false));
        btnQuit.setOnAction(e -> sceneDone(true));
        root.add(btnRestart, 0, 0);
        root.add(btnQuit, 1, 0);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                String[] line = Main.readLineFromFile(j, i);
                double score = Double.parseDouble(line[2]);
                Text scores = new Text(String.format("%s: %.1f ", line[0], score));
                scores.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 25));
                scores.setFill(Color.BLACK);
                root.add(scores, j, i + 4);
            }
        }
        finish(Main.STAGE, root);
    }

    /** {@code finish} completes the JavaFX scene, sets the scene to the stage **/
    private static void finish(Stage stage, GridPane root) {
        // Creating the scene
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
    }

    /** {@code sceneDone} allows the program to exit gracefully if the user decides to quit the game **/
    private static void sceneDone(boolean quitApp) {
        DONE = true;
        // Allowing the scenes to repeat if the user didn't ask to quit
        if (!quitApp) {
            // resetting, and going back to the Main Menu
            Main.resetScenes();
        } else {
            userQuit = true;
        }
    }
}

