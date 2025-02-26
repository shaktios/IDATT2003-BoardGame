package edu.ntnu.boardgame;

/*
 * A class representing a set of dice.
 * The class extends the Die class.
 * The class has a constructor that takes the number of dice as an argument.
 * The class has a roll method that rolls all the dice and returns the sum of the dice rolls.
 * 
 */
public class Dice extends Die {
  private final int numberOfDice; 


  public Dice(int sides, int numberOfDice){
    super(sides);
    if (numberOfDice < 1) {
      throw new IllegalArgumentException("Det må være minst én terning.");
    }
    this.numberOfDice = numberOfDice; 
  }

  @Override
  public int roll(){
    int sum = 0; 
    for(int i = 0; i<numberOfDice; i++){
      sum = sum + super.roll();
    }
    return sum; 
  }
    

}
