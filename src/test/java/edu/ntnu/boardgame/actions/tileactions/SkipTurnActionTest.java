package edu.ntnu.boardgame.actions.tileactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Unit tests for {@link SkipTurnAction}.
 */
public class SkipTurnActionTest {

    private Board board;
    private Player player;

    /**
     * Sets up a 9x10 board and a player at tile 1 before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9, 10);
        Tile startTile = board.getTile(1);
        player = new Player("Abdi", startTile, 1);
        player.setPosition(1, board);
    }

    /**
     * Verifies that executing the action sets the player's skip flag to true.
     */
    @Test
    void testExecuteSetsSkipNextTurn() {
        SkipTurnAction action = new SkipTurnAction();
        action.execute(player, board);

        assertTrue(player.shouldSkipTurn(), "Player should skip next turn after action is executed.");
    }

    /**
     * Verifies that getDestination returns -1 since this action does not move
     * the player.
     */
    @Test
    void testGetDestinationReturnsNegativeOne() {
        SkipTurnAction action = new SkipTurnAction();
        assertEquals(-1, action.getDestination(), "getDestination() should return -1 for non-moving actions.");
    }
}
