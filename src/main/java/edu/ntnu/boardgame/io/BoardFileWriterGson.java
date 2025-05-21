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
 * rows, columns, dice settings, tile positions, and tile actions.
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

        JsonArray tilesArray = serializeTiles(board);
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

        JsonArray tilesArray = serializeTiles(board);
        root.add("tiles", tilesArray);

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(root, writer);
        }
    }

    /**
     * Serializes all tiles from a board, including actions if they exist.
     *
     * @param board the board to serialize tiles from
     * @return a JsonArray representing all tiles
     */
    private JsonArray serializeTiles(Board board) {
        JsonArray tilesArray = new JsonArray();

        for (int i = 1; i <= board.getSize(); i++) {
            Tile tile = board.getTile(i);
            JsonObject tileObj = new JsonObject();

            tileObj.addProperty("position", tile.getPosition());
            tileObj.addProperty("x", tile.getX());
            tileObj.addProperty("y", tile.getY());

            if (tile.getAction() != null) {
                JsonObject actionObj = new JsonObject();
                String actionType = tile.getAction().getClass().getSimpleName();

                switch (actionType) {
                    case "LadderAction" -> {
                        actionObj.addProperty("type", "LADDER");
                        actionObj.addProperty("destination", tile.getAction().getDestination());
                    }
                    case "BackAction" -> {
                        actionObj.addProperty("type", "BACK");
                        actionObj.addProperty("destination", tile.getAction().getDestination());
                    }
                    case "ResetAction" ->
                        actionObj.addProperty("type", "RESET");
                    case "SkipTurnAction" ->
                        actionObj.addProperty("type", "SKIP");
                    case "TeleportRandomAction" ->
                        actionObj.addProperty("type", "TELEPORT_RANDOM");
                    case "ChessPuzzleAction" ->
                        actionObj.addProperty("type", "CHESSPUZZLE");
                }

                tileObj.add("action", actionObj);
            }

            tilesArray.add(tileObj);
        }

        return tilesArray;
    }
}
