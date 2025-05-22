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
 * Unit tests for the {@link BackAction} class.
 */
public class BackActionTest {

    private Board board;
    private Player player;

    /**
     * Sets up a 9x10 board and a player starting at tile 1 before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9, 10);
        Tile startTile = board.getTile(1);
        player = new Player("Abdi", startTile, 1);
        player.setPosition(1, board);
    }

    /**
     * Tests that the player is moved to the correct destination when the action
     * is executed.
     */
    @Test
    void testExecuteMovesPlayerBackwards() {
        BackAction action = new BackAction(3);
        action.execute(player, board);

        assertEquals(3, player.getPosition(), "Player should be moved to tile 3.");
    }

    /**
     * Tests that Boardgame.checkForWin() is called if a boardgame is attached
     * to the action.
     */
    @Test
    void testExecuteTriggersCheckForWinIfBoardgameSet() {
        BackAction action = new BackAction(1);
        Boardgame mockGame = mock(Boardgame.class);
        action.setBoardgame(mockGame);

        action.execute(player, board);

        verify(mockGame).checkForWin(player);
    }

    /**
     * Tests that getDestination() returns the correct destination value.
     */
    @Test
    void testGetDestinationReturnsCorrectValue() {
        BackAction action = new BackAction(4);
        assertEquals(4, action.getDestination());
    }
}
