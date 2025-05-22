package edu.ntnu.boardgame.constructors;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;



/**
 * Unit tests for the {@link Dice} class. These tests verify correct dice
 * behavior including sum of rolls, randomness, constructor validation, and
 * getter methods.
 */
public class DiceTest {


    /**
     * Tests that the sum of the dice roll is within the expected range for two
     * six-sided dice (between 2 and 12).
     */
    @Test 
    void testRollReturnsValidSum(){
        Dice dice = new Dice(6,2);

        for(int i = 0; i<101; i++){
            int roll = dice.roll();
            assertTrue(roll >= 2 && roll <=12, "Roll sum should be between 2 and 12, but was: " + roll); 
        }
    }
    

    /**
     * Tests that rolling multiple times produces different values, confirming
     * that the Dice class has randomness.
     */
    @Test
    void testRollingMultipleDiceChangesSum() {
        Dice dice = new Dice(6, 2);
        boolean differentValues = false;
        int firstRoll = dice.roll();

        for (int i = 0; i < 100; i++) {
            if (dice.roll() != firstRoll) {
                differentValues = true;
                break;
            }
        }

        assertTrue(differentValues, "Rolling multiple times should give different sums.");
    }
    
    /**
     * Tests that creating a Dice object with invalid parameters (zero or
     * negative number of dice, or less than 2 sides) throws
     * IllegalArgumentException.
     */
    @Test
    void testInvalidDiceCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Dice(6, 0), "Bør kaste exception for 0 terninger.");
        assertThrows(IllegalArgumentException.class, () -> new Dice(6, -1), "Bør kaste exception for negativt antall terninger.");
        assertThrows(IllegalArgumentException.class, () -> new Dice(0, 2), "Bør kaste exception for terning med 0 sider.");
    }

    /**
     * Tests that the getter methods for number of sides and number of dice
     * return the correct values after initialization.
     */
    @Test
    void testGetters() {
        Dice dice = new Dice(6, 3);
        assertTrue(dice.getNumSides() == 6,
                "Should return correct number of sides.");
        assertTrue(dice.getNumberOfDice() == 3,
                "Should return correct number of dice.");
    }


    
}
