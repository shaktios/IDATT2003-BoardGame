package edu.ntnu.boardgame.constructors;


/**
 * A simple class representing a Tic Tac Toe game between two players. Handles
 * board state, turns, winning conditions, and board reset.
 */
public class TicTacToe {

  private final String[][] board;
  private final int SIZE = 3;
  private final Player player1;
  private final Player player2;
  private Player currentPlayer;

  
    /**
     * Constructs a new TicTacToe game with two players. Player 1 is assigned
     * "X", Player 2 is assigned "O".
     *
     * @param player1 the first player
     * @param player2 the second player
     * @throws IllegalArgumentException if any player is null
     */
  public TicTacToe(Player player1, Player player2) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException("Spillere kan ikke være null.");
    }

    this.player1 = player1;
    this.player2 = player2;
    this.board = new String[SIZE][SIZE];

    // Set fixed names and tokens – cannot be changed later
    player1.setName("Spiller 1");
    player1.setToken("X");

    player2.setName("Spiller 2");
    player2.setToken("O");

    this.currentPlayer = player1;
  }

    /**
     * Attempts to make a move for the current player at the given position.
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @return true if the move was successful, false if the cell was already
     * occupied
     * @throws IllegalArgumentException if the position is out of bounds
     */
  public boolean makeMove(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
      throw new IllegalArgumentException("Ugyldig kvadrat");
    }
    if (board[row][col] != null) {
      return false; // Feltet er allerede opptatt
    }

    board[row][col] = currentPlayer.getToken();
    return true;
  }

    /**
     * Checks the board for a winning condition.
     *
     * @return the token ("X" or "O") of the winner, or null if no winner yet
     */
  public String checkWinner() {
    for (int i = 0; i < SIZE; i++) {
      if (equal(board[i][0], board[i][1], board[i][2])) return board[i][0];
      if (equal(board[0][i], board[1][i], board[2][i])) return board[0][i];
    }

    if (equal(board[0][0], board[1][1], board[2][2])) return board[0][0];
    if (equal(board[0][2], board[1][1], board[2][0])) return board[0][2];

    return null;
  }

    /**
     * Checks whether the board is completely filled.
     *
     * @return true if all cells are filled, false otherwise
     */
  public boolean isBoardFull() {
    for (int r = 0; r < SIZE; r++)
      for (int c = 0; c < SIZE; c++)
        if (board[r][c] == null) return false;
    return true;
  }

    /**
     * Switches the turn to the other player.
     */
  public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }



  /**
     * Returns the player whose turn it is currently.
     *
     * @return the current player
     */
  public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the token in the specified cell.
     *
     * @param row the row index
     * @param col the column index
     * @return the token in the cell, or null if empty
     */
  public String getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * Resets the board to an empty state and sets turn back to player 1.
     */
  public void resetBoard() {
    for (int r = 0; r < SIZE; r++)
      for (int c = 0; c < SIZE; c++)
        board[r][c] = null;
    currentPlayer = player1;
  }


    /**
     * Helper method that checks if three strings are equal and not null.
     *
     * @param a first string
     * @param b second string
     * @param c third string
     * @return true if all are equal and non-null
     */
  private boolean equal(String a, String b, String c) {
        return a != null && a.equals(b) && a.equals(c);
    }
}
