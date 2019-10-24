package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RunGame {
    public static boolean DONE = false;
    // userScore should be set to a valid number greater than or equal to 0
    // Failure to set userScore will result in the score not being saved
    public static double userScore = -1;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * - For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {
        // DEMO - REPLACE WITH YOUR OWN CODE
        GridPane root = new GridPane();
        Button btn = new Button("Run Game -> End Menu");
        btn.setOnAction(e -> sceneDone());
        root.getChildren().add(btn);

        // ***********************************************YOUR*CODE*HERE************************************************
        // DEMO - REPLACE THIS WITH YOUR OWN CODE
        System.out.println("[Run Game] DEMO - Press Enter to Continue");
        // Using Main.INPUT as a project-wide scanner, using more than one scanner in a program can result in an error
        // In this case, INPUT.nextLine() is used to pause the program until the user presses Enter
        Main.INPUT.nextLine();

        // *************************************************************************************************************
        // NOTE: sceneDone() must be called if you want your scene to end properly

        // TODO Uncomment this line when ready to move away from console
        // finish(Main.STAGE, root);
    }

    /** {@code finish} completes the JavaFX scene, sets the scene to the stage **/
    private static void finish(Stage stage, GridPane root) {
        // Creating the scene
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
    }

    /** {@code sceneDone} allows the scene handler to move onward to the next scene **/
    private static void sceneDone() {
        DONE = true;
    }
}
