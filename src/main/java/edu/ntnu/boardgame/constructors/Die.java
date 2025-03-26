package edu.ntnu.boardgame.constructors;

import java.util.Random;

/*
 * A class representing a die.
 * 
 */
public class Die {
  protected final int sides;
  protected int lastRolledValue; 
  private final Random random; 

  /*
   * Constructor for the Die class.
   * @param sides The number of sides on the die.
   * @throws IllegalArgumentException if the number of sides is less than 2.
   */
  public Die(int sides) {
    if(sides<2 ){
      throw new IllegalArgumentException("En terning må ha minst 2 sider.");
    }
    this.sides = sides; 
    this.random = new Random(); 
  }

  public int getSides() {
    return sides;
  }

  /*
  * Rolls the die.
  * @return The value of the die after rolling.
  */
  public int roll() {
    lastRolledValue = random.nextInt(sides) + 1; 
    return lastRolledValue; 
  }

  /*
  * @return The value of the last rolled die.
  */
  public int getValue() {
    return lastRolledValue; 
  }
}
