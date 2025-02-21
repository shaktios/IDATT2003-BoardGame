package edu.ntnu.boardgame;

public class BoardgameApp {

    public static void main(String[] args) {
        Board board = new Board(90); // Oppretter et brett med 90 felter
        Boardgame game = new Boardgame(board, 2, 6); // To terninger, 6 sider hver

        game.addPlayer("A");
        game.addPlayer("B");
        game.addPlayer("C");
        game.addPlayer("D");

        game.playGame(); // Starter spillet
    }
    



    
}
