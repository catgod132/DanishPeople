package com.example.danisharmysimulator;

import javafx.scene.shape.Line;

public class Crosshair implements Movable {
    private double x, y, dx, dy;
    private double minX, maxX, minY, maxY;  // Define the boundaries
    private Line horizontalLine, verticalLine;

    public Crosshair(double initialX, double initialY, double dx, double dy, double minX, double maxX, double minY, double maxY) {
        this.x = initialX;
        this.y = initialY;
        this.dx = dx;
        this.dy = dy;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        // Initialize the crosshair lines
        horizontalLine = new Line();
        verticalLine = new Line();
    }

    @Override
    public void move() {
        // Update crosshair position based on its movement speed
        x += dx;
        y += dy;

        // Restrict the crosshair's position to within boundaries
        x = Math.max(minX, Math.min(maxX, x));
        y = Math.max(minY, Math.min(maxY, y));

        // Update the crosshair lines
        horizontalLine.setStartX(x - 10);
        horizontalLine.setEndX(x + 10);
        horizontalLine.setStartY(y);
        horizontalLine.setEndY(y);

        verticalLine.setStartX(x);
        verticalLine.setEndX(x);
        verticalLine.setStartY(y - 10);
        verticalLine.setEndY(y + 10);
    }

    public Line getHorizontalLine() {
        return horizontalLine;
    }

    public Line getVerticalLine() {
        return verticalLine;
    }

    public void setSpeed(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
