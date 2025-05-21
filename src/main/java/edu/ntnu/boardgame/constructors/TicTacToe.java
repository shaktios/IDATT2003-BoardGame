package edu.ntnu.boardgame.constructors;

public class TicTacToe {
  private final String[][] board;
  private final int SIZE = 3;
  private final Player player1;
  private final Player player2;
  private Player currentPlayer;

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

  public String checkWinner() {
    for (int i = 0; i < SIZE; i++) {
      if (equal(board[i][0], board[i][1], board[i][2])) return board[i][0];
      if (equal(board[0][i], board[1][i], board[2][i])) return board[0][i];
    }

    if (equal(board[0][0], board[1][1], board[2][2])) return board[0][0];
    if (equal(board[0][2], board[1][1], board[2][0])) return board[0][2];

    return null;
  }

  public boolean isBoardFull() {
    for (int r = 0; r < SIZE; r++)
      for (int c = 0; c < SIZE; c++)
        if (board[r][c] == null) return false;
    return true;
  }

  public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

  public Player getCurrentPlayer() {
        return currentPlayer;
    }

  public String getCell(int row, int col) {
        return board[row][col];
    }

  public void resetBoard() {
    for (int r = 0; r < SIZE; r++)
      for (int c = 0; c < SIZE; c++)
        board[r][c] = null;
    currentPlayer = player1;
  }

  private boolean equal(String a, String b, String c) {
        return a != null && a.equals(b) && a.equals(c);
    }
}
