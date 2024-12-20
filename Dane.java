package com.example.danisharmysimulator;

import javafx.scene.shape.Circle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Dane implements Movable {
    private double x, y;
    private double speed;
    private double trenchLeft, trenchRight, trenchTop, trenchBottom;
    private Circle circle;

    public Dane(double x, double y, double speed, double trenchLeft, double trenchRight, double trenchTop, double trenchBottom) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.trenchLeft = trenchLeft;
        this.trenchRight = trenchRight;
        this.trenchTop = trenchTop;
        this.trenchBottom = trenchBottom;
        this.circle = new Circle(x, y, 18);
        this.circle.setFill(Color.RED);
    }

    @Override
    public void move() {
        if (x < trenchLeft || x > trenchRight) {
            x = Math.max(trenchLeft, Math.min(trenchRight, x));
        }
        if (y < trenchTop || y > trenchBottom) {
            y = Math.max(trenchTop, Math.min(trenchBottom, y));
        }
    }

    public Circle getCircle() {
        return circle;
    }
}
