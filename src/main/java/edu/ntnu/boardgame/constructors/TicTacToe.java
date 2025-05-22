package edu.ntnu.boardgame.constructors;

/**
 * Represents a Tic Tac Toe game using the shared Board, Tile, and Player
 * classes. Handles game logic including player turns, move validation, win
 * condition checks, and board reset.
 */
public class TicTacToe {

    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    private static final int SIZE = 3;

    /**
     * Constructs a new TicTacToe game with two players. Player 1 uses "X",
     * Player 2 uses "O".
     *
     * @param player1 the first player
     * @param player2 the second player
     * @throws IllegalArgumentException if any player is null
     */
    public TicTacToe(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Spillere kan ikke være null.");
        }

        this.board = new Board(SIZE, SIZE); // 3x3 board
        this.player1 = player1;
        this.player2 = player2;

        player1.setName("Spiller 1");
        player1.setToken("X");
        player2.setName("Spiller 2");
        player2.setToken("O");

        this.currentPlayer = player1;
    }

    /**
     * Attempts to make a move at the given row and column.
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @return true if move was successful, false if cell is already occupied
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Ugyldig posisjon.");
        }

        int pos = row * SIZE + col + 1;
        Tile tile = board.getTile(pos);

        if (tile.getToken() != null) {
            return false; // Already taken
        }

        tile.setToken(currentPlayer.getToken());
        return true;
    }

    /**
     * Checks if a player has won the game.
     *
     * @return the winning token ("X" or "O"), or null if no winner
     */
    public String checkWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (equal(getToken(i, 0), getToken(i, 1), getToken(i, 2))) {
                return getToken(i, 0);
            }
            if (equal(getToken(0, i), getToken(1, i), getToken(2, i))) {
                return getToken(0, i);
            }
        }

        if (equal(getToken(0, 0), getToken(1, 1), getToken(2, 2))) {
            return getToken(0, 0);
        }
        if (equal(getToken(0, 2), getToken(1, 1), getToken(2, 0))) {
            return getToken(0, 2);
        }

        return null;
    }

    /**
     * Checks if all tiles are filled.
     *
     * @return true if board is full, false otherwise
     */
    public boolean isBoardFull() {
        for (int i = 1; i <= board.getSize(); i++) {
            if (board.getTile(i).getToken() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Switches turn to the other player.
     */
    public void switchTurn() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    /**
     * Returns the player whose turn it is now.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the token in the specified cell.
     *
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @return the token ("X", "O") or null
     */
    public String getCell(int row, int col) {
        int pos = row * SIZE + col + 1;
        return board.getTile(pos).getToken();
    }

    /**
     * Resets the board and player turn.
     */
    public void resetBoard() {
        for (int i = 1; i <= board.getSize(); i++) {
            board.getTile(i).setToken(null);
        }
        currentPlayer = player1;
    }

    /**
     * Returns the internal board object.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Helper method for checking if 3 strings are non-null and equal.
     */
    private boolean equal(String a, String b, String c) {
        if (a == null || b == null || c == null) {
            return false;
        }
        return a.equals(b) && a.equals(c);
    }


    /**
     * Returns the token at the specified board position (row, col).
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @return the token on that tile, or null if none
     */
    private String getToken(int row, int col) {
        int position = row * SIZE + col + 1; // mapping til 1–9
        return board.getTile(position).getToken();
    }
}
