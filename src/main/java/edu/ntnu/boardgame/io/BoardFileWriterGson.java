package edu.ntnu.boardgame.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Tile;


public class BoardFileWriterGson implements BoardFileWriter{


    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void writeBoard(Path path, Board board) throws IOException {
        JsonObject root = new JsonObject();
        root.addProperty("size", board.getSize());

        JsonArray tilesArray = new JsonArray();
        for (int i = 1; i <= board.getSize(); i++) {
            Tile tile = board.getTile(i);
            JsonObject tileObj = new JsonObject();
            tileObj.addProperty("position", tile.getPosition());
            tilesArray.add(tileObj);
        }

        root.add("tiles", tilesArray);

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(root, writer);
        }
    }
    
}
