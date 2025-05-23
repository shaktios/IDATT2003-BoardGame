package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.BoardgameApp;
import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.common.StartScreenView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the main menu screen. Handles navigation to different game
 * types and responds to user input such as exit requests.
 */
public class MainPageController {

  private final Stage stage;
  private final MainPage mainPage;

  /**
   * Constructs the MainPageController and binds game selection behavior.
   *
   * @param stage the primary JavaFX stage to manage scene transitions
   */
  public MainPageController(Stage stage) {
    this.stage = stage;

    this.mainPage = new MainPage(selectedGame -> {
      switch (selectedGame) {
        case "Stigespill" -> {
          StartScreenView startView = new StartScreenView();
          startView.setSelectedGameVariant("Liten Stigespill"); //første alternativet som dukker opp
          StartScreenController controller = new StartScreenController(stage, startView);
          Scene scene = controller.getStartScene();
          stage.setScene(scene);
        }

        case "Tic Tac Toe" -> {
          BoardgameApp.openTicTacToe(stage);
        }

        default -> {
          System.err.println("Ukjent spill valgt: " + selectedGame);
        }
      }
    });

    setupActions();
  }

  /**
   * Initializes button actions for the main page. Currently sets up the exit
   * button to close the application.
   */
  private void setupActions() {
    mainPage.getExitButton().setOnAction(e -> stage.close());
  }

  /**
   * Creates and returns the scene for the main menu, including CSS styling.
   *
   * @return the constructed Scene object for the main page
   */
  public Scene getMainScene() {
    Scene scene = new Scene(mainPage.getRoot(), 1280, 800);
    scene.getStylesheets().add(getClass().getResource("/styles/mainPage.css").toExternalForm());
    return scene;
  }

  /**
   * Returns the view component representing the main page layout.
   *
   * @return the MainPage view instance
   */
  public MainPage getMainPage() {
    return mainPage;
  }
}
