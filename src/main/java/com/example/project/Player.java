package com.example.project;

public class Player {
    private String nickname;
    private int score;

    public Player(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("%s %d ", nickname, score);
    }
}
