package com.kdg.zolo.mvp.presenter;

import com.kdg.zolo.mvp.model.GameModel;
import com.kdg.zolo.mvp.model.ScoreManager;
import com.kdg.zolo.mvp.model.Ship;
import com.kdg.zolo.mvp.view.game.GameView;
import com.kdg.zolo.mvp.view.help.HelpPresenter;
import com.kdg.zolo.mvp.view.help.HelpView;
import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;

public class GamePresenter {

    private final GameModel model;
    private final GameView view;
    private Ship selectedShip;
    private boolean isHorizontal = true;
    private Timeline gameTimer;
    private Stage stage;

    public GamePresenter(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        initializePresenter();
    }

    public GamePresenter(GameModel model, GameView view, Stage stage) {
        this.model = model;
        this.view = view;
        this.stage = stage;
        initializePresenter();
    }

    private void initializePresenter() {
        // Bind model data to view
        updateViewGrid();
        initializeShipDragHandlers();
        initializeGameTimer();
        initializeCellClickHandlers();

        // Add event handlers
        view.setOnUndoClicked(e -> handleUndo());
        view.setOnResetClicked(e -> handleReset());
        view.setOnRotateClicked(e -> handleRotate());
        view.getHelpButton().setOnAction(e -> goToHelp());
        view.getNewPuzzleButton().setOnAction(e -> handleNewPuzzle());
        view.setOnShowMistakesClicked(e -> showMistakes());
    }

    private void initializeGameTimer() {
        gameTimer = new Timeline();
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        e -> view.updateTimer(formatTime(model.getElapsedTime())))
        );
        gameTimer.play();
    }

    private void handleUndo() {
        model.undoMove();
        updateViewGrid();
        checkGameCompletion();
    }

    private void handleReset() {
        model.resetGrid();
        updateViewGrid();
        gameTimer.playFromStart();
    }

    private void handleNewPuzzle() {
        // Logic to generate a new puzzle
        model.resetGrid();
        updateViewGrid();
        gameTimer.playFromStart();
    }

    private void handleRotate() {
        isHorizontal = !isHorizontal;
        view.updateRotationIndicator(isHorizontal);
    }

    private void goToHelp() {
        if (stage != null) {
            HelpView helpView = new HelpView();
            new HelpPresenter(helpView, stage);
            stage.setScene(new Scene(helpView));
        }
    }

    private void goToHome() {
        if (stage != null) {
            HomeView homeView = new HomeView();
            new HomePresenter(homeView, stage);
            stage.setScene(new Scene(homeView));
        }
    }

    private void initializeShipDragHandlers() {
        for (Ship ship : model.getShips()) {
            view.setShipDragHandlers(ship,
                    this::handleShipDragDetected,
                    this::handleShipDragOver,
                    this::handleShipDragDropped
            );
        }
    }

    private void handleShipDragDetected(MouseEvent event, Ship ship) {
        if (!ship.isPlaced()) {
            selectedShip = ship;
            view.startShipDrag(event, ship, isHorizontal);
        }
    }

    private void handleShipDragOver(DragEvent event) {
        if (selectedShip != null) {
            int[] gridPos = view.getGridPosition(event.getX(), event.getY());
            if (isValidDropLocation(gridPos[0], gridPos[1])) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
        event.consume();
    }

    private void handleShipDragDropped(DragEvent event) {
        if (selectedShip != null) {
            int[] gridPos = view.getGridPosition(event.getX(), event.getY());
            if (model.placeShip(gridPos[0], gridPos[1], selectedShip, isHorizontal)) {
                updateViewGrid();
                checkGameCompletion();
            }
        }
        selectedShip = null;
        event.consume();
    }

    private boolean isValidDropLocation(int row, int col) {
        return row >= 0 && col >= 0 && row < model.getGrid().length && col < model.getGrid()[0].length
                && model.isValidPlacement(row, col, selectedShip, isHorizontal);
    }

    private void updateViewGrid() {
        view.updateGrid(model.getGrid());
        view.updateScore(model.getScore());
        view.updateShipDisplay(model.getShips());
    }

    private void checkGameCompletion() {
        if (model.isGameComplete()) {
            gameTimer.stop();
            showVictoryDialog();
        }
    }

    private void showVictoryDialog() {
        String playerName = view.showVictoryDialog(
                model.getScore(),
                formatTime(model.getElapsedTime())
        );
        if (playerName != null) {
            // Get difficulty from stage title
            String title = stage.getTitle();
            String difficulty = "medium"; // Default

            if (title.contains("Easy")) {
                difficulty = "easy";
            } else if (title.contains("Hard")) {
                difficulty = "hard";
            }

            ScoreManager.getInstance().addScore(
                    playerName,
                    model.getScore(),
                    model.getElapsedTime(),
                    difficulty
            );
            goToHome();
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    private void initializeCellClickHandlers() {
        Button[][] gridButtons = view.getGridButtons();
        for (int row = 0; row < gridButtons.length; row++) {
            for (int col = 0; col < gridButtons[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                gridButtons[row][col].setOnAction(e -> handleCellClick(finalRow, finalCol));
            }
        }
    }

    private void handleCellClick(int row, int col) {
        // Toggle cell state: empty -> ship -> water -> empty
        char currentCell = model.getGrid()[row][col];

        if (currentCell == ' ') {
            // Place ship
            model.setCellValue(row, col, 'S');
        } else if (currentCell == 'S') {
            // Place water
            model.setCellValue(row, col, 'W');
        } else {
            // Clear cell
            model.setCellValue(row, col, ' ');
        }

        updateViewGrid();
        checkGameCompletion();
    }

    private void showMistakes() {
        model.validateAndMarkMistakes();
        updateViewGrid();
    }
}
