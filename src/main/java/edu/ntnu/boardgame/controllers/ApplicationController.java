package edu.ntnu.boardgame.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main JavaFX application class that serves as the entry point for the
 * board game system.
 * It initializes the primary stage and sets up the initial scene using
 * {@link MainPageController}.
 */
public class ApplicationController extends Application {

  /**
   * Launches the JavaFX application.
   *
   * @param args command-line arguments passed at launch time
   */
  public static void main(String[] args) {
    launch(args);
  }


  /**
   * Called automatically when the application is started. Sets the stage
   * title, loads the main scene, and displays the application window.
   *
   * @param stage the primary JavaFX window (Stage)
   */
  @Override
  public void start(Stage stage) {
    MainPageController controller = new MainPageController(stage);
    Scene scene = controller.getMainScene();

    stage.setTitle("BoardGame");
    stage.setScene(scene);
    stage.setResizable(false);  // Brukeren kan ikke endre størrelsestage.sizeToScene();
    // Tilpass vinduets størrelse til scenen
    stage.show();
  }
}
