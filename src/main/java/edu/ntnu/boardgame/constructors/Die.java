package edu.ntnu.boardgame.constructors;

import java.util.Random;

/*
 * Class for representing a single die with a specified number of sides.
 * The die can be rolled to produce a random value between 1 and the number of sides.
 */
public class Die {
  
  /*Number of sides of a die */
  protected final int sides;
  
  /*The value of the die after the last roll.*/
  protected int lastRolledValue;

  /*The random number generator used for rolling the die. */
  private final Random random; 

  /**
    * Constructs a new Die with the given number of sides.
    *
    * @param sides the number of sides on the die
    * @throws IllegalArgumentException if the number of sides is less than 2
  */
  public Die(int sides) {
    if(sides<2 ){
      throw new IllegalArgumentException("En terning må ha minst 2 sider.");
    }
    this.sides = sides; 
    this.random = new Random(); 
  }

  /**
  * Returns the number of sides on the die.
  * @return the number of sides
  */
  public int getSides() {
    return sides;
  }

  /**
  * Rolls the die.
  * @return The value of the die after rolling.
  */
  public int roll() {
    lastRolledValue = random.nextInt(sides) + 1; 
    return lastRolledValue; 
  }

  /**
  * @return The value of the last rolled die.
  */
  public int getValue() {
    return lastRolledValue; 
  }
}
