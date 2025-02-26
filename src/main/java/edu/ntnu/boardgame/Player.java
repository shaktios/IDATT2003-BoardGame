package edu.ntnu.boardgame;

/* Representerer en spiller i spillet. En spiller har et navn, og «bor» til enhver tid på et felt. En spiller
kan bli plassert på et felt og kan også bevege seg et antall steg på spillbrettet. Når spilleren når
siste felt eller passerer siste felt, har spilleren nådd slutten av spillet (mål).
 */

public class Player {
  private String name; 
  private Tile currentTile; 

  /*
    * Constructor for the Player class.
    * @param name The name of the player.
    * @param startTile The tile the player starts on.
    * @throws IllegalArgumentException if the name is null or empty, or if the startTile is null.
    * 
  */
  public Player(String name, Tile startTile) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Spillernavnet kan ikke være tomt.");
    }
    if (startTile == null) { 
      throw new IllegalArgumentException("Startfelt kan ikke være null.");
    }
        
    this.name = name; 
    this.currentTile = startTile;
  }

  /*
    * @return The name of the player. 
  */
  public String getName() {
    return name;
  }
  
  /*
    * @return The current tile the player is on.
  */
  public Tile getCurrentTile() {
    return currentTile;
  } 

  /*
    * Moves the player a given number of steps on the board.
    * @param steps The number of steps to move the player.
    * @param board The board the player is moving on.
    * @throws IllegalArgumentException if the number of steps is less than 1.
    * 
  */
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
