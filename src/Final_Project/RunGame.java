package Final_Project;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RunGame {
    public static boolean DONE = false;
    // userScore should be set to a valid number greater than or equal to 0
    // Failure to set userScore will result in the score not being saved
    public static double userScore = -1;
    public static boolean storeScore;

    /** {@code main} Declares JavaFX objects, giving them functionality and style **/
    public static void main(int userDifficulty) {
        GridPane gridPane = new GridPane();

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
        for (int i = 0; i < challengeString.length() + 5; i++) {
            hyphensBuilder.append("-");
        }
        String hyphens = hyphensBuilder.toString();

        // Displaying the challenge sentence, and providing the user with some instructions
        Label challengeLabel = new Label("Your challenge sentence is:\n\n" +
                "" + hyphens + "\n" +
                "" + challengeString + "\n" +
                "" + hyphens + "\n\n" +
                "Press the start button, then wait for the countdown to begin typing!\n" +
                "Press Enter when done:\n");

        Button startGameButton = new Button("Start");

        final boolean[] userDoneTyping = {false};

        Label countDownLabel = new Label("");

        TextField userTextBox = new TextField("Type Here");
        userTextBox.setDisable(true);

        userTextBox.setOnMouseClicked(e -> {
            userTextBox.setEditable(true);
            userTextBox.setText("");
        });

        userTextBox.setOnAction(e -> {
            userTextBox.setDisable(true);
            userDoneTyping[0] = true;
        });

        Label statsLabel = new Label("");

        // Initializing radio buttons for when user is done playing
        HBox radioButtons = new HBox();
        RadioButton yesChoice = new RadioButton("Yes");
        yesChoice.setTextFill(Color.GREEN);
        radioButtons.getChildren().add(yesChoice);
        RadioButton noChoice = new RadioButton("No");
        noChoice.setTextFill(Color.RED);
        noChoice.setSelected(true);
        radioButtons.getChildren().add(noChoice);
        radioButtons.setVisible(false);

        yesChoice.setOnAction(e -> {
            if (yesChoice.isSelected()) {
                storeScore = true;
                noChoice.setSelected(false);
            }
        });

        noChoice.setOnAction(e -> {
            if (noChoice.isSelected()) {
                storeScore = true;
                yesChoice.setSelected(false);
            }
        });

        // Initializing done button
        Button done = new Button("Done");
        done.setOnAction(e -> sceneDone());
        done.setVisible(false);

        startGameButton.setOnAction(e -> {
            new AnimationTimer() {
                float totalTime = 0;
                long startNT = System.nanoTime();
                @Override
                public void handle(long cNT) {
                    float deltaTime = (float) ((cNT - startNT) / 1000000000.0);
                    if (deltaTime > 1) {
                        totalTime += deltaTime;
                        startNT = System.nanoTime();
                        if (totalTime <= 5) {
                            countDownLabel.setText((5 - (int) totalTime) + "\n");
                        } else {
                            countDownLabel.setText("Go!\n");
                            userTextBox.setDisable(false);
                        }
                        // When the user is done typing; calculate the score & set the final value
                        if (userDoneTyping[0]) {
                            float timeTakenSeconds = totalTime - 5;
                            float userAccuracy = percentCorrect(challengeString, userTextBox.getText());
                            float userWPM = wordsPerMin(numWords, timeTakenSeconds);
                            userScore = userWPM * userAccuracy;
                            statsLabel.setText("Finished!\n" +
                                    "" + String.format("Time Elapsed: %.2f", timeTakenSeconds) + "\n" +
                                    "" + String.format("Accuracy: %.1f", userAccuracy * 100) + "%\n" +
                                    "" + String.format("Your WPM is: %.2f w/m", userWPM) + "\n" +
                                    "" + String.format("Final Score: %.2f (WPM x Accuracy)", userScore) + "\n\n" +
                                    "Do you want to save your score?");
                                    radioButtons.setVisible(true);
                                    done.setVisible(true);
                            this.stop();
                        }
                    }
                }
            }.start();
        });

        Node[][] nodes = new Node[][] {
                {challengeLabel},
                {countDownLabel, startGameButton},
                {userTextBox},
                {statsLabel},
                {radioButtons, done}
        };

        // Adding all nodes to the scene
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                gridPane.add(nodes[i][j], j, i);
            }
        }

        if (!Main.runConsole) {
            finish(Main.STAGE, gridPane);
        }
    }

    /** {@code percentCorrect} Calculates the percentage of words that the user got correct **/
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

    /** {@code wordsPerMin} Calculates the user's words per minute **/
    public static float wordsPerMin(int numWords, float timeTakenSeconds) {
        return (float) ((numWords) / (timeTakenSeconds / 60.0));
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
