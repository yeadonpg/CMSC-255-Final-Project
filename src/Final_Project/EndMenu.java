package Final_Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EndMenu {
    public static boolean DONE = false;
    public static boolean userQuit;

    /** {@code main} Declares JavaFX objects, giving them functionality and style
     * - For MILESTONE 2, the game only needs to be implemented in the console, JavaFX isn't needed until then **/
    public static void main() {
        // DEMO
        GridPane root = new GridPane();
        Button btnRestart = new Button("End Menu -> Main Menu");
        Button btnQuit = new Button("End Program");
        btnRestart.setOnAction(e -> sceneDone(false));
        btnQuit.setOnAction(e -> sceneDone(true));
        root.getChildren().add(btnRestart);
        root.getChildren().add(btnQuit);

        System.out.println("                             \"Typing Contest\"                          ");
        System.out.println("                                Best Scores:                            ");
        System.out.println("|----------Easy----------||------Intermediate------||----------Hard----------|");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                int ranking = i + 1;
                //Reading the example scores from file
                String[] line = Main.readLineFromFile(j, i);
                // Line format: [0] = Username, [1] = Difficulty, [2] = Score
                double score = Double.parseDouble(line[2]);
                // Using print formatting to create a nice looking output
                System.out.printf("| " + ranking + ". %s: %.1f |", line[0], score);
            }
            System.out.println();
        }
        System.out.println("|----------------------------------------------------------------------------|");

        // Asking the user whether they want to quit or not, pressing enter will return the user back to the main menu
        System.out.println("Enter \"quit\" to quit, to replay the game press Enter");
        // Using Main.INPUT as a project-wide scanner, using more than one scanner in a program can result in an error
        String quitAnswer = Main.INPUT.nextLine();
        if (quitAnswer.trim().equalsIgnoreCase("quit")) {
            userQuit = true;
        }

        if (!Main.runConsole) {
            finish(Main.STAGE, root);
        }
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

