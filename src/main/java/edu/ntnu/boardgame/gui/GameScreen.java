package edu.ntnu.boardgame.gui;

import java.util.List;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage; 






public class GameScreen {


    private int currentPlayerIndex = 0; 
    private List<Player> players; 
    private Board board; 
    private Label currentPlayerLabel = new Label(); // Viser hvem sin tur det er


    public List <Player> getPlayers(){
        return players; 
    }

    public Board getBoard(){
        return board; 
    }

    

    public Scene getScene(Stage stage, Boardgame boardgame){

        this.players = boardgame.getPlayers(); 
        this.board = boardgame.getBoard(); 
        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

    

        Board board = boardgame.getBoard();
        int rows = board.getRows(); 
        int cols = board.getColumns();

        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);

            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;

            StackPane cell = new StackPane();
            Rectangle bg = new Rectangle(50, 50);
            bg.setStyle("-fx-stroke: black; -fx-fill: white;");

            Label label = new Label(String.valueOf(pos));

            cell.getChildren().addAll(bg, label);
            gridPane.add(cell, colIndex, rowIndex);
        }


        Button throwDiceButton = new Button("Kast Terningen");

        throwDiceButton.setOnAction(event -> {

            Player player = players.get(currentPlayerIndex);
            Dice dice = new Dice(6,1); // Lager et terningobjekt
            int roll = dice.roll(); //bruker logikk som vi har lagd fra før av
            int newPosition = player.getPosition() + roll; 

            if (newPosition > board.getSize()){
                newPosition = board.getSize(); //stopper på siste tile. 
            }



            player.setPosition(newPosition,board); 
            Tile newTile = board.getTile(newPosition);
            player.setCurrentTile(newTile);

            currentPlayerLabel.setText(player.getName() + " kastet " + roll + " og flyttet til rute " + newPosition);

            // TODO: oppdater GUI-visningen av brikker


        });

        Button nextTurnButton = new Button("Neste tur");

        nextTurnButton.setOnAction(event -> {

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); 
            Player nextPlayer = players.get(currentPlayerIndex);
            currentPlayerLabel.setText("Spiller sin tur: " + nextPlayer.getName());

        });


        return new Scene(gridPane, 800, 600); 
    }
    
}
