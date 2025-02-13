package edu.ntnu.boardgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class DieTest {

    @Test 
    void testRollReturnsWithinRange(){
        Die die = new Die(6);
        for (int i = 0; i < 100; i++) { // Tester flere kast for å sjekke om det holder seg innenfor range
            int roll = die.roll();
            assertTrue(roll >= 1 && roll <= 6, "Roll should be between 1 and 6, but was: " + roll);
        }
    
    }

    @Test
    void getValueAfterRoll(){
        Die die = new Die(6); 
        int roll = die.roll(); 
        assertEquals(roll, die.getValue(), "getValue() should return the last rolled value."); 
    }

    @Test
    void testDifferentRollsOverTime() {
        Die die = new Die(6);
        int firstRoll = die.roll();
        int differentRollCount = 0;

        for (int i = 0; i < 100; i++) { // 
            if (die.roll() != firstRoll) {
                differentRollCount++;
            }
        }

        assertTrue(differentRollCount > 0, "Die should roll different values over multiple rolls.");
    }

    
}
