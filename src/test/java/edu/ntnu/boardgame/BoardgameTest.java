package edu.ntnu.boardgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BoardgameTest {

    private Board board;
    private Boardgame boardGame;

    @BeforeEach
    void setUp() {
        board = new Board(10); // Anta at Board har en konstruktør med størrelse
        boardGame = new Boardgame(board, 2, 6);
    }

    // Tester at konstruktøren kaster unntak på ugyldige inputverdier
    @Test
    void testConstructorWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(null, 2, 6), "Board kan ikke være null");
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(board, 0, 6), "Antall terninger må være minst 1");
        assertThrows(IllegalArgumentException.class, () -> new Boardgame(board, 2, 1), "En terning må ha minst 2 sider");
    }

    // Tester at man kan legge en spiller til
    @Test
    void testAddPlayerValid() {
        boardGame.addPlayer("Abdi");
        List<Player> players = boardGame.getPlayers();
        assertEquals(1, players.size(), "Antall spillere burde være 1");
        assertEquals("Abdi", players.get(0).getName(), "Navnet på spilleren burde være Abdi");
    }

    // Tester at ugyldige navn kaster unntak
    @Test
    void testAddPlayerInvalid() {
        assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer(null), "Navnet kan ikke være null");
        assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer(""), "Navnet kan ikke være tomt");
    }

    // Tester at terningen blir riktig satt opp
    @Test
    void testGetDice() {
        Dice dice = boardGame.getDice();
        assertNotNull(dice, "Dice-objektet burde ikke være null");

        int rollResult = dice.roll();
        assertTrue(rollResult >= 2 && rollResult <= 12, "Summen av to seks-sidede terninger burde være mellom 2 og 12");
    }


    
}
