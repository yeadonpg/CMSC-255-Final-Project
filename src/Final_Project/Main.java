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

    /** Whether to run the game in the console, or the in the GUI **/
    // TODO Change to false when ready to switch over to GUI
    public static boolean runConsole = true;

    /** Width of every scene in the application **/
    public static int WIDTH = 800;
    /** Height of every scene in the application **/
    public static int HEIGHT = 600;
    /** <h1>Package-wide stage for scenes</h1>
     * This will be used for staging every scene in the package **/
    public static Stage STAGE;

    /** <h1>USE THIS FOR GETTING USER INPUT</h1>
     * Scanner will be removed eventually; after the game has been moved away from the console **/
    public static Scanner INPUT = new Scanner(System.in);

    /** Will be given a value after the MainMenu completes **/
    private static int userDifficulty;
    /** Will be given a value after the MainMenu completes **/
    private static String userName;

    /** userScore is given a value during the RunGame scene **/
    private static double userScore;

    /** userQuit can be given a value during any scene, to quit the application instantly **/
    private static boolean userQuit;

    /** Keeping track of which scenes have been completed, allowing for scenes to be shown in chronological order **/
    private static boolean[] firstTimeScene = {true, true, true};

    private static String EASY_FILE_PATH = "src/Final_Project/assets/EasyScores.txt";
    private static String INTERMEDIATE_FILE_PATH = "src/Final_Project/assets/IntermediateScores.txt";
    private static String HARD_FILE_PATH = "src/Final_Project/assets/HardScores.txt";

    /** {@code main} Launches the Main Loop **/
    public static void main(String[] args) {
        launch(args);
    }

    /** {@code start} Contains the main loop; handles scene switching **/
    @Override
    public void start(Stage stage){
        // Giving the package-wide STAGE a value; it will be referenced by all scenes contained in the window
        STAGE = stage;

        AnimationTimer GuiHandler = new AnimationTimer () {
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
                        RunGame.main(userDifficulty);
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
        };
        if (!runConsole) {
            GuiHandler.start();
            stage.show();
        }
        // TODO Remove the code below when ready to switch over to GUI
        // Console scenes automatically complete chronologically
        while (!EndMenu.userQuit && runConsole) {
            // Starting the main menu
            MainMenu.main();
            // Getting stored values from the main menu
            userName = MainMenu.userName;
            userDifficulty = MainMenu.userDifficulty;

            // Starting the game
            RunGame.main(userDifficulty);
            // Getting the stored value from the game
            userScore = RunGame.userScore;

            if (userName != null && userDifficulty != -1 && userScore != -1) {
                // If the user-entered values aren't default values, store to file
                // The correct filename is automatically chosen based on the difficulty
                // If the difficulty is invalid, the score and username will be stored under the default difficulty
                storeToFile(userName, userDifficulty, userScore);
            }

            // Starting the end menu
            EndMenu.main();
        }
        // When the loop ends, the program will exit
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
    public static void storeToFile(String userName, int userDifficulty, double userScore) {
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
                assert os != null;
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** {@code readEntireFile} returns the file as a sorted list of strings, one string for each line **/
    public static String[] readEntireFile(String fileName) {
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
        if (fileName.equals(EASY_FILE_PATH) || fileName.equals(INTERMEDIATE_FILE_PATH) || fileName.equals(HARD_FILE_PATH)) {
            return sortByScore(lines);
        } else {
            return lines;
        }
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
                    // Parsing file lines to get the score
                    String[] splitLine1 = lines[i].split(",");
                    String[] splitLine2 = lines[i + 1].split(",");
                    double score1 = Double.parseDouble(splitLine1[2]);
                    double score2 = Double.parseDouble(splitLine2[2]);

                    // Comparing scores for sorting
                    // Sorting greatest-to-least, in this case
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
        String line = readLineFromFile(fileName, index);
        return line.split(",");
    }

    /** {@code readLineFromFile} Returns the string at a given index in the given filename **/
    public static String readLineFromFile(String fileName, int index) {
        String[] lines = readEntireFile(fileName);
        return lines[index];
    }
}
