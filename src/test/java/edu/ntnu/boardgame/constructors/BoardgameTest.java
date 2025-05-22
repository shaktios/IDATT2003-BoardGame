package edu.ntnu.boardgame.constructors;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Boardgame} class. Tests construction, player
 * management, dice rolling, and board access.
 */
public class BoardgameTest {

    private Board board;
    private Boardgame boardGame;

    /**
     * Sets up a 10x10 board and a new game before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(10, 10);
        boardGame = new Boardgame(board, 2, 6);
    }

    /**
     * Tests that the constructor throws exceptions on invalid inputs.
     */
    @Test
    void testConstructorWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(null, 2, 6), "Board should not be null.");
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(board, 0, 6), "Number of dice must be at least 1.");
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(board, 2, 1), "Each die must have at least 2 sides.");
    }

    /**
     * Tests that a player can be added successfully.
     */
    @Test
    void testAddPlayerValid() {
        boardGame.addPlayer("Abdi", 10);
        List<Player> players = boardGame.getPlayers();
        assertEquals(1, players.size(), "There should be one player in the game.");
        assertEquals("Abdi", players.get(0).getName(), "Player name should be 'Abdi'.");
    }

    /**
     * Tests that adding a player with null or empty name throws an exception.
     */
    @Test
    void testAddPlayerInvalid() {
        assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer(null, 10), "Name should not be null.");
        assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer("", 10), "Name should not be empty.");
    }

    /**
     * Tests that the dice object is correctly initialized and rolls within the
     * expected range.
     */
    @Test
    void testGetDice() {
        Dice dice = boardGame.getDice();
        assertNotNull(dice, "Dice object should not be null.");

        int rollResult = dice.roll();
        assertTrue(rollResult >= 2 && rollResult <= 12, "Roll should be between 2 and 12 for two 6-sided dice.");
    }

    /**
     * Tests that an existing player object can be added to the game.
     */
    @Test
    void testAddExistingPlayer() {
        Player player = new Player("Layla", board.getTile(1), 19);
        boardGame.addExistingPlayer(player);

        List<Player> players = boardGame.getPlayers();
        assertEquals(1, players.size(), "There should be one player in the game.");
        assertEquals("Layla", players.get(0).getName(), "Player name should be 'Layla'.");
    }

    /**
     * Tests that the board returned from the getter matches the original board.
     */
    @Test
    void testGetBoard() {
        assertEquals(board, boardGame.getBoard(), "Returned board should match the one used in constructor.");
    }
}
