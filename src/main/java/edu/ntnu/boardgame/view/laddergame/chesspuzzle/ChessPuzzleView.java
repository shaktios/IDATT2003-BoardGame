package edu.ntnu.boardgame.view.laddergame.chesspuzzle;

import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChessPuzzleView {

    private static final Map<String, String> PUZZLES = Map.of(
            "chess_puzzle1.png", "Qh4#",
            "chess_puzzle2.png", "Rg8#",
            "chess_puzzle3.png", "Rc8#",
            "chess_puzzle4.png", "Rc1#"
    );

    private Player player;
    private Board board;
    private Runnable onPuzzleComplete; 
    private static final int CORRECT_MOVE_FORWARD = 5;
    private static final int WRONG_MOVE_BACKWARD = 3;

    public ChessPuzzleView(Player player, Board board, Runnable onPuzzleComplete) {
        this.player = player;
        this.board = board;
        this.onPuzzleComplete = onPuzzleComplete; 
    }

    public void show() {
        List<String> imageList = new ArrayList<>(PUZZLES.keySet());
        Random rand = new Random();
        String chosenImage = imageList.get(rand.nextInt(imageList.size()));
        String correctMove = PUZZLES.get(chosenImage);

        Image image = new Image(getClass().getResourceAsStream("/images/" + chosenImage));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);

        Label instruction = new Label("Velg riktig trekk for å vinne:");
        instruction.setStyle("-fx-font-weight: bold;");

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-weight: bold;");

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        List<String> options = generateOptions(correctMove);

        List<Button> buttons = new ArrayList<>();

        for (String option : options) {
            Button optionButton = new Button(option);
            optionButton.setOnAction(e -> {
                Stage stage = (Stage) optionButton.getScene().getWindow();

                // Deaktiver ALLE knapper så spilleren kun kan svare én gang
                buttons.forEach(btn -> btn.setDisable(true));

                if (option.equals(correctMove)) {
                    resultLabel.setText("Riktig! Du går fremover " + CORRECT_MOVE_FORWARD + " felt!");
                    movePlayer(CORRECT_MOVE_FORWARD);
                } else {
                    resultLabel.setText("Feil! Du går bakover " + WRONG_MOVE_BACKWARD + " felt!");
                    movePlayer(-WRONG_MOVE_BACKWARD);
                }

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(event -> {
                    if (onPuzzleComplete != null) {
                        onPuzzleComplete.run();
                    }
                    stage.close();
                });
                pause.play();
            });
            buttons.add(optionButton);
            buttonBox.getChildren().add(optionButton);
        }

        VBox layout = new VBox(20, imageView, instruction, buttonBox, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Chess Puzzle");
        stage.setScene(new Scene(layout, 850, 850));
        stage.show();
    }

    private List<String> generateOptions(String correctMove) {
        List<String> moves = new ArrayList<>(List.of(
                "Qh4#", "Rg8#", "Rc8#", "Rc1#", "Qd8#", "e4#", "Bb5#", "Nh5#",
                "Bd3#", "Rd1#", "Qe7#", "Nd5#" // 
        ));
        moves.remove(correctMove); // Fjern riktig svar
        Collections.shuffle(moves);

        List<String> selected = new ArrayList<>();
        int i = 0;
        while (selected.size() < 3 && i < moves.size()) {
            selected.add(moves.get(i));
            i++;
        }

        // Hvis det fortsatt er færre enn 3, bare kopier noen tilfeldige trekk igjen
        Random rand = new Random();
        while (selected.size() < 3) {
            selected.add(moves.get(rand.nextInt(moves.size())));
        }

        selected.add(correctMove); // Legg til riktig svar
        Collections.shuffle(selected); // Bland rekkefølgen
        return selected;
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
