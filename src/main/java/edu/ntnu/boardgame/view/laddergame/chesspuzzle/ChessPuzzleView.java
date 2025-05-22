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
 * View class for displaying a chess puzzle in a separate window and relaying
 * the selected move back to the controller. Displays an image-based puzzle and
 * a set of possible moves, disables buttons after one is selected, and allows
 * feedback to be shown.
 */
public class ChessPuzzleView {

  private final String imageFile;
  private final String correctMove; 
  private final List<String> options;
  private final Consumer<String> onMoveSelected;
  private Label resultLabel;
  private Stage stage;

    /**
     * Constructs a new ChessPuzzleView instance.
     *
     * @param imageFile the filename of the chess puzzle image to display
     * @param correctMove the correct move for this puzzle 
     * @param options the list of possible answer moves (including correct and
     * distractors)
     * @param onMoveSelected callback to pass the player's selected move to the
     * controller
     */
  public ChessPuzzleView(String imageFile, String correctMove, List<String> options, Consumer<String> onMoveSelected) {
    this.imageFile = imageFile;
    this.correctMove = correctMove;
    this.options = options;
    this.onMoveSelected = onMoveSelected;
  }

    /**
     * Builds and displays the chess puzzle window. Includes the puzzle image,
     * move options, and a result label. Disables all answer buttons after a
     * selection is made and sends the selected move to the controller via the
     * provided callback. The window cannot be closed manually by the user.
     */
  public void show() {
    Image image = new Image(getClass().getResource("/images/" + imageFile).toExternalForm());
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
     * Displays a feedback message after the user has selected a move. Typically
     * used to show whether the move was correct or incorrect.
     *
     * @param text the feedback text to display (e.g., "Correct!" or "Wrong
     * move")
     */
  public void showResult(String text) {
        resultLabel.setText(text);
    }

    /**
     * Closes the puzzle window programmatically. Called by the controller after
     * feedback has been shown and the game continues.
     */
  public void closeStage() {
    if (stage != null) {
      stage.close();
    }
  }
}
