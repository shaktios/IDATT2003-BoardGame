package edu.ntnu.boardgame.actions.tileactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Unit tests for TeleportRandomAction.
 */
public class TeleportRandomActionTest {

    private Board board;
    private Player player;

    /**
     * Sets up a 9x10 board and a player starting at tile 1.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9, 10); // 90 tiles
        Tile startTile = board.getTile(1);
        player = new Player("Abdi", startTile, 1);
        player.setPosition(1, board);
    }

    /**
     * Verifies that the action teleports the player to a valid tile on the
     * board.
     */
    @Test
    void testExecuteTeleportsPlayerWithinValidRange() {
        TeleportRandomAction action = new TeleportRandomAction();
        action.execute(player, board);

        int position = player.getPosition();
        assertTrue(position >= 1 && position <= board.getSize(),
                "Player should be moved to a tile between 1 and " + board.getSize());
    }

    /**
     * Verifies that checkForWin is called when Boardgame is set.
     */
    @Test
    void testExecuteTriggersCheckForWinIfBoardgameSet() {
        TeleportRandomAction action = new TeleportRandomAction();
        Boardgame mockGame = mock(Boardgame.class);
        action.setBoardgame(mockGame);

        action.execute(player, board);

        verify(mockGame).checkForWin(player);
    }

    /**
     * Verifies that getDestination returns -1, as the action has no fixed
     * target.
     */
    @Test
    void testGetDestinationReturnsMinusOne() {
        TeleportRandomAction action = new TeleportRandomAction();
        assertEquals(-1, action.getDestination());
    }
}
