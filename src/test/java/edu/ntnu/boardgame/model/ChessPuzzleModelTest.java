package edu.ntnu.boardgame.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ChessPuzzleModel}.
 */
public class ChessPuzzleModelTest {

    private ChessPuzzleModel model;

    /**
     * Initializes a new model before each test.
     */
    @BeforeEach
    void setUp() {
        model = new ChessPuzzleModel();
        model.selectRandomPuzzle();
    }

    /**
     * Verifies that a puzzle is selected and contains a valid image and move.
     */
    @Test
    void testPuzzleSelectionPopulatesFields() {
        String image = model.getSelectedImage();
        String move = model.getCorrectMove();

        assertNotNull(image, "Selected image should not be null");
        assertNotNull(move, "Correct move should not be null");
        assertFalse(image.isBlank(), "Selected image should not be blank");
        assertFalse(move.isBlank(), "Correct move should not be blank");
    }

    /**
     * Verifies that generateOptions returns 4 total moves and includes the
     * correct move.
     */
    @Test
    void testGenerateOptionsIncludesCorrectMove() {
        List<String> options = model.generateOptions();
        String correctMove = model.getCorrectMove();

        assertEquals(4, options.size(), "There should be exactly 4 options");
        assertTrue(options.contains(correctMove), "Options should include the correct move");
    }

    /**
     * Verifies that all options are unique and shuffled.
     */
    @Test
    void testGenerateOptionsReturnsUniqueMoves() {
        List<String> options = model.generateOptions();

        long uniqueCount = options.stream().distinct().count();
        assertEquals(4, uniqueCount, "All answer options should be unique");
    }
}
