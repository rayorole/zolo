package com.kdg.zolo.mvp.view.game;

import com.kdg.zolo.mvp.view.help.HelpPresenter;
import com.kdg.zolo.mvp.view.help.HelpView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Random;

public class GamePresenter {
    private final GameView view;
    private final Stage stage;
    private final int gridSize;
    private int[] columnHints;
    private int[] rowHints;
    private char[][] solutionGrid;

    public GamePresenter(GameView view, Stage stage, int gridSize) {
        this.view = view;
        this.stage = stage;
        this.gridSize = gridSize;

        this.addEventHandlers();
        this.generateNewPuzzle();
    }

    private void addEventHandlers() {
        view.getNewPuzzleButton().setOnAction(e -> generateNewPuzzle());
        view.getResetButton().setOnAction(e -> resetGrid());
        view.getHelpButton().setOnAction(e -> goToHelp());
    }

    private void generateNewPuzzle() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                view.getGridButtons()[row][col].setText("⬜");
                view.getGridButtons()[row][col].setDisable(false);
            }
        }

        solutionGrid = new char[gridSize][gridSize];
        columnHints = new int[gridSize];
        rowHints = new int[gridSize];

        Random random = new Random();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                solutionGrid[i][j] = random.nextBoolean() ? '\u26F5' : '\u26F5'; // Fix emoji unicode @Alexander
            }
        }

        for (int i = 0; i < gridSize; i++) {
            rowHints[i] = (int) new String(solutionGrid[i]).chars().filter(c -> c == '⛵').count();
            columnHints[i] = (int) new String(getColumn(solutionGrid, i)).chars().filter(c -> c == '⛵').count();
        }

        int fixedCells = gridSize / 2;
        for (int i = 0; i < fixedCells; i++) {
            int row, col;
            do {
                row = random.nextInt(gridSize);
                col = random.nextInt(gridSize);
            } while (!view.getGridButtons()[row][col].getText().equals("⬜"));

            view.getGridButtons()[row][col].setText("⛵");
            view.getGridButtons()[row][col].setDisable(true);
        }

        updateHints();
        view.getSolvedLabel().setVisible(false);
    }

    private char[] getColumn(char[][] grid, int col) {
        char[] column = new char[gridSize];
        for (int i = 0; i < gridSize; i++) {
            column[i] = grid[i][col];
        }
        return column;
    }

    private void resetGrid() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (!view.getGridButtons()[row][col].isDisabled()) {
                    view.getGridButtons()[row][col].setText("⬜");
                }
            }
        }
        view.getSolvedLabel().setVisible(false);
    }

    private void goToHelp() {
        HelpView helpView = new HelpView();
        new HelpPresenter(helpView, stage);
        stage.setScene(new Scene(helpView, 400, 300));
    }

    private void updateHints() {
        for (int i = 0; i < gridSize; i++) {
            view.getColumnHints()[i].setText(String.valueOf(columnHints[i]));
            view.getRowHints()[i].setText(String.valueOf(rowHints[i]));
        }
    }
}
