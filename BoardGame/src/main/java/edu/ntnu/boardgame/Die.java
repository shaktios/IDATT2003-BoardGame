package edu.ntnu.boardgame;

import java.util.Random;


public class Die {
    protected int sides; 
    protected int lastRolledValue; 
    private final Random random; 

    public Die(int sides){
        this.sides = sides; 
        this.random = new Random(); 
    }

    public int roll(){
        lastRolledValue = random.nextInt(sides) + 1; 
        return lastRolledValue; 
    }
    
    public int getValue(){
        return lastRolledValue; 
    }

}
