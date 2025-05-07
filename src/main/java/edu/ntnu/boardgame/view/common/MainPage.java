package edu.ntnu.boardgame.view.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class MainPage {
  private VBox layout;
  private Button startGameButton;
  private Button exitButton;

  public MainPage() {
      layout = new VBox(20);
      layout.setAlignment(Pos.TOP_CENTER);
      layout.setPadding(new Insets(30));

      //Title
      Text title = new Text("Welcome to Board Games");
      title.setFont(Font.font(24));

      //Layout for all the pictures
      HBox imageBox = new HBox(20);
      imageBox.setAlignment(Pos.TOP_CENTER);

      imageBox.getChildren().addAll(
              createImageView("game1.png"),
              createImageView("game2.png"),
              createImageView("game3.png")
      );

      //Button Layouts
      startGameButton = new Button("Start Game");
      exitButton = new Button("Exit");

      VBox buttonBox = new VBox(10, startGameButton, exitButton);
      buttonBox.setAlignment(Pos.CENTER);

      layout.getChildren().addAll(title, imageBox, buttonBox);
    }


    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResource("/images/" + imageName).toExternalForm());
        ImageView view = new ImageView(image);
        view.setFitWidth(120);
        view.setFitHeight(120);
        view.setPreserveRatio(true);
        return view;
    }

    public VBox getRoot() {
        return layout;
    }

    // Getters so controller can attach actions
    public Button getStartGameButton() {
        return startGameButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
