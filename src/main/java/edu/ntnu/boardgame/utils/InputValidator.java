package edu.ntnu.boardgame.utils;

import java.util.List;

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
}
