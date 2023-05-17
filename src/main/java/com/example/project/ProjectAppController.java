package com.example.project;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectAppController implements Initializable {
    AnimationTimer gameLoop;
    private double time = 0;
    private double gameTime = 0;
    private int score = 0;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle bird;
    @FXML
    private Text scoreText;
    private Bird player;
    private Obstacles obstacles;
    ArrayList<Rectangle> obstaclesList = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new Bird(bird, 3);
        obstacles = new Obstacles(anchorPane, anchorPane.getPrefHeight(), anchorPane.getPrefWidth());
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    positionUpdate();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        load();
        gameLoop.start();
    }

    @FXML
    void spaceKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            player.birdJumping();
            time = 0;
        }
    }

    private void positionUpdate() throws IOException {
        time++;
        gameTime++;
        double gravity = 0.1;
        if (player.isJumping()) {
            if (player.getY() <=  player.getBirdImpulse() - gravity * time) {
                player.setY(player.getY());
            } else {
                player.setY(player.getY() + gravity * time - player.getBirdImpulse());
            }
        } else {
            player.setY(player.getY() + gravity * time);
        }
        if(pointChecker(obstaclesList, bird)){
            score++;
            scoreText.setText(String.valueOf(score));
        }
        if(isBirdDead(anchorPane)){
            endGame();
        }
        if (time == 30 && player.isJumping()) {
            player.setJumping(false);
            time = 0;
        }
        scoreText.toFront();
        double movingSpeed = 0.75;
        obstacles.moveObstacles(obstaclesList, movingSpeed);
        int distanceTime = 250;
        if(gameTime % distanceTime == 0){
            obstaclesList.addAll(obstacles.createObstacles());
        }
    }

    private void load(){
        obstaclesList.addAll(obstacles.createObstacles());
    }

    private void endGame() throws IOException {
        gameLoop.stop();
        String localDir = System.getProperty("user.dir");
        File recentScore = new File(localDir + "\\src\\main\\resources\\com\\example\\project\\RecentScore.txt");
        FileWriter scoreWriter = new FileWriter(recentScore);
        BufferedWriter writer = new BufferedWriter(scoreWriter);
        writer.append(String.valueOf(score));
        writer.close();
        openSubmitWindow();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public boolean collides(ArrayList<Rectangle> obstaclesList, Rectangle player){
        for (Rectangle rectangle: obstaclesList) {
            if(rectangle.getBoundsInParent().intersects(player.getBoundsInParent())){
                return true;
            }
        }
        return  false;
    }

    public boolean isBirdDead(AnchorPane anchorPane){
        return player.getY() + bird.getHeight() >= anchorPane.getHeight() || collides(obstaclesList, player.getBird());
    }

    private boolean pointChecker(ArrayList<Rectangle> obstaclesList, Rectangle player){
        for (Rectangle obstacle: obstaclesList) {
            int birdPositionX = (int) (player.getLayoutX() + player.getX());
            if(((int)(obstacle.getLayoutX() + obstacle.getX()) == birdPositionX)){
                return true;
            }
        }
        return false;
    }

    public void openSubmitWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApp.class.getResource("SubmitWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getRoot().requestFocus();
        Stage stage = new Stage();
        stage.setTitle("Submit your nickname");
        stage.setScene(scene);
        stage.show();
    }
}