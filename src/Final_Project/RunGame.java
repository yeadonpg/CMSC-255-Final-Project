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
    public static void main(int userDifficulty) {
        // DEMO - REPLACE WITH YOUR OWN CODE
        GridPane root = new GridPane();
        Button btn = new Button("Run Game -> End Menu");
        btn.setOnAction(e -> sceneDone());
        root.getChildren().add(btn);

        // ***********************************************YOUR*CODE*HERE************************************************
        // Declaring the file path that the dictionary.txt is stored at:
        String fileName = "src/Final_Project/assets/dictionary.txt";

        // Using pre-made method that I made in the Main class
        // Returns a String array, each String contains one line of the txt file
        // Each line in the file contains a singular word, so I can randomly choose from this list of words
        String[] dictionary = Main.readEntireFile(fileName);

        StringBuilder challengeStringBuilder = new StringBuilder();

        // Change these values to change the difficulty of the challenge
        int minNumWords = 5 * userDifficulty + 5;
        int maxNumWords = 5 * userDifficulty + 5;
        // Generating a random number of words between the values given
        int numWords = (int)(Math.random() * (maxNumWords - minNumWords + 1) + minNumWords);

        // Generating the challenge sentence by choosing random words from dictionary.txt
        for (int i = 0; i < numWords; i++) {
            int index = (int) (Math.random() * dictionary.length + 1);
            String line = dictionary[index];
            challengeStringBuilder.append(line);
            // Adding spaces between words
            if (i + 1 < numWords) {
                challengeStringBuilder.append(" ");
            }
        }

        // Finished calculating challenge sentence
        String challengeString = challengeStringBuilder.toString();

        // Generating a line of hyphens with the same length as the challenge sentence
        StringBuilder hyphensBuilder = new StringBuilder();
        for (int i = 0; i < challengeString.length(); i++) {
            hyphensBuilder.append("-");
        }
        String hyphens = hyphensBuilder.toString();

        // Printing the challenge sentence, and providing the user with some instructions
        System.out.println("Your challenge sentence is:");
        System.out.println(hyphens);
        System.out.println(challengeString);
        System.out.println(hyphens);
        System.out.print("Press Enter, then wait for the countdown to begin typing!");

        // Waiting for the user to press enter before starting the countdown
        Main.INPUT.nextLine();
        // 5, 4, 3, 2, 1, Go!
        printCountdown(5, 1, 1000);
        System.out.println("Go!\n");

        // Getting a start time, allowing the user to be timed
        float startNanoTime = System.nanoTime();

        // Getting user input
        String userString = Main.INPUT.nextLine();

        // Calculating the time taken using the start time as reference
        float timeTakenSeconds = (float) ((System.nanoTime() - startNanoTime) / (Math.pow(10, 9)));

        // Calculating accuracy of word spelling
        float userAccuracy = percentCorrect(challengeString, userString);

        // Calculating Words Per Minute (WPM) Based on the time taken to finish
        float userWPM = wordsPerMin(numWords, timeTakenSeconds);

        // Calculating score based on accuracy and words per minute
        float userScore = userWPM * userAccuracy;

        // Printing calculated statistics and final score
        System.out.println("\nFinished!");
        System.out.printf("Time Elapsed: %.2fs\n", timeTakenSeconds);
        System.out.println("Accuracy: " + userAccuracy * 100 + "%");
        System.out.printf("Your WPM is: %.2f w/m\n", userWPM);
        System.out.printf("Final Score: %.2f (WPM x Accuracy)\n", userScore);

        Main.INPUT.nextLine();

        // *************************************************************************************************************
        // NOTE: sceneDone() must be called if you want your scene to end properly

        // TODO Uncomment this line when ready to move away from console
        // finish(Main.STAGE, root);
    }

    public static float percentCorrect(String challengeString, String userString) {
        String[] challengeWords = challengeString.split(" ");
        int numWords = challengeWords.length;
        String[] userWords = userString.split(" ");

        int numCorrect = 0;
        for (String word : challengeWords) {
            if (stringArrayContains(userWords, word)) {
                numCorrect++;
            }
        }

        return (float) numCorrect / numWords;
    }

    // Calculating words per minute
    public static float wordsPerMin(int numWords, float timeTakenSeconds) {
        return (float) ((numWords) / (timeTakenSeconds / 60.0));
    }

    public static void printCountdown(int start, int end, int pauseMillis) {
        if (start <= end) {
            return;
        }

        try {
            Thread.sleep(pauseMillis);
            for (int i = start; i >= end; i--) {
                System.out.printf("%d, ", i);
                Thread.sleep(pauseMillis);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean stringArrayContains(String[] strArr, String str) {
        for (String elem : strArr) {
            if (elem.equals(str)) {
                return true;
            }
        }
        return false;
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
