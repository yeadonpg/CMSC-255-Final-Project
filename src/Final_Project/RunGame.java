package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RunGame {
    public static boolean DONE = false;
    public static double userScore;
    public static void main() {
        GridPane root = new GridPane();

        // ***********************************************YOUR*CODE*HERE************************************************
        // This button is for the Demo; replace it when needed
        Button btn = new Button("Run Game -> End Menu");

        // Assigning the button a function
        btn.setOnAction(e -> sceneDone());

        // Adding the button to the scene (root)
        root.getChildren().add(btn);

        // *************************************************************************************************************
        // NOTE: sceneDone() must be called if you want your scene to end properly

        finish(Main.STAGE, root);
    }

    /** You do not need to touch this method **/
    private static void finish(Stage stage, GridPane root) {
        // Creating the scene
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
    }

    private static void sceneDone() {
        DONE = true;
    }
}
