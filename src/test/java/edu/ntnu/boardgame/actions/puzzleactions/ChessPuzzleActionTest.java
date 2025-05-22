package edu.ntnu.boardgame.actions.puzzleactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.controllers.ChessPuzzleViewController;

/**
 * Unit tests for {@link ChessPuzzleAction}.
 */
public class ChessPuzzleActionTest {

    private Board board;
    private Player player;
    private Boardgame boardgame;

    /**
     * Sets up a board and a player before each test.
     */
    @BeforeEach
    void setUp() {
        board = new Board(9, 10);
        player = new Player("Abdi", board.getTile(1), 20);
        boardgame = mock(Boardgame.class);
    }

    /**
     * Verifies that ChessPuzzleViewController.startPuzzle() is called when the
     * action is executed.
     */
    @Test
    void testExecuteStartsPuzzle() {
        try (MockedConstruction<ChessPuzzleViewController> mocked = mockConstruction(ChessPuzzleViewController.class)) {
            ChessPuzzleAction action = new ChessPuzzleAction(boardgame);
            action.execute(player, board);

            ChessPuzzleViewController controller = mocked.constructed().get(0);
            verify(controller).startPuzzle();
        }
    }

    /**
     * Verifies that Boardgame.checkForWin() is called after the puzzle callback
     * is executed.
     */
    @Test
    void testCallbackTriggersCheckForWin() {
        try (MockedConstruction<ChessPuzzleViewController> mocked = mockConstruction(ChessPuzzleViewController.class,
                (mock, context) -> {
                    // Simulate puzzle being solved immediately
                    Runnable callback = (Runnable) context.arguments().get(2);
                    callback.run();
                })) {

            ChessPuzzleAction action = new ChessPuzzleAction(boardgame);
            action.execute(player, board, () -> {
            });

            verify(boardgame).checkForWin(player);
        }
    }

    /**
     * Verifies that getDestination() returns -1.
     */
    @Test
    void testGetDestinationReturnsMinusOne() {
        ChessPuzzleAction action = new ChessPuzzleAction(boardgame);
        assertEquals(-1, action.getDestination());
    }
}
