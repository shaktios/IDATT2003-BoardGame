package edu.ntnu.boardgame;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.constructors.Player;

/**
 * Unit tests for the {@link Player} class. Verifies correct player
 * initialization, movement, and validation of input.
 */
public class PlayerTest {
    
    private Board board;
    private Player player;


    /**
     * Sets up a standard board and a player before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(10, 10); // Brett med 10 felt
        player = new Player("Abdi", board.getTile(1), 23); // Starter på felt 1
    }


    /**
     * Tests that the player is initialized correctly with name and starting
     * tile.
     */
    @Test
    void testPlayerInitialization() {
        assertEquals("Abdi", player.getName(), "Navnet på spilleren er feil.");
        assertEquals(1, player.getCurrentTile().getPosition(), "Startfeltet er feil.");
    }


    /**
     * Tests that invalid player creation throws exceptions:
     * - Empty name
     * - Null name
     * - Null starting tile
     */
    @Test
    void testInvalidPlayerCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Player("", board.getTile(1), 23),
                "Bør kaste exception for tomt navn.");
        assertThrows(IllegalArgumentException.class, () -> new Player(null, board.getTile(1), 10),
                "Bør kaste exception for null navn.");
        assertThrows(IllegalArgumentException.class, () -> new Player("Abdi", null, 10),
                "Bør kaste exception for null startfelt.");
    }

    /**
     * Tests that moving a player by a valid number of steps changes their tile accordingly.
     */
    @Test
    void testMoveValidSteps() {
        player.move(3, board);
        assertEquals(4, player.getCurrentTile().getPosition(), "Spilleren burde være på felt 4.");
    }

    /**
     * Tests that moving the player beyond the last tile places them on the
     * final tile.
     */
    @Test
    void testMoveBeyondBoardSize() {
        player.move(9, board); // Brettet har 10 felt, så spilleren stopper der
        assertEquals(10, player.getCurrentTile().getPosition(), "Spilleren burde være på siste felt.");
    }

    /**
     * Tests that moving the player by a negative number of steps throws an exception.
     */
    @Test
    void testMoveNegativeStepsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.move(-2, board),
                "Bør kaste exception for negativt antall steg.");
    }
    /**
     * Tests that the player's token can be set and retrieved correctly.
     */
    @Test
    void testSetAndGetToken() {
        player.setToken("Hat");
        assertEquals("Hat", player.getToken(), "Token should be 'Hat'.");
    }

    /**
     * Tests that setting a null or empty token throws an exception.
     */
    @Test
    void testInvalidTokenSetting() {
        assertThrows(IllegalArgumentException.class, () -> player.setToken(null), "Should throw exception for null token.");
        assertThrows(IllegalArgumentException.class, () -> player.setToken("   "), "Should throw exception for blank token.");
    }

    /**
     * Tests setting and getting the player's age.
     */
    @Test
    void testSetAndGetAge() {
        player.setAge(30);
        assertEquals(30, player.getAge(), "Age should be 30.");
    }

    /**
     * Tests that skip-turn flag works as expected.
     */
    @Test
    void testSkipNextTurnFlag() {
        assertFalse(player.shouldSkipTurn(), "Player should not skip turn by default.");
        player.setSkipNextTurn(true);
        assertTrue(player.shouldSkipTurn(), "Player should skip the next turn.");
    }
}
