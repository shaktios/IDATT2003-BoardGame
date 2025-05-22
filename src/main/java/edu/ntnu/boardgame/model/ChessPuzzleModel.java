package edu.ntnu.boardgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Model class for handling chess puzzle data and logic. Responsible for
 * selecting a puzzle, generating answer options, and providing the correct
 * move.
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
     * Randomly selects a puzzle from the predefined list. Stores both the image
     * name and correct move.
     */
    public void selectRandomPuzzle() {
        List<String> keys = new ArrayList<>(PUZZLES.keySet());
        selectedImage = keys.get(random.nextInt(keys.size()));
        correctMove = PUZZLES.get(selectedImage);
    }

    /**
     * Returns the filename of the currently selected puzzle image.
     *
     * @return the selected image file name
     */
    public String getSelectedImage() {
        return selectedImage;
    }

    /**
     * Returns the correct move for the current puzzle.
     *
     * @return the correct move in standard notation (e.g., "Qb5+#")
     */
    public String getCorrectMove() {
        return correctMove;
    }

    /**
     * Generates four answer options: the correct move and three random
     * distractors. The list is shuffled before being returned.
     *
     * @return a shuffled list of answer move strings
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
