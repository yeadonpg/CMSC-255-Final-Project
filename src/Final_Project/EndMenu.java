package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EndMenu {
    public static boolean DONE = false;
    public static boolean userQuit;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {
        GridPane root = new GridPane();
        Button btn = new Button("End Menu -> Main Menu");
        Button btnQuit = new Button("End Program");
        btn.setOnAction(e -> sceneDone(false));
        btnQuit.setOnAction(e -> sceneDone(true));
        root.getChildren().add(btn);
        root.getChildren().add(btnQuit);

        // ***********************************************YOUR*CODE*HERE************************************************
        // DEMO - REPLACE THIS WITH YOUR OWN CODE
        System.out.println("[End Menu] DEMO");

        // Example of reading scores from the files
        System.out.println("|--------Easy--------||-----Intermediate-----||--------Hard--------|");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                String[] line = Main.readLineFromFile(j, i);
                double score = Double.parseDouble(line[2]);
                System.out.printf("| %s: %.1f |", line[0], score);
            }
            System.out.println();
        }

        System.out.println("Enter \"quit\" to quit, to replay press Enter");
        String quitAnswer = Main.INPUT.nextLine();
        if (quitAnswer.trim().equalsIgnoreCase("quit")) {
            userQuit = true;
        }

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
