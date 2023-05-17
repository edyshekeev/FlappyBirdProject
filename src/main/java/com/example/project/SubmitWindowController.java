package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SubmitWindowController implements Initializable {

    @FXML
    private TextField nicknameSubmitTextField;

    @FXML
    private Button submitButton;

    @FXML
    public void submitButtonPressed(ActionEvent event) throws IOException {
        String nickname = nicknameSubmitTextField.getText();
        String localDir = System.getProperty("user.dir");
        File recentScore = new File(localDir + "\\src\\main\\resources\\com\\example\\project\\RecentScore.txt");
        Scanner scanner = new Scanner(recentScore);
        int score = scanner.nextInt();
        scanner.close();
        Player player = new Player(nickname, score);
        File scoreBoard = new File(localDir + "\\src\\main\\resources\\com\\example\\project\\ScoreBoard.txt");
        scanner = new Scanner(scoreBoard);
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        scanner.close();
        result.append(player);
        FileWriter scoreWriter = new FileWriter(scoreBoard);
        BufferedWriter writer = new BufferedWriter(scoreWriter);
        writer.write(String.valueOf(result));
        writer.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApp.class.getResource("ScoreBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 666, 468);
        scene.getRoot().requestFocus();
        Stage stage = new Stage();
        stage.setTitle("Scoreboard");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) nicknameSubmitTextField.getScene().getWindow();
        oldStage.close();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {}

}
