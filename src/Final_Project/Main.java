package Final_Project;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * <h1>Typing Contest</h1>
 * <b>Description:</b>
 * TODO
 * @author Patrick Yeadon, Yusuf Niamati, Austin Escobedo, Lokendra Tamang
 * @version TODO
 * @since TODO
 *  **/
@SuppressWarnings("WeakerAccess")
public class Main extends Application {

    /** Declaring package-wide variables **/
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static Stage STAGE;

    /** Scanner will be removed eventually; after the game has been moved away from the console **/
    public static Scanner INPUT = new Scanner(System.in);

    /** userDifficulty, and userName are given a value during the MainMenu scene **/
    public static int userDifficulty;
    public static String userName;

    /** userScore is given a value during the RunGame scene **/
    public static double userScore;

    /** userQuit can be given a value during any scene, to quit the application instantly **/
    public static boolean userQuit;

    /** Keeping track of which scenes have been completed, allowing for scenes to be shown in chronological order **/
    public static boolean[] firstTimeScene = {true, true, true};

    /** {@code main} Launches the Main Loop **/
    public static void main(String[] args) {
        launch(args);
    }

    /** {@code start} Contains the main loop; handles scene switching **/
    @Override
    public void start(Stage stage) throws Exception {
        // Giving the package-wide STAGE a value; it will be referenced by all scenes contained in the window
        STAGE = stage;

        new AnimationTimer () {
            @Override
            public void handle(long cNT) {
                // Handling scenes in a manner that allows them to complete chronologically
                userQuit = false;
                if (firstTimeScene[0]) {
                    System.out.println("[DEBUG] Starting Main Menu");
                    MainMenu.main();
                    firstTimeScene[0] = false;
                }
                if (MainMenu.DONE) {
                    userDifficulty = MainMenu.userDifficulty;
                    userName = MainMenu.userName;
                    if (firstTimeScene[1]) {
                        System.out.println("[DEBUG] Starting Run Game");
                        RunGame.main();
                        firstTimeScene[1] = false;
                    }
                    if (RunGame.DONE) {
                        userScore = RunGame.userScore;
                        if (firstTimeScene[2]) {
                            System.out.println("[DEBUG] Starting End Menu");
                            storeToFile(userName, userScore);
                            EndMenu.main();
                            firstTimeScene[2] = false;
                        }
                        if (EndMenu.DONE) {
                            userQuit = EndMenu.userQuit;
                        }
                    }
                }
                if (userQuit) {
                    stage.close();
                }
            }
        }.start();
        stage.show();
    }

    /** {@code resetScenes} Resets the scenes; effectively returning the user back to the Main Menu **/
    public static void resetScenes() {
        firstTimeScene = new boolean[] {true, true, true};
        MainMenu.DONE = false;
        RunGame.DONE = false;
        EndMenu.DONE = false;
    }

    /** {@code storeToFile} Stores username and score in a file that can be read from later **/
    private static void storeToFile(String userName, double userScore) {
        // TODO
    }

    /** {@code readFromFile} Returns the score and username stored in the given index **/
    private static String[] readFromFile(int index) {
        // TODO; fix placeholder return value when complete
        return null;
    }
}
