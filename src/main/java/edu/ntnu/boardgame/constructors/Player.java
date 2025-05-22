package edu.ntnu.boardgame.constructors;

public class Player {
  private String name; 
  private Tile currentTile;
  private String token;
  private boolean skipNextTurn = false; 
  private int age ; 


  /**
    * Constructor for the Player class.
    *
    * @param name The name of the player.
    * @param startTile The tile the player starts on
    * @param age The players age
    * @throws IllegalArgumentException if the name is null or empty, if the startTile is null or the age is negative
    * 
  */
  public Player(String name, Tile startTile, int age) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Spillernavnet kan ikke være tomt.");
    }
    if (startTile == null) { 
      throw new IllegalArgumentException("Startfelt kan ikke være tomt/Null(null ≠ tallverdien).");
    }
    if (age < 0 ){
      throw new IllegalArgumentException("Alderen kan ikke være tom eller 0"); 
    }
        
    this.name = name; 
    this.currentTile = startTile;
    this.token = token;
    this.age = age; 
  }

  /**
  * Returns the player's name.
  *
  * @return The name of the player. 
  */
  public String getName() {
    return name;
  }
  
  /**
  * Returns the current tile the player is standing on.
  * @return The current tile 
  */
  public Tile getCurrentTile() {
    return currentTile;
  } 

   

  /**
  * Sets the age of the player.
  *
  * @param age the age to assign
  */
  public void setAge(int age) {
      this.age = age;
  }

  /**
  * Returns the age of the player.
  *
  * @return the player's age
  */
  public int getAge() {
      return age;
  }


  /**
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
     * Checks whether the player is supposed to skip their next turn.
     * @return true if the player have to stand over his turn. 
     */
    public boolean shouldSkipTurn() {
        return skipNextTurn;
    }


    /**
     * Sets the player's name.
     *
     * @param name the new name to assign
     */
  public void setName(String name) {
    this.name = name;
  }


}
