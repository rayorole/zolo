package com.kdg.zolo.mvp.view.game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView extends VBox {
    private Button newPuzzleButton;
    private Button resetButton;
    private Button helpButton;
    private Button[][] gridButtons;
    private Label[] columnHints;
    private Label[] rowHints;
    private Label solvedLabel;
    private int gridSize;
    private static final int MAX_WINDOW_SIZE = 600;

    public GameView(int size) {
        this.gridSize = size;
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        newPuzzleButton = new Button("Nieuw Puzzel");
        resetButton = new Button("Reset");
        helpButton = new Button("Help?");

        solvedLabel = new Label("Solved!");
        solvedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: green;");
        solvedLabel.setVisible(false);

        gridButtons = new Button[gridSize][gridSize];
        columnHints = new Label[gridSize];
        rowHints = new Label[gridSize];

        for (int i = 0; i < gridSize; i++) {
            columnHints[i] = new Label("0");
            rowHints[i] = new Label("0");
        }

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridButtons[row][col] = new Button(" ");
                int cellSize = MAX_WINDOW_SIZE / (gridSize + 2);
                gridButtons[row][col].setMinSize(cellSize, cellSize);
                gridButtons[row][col].setStyle("-fx-border-color: black; -fx-font-size: 12px;");
                final int r = row, c = col;
                gridButtons[row][col].setOnAction(e -> {
                    toggleCell(r, c);
                });
            }
        }
    }

    private void layoutNodes() {
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);

        HBox topButtons = new HBox(10);
        topButtons.setAlignment(Pos.TOP_RIGHT);
        topButtons.getChildren().add(helpButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);

        for (int col = 0; col < gridSize; col++) {
            gridPane.add(columnHints[col], col + 1, 0);
        }

        for (int row = 0; row < gridSize; row++) {
            gridPane.add(rowHints[row], 0, row + 1);
            for (int col = 0; col < gridSize; col++) {
                gridPane.add(gridButtons[row][col], col + 1, row + 1);
            }
        }

        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.getChildren().addAll(newPuzzleButton, resetButton);

        this.getChildren().addAll(topButtons, gridPane, solvedLabel, bottomButtons);
    }

    private void toggleCell(int row, int col) {
        String currentText = gridButtons[row][col].getText();
        if (currentText.equals(" ")) {
            gridButtons[row][col].setText("🚢");
        } else if (currentText.equals("🚢")) {
            gridButtons[row][col].setText("🌊");
        } else {
            gridButtons[row][col].setText(" ");
        }
    }

    public Button getNewPuzzleButton() {
        return newPuzzleButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getHelpButton() {
        return helpButton;
    }

    public Label[] getColumnHints() {
        return columnHints;
    }

    public Label[] getRowHints() {
        return rowHints;
    }

    public Button[][] getGridButtons() {
        return gridButtons;
    }

    public Label getSolvedLabel() {
        return solvedLabel;
    }
}
