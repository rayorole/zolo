package com.kdg.zolo.mvp.view.home;

import com.kdg.zolo.GameSetup;
import com.kdg.zolo.mvp.presenter.HighScorePresenter;
import com.kdg.zolo.mvp.view.about.AboutPresenter;
import com.kdg.zolo.mvp.view.about.AboutView;
import com.kdg.zolo.mvp.view.help.HelpPresenter;
import com.kdg.zolo.mvp.view.help.HelpView;
import com.kdg.zolo.mvp.view.highscore.HighScoreView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePresenter {

    private final HomeView view;
    private final Stage stage;
    private static final int DEFAULT_GRID_SIZE = 7;

    public HomePresenter(HomeView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Button event handlers
        view.getPlayButton().setOnAction(e -> startGame());
        view.getHighScoreButton().setOnAction(e -> showHighScores());
        view.getHelpButton().setOnAction(e -> showHelp());
        view.getAboutButton().setOnAction(e -> showAbout());
        view.getExitButton().setOnAction(e -> exitApplication());

        // Menu item event handlers
        view.getNewGameMenuItem().setOnAction(e -> startGame());
        view.getHighScoreMenuItem().setOnAction(e -> showHighScores());
        view.getHelpMenuItem().setOnAction(e -> showHelp());
        view.getAboutMenuItem().setOnAction(e -> showAbout());
        view.getExitMenuItem().setOnAction(e -> exitApplication());
    }

    private void startGame() {
        String difficulty = view.getSelectedDifficulty();
        int gridSize = getGridSizeForDifficulty(difficulty);
        GameSetup.setupGame(stage, gridSize, difficulty);
    }

    private int getGridSizeForDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                return 5;
            case "hard":
                return 9;
            case "medium":
            default:
                return 7;
        }
    }

    private void showHighScores() {
        HighScoreView highScoreView = new HighScoreView();
        new HighScorePresenter(highScoreView, stage);
        stage.setScene(new Scene(highScoreView));
        stage.setTitle("Zolo - Highscores");
    }

    private void showHelp() {
        HelpView helpView = new HelpView();
        new HelpPresenter(helpView, stage);
        stage.setScene(new Scene(helpView));
        stage.setTitle("Zolo - Help");
    }

    private void showAbout() {
        AboutView aboutView = new AboutView();
        new AboutPresenter(aboutView, stage);
        stage.setScene(new Scene(aboutView));
        stage.setTitle("Zolo - Over Ons");
    }

    private void exitApplication() {
        Platform.exit();
    }
}
