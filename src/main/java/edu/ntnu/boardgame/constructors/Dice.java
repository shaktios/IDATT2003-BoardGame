package edu.ntnu.boardgame.constructors;



/**
  * A class representing a set of dice.
  * The class extends the Die class.
  * The class has a constructor that takes the number of dice as an argument.
  * The class has a roll method that rolls all the dice and returns the sum of the dice rolls.
  *
  */
public class Dice extends Die {
  private final int numberOfDice; 

  /**
    * Constructor for the Dice class.
    *
    * @param sides The number of sides on the die.
    * @param numberOfDice The number of dice in the set.
    * @throws IllegalArgumentException if the number of sides is
    * less than 2 or the number of dice is less than 1.
    */
  public Dice(int sides, int numberOfDice) {
    super(sides);
    if (numberOfDice < 1) {
      throw new IllegalArgumentException("Det må være minst én terning.");
    }
    this.numberOfDice = numberOfDice; 
  }

  /**
    * Rolls all the dice in the set.
    *
    * @return The sum of the dice rolls.
    */
  @Override
  public int roll() {
    int sum = 0; 
    for (int i = 0; i < numberOfDice; i++) {
      sum = sum + super.roll();
    }
    return sum; 
  }

  public int getNumSides() {
    return sides;
  }

  public int getNumberOfDice() {
    return numberOfDice;
  }

}
