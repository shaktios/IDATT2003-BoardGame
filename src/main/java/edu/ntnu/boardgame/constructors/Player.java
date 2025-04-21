package edu.ntnu.boardgame.constructors;

/* Representerer en spiller i spillet. En spiller har et navn, og «bor» til enhver tid på et felt. En spiller
kan bli plassert på et felt og kan også bevege seg et antall steg på spillbrettet. Når spilleren når
siste felt eller passerer siste felt, har spilleren nådd slutten av spillet (mål).
 */

import edu.ntnu.boardgame.Board;

public class Player {
  private String name; 
  private Tile currentTile;
  private String token;
  private boolean skipNextTurn = false; //for skipaction-actionen


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
      throw new IllegalArgumentException("Startfelt kan ikke være tomt/Null(null ≠ tallverdien).");
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


    /**
     * Returns the token (piece) representing the player on the board.
     *
     * @return the player's token as a String
     */

  public String getToken() {
    return token;
  }


    /**
     * Sets the player's token.
     *
     * @param token the new token to assign to the player
     * @throws IllegalArgumentException if the token is null or blank
     */
  public void setToken(String token) {
    if(token == null || token.trim().isEmpty()) {
      throw new IllegalArgumentException("Token/brikke kan ikke være tom");
    }
    this.token = token;
  }



    /**
     * Returns the current position of the player based on their tile.
     *
     * @return the position number of the tile the player is on
     */
    public int getPosition() {
        return currentTile.getPosition();
    }



    /**
     * Sets the player's position to a specific tile on the board.
     * @param position the new target position to place the player on (must be
     * within board limits)
     * @param board the game board used to retrieve the tile corresponding to
     * the position
     * @throws IllegalArgumentException if the position is outside the board
     * range
     */

    public void setPosition(int position, Board board){

      if(position<1 || position > board.getSize()){
          throw new IllegalArgumentException("Ugyldig posisjon: " + position);
      }

      this.currentTile = board.getTile(position);
    }


    /**
     * Sets the current tile of the player directly.
     *
     * @param tile the tile to set as the player's current tile
     * @throws IllegalArgumentException if the tile is null
     */
    public void setCurrentTile(Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("Tile kan ikke være null.");
        }
        this.currentTile = tile;
    }
    

    /**
     * Setter for if the player have to skip the next round 
     *
     * @param skip true if the player is supposed to skip the next round
     */
    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }


    /**
     * Returns true if the player have to stand over his turn. 
     */
    public boolean shouldSkipTurn() {
        return skipNextTurn;
    }


}
