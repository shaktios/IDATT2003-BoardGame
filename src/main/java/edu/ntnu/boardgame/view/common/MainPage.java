package edu.ntnu.boardgame.view.common;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The MainPage class represents the initial landing screen where the user
 * chooses which game to start or exits the application.
 *
 * It displays a styled title, two clickable game previews (e.g., Stigespill and
 * Tic Tac Toe), and an "Avslutt" button.
 */
public class MainPage {
    /**
     * The main layout container using BorderPane to organize title, game
     * previews, and exit button.
     */
  private final BorderPane layout;

    /**
     * The exit button shown at the bottom of the screen.
     */
  private final Button exitButton;

    /**
     * A callback used to notify the controller which game was selected.
     */
  private final Consumer<String> onGameSelected;

    /**
     * Constructs the MainPage view with title, game options, and exit button.
     *
     * @param onGameSelected a callback function invoked when a game is selected
     */
  public MainPage(Consumer<String> onGameSelected) {
    this.onGameSelected = onGameSelected;

    layout = new BorderPane();
    layout.getStyleClass().add("main-layout");

    // Title
    Text title = new Text("🎲 Velkommen til Board Games!");
    title.setFont(Font.font("Arial", 40));
    BorderPane.setAlignment(title, Pos.CENTER);
    BorderPane.setMargin(title, new Insets(120, 0, 0, 0));
    layout.setTop(title);

    // Game image row
    HBox imageBox = new HBox(60);
    imageBox.setAlignment(Pos.CENTER);
    imageBox.setPadding(new Insets(60, 30, 30, 30));

    imageBox.getChildren().addAll(
          createGameImage("game2.png", "Stigespill"),
          createGameImage("game3.png", "Tic Tac Toe")
    );
    layout.setCenter(imageBox);

    // Exit button
    exitButton = new Button("Avslutt");
    exitButton.setFont(Font.font("Arial", 18));
    exitButton.getStyleClass().add("exit-button");
    BorderPane.setAlignment(exitButton, Pos.CENTER);
    BorderPane.setMargin(exitButton, new Insets(20));
    layout.setBottom(exitButton);
  }

    /**
     * Creates a visual representation of a game as an image with a label. The
     * image becomes clickable and invokes a callback when clicked.
     *
     * @param imageName the filename of the image (located under
     * /resources/images/)
     * @param gameName the name of the game associated with the image
     * @return a VBox containing the image and its label, styled for
     * interactivity
     */
  private VBox createGameImage(String imageName, String gameName) {
    Image image = new Image(getClass().getResource("/images/" + imageName).toExternalForm());
    ImageView view = new ImageView(image);
    view.setFitWidth(320); // Larger size
    view.setFitHeight(200); // Ensure same height for consistency
    view.setPreserveRatio(false); // Force size equality
    view.setSmooth(true);
    view.setCursor(Cursor.HAND);
    view.getStyleClass().add("game-image");

    Text label = new Text(gameName);
    label.setFont(Font.font("Arial", 20));

    VBox box = new VBox(15, view, label);
    box.setAlignment(Pos.CENTER);
    box.setOnMouseClicked(e -> handleGameClick(gameName));

    // Hover effect
    box.setOnMouseEntered(e -> {
      view.setScaleX(1.05);
      view.setScaleY(1.05);
    });
    box.setOnMouseExited(e -> {
      view.setScaleX(1.0);
      view.setScaleY(1.0);
    });

    return box;
  }

    /**
     * Handles the click event when a user selects a game. Delegates the game
     * name to the controller via callback.
     *
     * @param gameName the selected game name
     */
  public void handleGameClick(String gameName) {
    if (onGameSelected != null) {
      onGameSelected.accept(gameName);
    }
  }

    /**
     * Returns the root layout of this view.
     *
     * @return the root BorderPane
     */
  public Pane getRoot() {
    return layout;
  }

    /**
     * Returns the exit button for potential controller customization.
     *
     * @return the exit button
     */
  public Button getExitButton() {
    return exitButton;
  }
}