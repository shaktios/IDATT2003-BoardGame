package edu.ntnu.boardgame.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Writes a full boardgame configuration to a JSON file. Includes board size,
 * rows, columns, dice settings, and tile positions.
 */
public class BoardFileWriterGson implements BoardFileWriter {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Writes only the board structure (rows, columns, tiles) to a JSON file.
     *
     * @param path the file path to write to
     * @param board the board object to save
     * @throws IOException if writing fails
     */
    @Override
    public void writeBoard(Path path, Board board) throws IOException {
        JsonObject root = new JsonObject();
        root.addProperty("size", board.getSize());
        root.addProperty("rows", board.getRows());
        root.addProperty("columns", board.getColumns());

        JsonArray tilesArray = new JsonArray();
        for (int i = 1; i <= board.getSize(); i++) {
            Tile tile = board.getTile(i);
            JsonObject tileObj = new JsonObject();
            tileObj.addProperty("position", tile.getPosition());
            tileObj.addProperty("x", tile.getX());
            tileObj.addProperty("y", tile.getY());
            tilesArray.add(tileObj);
        }

        root.add("tiles", tilesArray);

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(root, writer);
        }
    }

    /**
     * Writes a full boardgame (board + dice config) to JSON.
     *
     * @param path the file path to write to
     * @param boardgame the boardgame to serialize
     * @throws IOException if writing fails
     */
    public void writeBoardgame(Path path, Boardgame boardgame) throws IOException {
        JsonObject root = new JsonObject();

        Board board = boardgame.getBoard();
        root.addProperty("size", board.getSize());
        root.addProperty("rows", board.getRows());
        root.addProperty("columns", board.getColumns());

        JsonObject diceObj = new JsonObject();
        diceObj.addProperty("count", boardgame.getDice().getNumberOfDice());
        diceObj.addProperty("sides", boardgame.getDice().getNumSides());
        root.add("dice", diceObj);

        JsonArray tilesArray = new JsonArray();
        for (int i = 1; i <= board.getSize(); i++) {
            Tile tile = board.getTile(i);
            JsonObject tileObj = new JsonObject();
            tileObj.addProperty("position", tile.getPosition());
            tileObj.addProperty("x", tile.getX());
            tileObj.addProperty("y", tile.getY());
            tilesArray.add(tileObj);
        }
        root.add("tiles", tilesArray);

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(root, writer);
        }
    }
}
