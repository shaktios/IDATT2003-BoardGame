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
 * Unit tests for {@link LadderAction}. Verifies that the player is moved to the
 * correct tile, that checkForWin is triggered if a Boardgame is set, and that
 * getDestination returns the correct value.
 */
public class LadderActionTest {

    private Board board;
    private Player player;

    /**
     * Sets up a  9x10 board and places a player at the first tile.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9,10);
        Tile startTile = board.getTile(1);
        player = new Player("Abdi",startTile,1);
        player.setPosition(1, board);
    }

    /**
     * Verifies that executing the ladder action moves the player to the defined
     * destination tile.
     */
    @Test
    void testExecuteMovesPlayerToDestination() {
        LadderAction action = new LadderAction(5);
        action.execute(player, board);

        assertEquals(5, player.getPosition(), "Spilleren burde ha blitt flyttet til posisjon 5.");
    }

    /**
     * Verifies that {@code Boardgame.checkForWin()} is called if a Boardgame
     * object is attached to the action.
     */
    @Test
    void testExecuteCallsCheckForWinIfBoardgameIsSet() {
        LadderAction action = new LadderAction(10);
        Boardgame mockGame = mock(Boardgame.class);
        action.setBoardgame(mockGame);

        action.execute(player, board);

        verify(mockGame).checkForWin(player);
    }

    /**
     * Verifies that the action reports the correct destination.
     */
    @Test
    void testGetDestinationReturnsCorrectValue() {
        LadderAction action = new LadderAction(7);
        assertEquals(7, action.getDestination(), "getDestination() burde returnere 7");
    }
}
