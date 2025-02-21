package edu.ntnu.boardgame;


import java.util.ArrayList;
import java.util.List;

public class Boardgame {

    private final List<Player> players;
    private final Dice dice;
    private final Board board;

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

    public void addPlayer(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Navnet kan ikke være tomt eller null");
        }

        players.add(new Player(name));
        System.out.println("Spiller " + name + " har blitt lagt til i spillet");
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Dice getDice() {
        return dice;
    }

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
                System.out.println("Player " + player.getName() + " on tile " + player.getCurrentTile().getPosition());

                if (player.getCurrentTile().getPosition() == board.getSize()) {
                    System.out.println("\nAnd the winner is: " + player.getName() + "!");
                    gameOver = true;
                    break;
                }
            }
            round++;
        }
    }

   
}
