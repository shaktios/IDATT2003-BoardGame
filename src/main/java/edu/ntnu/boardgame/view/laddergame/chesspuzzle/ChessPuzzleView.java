package edu.ntnu.boardgame.view.laddergame.chesspuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View class for displaying a chess puzzle and relaying user choice.
 */
public class ChessPuzzleView {

    private final String imageFile;
    private final String correctMove; // optional, can be removed if View shouldn't know
    private final List<String> options;
    private final Consumer<String> onMoveSelected;
    private Label resultLabel;
    private Stage stage;

    /**
     * Constructs the ChessPuzzleView.
     *
     * @param imageFile the puzzle image filename
     * @param correctMove the correct move (optional use)
     * @param options the answer choices to show
     * @param onMoveSelected callback to pass selected move to controller
     */
    public ChessPuzzleView(String imageFile, String correctMove, List<String> options, Consumer<String> onMoveSelected) {
        this.imageFile = imageFile;
        this.correctMove = correctMove;
        this.options = options;
        this.onMoveSelected = onMoveSelected;
    }

    /**
     * Displays the chess puzzle window.
     */
    public void show() {
        Image image = new Image(getClass().getResourceAsStream("/images/" + imageFile));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);

        Label instruction = new Label("Velg riktig trekk for å ta sjakk matt i ett trekk:");
        instruction.getStyleClass().add("chess-label");

        resultLabel = new Label();
        resultLabel.getStyleClass().add("chess-label");

        VBox buttonBox = new VBox(10);
        buttonBox.getStyleClass().add("puzzle-button-box");
        buttonBox.setAlignment(Pos.CENTER);

        List<Button> buttons = new ArrayList<>();
        for (String option : options) {
            Button btn = new Button(option);
            btn.getStyleClass().add("puzzle-option-button");

            btn.setOnAction(e -> {
                // Deaktiver alle knapper
                buttons.forEach(b -> b.setDisable(true));
                onMoveSelected.accept(option); // send valgt trekk til controller
            });

            buttons.add(btn);
            buttonBox.getChildren().add(btn);
        }

        VBox layout = new VBox(20, imageView, instruction, buttonBox, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 850, 850);
        scene.getStylesheets().add(getClass().getResource("/styles/chessPuzzles.css").toExternalForm());

        stage = new Stage();
        stage.setTitle("Chess Puzzle");
        stage.setResizable(false);
        stage.setScene(scene);

        // forhindrer lukking
        stage.setOnCloseRequest(event->{
            event.consume();
        }); 
        
        stage.show();
    }

    /**
     * Displays the result text after answer is selected.
     *
     * @param text the result text to show
     */
    public void showResult(String text) {
        resultLabel.setText(text);
    }

    /**
     * Closes the puzzle stage.
     */
    public void closeStage() {
        if (stage != null) {
            stage.close();
        }
    }
}
