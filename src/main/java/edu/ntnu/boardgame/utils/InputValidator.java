package edu.ntnu.boardgame.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * Utility class for validating user input in the game setup screen. Provides
 * methods to validate player names, tokens, and player count.
 */
public class InputValidator {

  /**
   * Checks whether all provided text fields contain non-empty input.
   *
   * @param fields a list of {@link TextField} objects to validate
   * @return {@code true} if all fields are filled, {@code false} otherwise
   */
  public static boolean areAllFieldsFilled(List<TextField> fields) {
    for (TextField field : fields) {
      String text = field.getText();
      if (text == null || text.isBlank()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether all names entered in the text fields are unique
   * (case-insensitive).
   *
   * @param fields a list of {@link TextField} objects representing player
   * names
   * @return {@code true} if all names are unique, {@code false} if duplicates
   * are found
   */
  public static boolean areNamesUnique(List<TextField> fields) {
    Set<String> names = new HashSet<>();
    for (TextField field : fields) {
      String name = field.getText().trim().toLowerCase();
      if (!names.add(name)) {
        return false; // navn finnes allerede
      }
    }
    return true;
  }

  /**
   * Checks whether all selected tokens are unique and not null.
   *
   * @param tokenBoxes a list of {@link ComboBox} containing selected tokens
   * @return {@code true} if all tokens are unique and non-null, {@code false}
   * otherwise
   */
  public static boolean areTokensUnique(List<ComboBox<String>> tokenBoxes) {
    Set<String> seen = new HashSet<>();
    for (ComboBox<String> box : tokenBoxes) {
      String token = box.getValue();
      if (token == null || !seen.add(token)) {
        return false; // token er null eller allerede brukt
      }
    }
    return true;
  }


  /**
   * Validates the number of players based on the game rules.
   *
   * @param count the number of players
   * @return {@code true} if the count is between 1 and 5 (inclusive),
   * {@code false} otherwise
   */
  public static boolean isPlayerCountValid(int count) {
    return count >= 1 && count <= 5;
  }
}
