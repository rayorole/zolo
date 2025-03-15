package com.kdg.zolo.mvp.view.game;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.kdg.zolo.mvp.model.Ship;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameView extends BorderPane {

    private Button newPuzzleButton;
    private Button resetButton;
    private Button helpButton;
    private Button undoButton;
    private Button rotateButton;
    private Button showMistakesButton;
    private Button[][] gridButtons;
    private Label[] columnHints;
    private Label[] rowHints;
    private Label solvedLabel;
    private Label timerLabel;
    private Label scoreLabel;
    private VBox shipContainer;
    private Label rotationIndicator;
    private int gridSize;
    private static final int MAX_WINDOW_SIZE = 600;

    public GameView(int size) {
        this.gridSize = size;
        this.initialiseNodes();
        this.layoutNodes();
        this.applyStyles();
    }

    private void initialiseNodes() {
        newPuzzleButton = new Button("Nieuw Puzzel");
        resetButton = new Button("Reset");
        helpButton = new Button("Help?");
        undoButton = new Button("Ongedaan maken");
        rotateButton = new Button("Draaien");
        showMistakesButton = new Button("Show Mistakes");

        solvedLabel = new Label("Opgelost!");
        solvedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: green;");
        solvedLabel.setVisible(false);

        timerLabel = new Label("00:00");
        scoreLabel = new Label("Score: 0");
        rotationIndicator = new Label("Horizontaal");

        gridButtons = new Button[gridSize][gridSize];
        columnHints = new Label[gridSize];
        rowHints = new Label[gridSize];
        shipContainer = new VBox(10);

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
            }
        }
    }

    private void layoutNodes() {
        // Top section with help button and timer
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.getChildren().addAll(timerLabel, helpButton);
        this.setTop(topBar);

        // Center section with grid
        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));

        // Add column hints
        for (int col = 0; col < gridSize; col++) {
            gridPane.add(columnHints[col], col + 1, 0);
        }

        // Add row hints and grid buttons
        for (int row = 0; row < gridSize; row++) {
            gridPane.add(rowHints[row], 0, row + 1);
            for (int col = 0; col < gridSize; col++) {
                gridPane.add(gridButtons[row][col], col + 1, row + 1);
            }
        }

        // Add solved label below grid
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(gridPane, solvedLabel);
        this.setCenter(centerBox);

        // Right section with ships
        VBox rightPanel = new VBox(15);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.CENTER);

        Label shipsLabel = new Label("Schepen");
        shipsLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        rightPanel.getChildren().addAll(shipsLabel, shipContainer, rotationIndicator, rotateButton);
        this.setRight(rightPanel);

        // Bottom section with buttons and score
        HBox bottomBar = new HBox(10);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));
        bottomBar.getChildren().addAll(newPuzzleButton, resetButton, undoButton, showMistakesButton, scoreLabel);
        this.setBottom(bottomBar);
    }

    private void applyStyles() {
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f0f8ff;");

        String buttonStyle = "-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold;";
        newPuzzleButton.setStyle(buttonStyle);
        resetButton.setStyle(buttonStyle);
        helpButton.setStyle(buttonStyle);
        undoButton.setStyle(buttonStyle);
        rotateButton.setStyle(buttonStyle);
        showMistakesButton.setStyle(buttonStyle);

        timerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        for (int i = 0; i < gridSize; i++) {
            columnHints[i].setFont(Font.font("System", FontWeight.BOLD, 14));
            rowHints[i].setFont(Font.font("System", FontWeight.BOLD, 14));
        }
    }

    public void toggleCell(int row, int col) {
        String currentText = gridButtons[row][col].getText();
        String currentStyle = gridButtons[row][col].getStyle();

        if (currentStyle.contains("#E0E0E0")) {
            // Empty to Ship
            gridButtons[row][col].setText("");
            gridButtons[row][col].setStyle(
                    "-fx-background-color: #87CEFA; "
                    + "-fx-border-color: black; "
                    + "-fx-background-radius: 15; "
                    + "-fx-border-radius: 15;"
            );
        } else if (currentStyle.contains("#87CEFA")) {
            // Ship to Water
            gridButtons[row][col].setText("");
            gridButtons[row][col].setStyle(
                    "-fx-background-color: #1E90FF; "
                    + "-fx-border-color: black; "
                    + "-fx-background-radius: 15; "
                    + "-fx-border-radius: 15;"
            );
        } else {
            // Water to Empty
            gridButtons[row][col].setText("");
            gridButtons[row][col].setStyle(
                    "-fx-background-color: #E0E0E0; "
                    + "-fx-border-color: black;"
            );
        }
    }

    public void updateGrid(char[][] grid) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] == 'S') {
                    gridButtons[row][col].setText("");
                    gridButtons[row][col].setStyle(
                            "-fx-background-color: #87CEFA; "
                            + "-fx-border-color: black; "
                            + "-fx-background-radius: 15; "
                            + "-fx-border-radius: 15;"
                    );
                } else if (grid[row][col] == 'W') {
                    gridButtons[row][col].setText("");
                    gridButtons[row][col].setStyle(
                            "-fx-background-color: #1E90FF; "
                            + "-fx-border-color: black; "
                            + "-fx-background-radius: 15; "
                            + "-fx-border-radius: 15;"
                    );
                } else if (grid[row][col] == 'X') {
                    // Show mistake
                    gridButtons[row][col].setText("");
                    gridButtons[row][col].setStyle(
                            "-fx-background-color: #FF6347; "
                            + "-fx-border-color: black; "
                            + "-fx-background-radius: 15; "
                            + "-fx-border-radius: 15;"
                    );
                } else {
                    gridButtons[row][col].setText("");
                    gridButtons[row][col].setStyle(
                            "-fx-background-color: #E0E0E0; "
                            + "-fx-border-color: black;"
                    );
                }
            }
        }
    }

    public void updateShipDisplay(List<Ship> ships) {
        shipContainer.getChildren().clear();
        for (Ship ship : ships) {
            HBox shipBox = new HBox();
            shipBox.setAlignment(Pos.CENTER_LEFT);

            Label shipLabel = new Label(ship.getType() + " (" + ship.getLength() + ")");
            shipLabel.setMinWidth(100);

            Button shipButton = new Button();
            shipButton.setMinWidth(ship.getLength() * 30);
            shipButton.setMinHeight(30);
            shipButton.setStyle("-fx-background-color: " + (ship.isPlaced() ? "#A9A9A9" : "#4682b4"));
            shipButton.setDisable(ship.isPlaced());

            shipBox.getChildren().addAll(shipLabel, shipButton);
            shipContainer.getChildren().add(shipBox);
        }
    }

    public void updateTimer(String time) {
        timerLabel.setText(time);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateRotationIndicator(boolean isHorizontal) {
        rotationIndicator.setText(isHorizontal ? "Horizontaal" : "Verticaal");
    }

    public void setShipDragHandlers(Ship ship,
            BiConsumer<MouseEvent, Ship> onDragDetected,
            Consumer<DragEvent> onDragOver,
            Consumer<DragEvent> onDragDropped) {
        // Find the ship button in the shipContainer
        for (int i = 0; i < shipContainer.getChildren().size(); i++) {
            HBox shipBox = (HBox) shipContainer.getChildren().get(i);
            Button shipButton = (Button) shipBox.getChildren().get(1);

            if (shipBox.getChildren().get(0) instanceof Label) {
                Label shipLabel = (Label) shipBox.getChildren().get(0);
                if (shipLabel.getText().contains(ship.getType())) {
                    // Set drag detected handler
                    shipButton.setOnDragDetected(event -> {
                        if (!ship.isPlaced()) {
                            onDragDetected.accept(event, ship);
                        }
                    });
                    break;
                }
            }
        }

        // Set drag over and dropped handlers on grid buttons
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridButtons[row][col].setOnDragOver(onDragOver::accept);
                gridButtons[row][col].setOnDragDropped(onDragDropped::accept);
            }
        }
    }

    public void startShipDrag(MouseEvent event, Ship ship, boolean isHorizontal) {
        // Start drag operation
        javafx.scene.input.Dragboard db = ((Button) event.getSource()).startDragAndDrop(javafx.scene.input.TransferMode.MOVE);

        // Create a snapshot of the ship for drag image
        javafx.scene.image.WritableImage snapshot = new javafx.scene.image.WritableImage(
                (int) (ship.getLength() * 30), 30);
        ((Button) event.getSource()).snapshot(null, snapshot);

        // Set drag content
        javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
        content.putString(ship.getType() + "," + ship.getLength() + "," + (isHorizontal ? "H" : "V"));
        db.setContent(content);
        db.setDragView(snapshot);

        event.consume();
    }

    public int[] getGridPosition(double x, double y) {
        // Calculate grid position from coordinates
        int row = (int) (y / (MAX_WINDOW_SIZE / (gridSize + 2)));
        int col = (int) (x / (MAX_WINDOW_SIZE / (gridSize + 2)));
        return new int[]{row, col};
    }

    public String showVictoryDialog(int score, String time) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gefeliciteerd!");
        dialog.setHeaderText("Je hebt de puzzel opgelost!");
        dialog.setContentText("Score: " + score + "\nTijd: " + time + "\n\nVoer je naam in:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void setOnUndoClicked(EventHandler<ActionEvent> handler) {
        undoButton.setOnAction(handler);
    }

    public void setOnResetClicked(EventHandler<ActionEvent> handler) {
        resetButton.setOnAction(handler);
    }

    public void setOnRotateClicked(EventHandler<ActionEvent> handler) {
        rotateButton.setOnAction(handler);
    }

    public void setOnShowMistakesClicked(EventHandler<ActionEvent> handler) {
        showMistakesButton.setOnAction(handler);
    }

    public void updateHints(int[] rowHints, int[] colHints) {
        // Update row hints
        for (int i = 0; i < rowHints.length && i < this.rowHints.length; i++) {
            this.rowHints[i].setText(String.valueOf(rowHints[i]));
        }

        // Update column hints
        for (int i = 0; i < colHints.length && i < this.columnHints.length; i++) {
            this.columnHints[i].setText(String.valueOf(colHints[i]));
        }
    }

    // Original getters
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
