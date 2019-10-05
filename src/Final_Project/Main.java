package Final_Project;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
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

    /** Paths where high scores will be stored **/
    private static String EASY_FILE_PATH = "src/Final_Project/assets/EasyScores.txt";
    private static String INTERMEDIATE_FILE_PATH = "src/Final_Project/assets/IntermediateScores.txt";
    private static String HARD_FILE_PATH = "src/Final_Project/assets/HardScores.txt";

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
                            storeToFile(userName, userDifficulty, userScore);
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
        }; //.start();
        // stage.show();
        // -------TODO-When-ready-to-move-away-from-console,-remove-the-above-comments,-and-remove-the-code-below-------
        // Handling console scenes in a manner that allows them to complete chronologically
        while (!EndMenu.userQuit) {
            MainMenu.main();
            userName = MainMenu.userName;
            userDifficulty = MainMenu.userDifficulty;
            RunGame.main();
            userScore = RunGame.userScore;
            storeToFile(userName, userDifficulty, userScore);
            EndMenu.main();
        }
        System.exit(0);
    }

    /** {@code resetScenes} Resets the scenes; effectively returning the user back to the Main Menu **/
    public static void resetScenes() {
        firstTimeScene = new boolean[] {true, true, true};
        MainMenu.DONE = false;
        RunGame.DONE = false;
        EndMenu.DONE = false;
    }

    /** {@code storeToFile} Stores username and score in HIGH_SCORES_FILE_PATH **/
    private static void storeToFile(String userName, int userDifficulty, double userScore) {
        String csv = "\n" + userName + "," + userDifficulty + "," + userScore;
        String fileName;
        switch (userDifficulty) {
            case 1:
                fileName = INTERMEDIATE_FILE_PATH;
                break;
            case 2:
                fileName = HARD_FILE_PATH;
                break;
            default :
                fileName = EASY_FILE_PATH;
                break;
        }
        appendToFile(csv, fileName);
    }

    /** {@code appendToFile} Appends the given string to HIGH_SCORES_FILE_PATH **/
    private static void appendToFile(String str, String fileName) {
        OutputStream os = null;
        try {
            // true argument tells OutputStream to append to file, rather than overwrite
            os = new FileOutputStream(new File(fileName), true);
            os.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** {@code readEntireFile} returns the file as a sorted list of strings, one string for each line **/
    private static String[] readEntireFile(String fileName) {
        String[] lines = new String[0];
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lines = Arrays.copyOf(lines, lines.length + 1);
                lines[lines.length - 1] = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sortByScore(lines);
    }

    /** {@code sortByScore} returns a sorted version of the lines given to it
     * Lines must be in the form: "[userName],[userDifficulty],[userScore]"
     * Lines are sorted by score, and nothing else **/
    private static String[] sortByScore(String[] lines) {
        int numMoves;
        do {
            numMoves = 0;
            for (int i = 0; i < lines.length; i++) {
                if (!(i + 1 >= lines.length)) {
                    String[] splitLine1 = lines[i].split(",");
                    String[] splitLine2 = lines[i + 1].split(",");
                    double score1 = Double.parseDouble(splitLine1[2]);
                    double score2 = Double.parseDouble(splitLine2[2]);
                    if (score2 > score1) {
                        numMoves++;
                        String newLine1 = splitLine2[0] + "," + splitLine2[1] + "," + splitLine2[2];
                        String newLine2 = splitLine1[0] + "," + splitLine1[1] + "," + splitLine1[2];
                        lines[i] = newLine1;
                        lines[i + 1] = newLine2;
                    }
                }
            }
        } while (numMoves != 0);
        return lines;
    }

    /** {@code readLineFromFile} Returns a string list containing the userName, userDifficulty, & userScore **/
    public static String[] readLineFromFile(int difficulty, int index) {
        String fileName;
        switch (difficulty) {
            case 1:
                fileName = INTERMEDIATE_FILE_PATH;
                break;
            case 2:
                fileName = HARD_FILE_PATH;
                break;
            default :
                fileName = EASY_FILE_PATH;
                break;
        }
        String[] lines = readEntireFile(fileName);
        return lines[index].split(",");
    }
}
