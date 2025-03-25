package edu.ntnu.boardgame.io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.boardgame.Board;



public class BoardFileReaderGson implements BoardFileReader{

    @Override
    public Board readBoard(Path path) throws IOException{
        try (FileReader reader = new FileReader(path.toFile())){
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            int size = root.get("size").getAsInt(); 
            Board board = new Board(size);


           
            if (root.has("tiles")) {
                JsonArray tilesArray = root.getAsJsonArray("tiles");

                for (int i = 0; i < tilesArray.size(); i++) {
                    JsonObject tileObj = tilesArray.get(i).getAsJsonObject();
                    int position = tileObj.get("position").getAsInt();
                    // Her kan man senere hente ut f.eks. "action", "row", "col" osv.
                    board.getTile(position); // Tile lages allerede i Board(int size), så vi gjenbruker
                }
            }

            return board;
        }
    }
    
}
