package edu.ntnu.boardgame;

/* Representerer en spiller i spillet. En spiller har et navn, og «bor» til enhver tid på et felt. En spiller
kan bli plassert på et felt og kan også bevege seg et antall steg på spillbrettet. Når spilleren når
siste felt eller passerer siste felt, har spilleren nådd slutten av spillet (mål).
 */

public class Player {
    private String name; 
    //private Tile currentTile ; // implementeres senere 

    public Player(String name /* , Tile startTile)*/){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Spillernavnet kan ikke være tomt.");
        }
        this.name = name; 
        //this.currentTile = startTile;

    }

    public String getName(){
        return name; 
    }
    
   /*  public Tile getCurrentTile(){
        return currentTile; 
    } */

    public void move(int steps){
        // todo!!:  implementer hvordan spilleren skal bevege seg på brettet. 
    }
    
}
