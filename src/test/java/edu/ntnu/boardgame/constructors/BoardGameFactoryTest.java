package edu.ntnu.boardgame.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.actions.puzzleactions.ChessPuzzleAction;
import edu.ntnu.boardgame.actions.tileactions.BackAction;
import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.actions.tileactions.ResetAction;
import edu.ntnu.boardgame.actions.tileactions.SkipTurnAction;
import edu.ntnu.boardgame.actions.tileactions.TeleportRandomAction;

/**
 * Unit tests for the {@link BoardGameFactory} class. Verifies correct creation
 * of board games and expected tile actions.
 */
public class BoardGameFactoryTest {

    /**
     * Tests that createClassicGame() returns a valid Boardgame with expected
     * dice configuration and no null reference.
     */
    @Test
    void testCreateClassicGame() {
        Boardgame game = BoardGameFactory.createClassicGame();
        assertNotNull(game, "Factory should return a valid Boardgame object");

        assertEquals(6, game.getDice().getNumSides(), "Die should have 6 sides");
        assertEquals(2, game.getDice().getNumberOfDice(), "There should be 2 dice in play");
    }

    /**
     * Tests that createClassicGame() sets expected actions on specific tiles.
     */
    @Test
    void testClassicGameActionsSet() {
        Boardgame game = BoardGameFactory.createClassicGame();

        assertTrue(game.getBoard().getTile(3).getAction() instanceof LadderAction, "Tile 3 should have LadderAction");
        assertTrue(game.getBoard().getTile(62).getAction() instanceof BackAction, "Tile 62 should have BackAction");
        assertTrue(game.getBoard().getTile(43).getAction() instanceof ResetAction, "Tile 43 should have ResetAction");
        assertTrue(game.getBoard().getTile(13).getAction() instanceof SkipTurnAction, "Tile 13 should have SkipTurnAction");
        assertTrue(game.getBoard().getTile(6).getAction() instanceof TeleportRandomAction, "Tile 6 should have TeleportRandomAction");
        assertTrue(game.getBoard().getTile(47).getAction() instanceof ChessPuzzleAction, "Tile 47 should have ChessPuzzleAction");
    }

    /**
     * Tests that createMiniGame() returns a valid Boardgame with expected dice
     * configuration.
     */
    @Test
    void testCreateMiniGame() {
        Boardgame game = BoardGameFactory.createMiniGame();
        assertNotNull(game, "Mini game should not be null");

        assertEquals(6, game.getDice().getNumSides(), "Die should have 6 sides");
        assertEquals(1, game.getDice().getNumberOfDice(), "There should be 1 die in play");
    }

    /**
     * Tests that createMiniGame() sets expected actions on specific tiles.
     */
    @Test
    void testMiniGameActionsSet() {
        Boardgame game = BoardGameFactory.createMiniGame();

        assertTrue(game.getBoard().getTile(3).getAction() instanceof LadderAction);
        assertTrue(game.getBoard().getTile(17).getAction() instanceof BackAction);
        assertTrue(game.getBoard().getTile(21).getAction() instanceof ResetAction);
        assertTrue(game.getBoard().getTile(7).getAction() instanceof SkipTurnAction);
        assertTrue(game.getBoard().getTile(10).getAction() instanceof TeleportRandomAction);
        assertTrue(game.getBoard().getTile(5).getAction() instanceof ChessPuzzleAction);
    }
}
