package edu.ntnu.boardgame.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * A utility class for reading and writing player data to and from JSON files
 * using Gson.
 */
public class PlayerFileHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Reads a list of players from a JSON file.
     *
     * @param file the file to read from
     * @return a list of Player objects
     * @throws IOException if an I/O error occurs
     */
    public static List<Player> readFromJSON(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            Type playerListType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            List<Player> players = gson.fromJson(reader, playerListType);

            // Midlertidig tile for å unngå null (kan tilpasses ved bruk av faktisk spillstart)
            Tile startTile = new Tile(1);
            for (Player player : players) {
                player.setCurrentTile(startTile);
            }

            return players;
        }
    }

    /**
     * Writes a list of players to a JSON file.
     *
     * @param file the file to write to
     * @param players the list of Player objects
     * @throws IOException if an I/O error occurs
     */
    public static void writeToJSON(File file, List<Player> players) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(players, writer);
        }
    }
}
