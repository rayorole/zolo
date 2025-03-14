package com.kdg.zolo;

import com.kdg.zolo.mvp.model.GameModel;
import com.kdg.zolo.mvp.model.PuzzleLoader;
import com.kdg.zolo.mvp.presenter.GamePresenter;
import com.kdg.zolo.mvp.view.game.GameView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameSetup {

    public static void setupGame(Stage primaryStage, int gridSize) {
        setupGame(primaryStage, gridSize, "medium");
    }

    public static void setupGame(Stage primaryStage, int gridSize, String difficulty) {
        PuzzleLoader loader = new PuzzleLoader();
        PuzzleLoader.Puzzle puzzle = loader.getRandomPuzzle(difficulty);
        GameModel model = new GameModel(puzzle);
        GameView view = new GameView(gridSize);
        GamePresenter presenter = new GamePresenter(model, view, primaryStage);

        primaryStage.setScene(new Scene(view));
        primaryStage.setTitle("Zolo - Battleship Puzzle - " + difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1));
    }
}
