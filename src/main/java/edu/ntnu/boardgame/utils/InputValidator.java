package edu.ntnu.boardgame.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class InputValidator {

        public static boolean areAllFieldsFilled(List<TextField> fields) {
            for (TextField field : fields) {
                String text = field.getText();
                if (text == null || text.isBlank()) {
                    return false;
                }
            }
            return true;
        }

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


    //maks 5 spillere i henhold til oppgavebeskrivelsen...
    
    public static boolean isPlayerCountValid(int count) {
        return count >= 1 && count <= 5;
    }



}
