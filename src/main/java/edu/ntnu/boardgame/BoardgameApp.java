package edu.ntnu.boardgame;

import edu.ntnu.boardgame.controllers.MainPageController;
import edu.ntnu.boardgame.controllers.StartScreenController;
import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.common.StartScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Boardgame application.
 */
public class BoardgameApp extends Application {

    /**
     * Launches the application by displaying the start screen.
     *
     * @param primaryStage the primary JavaFX window
     */
    @Override
    public void start(Stage primaryStage) {
        Scene startScene = createFreshStartScene(primaryStage);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("Boardgame");
        primaryStage.show();
    }

    /**
     * Creates a new, fully wired-up start screen with view and controller.
     *
     * @param stage the current stage to bind the scene to
     * @return a fresh start screen scene
     */
    public static Scene createFreshStartScene(Stage stage) {
        MainPage mainPage = new MainPage();
        MainPageController controller = new MainPageController(stage, mainPage);
        Scene mainScene = controller.getMainScene();

        stage.setScene(mainScene);
        return mainScene;
    }

        public static void main(String[] args) {
        launch(args);
    }
}
