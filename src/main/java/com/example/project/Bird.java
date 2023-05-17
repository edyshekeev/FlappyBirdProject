package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class Bird {

    @FXML
    private Rectangle bird;
    private boolean jumping = false;
    private int birdImpulse;

    public Bird(Rectangle bird, int birdImpulse) {
        this.bird = bird;
        this.birdImpulse = birdImpulse;
    }

    public void birdJumping() {
        jumping = true;
    }

    public double getY() {
        return bird.getLayoutY() + bird.getY();
    }

    public void setY(double position) {
        bird.setY(position - bird.getLayoutY());
    }

    public int getBirdImpulse() {
        return birdImpulse;
    }

    public boolean isJumping(){
        return jumping;
    }

    public void setJumping(boolean value) {
        this.jumping = value;
    }

    public Rectangle getBird() {
        return bird;
    }
}
