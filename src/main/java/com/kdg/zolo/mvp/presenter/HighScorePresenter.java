package com.kdg.zolo.mvp.presenter;

import com.kdg.zolo.mvp.model.ScoreManager;
import com.kdg.zolo.mvp.util.ScoreEntry;
import com.kdg.zolo.mvp.view.highscore.HighScoreView;
import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class HighScorePresenter {

    private final ScoreManager scoreManager;
    private final HighScoreView view;
    private final Stage stage;

    public HighScorePresenter(HighScoreView view) {
        this(view, null);
    }

    public HighScorePresenter(HighScoreView view, Stage stage) {
        this.scoreManager = ScoreManager.getInstance();
        this.view = view;
        this.stage = stage;
        initializePresenter();
    }

    private void initializePresenter() {
        updateScoreDisplay();
        addEventHandlers();
    }

    private void addEventHandlers() {
        if (stage != null) {
            view.getBackButton().setOnAction(e -> navigateToHome());
        }
    }

    private void navigateToHome() {
        HomeView homeView = new HomeView();
        new HomePresenter(homeView, stage);
        stage.setScene(new Scene(homeView));
        stage.setTitle("Zolo - KdG");
    }

    private void updateScoreDisplay() {
        List<ScoreEntry> scoreEntries = scoreManager.getHighScores()
                .stream()
                .map(score -> new ScoreEntry(
                score.getPlayerName(),
                score.getScore(),
                formatTime(score.getTime())
        ))
                .collect(Collectors.toList());

        view.updateScores(scoreEntries);
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
}
