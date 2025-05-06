package edu.ntnu.boardgame.constructors;

public class TicTacToe {
    private final String[][] board;
    private final int SIZE = 3;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;

    public TicTacToe(Player playerX, Player playerO) {
        if (playerX == null || playerO == null) {
            throw new IllegalArgumentException("Spillere kan ikke være null.");
        }

        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new String[SIZE][SIZE];
        this.currentPlayer = playerX;
        playerX.setToken("X");
        playerO.setToken("O");
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
        // Rader og kolonner
        for (int i = 0; i < SIZE; i++) {
            if (equal(board[i][0], board[i][1], board[i][2])) return board[i][0];
            if (equal(board[0][i], board[1][i], board[2][i])) return board[0][i];
        }

        // Diagonaler
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
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
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
        currentPlayer = playerX;
    }

    private boolean equal(String a, String b, String c) {
        return a != null && a.equals(b) && a.equals(c);
    }
}
