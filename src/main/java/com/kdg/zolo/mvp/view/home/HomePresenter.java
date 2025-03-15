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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePresenter {

    private final HomeView view;
    private final Stage stage;
    private static final int DEFAULT_GRID_SIZE = 7;
    private int selectedGridSize = DEFAULT_GRID_SIZE;

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

        // Grid size button handlers
        Button[] gridSizeButtons = view.getGridSizeButtons();
        gridSizeButtons[0].setOnAction(e -> selectedGridSize = 6);
        gridSizeButtons[1].setOnAction(e -> selectedGridSize = 7);
        gridSizeButtons[2].setOnAction(e -> selectedGridSize = 8);
        gridSizeButtons[3].setOnAction(e -> selectedGridSize = 10);
        gridSizeButtons[4].setOnAction(e -> selectedGridSize = 12);
        gridSizeButtons[5].setOnAction(e -> selectedGridSize = 15);

        // Highlight the default selected grid size
        updateGridSizeButtonStyles(DEFAULT_GRID_SIZE);

        // Add listeners to update button styles when a grid size is selected
        for (Button button : gridSizeButtons) {
            button.setOnAction(e -> {
                String sizeText = button.getText().trim();
                int size = Integer.parseInt(sizeText.split("x")[0].trim());
                selectedGridSize = size;
                updateGridSizeButtonStyles(size);
            });
        }

        // Menu item event handlers
        view.getNewGameMenuItem().setOnAction(e -> startGame());
        view.getHighScoreMenuItem().setOnAction(e -> showHighScores());
        view.getHelpMenuItem().setOnAction(e -> showHelp());
        view.getAboutMenuItem().setOnAction(e -> showAbout());
        view.getExitMenuItem().setOnAction(e -> exitApplication());
    }

    private void updateGridSizeButtonStyles(int selectedSize) {
        Button[] buttons = view.getGridSizeButtons();
        for (Button button : buttons) {
            String sizeText = button.getText().trim();
            int size = Integer.parseInt(sizeText.split("x")[0].trim());

            if (size == selectedSize) {
                button.setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white; -fx-font-weight: bold;");
            } else {
                button.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold;");
            }
        }
    }

    private void startGame() {
        String difficulty = view.getSelectedDifficulty();
        GameSetup.setupGame(stage, selectedGridSize, difficulty);
    }

    private int getGridSizeForDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                return 6;
            case "medium":
                return 8;
            case "hard":
                return 10;
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
