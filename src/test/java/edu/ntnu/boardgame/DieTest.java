package edu.ntnu.boardgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.constructors.Die;



/**
 * Unit tests for the {@link Die} class. Verifies correct die behavior including
 * value ranges, randomness, input validation, and access to die properties.
 */
public class DieTest {

    /**
     * Tests that rolling a six-sided die always returns a value between 1 and
     * 6.
     */
    @Test 
    void testRollReturnsWithinRange(){
        Die die = new Die(6);
        for (int i = 0; i < 100; i++) { // Tester flere kast for å sjekke om det holder seg innenfor range
            int roll = die.roll();
            assertTrue(roll >= 1 && roll <= 6, "Roll should be between 1 and 6, but was: " + roll);
        }
    
    }

    /**
     * Tests that getValue() correctly returns the result of the last roll.
     */
    @Test
    void getValueAfterRoll(){
        Die die = new Die(6); 
        int roll = die.roll(); 
        assertEquals(roll, die.getValue(), "getValue() should return the last rolled value."); 
    }


    /**
     * Tests that multiple rolls result in variation over time, indicating
     * randomness.
     */
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

    
    /**
     * Tests that creating a Die with fewer than 2 sides throws an exception.
     */
    @Test
    void testInvalidDieCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Die(0), "Bør kaste exception for terning med 0 sider.");
        assertThrows(IllegalArgumentException.class, () -> new Die(-3), "Bør kaste exception for negativt antall sider.");
    }
    

    /**
     * Tests that the getSides() method returns the correct number of sides.
     */
    @Test
    void testValidDieCreation() {
        Die die = new Die(6);
        int side = die.getSides();
        assertEquals(6, side, "Feil antall sider på terningen.");
    }

    
}
