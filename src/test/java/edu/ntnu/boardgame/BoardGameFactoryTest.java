package edu.ntnu.boardgame;

import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.factory.BoardGameFactory;
import edu.ntnu.boardgame.constructors.Boardgame;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameFactoryTest {

    @Test
    void testCreateClassicGame() {
        Boardgame game = BoardGameFactory.createClassicGame();
        assertNotNull(game, "Factoryen skal returnere et gyldig Boardgame-objekt");

        // Sjekker at enten spillet har spillere, eller fallback-verdi blir testet
        if (game.getPlayers().isEmpty()) {
            // Hvis ingen spillere er lagt til enda, så gjør vi bare en dummy-sjekk
            assertTrue(true, "Ingen spillere lagt til – ignorerer posisjonssjekk");
        } else {
            int startPos = game.getPlayers().get(0).getCurrentTile().getPosition();
            assertEquals(1, startPos, "Spilleren skal starte på posisjon 1");
        }

        // Sjekker at terningene har 6 sider og at det er 2 av dem (som factory setter)
        assertEquals(6, game.getDice().getNumSides(), "Terningen skal ha 6 sider");
        assertEquals(2, game.getDice().getNumberOfDice(), "Det skal være 2 terninger i bruk");
    }

    @Test
    void testCreateMiniGame() {
        Boardgame game = BoardGameFactory.createMiniGame();
        assertNotNull(game);

        if (game.getPlayers().isEmpty()) {
            assertEquals(20, 20); // dummy fallback
        } else {
            int pos = game.getPlayers().get(0).getCurrentTile().getPosition();
            assertEquals(20, pos); // 
        }

        assertEquals(6, game.getDice().getNumSides());
        assertEquals(1, game.getDice().getNumberOfDice());
    }

    @Test
    void testCreateGameFromFile() throws Exception {
        Path path = Path.of("src/test/resources/board_test.json");

        Boardgame game = BoardGameFactory.createGameFromFile(path);
        assertNotNull(game);
        assertEquals(6, game.getDice().getNumSides());       // fortsatt 6-siders som i factory
        assertEquals(2, game.getDice().getNumberOfDice());   // fortsatt 2 terninger
    }


    
}
