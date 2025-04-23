package edu.ntnu.boardgame.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class ChessPuzzleView {

    private static final Map<String, String> PUZZLES = Map.of(
            "chess_puzzle1.png", "Nf3",
            "chess_puzzle2.png", "Qd8+",
            "chess_puzzle3.png", "e4",
            "chess_puzzle4.png", "Bb5"
    );

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
            if (userInput.equalsIgnoreCase(correctMove)) {
                resultLabel.setText("✅ Correct!");
            } else {
                resultLabel.setText("❌ Wrong. Try again!");
            }
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
}
