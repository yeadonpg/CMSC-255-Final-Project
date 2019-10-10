package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu {
    public static boolean DONE = false;
    public static String userName;
    public static int userDifficulty = -1;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {
        GridPane root = new GridPane();
        Button btn = new Button("Main Menu -> Run Game");
        btn.setOnAction(e -> sceneDone());
        root.getChildren().add(btn);

        // ***********************************************YOUR*CODE*HERE************************************************
        // DEMO - REPLACE THIS WITH YOUR OWN CODE
        System.out.println("[Main Menu] DEMO - Press Enter to Continue");
        Main.INPUT.nextLine();

        // *************************************************************************************************************
        // NOTE: sceneDone() must be called if you want your scene to end properly

        // TODO Uncomment this line when ready to move away from console
        // finish(Main.STAGE, root);
    }

    /** {@code finish} completes the JavaFX scene, sets the scene to the stage **/
    private static void finish(Stage stage, GridPane root) {
        // Creating the scene, showing the stage
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
    }

    /** {@code sceneDone} allows the program to exit gracefully if the user decides to quit the game **/
    private static void sceneDone() {
        DONE = true;
    }
}
