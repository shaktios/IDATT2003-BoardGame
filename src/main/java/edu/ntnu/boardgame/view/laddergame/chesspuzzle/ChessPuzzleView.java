package edu.ntnu.boardgame.view.laddergame.chesspuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChessPuzzleView {

    private static final Map<String, String> PUZZLES = Map.of(
            "chess_puzzle1.png", "Nf3",
            "chess_puzzle2.png", "Qd8+",
            "chess_puzzle3.png", "e4",
            "chess_puzzle4.png", "Bb5"
    );

    private Player player;
    private Board board;
    private int correctMoveForward = 5; // hvor mange ruter frem
    private int wrongMoveBackward = 3;  // hvor mange ruter bak

    public ChessPuzzleView(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    public void show() {
        // Pick a random puzzle
        List<String> imageList = new ArrayList<>(PUZZLES.keySet());
        Random rand = new Random();
        String chosenImage = imageList.get(rand.nextInt(imageList.size()));
        String correctMove = PUZZLES.get(chosenImage);

        // Load puzzle image
        Image image = new Image(getClass().getResourceAsStream("/images/" + chosenImage));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);

        // Input and feedback
        TextField inputField = new TextField();
        inputField.setPromptText("Type the next move (e.g. Nf3)");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-weight: bold;");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText().trim();
            Stage stage = (Stage) inputField.getScene().getWindow(); // hent stage for å lukke senere

            if (userInput.equalsIgnoreCase(correctMove)) {
                resultLabel.setText(" Correct!");
                movePlayer(correctMoveForward);
            } else {
                resultLabel.setText("Wrong!");
                movePlayer(-wrongMoveBackward);
            }
            // Lukk etter en liten pause for å vise resultatet
            PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1.5));
            pause.setOnFinished(event -> stage.close());
            pause.play();
        });

        // Layout
        VBox layout = new VBox(15, imageView, inputField, submitButton, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Scene and stage
        Stage stage = new Stage();
        stage.setTitle("Chess Puzzle");
        stage.setScene(new Scene(layout, 600, 700));
        stage.show();
    }

    private void movePlayer(int offset) {
        int newPosition = player.getPosition() + offset;

        if (newPosition < 1) {
            newPosition = 1;
        } else if (newPosition > board.getSize()) {
            newPosition = board.getSize();
        }

        player.setPosition(newPosition, board);
        player.setCurrentTile(board.getTile(newPosition));
    }
}
