package com.kdg.zolo.mvp.util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreEntry {

    private final SimpleStringProperty playerName;
    private final SimpleIntegerProperty score;
    private final SimpleStringProperty time;

    public ScoreEntry(String playerName, int score, String time) {
        this.playerName = new SimpleStringProperty(playerName);
        this.score = new SimpleIntegerProperty(score);
        this.time = new SimpleStringProperty(time);
    }

    public SimpleStringProperty playerNameProperty() {
        return playerName;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }
}
