package edu.ntnu.boardgame.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicTacToeTest {

    private Player p1;
    private Player p2;
    private TicTacToe game;

    @BeforeEach
    void setup() {
        Tile startTile = new Tile(1); // dummy tile
        p1 = new Player("A", startTile, 25);
        p2 = new Player("B", startTile, 30);
        p1.setToken("X");
        p2.setToken("O");
        game = new TicTacToe(p1, p2);
    }

    /**
     * Tests that constructor throws exception if any player is null
     */
    @Test
    void nullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(null, p2));
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(p1, null));
    }

    /**
     * Tests a valid move is accepted and token placed
     */
    @Test
    void validMove() {
        assertTrue(game.makeMove(0, 0));
        assertEquals("X", game.getCell(0, 0));
    }

    /**
     * Tests that a move to an already occupied cell is rejected
     */
    @Test
    void occupiedMove() {
        game.makeMove(0, 0);
        assertFalse(game.makeMove(0, 0));
    }

    /**
     * Tests move with invalid coordinates throws exception
     */
    @Test
    void invalidCoords() {
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(3, 3));
    }

    /**
     * Tests that turn switches between players
     */
    @Test
    void switchTurn() {
        Player first = game.getCurrentPlayer();
        game.switchTurn();
        Player second = game.getCurrentPlayer();
        assertNotEquals(first, second);
        game.switchTurn();
        assertEquals(first, game.getCurrentPlayer());
    }

    /**
     * Tests horizontal win condition
     */
    @Test
    void winRow() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(1, 0); // O
        game.switchTurn();
        game.makeMove(0, 1); // X
        game.switchTurn();
        game.makeMove(1, 1); // O
        game.switchTurn();
        game.makeMove(0, 2); // X
        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests vertical win condition
     */
    @Test
    void winCol() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(0, 1); // O
        game.switchTurn();
        game.makeMove(1, 0); // X
        game.switchTurn();
        game.makeMove(1, 1); // O
        game.switchTurn();
        game.makeMove(2, 0); // X
        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests diagonal win condition
     */
    @Test
    void winDiag() {
        game.makeMove(0, 0); // X
        game.switchTurn();
        game.makeMove(0, 1); // O
        game.switchTurn();
        game.makeMove(1, 1); // X
        game.switchTurn();
        game.makeMove(2, 1); // O
        game.switchTurn();
        game.makeMove(2, 2); // X
        assertEquals("X", game.checkWinner());
    }

    /**
     * Tests no winner when board is still incomplete
     */
    @Test
    void noWinner() {
        game.makeMove(0, 0);
        assertNull(game.checkWinner());
    }

    /**
     * Tests full board detection
     */
    @Test
    void boardFull() {
        int[][] moves = {
            {0, 0}, {0, 1}, {0, 2},
            {1, 1}, {1, 0}, {1, 2},
            {2, 1}, {2, 2}, {2, 0}
        };
        for (int i = 0; i < moves.length; i++) {
            game.makeMove(moves[i][0], moves[i][1]);
            if (i < moves.length - 1) {
                game.switchTurn();
            }
        }
        assertTrue(game.isBoardFull());
    }

    /**
     * Tests board is not full initially
     */
    @Test
    void boardNotFull() {
        game.makeMove(0, 0);
        assertFalse(game.isBoardFull());
    }

    /**
     * Tests board reset clears all tiles and resets turn
     */
    @Test
    void reset() {
        game.makeMove(0, 0);
        game.switchTurn();
        game.resetBoard();
        assertNull(game.getCell(0, 0));
        assertEquals(p1, game.getCurrentPlayer());
    }

    /**
     * Tests getBoard returns a valid board
     */
    @Test
    void getBoard() {
        Board b = game.getBoard();
        assertNotNull(b);
        assertEquals(9, b.getSize());
    }
}
