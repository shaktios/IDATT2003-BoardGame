package edu.ntnu.boardgame;

public class Dice extends Die {
    private int numberOfDice; 

    public Dice(int sides, int numberOfDice){
        super(sides);
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
