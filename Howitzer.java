package com.example.danisharmysimulator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Howitzer extends Pane {

    private Rectangle cannon; // Main cannon barrel
    private Rectangle tip; // Tip of the cannon

    private final double CANNON_LENGTH = 80; // Length of the cannon
    private final double TIP_LENGTH = 10; // Length of the cannon tip

    public Howitzer() {
        // Howitzer cannon
        cannon = new Rectangle(0, 0, CANNON_LENGTH, 10); // Cannon of the howitzer
        cannon.setFill(Color.DIMGRAY);

        // Tip of the cannon
        tip = new Rectangle(CANNON_LENGTH, -5, TIP_LENGTH, 20); // Tip of the cannon
        tip.setFill(Color.DARKGRAY);

        // Add components to the Howitzer
        this.getChildren().addAll(cannon, tip);
    }

    // Method to get the position of the tip relative to the Howitzer's layout
    public double getTipX() {
        // Position of the cannon's tip, considering its rotation.
        double cannonTipX = CANNON_LENGTH * Math.cos(Math.toRadians(getRotate()));
        double tipX = TIP_LENGTH * Math.cos(Math.toRadians(getRotate()));

        // Final tip position relative to the layoutX of the howitzer
        return getLayoutX() + cannonTipX + tipX;
    }

    public double getTipY() {
        // Position of the cannon's tip, considering its rotation.
        double cannonTipY = CANNON_LENGTH * Math.sin(Math.toRadians(getRotate()));
        double tipY = TIP_LENGTH * Math.sin(Math.toRadians(getRotate()));

        // Final tip position relative to the layoutY of the howitzer
        return getLayoutY() + cannonTipY + tipY;
    }
}
