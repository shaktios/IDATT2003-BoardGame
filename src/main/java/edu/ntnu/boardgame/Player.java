package edu.ntnu.boardgame;

/* Representerer en spiller i spillet. En spiller har et navn, og «bor» til enhver tid på et felt. En spiller
kan bli plassert på et felt og kan også bevege seg et antall steg på spillbrettet. Når spilleren når
siste felt eller passerer siste felt, har spilleren nådd slutten av spillet (mål).
 */

public class Player {
    private String name; 
    private Tile currentTile; 

    public Player(String name, Tile startTile){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Spillernavnet kan ikke være tomt.");
        }
        if (startTile == null) { 
            throw new IllegalArgumentException("Startfelt kan ikke være null.");
        }
        
        this.name = name; 
        this.currentTile = startTile;

    }

    public String getName() {
        return name;
    }
    
    public Tile getCurrentTile(){
        return currentTile;
    } 

    public void move(int steps, Board board){
        if (steps < 1) {
            throw new IllegalArgumentException("Spilleren må flytte seg minst ett steg.");
    }
        int newPosition = currentTile.getPosition() + steps;
        if (newPosition > board.getSize()) {
            System.out.println(name + " har nådd eller passert sluttmålet.");
            currentTile = board.getTile(board.getSize()); // Sett spilleren på siste felt
        } else {
            currentTile = board.getTile(newPosition);
            System.out.println(name + " flyttet til felt " + newPosition);
        }
    }

}
