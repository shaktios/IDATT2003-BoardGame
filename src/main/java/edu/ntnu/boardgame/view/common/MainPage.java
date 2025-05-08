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

public class MainPage {
    private final BorderPane layout;
    private final Button exitButton;
    private final Consumer<String> onGameSelected;

    public MainPage(Consumer<String> onGameSelected) {
        this.onGameSelected = onGameSelected;

        layout = new BorderPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #e8eaf6, #ffffff);");

        // Title
        Text title = new Text("🎲 Velkommen til Board Games!");
        title.setFont(Font.font("Arial", 40));
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(40, 0, 0, 0));
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
        BorderPane.setAlignment(exitButton, Pos.CENTER);
        BorderPane.setMargin(exitButton, new Insets(20));
        layout.setBottom(exitButton);
    }

    /**
     * Creates a clickable image that represents a game.
     *
     * @param imageName filename under /resources/images/
     * @param gameName string passed back to the controller when clicked
     * @return ImageView wrapped in a VBox for title + interactivity
     */
    private VBox createGameImage(String imageName, String gameName) {
        Image image = new Image(getClass().getResource("/images/" + imageName).toExternalForm());
        ImageView view = new ImageView(image);
        view.setFitWidth(320); // Larger size
        view.setFitHeight(200); // Ensure same height for consistency
        view.setPreserveRatio(false); // Force size equality
        view.setSmooth(true);
        view.setCursor(Cursor.HAND);
        view.setStyle("-fx-effect: dropshadow(gaussian, gray, 10, 0.5, 0, 0);");

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

    public void handleGameClick(String gameName) {
        if (onGameSelected != null) {
            onGameSelected.accept(gameName);
        }
    }

    public Pane getRoot() {
        return layout;
    }

    public Button getExitButton() {
        return exitButton;
    }
}