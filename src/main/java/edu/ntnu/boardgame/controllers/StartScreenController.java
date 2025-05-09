package edu.ntnu.boardgame.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.BoardGameFactory;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.utils.InputValidator;
import edu.ntnu.boardgame.view.common.StartScreenView;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartScreenController {

    private final Stage stage;
    private final StartScreenView view;

    public StartScreenController(Stage stage, StartScreenView view) {
        this.stage = stage;
        this.view = view;

        view.setNextButtonAction(e -> handleNextButton());
        view.setStartGameButtonAction(e -> handleStartGameButton());
    }

    private void handleNextButton() {
        int amountOfPlayers = view.getSelectedPlayerCount();
        view.generatePlayerInputs(amountOfPlayers);
    }

    private void handleStartGameButton() {
        Boardgame boardgame;

        switch (view.getSelectedGameVariant()) {
            case "Liten Stigespill" ->
                boardgame = BoardGameFactory.createMiniGame();
            case "Stort Stigespill" ->
                boardgame = BoardGameFactory.createClassicGame();
            case "Importer eget brett (.json)" -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Velg JSON-fil");
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile == null) {
                    return;
                }

                try {
                    boardgame = BoardGameFactory.createGameFromFile(Paths.get(selectedFile.getAbsolutePath()));
                } catch (IOException | InvalidBoardFileException ex) {
                    view.showAlert("Feil ved lasting av fil", ex.getMessage());
                    return;
                }
            }
            default -> {
                view.showAlert("Feil", "Ugyldig spillvariant valgt.");
                return;
            }
        }

        if (!InputValidator.areAllFieldsFilled(view.getPlayerNameFields())) {
            view.showAlert("Ugyldig navn", "Alle spillere må skrive inn et navn");
            return;
        }
        if (!InputValidator.areNamesUnique(view.getPlayerNameFields())) {
            view.showAlert("Duplikatnavn", "Alle spillere må ha unike navn");
            return;
        }
        if (!InputValidator.areTokensUnique(view.getPlayerTokenChoices())) {
            view.showAlert("Duplikattoken", "Alle spillere må velge en unik brikke");
            return;
        }

        List<Player> players = new ArrayList<>();
        Tile startTile = boardgame.getBoard().getTile(1);
        List<TextField> ageFields = view.getPlayerAgeFields();

        int youngestAge = Integer.MAX_VALUE;
        int youngestIndex = 0;

        for (int i = 0; i < view.getPlayerNameFields().size(); i++) {
            String name = view.getPlayerNameFields().get(i).getText();
            String token = view.getPlayerTokenChoices().get(i).getValue();
            int age;

            try {
                age = Integer.parseInt(ageFields.get(i).getText());
            } catch (NumberFormatException e) {
                view.showAlert("Ugyldig alder", "Alder må være et heltall for spiller " + (i + 1));
                return;
            }

            Player player = new Player(name, startTile, age);
            player.setToken(token);
            players.add(player);
            boardgame.addExistingPlayer(player);

            if (age < youngestAge) {
                youngestAge = age;
                youngestIndex = i;
            }
        }

        LadderGameScreen gameScreen = new LadderGameScreen();
        Scene ladderGameScene = gameScreen.createScene(stage, boardgame, boardgame.getBoard(), players);
        String selectedVariant = view.getSelectedGameVariant();

        LadderGameController ladderGameController = new LadderGameController(
                boardgame, gameScreen, stage, selectedVariant, youngestIndex
        );

        gameScreen.disableNextTurnButton();
        gameScreen.enableDiceButton();

        gameScreen.getThrowDiceButton().setOnAction(e -> ladderGameController.handleDiceRoll(stage));
        gameScreen.getNextTurnButton().setOnAction(e -> ladderGameController.handleNextTurn());

        stage.setScene(ladderGameScene);
    }

    public Scene getStartScene() {
        return view.createScene(stage);
    }
}
