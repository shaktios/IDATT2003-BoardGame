package edu.ntnu.boardgame;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.observer.TestObserver;

public class BoardGameObserverTest {

        @Test
        void testObserverReceivesCallbacks() {
            // Setter opp spillet
            Board board = new Board(10, 10);
            Boardgame game = new Boardgame(board, 1, 6);
            game.addPlayer("Abdi", 23);

            // Registrerer observeren
            TestObserver observer = new TestObserver();
            game.registerObserver(observer);

            // 3. Kjører én spillrunde
            game.playGame();

            // 4. Sjekker at observer ble kalt
            assertTrue(observer.playerMovedCalled, "Observer skal få beskjed når en spiller flytter");
            assertTrue(observer.gameWonCalled, "Observer skal få beskjed når noen vinner");
        }


    
}
