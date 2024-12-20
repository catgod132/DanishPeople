package com.example.danisharmysimulator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Projectile {
    private Circle circle;
    private double initialX, initialY;
    private double initialVelocityX, initialVelocityY;
    private double time; // Time since the projectile was fired

    // Constructor to initialize the projectile
    public Projectile(double x, double y, double velocityX, double velocityY) {
        this.circle = new Circle(x, y, 5, Color.RED);
        this.initialX = x;
        this.initialY = y;
        this.initialVelocityX = velocityX;
        this.initialVelocityY = velocityY;
        this.time = 0.0;
    }

    // Getter for the Circle object representing the projectile
    public Circle getCircle() {
        return this.circle;
    }

    // Method to update the position based on time and gravity
    public void updatePosition(double angle) {
        double gravity = 9.81; // Gravity constant
        double velocityX = initialVelocityX * Math.cos(Math.toRadians(angle));  // Horizontal velocity
        double velocityY0 = initialVelocityY * Math.sin(Math.toRadians(angle));  // Initial vertical velocity

        // Calculate vertical velocity at time 't'
        double velocityY = velocityY0 - gravity * time;
        double deltaX = velocityX * time; // Horizontal displacement, no scaling needed
        double deltaY = velocityY0 * time - 0.5 * gravity * time * time;  // Apply gravity to vertical motion

        if (deltaY < -7.5) {
            deltaY = -7.5;
        }


       // System.out.println("Velcotiy y:" + deltaY);

        // Update the projectile's position
        this.circle.setCenterX(this.circle.getCenterX() + deltaX);
        this.circle.setCenterY(this.circle.getCenterY() - deltaY); // Subtract because Y increases downward in JavaFX

        // Increment time for the next frame
        this.time += 0.016; // Assuming 60 FPS (1/60 seconds per frame)
    }
}
