package edu.ntnu.boardgame.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * Utility class for reading and writing {@link Player} data to and from JSON or
 * CSV files.
 * <p>
 * This class provides static methods to handle persistence of player
 * information, including name, age, and token. Used by the GUI for
 * import/export functionality.
 */
public class PlayerFileHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    // === JSON ===

    /**
     * Reads a list of {@link Player} objects from a JSON file.
     * 
     * Automatically sets each player's {@link Tile} to position 1 as a default
     * placeholder; this should be updated later in the controller.
     *
     * @param file the JSON file to read from
     * @return a list of Player objects parsed from the file
     * @throws IOException if reading fails
     */
    public static List<Player> readFromJSON(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<ArrayList<Player>>() {
            }.getType();
            List<Player> players = gson.fromJson(reader, type);

            Tile startTile = new Tile(1); // Placeholder, set in controller
            for (Player p : players) {
                p.setCurrentTile(startTile);
            }
            return players;
        }
    }

    /**
     * Writes a list of {@link Player} objects to a JSON file.
     *
     * @param file the destination JSON file
     * @param players the list of players to serialize
     * @throws IOException if writing fails
     */
    public static void writeToJSON(File file, List<Player> players) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(players, writer);
        }
    }

    // === CSV ===
    /**
     * Reads a list of {@link Player} objects from a CSV file.
     * 
     * Each line must be in the format: {@code name,age,token}. The player's
     * tile is initialized to {@code new Tile(1)}.
     *
     * @param file the CSV file to read from
     * @return a list of Player objects
     * @throws IOException if reading fails or the format is invalid
     */
    public static List<Player> readFromCSV(File file) throws IOException {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    continue;
                }

                String name = parts[0].trim();
                int age = Integer.parseInt(parts[1].trim());
                String token = parts[2].trim();

                Player player = new Player(name, new Tile(1), age);
                player.setToken(token);
                players.add(player);
            }
        }
        return players;
    }

    /**
     * Writes a list of {@link Player} objects to a CSV file.
     * 
     * Each player is written as a line in the format: {@code name,age,token}.
     *
     * @param file the destination CSV file
     * @param players the players to write
     * @throws IOException if writing fails
     */
    public static void writeToCSV(File file, List<Player> players) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Player p : players) {
                writer.write(p.getName() + "," + p.getAge() + "," + p.getToken());
                writer.newLine();
            }
        }
    }
}
