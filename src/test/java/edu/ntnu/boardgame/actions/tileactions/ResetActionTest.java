package edu.ntnu.boardgame.actions.tileactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Unit tests for ResetAction.
 */
public class ResetActionTest {

    private Board board;
    private Player player;

    /**
     * Sets up a 9x10 board and a player at tile 5 before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9, 10);
        Tile startTile = board.getTile(5);
        player = new Player("Abdi", startTile, 1);
        player.setPosition(5, board);
    }

    /**
     * Verifies that executing ResetAction sends the player back to tile 1.
     */
    @Test
    void testExecuteMovesPlayerToStart() {
        ResetAction action = new ResetAction();
        action.execute(player, board);

        assertEquals(1, player.getPosition(), "Player should have been reset to tile 1.");
    }

    /**
     * Verifies that Boardgame.checkForWin() is called if Boardgame is set.
     */
    @Test
    void testExecuteTriggersCheckForWinIfBoardgameSet() {
        ResetAction action = new ResetAction();
        Boardgame mockGame = mock(Boardgame.class);
        action.setBoardgame(mockGame);

        action.execute(player, board);

        verify(mockGame).checkForWin(player);
    }

    /**
     * Verifies that getDestination() always returns 1.
     */
    @Test
    void testGetDestinationAlwaysReturnsOne() {
        ResetAction action = new ResetAction();
        assertEquals(1, action.getDestination());
    }
}
