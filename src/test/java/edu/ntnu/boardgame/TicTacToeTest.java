package edu.ntnu.boardgame;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.TicTacToe;
import edu.ntnu.boardgame.constructors.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    private TicTacToe game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        Tile dummyTile = new Tile(1); // a simple tile with position 1
        player1 = new Player("Alice", dummyTile, 25);
        player2 = new Player("Bob", dummyTile, 30);
        game = new TicTacToe(player1, player2);
    }

    @Test
    void testMakeMoveValid() {
        assertTrue(game.makeMove(0, 0));
        assertEquals("X", game.getCell(0, 0));
    }

    @Test
    void testMakeMoveOccupiedCell() {
        game.makeMove(0, 0);
        assertFalse(game.makeMove(0, 0)); // already taken
    }

    @Test
    void testMakeMoveInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(3, 1));
    }

    @Test
    void testSwitchTurn() {
        assertEquals(player1, game.getCurrentPlayer());
        game.switchTurn();
        assertEquals(player2, game.getCurrentPlayer());
    }

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

    @Test
    void testNullPlayersThrows() {
        Tile tile = new Tile(1);
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(null, new Player("Test", tile, 20)));
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(new Player("Test", tile, 20), null));
    }
}
