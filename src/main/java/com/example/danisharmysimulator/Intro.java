package com.example.danisharmysimulator;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Intro {
    private Stage stage;

    public Intro(Stage stage) {
        this.stage = stage;
    }

    public void showIntro() {
        Pane pane = new Pane();
        Scene introScene = new Scene(pane, 800, 600);

        // Title text
        Text title = new Text("Danish Army Simulator");
        title.setFont(Font.font("Arial", 36));
        title.setFill(Color.BLACK);
        title.setX(200);
        title.setY(150);

        // Rules text
        Text rules = new Text(
                "Use WASD to control and left-click to fire the Howitzer.\n" +
                        "Use arrow keys (<^v>) to control the Dane.\n" +
                        "Game ends when Dane avoids 20 projectiles or a projectile hits Dane."
        );
        rules.setFont(Font.font("Arial", 18));
        rules.setFill(Color.DARKBLUE);
        rules.setX(100);
        rules.setY(250);

        // Play button
        Button playButton = new Button("Play");
        playButton.setFont(Font.font("Arial", 24));
        playButton.setLayoutX(350);
        playButton.setLayoutY(400);
        playButton.setOnAction(event -> {
            App app = new App(); // Create the main app
            app.startGame(stage); // Start the game
        });

        pane.getChildren().addAll(title, rules, playButton);

        stage.setScene(introScene);
        stage.setTitle("Danish Army Simulator - Intro");
        stage.show();
    }
}
