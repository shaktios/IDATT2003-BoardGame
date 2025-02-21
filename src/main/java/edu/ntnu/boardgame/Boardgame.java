package edu.ntnu.boardgame;


import java.util.ArrayList;
import java.util.List;

public class Boardgame {

    private final List<Player> players; 
    private final Dice dice; 
    private final Board board; 

    public Boardgame(Board board, int numDice, int sidesPerDie){
        if(board == null){
            throw new IllegalArgumentException("Board kan ikke være null"); 
        }
        if(numDice <1){
            throw new IllegalArgumentException("Kan ikke være færre enn 1 terninger"); 
        }
        if(sidesPerDie<2){
            throw new IllegalArgumentException("Terningen må ha minst 2 sider"); 
        }

    this.board = board; 
    this.players = new ArrayList<>();
    this.dice = new Dice(sidesPerDie, numDice);

    }

    public void addPlayer(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Navnet kan ikke være tomt eller null");
        }

        Tile startTile = board.getTile(1); 
        players.add(new Player(name,startTile));
        System.out.println("Spiller " + name + " har blitt lagt til i spillet på felt; " + startTile.getPosition());
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    public Dice getDice() {
        return dice;
    }



}
