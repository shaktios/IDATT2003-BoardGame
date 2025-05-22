package edu.ntnu.boardgame.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link TicTacToe} class. Tests game mechanics including
 * making moves, switching turns, winning, full board detection, board reset,
 * and constructor validation.
 */
class TicTacToeTest {

    private TicTacToe game;
    private Player player1;
    private Player player2;

    /**
     * Initializes a new TicTacToe game before each test.
     */
    @BeforeEach
    void setUp() {
        Tile dummyTile = new Tile(1); // a simple tile with position 1
        player1 = new Player("Alice", dummyTile, 25);
        player2 = new Player("Bob", dummyTile, 30);
        game = new TicTacToe(player1, player2);
    }

    /**
     * Tests that a valid move can be made and the correct token is placed.
     */
    @Test
    void testMakeMoveValid() {
        assertTrue(game.makeMove(0, 0));
        assertEquals("X", game.getCell(0, 0));
    }

    /**
     * Tests that a move cannot be made in an already occupied cell.
     */
    @Test
    void testMakeMoveOccupiedCell() {
        game.makeMove(0, 0);
        assertFalse(game.makeMove(0, 0)); // already taken
    }


    /**
     * Tests that making a move with invalid coordinates throws an exception.
     */
    @Test
    void testMakeMoveInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(3, 1));
    }

    /**
     * Tests that the current player changes after switching turns.
     */
    @Test
    void testSwitchTurn() {
        assertEquals(player1, game.getCurrentPlayer());
        game.switchTurn();
        assertEquals(player2, game.getCurrentPlayer());
    }



    /**
     * Tests that a win is detected correctly in a horizontal row.
     */
    @Test
    void testWinInRow() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(1, 0); // O
        game.switchTurn();
        game.makeMove(0, 1); // X
        game.switchTurn();
        game.makeMove(1, 1); // O
        game.switchTurn();
        game.makeMove(0, 2); // X wins

        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests that a win is detected correctly in a vertical column.
     */
    @Test
    void testWinInColumn() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(0, 1); // O
        game.switchTurn();
        game.makeMove(1, 0); // X
        game.switchTurn();
        game.makeMove(1, 1); // O
        game.switchTurn();
        game.makeMove(2, 0); // X wins

        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests that a win is detected correctly on the diagonal.
     */
    @Test
    void testWinInDiagonal() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(0, 1); // O
        game.switchTurn();
        game.makeMove(1, 1); // X
        game.switchTurn();
        game.makeMove(0, 2); // O
        game.switchTurn();
        game.makeMove(2, 2); // X wins

        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests that the board is detected as full and no winner is present (draw).
     */
    @Test
    void testBoardFullNoWinner() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(0, 1); // O
        game.switchTurn();
        game.makeMove(0, 2); // X
        game.switchTurn();
        game.makeMove(1, 1); // O
        game.switchTurn();
        game.makeMove(1, 0); // X
        game.switchTurn();
        game.makeMove(1, 2); // O
        game.switchTurn();
        game.makeMove(2, 1); // X
        game.switchTurn();
        game.makeMove(2, 0); // O
        game.switchTurn();
        game.makeMove(2, 2); // X

        assertTrue(game.isBoardFull());
        assertNull(game.checkWinner());
    }


    /**
     * Tests that the board can be reset and the current player is set back to
     * player1.
     */
    @Test
    void testResetBoard() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.resetBoard();

        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                assertNull(game.getCell(r, c));

        assertEquals(player1, game.getCurrentPlayer());
    }

    /**
     * Tests that the constructor throws an exception if any player is null.
     */
    @Test
    void testNullPlayersThrows() {
        Tile tile = new Tile(1);
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(null, new Player("Test", tile, 20)));
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(new Player("Test", tile, 20), null));
    }
}
