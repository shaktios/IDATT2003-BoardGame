package edu.ntnu.boardgame;


import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;

/*
    * A class representing a board game.
    * The class has a constructor that takes a board, the number of dice and the number of sides per die as arguments.
 */
public class Boardgame {

  private final List<Player> players;
  private final Dice dice;
  private final Board board;
  private final List<BoardGameObserver> observers = new ArrayList<>(); 

  /*
    * Constructor for the Boardgame class.
    * @param board The board for the game.
    * @param numDice The number of dice in the game.
    * @param sidesPerDie The number of sides per die.
    * @throws IllegalArgumentException if the board is null, the number of dice is less than 1 or the number of sides per die is less than 2.
   */
  public Boardgame(Board board, int numDice, int sidesPerDie) {
    if (board == null) {
      throw new IllegalArgumentException("Board kan ikke være null");
    }
    if (numDice < 1) {
      throw new IllegalArgumentException("Kan ikke være færre enn 1 terning");
    }
    if (sidesPerDie < 2) {
      throw new IllegalArgumentException("Terningen må ha minst 2 sider");
    }

    this.board = board;
    this.players = new ArrayList<>();
    this.dice = new Dice(sidesPerDie, numDice);
  }

  /*
    * Adds a player to the game.
    * @param name The name of the player.
    * @param age The age of the player
    * @throws IllegalArgumentException if the name is null or empty.
    @ @throws IllegalArgumentException if the age is less than or equal to 0. 
   */
  
  public void addPlayer(String name, int age) {
      if (name == null || name.trim().isEmpty()) {
          throw new IllegalArgumentException("Navnet kan ikke være tomt eller null.");
      }
      if (age <= 0) {
          throw new IllegalArgumentException("Alderen må være større enn 0.");
      }

      Tile startTile = board.getTile(1);
      players.add(new Player(name, startTile, age));
      System.out.println("Spiller " + name + " med alder " + age + " har blitt lagt til på felt " + startTile.getPosition());
    }

  /*
    * @return A list of the players in the game.
   */
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /*
    * @return The board for the game.
   */
  public Dice getDice() {
    return dice;
  }

  /*
    * Registers an observer for the game.
    * @param observer The observer to register.
   */
    public void registerObserver(BoardGameObserver observer){
      if (observer != null && !observers.contains(observer)){
        observers.add(observer);
      }
    }

    /*
    * Unregisters an observer for the game.
    * @param observer The observer to unregister.
     */
    public void notifyPlayerMoved(Player player){
      for(BoardGameObserver observer : observers){
        observer.onPlayerMove(player);
      }
    }

    /*
    * Notifies the observers that a player has moved.
    * @param player The player that has moved.
     */
    public void notifyGameWon(Player player){
      for(BoardGameObserver observer : observers){
        observer.onGameWon(player);
      }
    }


    /**
     * Adds an already constructed Player object to the game.
     * <p>
     * This method is typically used when Player instances are created
     * externally, such as through a graphical user interface where players
     * provide names and tokens.
     *
     * @param player the Player object to be added to the game
     * @throws IllegalArgumentException if the provided Player is null
     */
    public void addExistingPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Spiller kan ikke være null.");
        }
        players.add(player);
        System.out.println("Spiller " + player.getName() + " har blitt lagt til manuelt.");
    }



    /**
     * Returns the game board.
     *
     * @return the Board object used in the game
     */
    public Board getBoard() {
        return this.board;
    }

  /*
    * Starts the game.
    * The players take turns rolling the dice and moving on the board.
    * The game ends when a player reaches the last tile on the board.
   */
  public void playGame() {
    System.out.println("\nThe following players are playing the game:");
    for (Player player : players) {
      System.out.println("Name: " + player.getName());
    }

    boolean gameOver = false;
    int round = 1;

    while (!gameOver) {
      System.out.println(); // Tom linje, bedre lesbarhet
      System.out.println("Round number " + round);

      for (Player player : players) {
        int roll = dice.roll();
        player.move(roll, board);
        notifyPlayerMoved(player);
        System.out.println("Player " + player.getName() + " on tile " + player.getCurrentTile().getPosition());

        if (player.getCurrentTile().getPosition() == board.getSize()) {
          notifyGameWon(player);
          System.out.println("\nAnd the winner is: " + player.getName() + "!");
          gameOver = true;
          break;
        }
      }
      round++;
    }
  }

  public void checkForWin(Player player) {
    if (player.getCurrentTile().getPosition() == board.getSize()) {
        notifyGameWon(player);
    }
}

}
