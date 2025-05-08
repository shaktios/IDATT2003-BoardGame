package edu.ntnu.boardgame.model.laddergame.chesspuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Model class for handling chess puzzle logic and data.
 */
public class ChessPuzzleModel {

    private static final Map<String, String> PUZZLES = Map.of(
            "chess_puzzle1.png", "Qb5+#",
            "chess_puzzle2.png", "Qc2+#",
            "chess_puzzle3.png", "NG4+#",
            "chess_puzzle4.png", "Qh7+#"
    );

    private String selectedImage;
    private String correctMove;
    private final Random random = new Random();

    /**
     * Selects a random puzzle and stores its correct move.
     */
    public void selectRandomPuzzle() {
        List<String> keys = new ArrayList<>(PUZZLES.keySet());
        selectedImage = keys.get(random.nextInt(keys.size()));
        correctMove = PUZZLES.get(selectedImage);
    }

    /**
     * Returns the filename of the selected puzzle image.
     *
     * @return the image file name
     */
    public String getSelectedImage() {
        return selectedImage;
    }

    /**
     * Returns the correct move for the current puzzle.
     *
     * @return the correct move string
     */
    public String getCorrectMove() {
        return correctMove;
    }

    /**
     * Generates a list of answer options including the correct move and three
     * distractors. Ensures no duplicates of the correct move are present.
     *
     * @return shuffled list of answer options
     */
    public List<String> generateOptions() {
        List<String> allMoves = new ArrayList<>(List.of(
                "Qb5#", "Qh2#", "Qh4#", "Rg8#", "Rc8#", "Rc1#", "Qd8#", "e4#",
                "Bb5#", "Nh5#", "Bd3#", "Rd1#", "Qe7#", "Nd5#"
        ));

      
        allMoves.removeIf(move -> move.equals(correctMove));

        // Plukk ut 3 unike feilalternativer
        Collections.shuffle(allMoves);
        List<String> options = new ArrayList<>(allMoves.subList(0, 3));

        // Legg til riktig trekk og bland alt
        options.add(correctMove);
        Collections.shuffle(options);

        return options;
    }
}
