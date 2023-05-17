package com.example.project;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ScoreBoardController implements Initializable {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private ListView<Player> scoreListView;
    @FXML
    private TextField scoreTextField;
    @FXML
    private Button exitButton;
    @FXML
    private Button restartButton;
    private final ObservableList<Player> players = FXCollections.observableArrayList();
    private Player playerCurrent;

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void restartButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApp.class.getResource("ProjectApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 558, 638);
        scene.getRoot().requestFocus();
        Stage stage = new Stage();
        stage.setTitle("Project App");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) scoreListView.getScene().getWindow();
        oldStage.close();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        String localDir = System.getProperty("user.dir");
        File scoreboard = new File(localDir + "\\src\\main\\resources\\com\\example\\project\\ScoreBoard.txt");
        try {
            Scanner scanner = new Scanner(scoreboard);
            String nickname;
            int score;
            while (scanner.hasNext()) {
                nickname = scanner.next();
                score = scanner.nextInt();
                players.add(new Player(nickname, score));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        SortedList<Player> sortedPlayers = new SortedList<>(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return -Integer.compare(o1.getScore(), o2.getScore());
            }
        });
        scoreListView.setItems(sortedPlayers);
        scoreListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                nicknameTextField.setText(newValue.getNickname());
                scoreTextField.setText(String.valueOf(newValue.getScore()));
            }
        });
    }
}
