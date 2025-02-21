package edu.ntnu.boardgame;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    
    private Board board;
    private Player player;

    @BeforeEach
    void setUp() {
        board = new Board(10); // Brett med 10 felt
        player = new Player("Abdi", board.getTile(1)); // Starter på felt 1
    }

    @Test
    void testPlayerInitialization() {
        assertEquals("Abdi", player.getName(), "Navnet på spilleren er feil.");
        assertEquals(1, player.getCurrentTile().getPosition(), "Startfeltet er feil.");
    }

    @Test
    void testInvalidPlayerCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Player("", board.getTile(1)),
                "Bør kaste exception for tomt navn.");
        assertThrows(IllegalArgumentException.class, () -> new Player(null, board.getTile(1)),
                "Bør kaste exception for null navn.");
        assertThrows(IllegalArgumentException.class, () -> new Player("Abdi", null),
                "Bør kaste exception for null startfelt.");
    }

    @Test
    void testMoveValidSteps() {
        player.move(3, board);
        assertEquals(4, player.getCurrentTile().getPosition(), "Spilleren burde være på felt 4.");
    }

    @Test
    void testMoveBeyondBoardSize() {
        player.move(10, board); // Brettet har 10 felt, så spilleren stopper der
        assertEquals(10, player.getCurrentTile().getPosition(), "Spilleren burde være på siste felt.");
    }

    @Test
    void testMoveNegativeStepsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.move(-2, board),
                "Bør kaste exception for negativt antall steg.");
    }
}
