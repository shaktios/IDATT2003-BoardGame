package edu.ntnu.boardgame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.boardgame.constructors.Dice;
import org.junit.jupiter.api.Test;



public class DiceTest {

    @Test 
    void testRollReturnsValidSum(){
        Dice dice = new Dice(6,2);

        for(int i = 0; i<101; i++){
            int roll = dice.roll();
            assertTrue(roll >= 2 && roll <=12, "Roll sum should be between 2 and 12, but was: " + roll); 
        }
    }
    
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
    
    @Test
    void testInvalidDiceCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Dice(6, 0), "Bør kaste exception for 0 terninger.");
        assertThrows(IllegalArgumentException.class, () -> new Dice(6, -1), "Bør kaste exception for negativt antall terninger.");
        assertThrows(IllegalArgumentException.class, () -> new Dice(0, 2), "Bør kaste exception for terning med 0 sider.");
    }


    
}
