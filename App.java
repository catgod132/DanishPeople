package com.example.danisharmysimulator;

import javafx.application.Application;
import javafx.scene.image.Image;  // For loading images
import javafx.scene.image.ImageView;  // For displaying images
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Random;

public class App extends Application {
    private double dx = 3; // Horizontal speed for the crosshair
    private double dy = 3; // Vertical speed for the crosshair
    private double ballSpeed = 2; // Slower speed for the red ball
    private double ballRadius = 18; // Slightly bigger ball radius
    final int width = 800;
    final int height = 600;

    private double crosshairX = width / 2;
    private double crosshairY = height / 2;

    private double daneX = (2 * width) / 3;
    private double daneY = height - 45;

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    private boolean daneUp = false;
    private boolean daneDown = false;
    private boolean daneLeft = false;
    private boolean daneRight = false;

    private double angle = 0;

    private Random r = new Random();

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int howitzerFiredCount = 0; // Track howitzers fired
    private boolean gameOver = false; // Track if game is over

    // Shots fired counter
    private int shotsFired = 0;
    private Text shotsFiredText = new Text();

    public void start(Stage primaryStage) {
        // Show intro screen first
        Intro intro = new Intro(primaryStage);
        intro.showIntro();
    }

    public void startGame(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, width, height);



        // Darker Green area above the ground up to the halfway mark
        Rectangle darkGreenArea = new Rectangle(0, 250, width, height / 2);
        darkGreenArea.setFill(Color.DARKGREEN); // Slightly darker shade of green

        // Water area in the top right corner of the dark green area
        Rectangle water = new Rectangle(width-500, 250, 500, 100);
        water.setFill(Color.BLUE); // Set the water to blue

        // Add the rectangles to the pane
        pane.getChildren().addAll(darkGreenArea, water);

        // Create an Image object for the background
       // Image backgroundImage = new Image("file://C:\\Users\\Evan\\Documents\\PrussiaPainting.png");
        //ImageView backgroundImageView = new ImageView(backgroundImage);
        //backgroundImageView.setFitWidth(width -100);
        //backgroundImageView.setFitHeight(height -100);

        // Add the image background to the pane
        //pane.getChildren().add(backgroundImageView);

        Line horizontalLine = new Line(0, 0, 0, 0);
        horizontalLine.setStroke(Color.BLACK);
        horizontalLine.setStrokeWidth(1);

        Line verticalLine = new Line(0, 0, 0, 0);
        verticalLine.setStroke(Color.BLACK);
        verticalLine.setStrokeWidth(1);

        horizontalLine.setStartX(crosshairX - 10);
        horizontalLine.setEndX(crosshairX + 10);
        horizontalLine.setStartY(crosshairY);
        horizontalLine.setEndY(crosshairY);

        verticalLine.setStartX(crosshairX);
        verticalLine.setEndX(crosshairX);
        verticalLine.setStartY(crosshairY - 10);
        verticalLine.setEndY(crosshairY + 10);

        pane.getChildren().addAll(horizontalLine, verticalLine);

        Rectangle bottomRectangle = new Rectangle(0, height - 75, width, 75);
        bottomRectangle.setFill(Color.GREEN);

        Rectangle trench = new Rectangle(2 * width / 3, height - 75, width / 3, 75);
        trench.setFill(Color.BROWN);

        Rectangle howitzerBase = new Rectangle(50, height - 75, 80, 25);
        howitzerBase.setFill(Color.GRAY);

        Howitzer cannon = new Howitzer();
        cannon.setLayoutX(75);
        cannon.setLayoutY(height - 95);

        Circle cannonBaseCircle = new Circle(75 + 40, height - 75, 15);
        cannonBaseCircle.setFill(Color.DARKGRAY);

        Circle smallerCircle = new Circle(75 + 40, height - 75, 10);
        smallerCircle.setFill(Color.BLACK);

        Circle dane = new Circle(daneX, daneY, ballRadius);
        dane.setFill(Color.RED);








        pane.getChildren().addAll(bottomRectangle, trench, howitzerBase, cannon, cannonBaseCircle, smallerCircle, dane);

        // Text elements for displaying winner
        Text winnerText = new Text();
        winnerText.setFont(Font.font("Arial", 30));
        winnerText.setFill(Color.BLACK);
        winnerText.setX(width / 2 - 90);
        winnerText.setY(height / 3);

        // Danish flag components
        Rectangle danishFlag = new Rectangle(width / 2 - 100, height / 2 - 50, 200, 120);
        danishFlag.setFill(Color.RED);

        // Vertical cross section (white) - thinner and correctly positioned
        Rectangle danishCrossVertical = new Rectangle(width / 2 - 50, height / 2 - 50, 20, 120);
        danishCrossVertical.setFill(Color.WHITE);

        // Horizontal cross section (white) - adjusted to be centered horizontally and moved slightly down
        Rectangle danishCrossHorizontal = new Rectangle(width / 2 - 100, height / 2 - 50 + 30, 200, 30);
        danishCrossHorizontal.setFill(Color.WHITE);

        // Prussian flag components (fixed)
        Rectangle prussianFlag = new Rectangle(width / 2 - 100, height / 2 - 50, 200, 120);
        prussianFlag.setFill(Color.BLACK);

        // Small square in the center of the Prussian flag
        Rectangle prussianSquare = new Rectangle(width / 2 - 15, height / 2 - 15, 30, 30);
        prussianSquare.setFill(Color.WHITE);

        // Add shots fired counter to the pane
        shotsFiredText.setFont(Font.font("Arial", 20));
        shotsFiredText.setFill(Color.BLACK);
        shotsFiredText.setX(10); // X position in the top left corner
        shotsFiredText.setY(30); // Y position
        pane.getChildren().add(shotsFiredText);

        pane.getChildren().addAll(winnerText);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameOver) {
                    return; // Stop game if it's over
                }

                if (moveUp) crosshairY -= dy;
                if (moveDown) crosshairY += dy;
                if (moveLeft) crosshairX -= dx;
                if (moveRight) crosshairX += dx;

                double minX = howitzerBase.getX() + 80;
                double maxX = width - 10;
                crosshairX = Math.max(minX, Math.min(maxX, crosshairX));

                double minY = 100;
                double maxY = height - 100;
                crosshairY = Math.max(minY, Math.min(maxY, crosshairY));

                horizontalLine.setStartX(crosshairX - 10);
                horizontalLine.setEndX(crosshairX + 10);
                horizontalLine.setStartY(crosshairY);
                horizontalLine.setEndY(crosshairY);

                verticalLine.setStartX(crosshairX);
                verticalLine.setEndX(crosshairX);
                verticalLine.setStartY(crosshairY - 10);
                verticalLine.setEndY(crosshairY + 10);

                double dx = crosshairX - (howitzerBase.getX() + howitzerBase.getWidth());
                double dy = crosshairY - (howitzerBase.getY() + howitzerBase.getHeight() / 2);
                angle = Math.atan2(dy, dx) * 180 / Math.PI;

                cannon.setRotate(angle);

                // Update all projectiles
                for (Projectile p : projectiles) {
                    p.updatePosition(angle);
                }

                // Check if Dane is hit
                for (Projectile p : projectiles) {
                    if (Math.hypot(p.getCircle().getCenterX() - dane.getCenterX(), p.getCircle().getCenterY() - dane.getCenterY()) <= ballRadius) {
                        gameOver = true;
                        winnerText.setText("Prussens Win");
                        pane.getChildren().addAll(prussianFlag, prussianSquare);
                        return;
                    }
                }

                // Check if 20 projectiles fired (Danish Win condition)
                if (howitzerFiredCount >= 20) {
                    gameOver = true;
                    winnerText.setText("Danish Win");
                    pane.getChildren().addAll(danishFlag, danishCrossVertical, danishCrossHorizontal);
                }

                if (daneUp) daneY -= ballSpeed;
                if (daneDown) daneY += ballSpeed;
                if (daneLeft) daneX -= ballSpeed;
                if (daneRight) daneX += ballSpeed;

                double trenchLeft = trench.getX();
                double trenchRight = trenchLeft + trench.getWidth();
                double trenchTop = trench.getY();
                double trenchBottom = trenchTop + trench.getHeight();

                daneX = Math.max(trenchLeft + ballRadius, Math.min(trenchRight - ballRadius, daneX));
                daneY = Math.max(trenchTop + ballRadius, Math.min(trenchBottom - ballRadius, daneY));

                dane.setCenterX(daneX);
                dane.setCenterY(daneY);
            }
        };

        scene.setOnMouseClicked(event -> {
            // Get the corrected spawn position at the cannon's tip, considering the rotation
            double spawnX = cannon.getTipX();
            double spawnY = cannon.getTipY();

            // Calculate the distance between the howitzer and the crosshair (pointer)
            double dx = crosshairX - (howitzerBase.getX() + howitzerBase.getWidth() / 2);  // Horizontal distance
            double dy = crosshairY - (howitzerBase.getY() + howitzerBase.getHeight() / 2);  // Vertical distance
            double distance = Math.sqrt(dx * dx + dy * dy);  // Euclidean distance

            // Calculate initial velocity based on this distance, and scale it to reach roughly 12 at the end of the screen
            double v0 = distance / width * 12;  // Scale the velocity to be proportional to the distance

            // Ensure that the velocity is not too small or large
            v0 = Math.min(v0, 12);  // Maximum velocity cap (you can adjust this if needed)

            // Calculate the components of the initial velocity in X and Y direction based on angle
            double initialVelocityX = v0 * Math.cos(Math.toRadians(angle));
            double initialVelocityY = v0 * Math.sin(Math.toRadians(angle));

            // Create a new projectile at the correct position and velocity
            Projectile projectile = new Projectile(spawnX, spawnY, initialVelocityX, initialVelocityY);
            projectiles.add(projectile);
            pane.getChildren().add(projectile.getCircle());

            // Increment howitzer count
            howitzerFiredCount++;

            // Increment shots fired counter
            shotsFired++;

            // Update the shots fired display
            shotsFiredText.setText("Shots Fired: " + shotsFired);
        });

        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        timer.start();

        primaryStage.setTitle("Danish Army Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.W) moveUp = true;
        if (event.getCode() == KeyCode.S) moveDown = true;
        if (event.getCode() == KeyCode.A) moveLeft = true;
        if (event.getCode() == KeyCode.D) moveRight = true;

        if (event.getCode() == KeyCode.UP) daneUp = true;
        if (event.getCode() == KeyCode.DOWN) daneDown = true;
        if (event.getCode() == KeyCode.LEFT) daneLeft = true;
        if (event.getCode() == KeyCode.RIGHT) daneRight = true;
    }

    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.W) moveUp = false;
        if (event.getCode() == KeyCode.S) moveDown = false;
        if (event.getCode() == KeyCode.A) moveLeft = false;
        if (event.getCode() == KeyCode.D) moveRight = false;

        if (event.getCode() == KeyCode.UP) daneUp = false;
        if (event.getCode() == KeyCode.DOWN) daneDown = false;
        if (event.getCode() == KeyCode.LEFT) daneLeft = false;
        if (event.getCode() == KeyCode.RIGHT) daneRight = false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
