package com.example.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Obstacles {
    private AnchorPane anchorPane;
    private double height;
    private double width;
    Random random = new Random();

    public Obstacles(AnchorPane anchorPane, double height, double width) {
        this.anchorPane = anchorPane;
        this.height = height;
        this.width = width;
    }

    public ArrayList<Rectangle> createObstacles(){
        int width = 40;
        double xPos = this.width;
        double space = 200;
        double recTopHeight = random.nextInt((int)(height - space - 100)) + 50;
        double recBottomHeight = height - space - recTopHeight;
        Rectangle rectangleTop = new Rectangle(xPos,0,width,recTopHeight);
        rectangleTop.setFill(Paint.valueOf("#b800ff"));
        Rectangle rectangleBottom = new Rectangle(xPos, recTopHeight + space, width, recBottomHeight);
        rectangleBottom.setFill(Paint.valueOf("#b800ff"));
        anchorPane.getChildren().addAll(rectangleTop,rectangleBottom);
        return new ArrayList<>(Arrays.asList(rectangleTop,rectangleBottom));
    }

    public void moveObstacles(ArrayList<Rectangle> obstacles, double movingSpeed){
        ArrayList<Rectangle> outOfScreen = new ArrayList<>();
        for (Rectangle rectangle: obstacles) {
            rectangle.setX(rectangle.getX() - movingSpeed);
            if(rectangle.getX() <= -rectangle.getWidth()){
                outOfScreen.add(rectangle);
            }
        }
        obstacles.removeAll(outOfScreen);
        anchorPane.getChildren().removeAll(outOfScreen);
    }
}
