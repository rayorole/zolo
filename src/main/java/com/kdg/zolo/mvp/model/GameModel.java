package com.kdg.zolo.mvp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameModel {

    private final int gridSize;
    private final char[][] grid;
    private final List<Ship> ships;
    private final Stack<Move> moves;
    private final int[] rowHints;
    private final int[] colHints;
    private long startTime;
    private int score;
    private final char[][] initialGrid;

    public GameModel(int size) {
        this.gridSize = size;
        this.grid = new char[size][size];
        this.ships = new ArrayList<>();
        this.moves = new Stack<>();
        this.rowHints = new int[size];
        this.colHints = new int[size];
        this.initialGrid = new char[size][size];
        initializeShips();
        resetGrid();
    }

    public GameModel(PuzzleLoader.Puzzle puzzle) {
        this.gridSize = puzzle.getSize();
        this.grid = new char[gridSize][gridSize];
        this.ships = new ArrayList<>(puzzle.getShips());
        this.moves = new Stack<>();
        this.rowHints = puzzle.getRowHints();
        this.colHints = puzzle.getColHints();
        this.initialGrid = puzzle.getInitialGrid();
        resetGrid();
    }

    private void initializeShips() {
        ships.add(new Ship(4, "Battleship"));
        ships.add(new Ship(3, "Cruiser"));
        ships.add(new Ship(2, "Destroyer"));
        ships.add(new Ship(1, "Submarine"));
    }

    public void resetGrid() {
        // First clear the grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = ' ';
            }
        }

        // Then apply initial grid values if available
        if (initialGrid != null) {
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (initialGrid[i][j] != ' ') {
                        grid[i][j] = initialGrid[i][j];
                    }
                }
            }
        }

        // Reset ships
        for (Ship ship : ships) {
            ship.setPlaced(false);
        }

        moves.clear();
        startTime = System.currentTimeMillis();
        score = 0;
    }

    public boolean placeShip(int row, int col, Ship ship, boolean isHorizontal) {
        if (!isValidPlacement(row, col, ship, isHorizontal)) {
            return false;
        }

        // Place the ship
        for (int i = 0; i < ship.getLength(); i++) {
            if (isHorizontal) {
                grid[row][col + i] = 'S';
            } else {
                grid[row + i][col] = 'S';
            }
        }

        moves.push(new Move(row, col, ship, isHorizontal));
        ship.setPlaced(true);
        updateScore();
        return true;
    }

    public boolean isValidPlacement(int row, int col, Ship ship, boolean isHorizontal) {
        // Check boundaries
        if (isHorizontal) {
            if (col + ship.getLength() > gridSize) {
                return false;
            }
        } else {
            if (row + ship.getLength() > gridSize) {
                return false;
            }
        }

        // Check for overlapping ships
        for (int i = 0; i < ship.getLength(); i++) {
            int checkRow = isHorizontal ? row : row + i;
            int checkCol = isHorizontal ? col + i : col;

            if (grid[checkRow][checkCol] != ' ') {
                return false;
            }
        }

        return true;
    }

    public void undoMove() {
        if (!moves.isEmpty()) {
            Move move = moves.pop();
            int row = move.getRow();
            int col = move.getCol();
            Ship ship = move.getShip();

            for (int i = 0; i < ship.getLength(); i++) {
                if (move.isHorizontal()) {
                    grid[row][col + i] = ' ';
                } else {
                    grid[row + i][col] = ' ';
                }
            }

            ship.setPlaced(false);
            updateScore();
        }
    }

    private void updateScore() {
        // Basic scoring: 100 points per correct ship placement, -50 for each undo
        score = moves.size() * 100;
    }

    // Getters
    public char[][] getGrid() {
        return grid;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public int getScore() {
        return score;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean isGameComplete() {
        return ships.stream().allMatch(Ship::isPlaced) && validatePlacement();
    }

    private boolean validatePlacement() {
        // Validate row hints
        for (int i = 0; i < gridSize; i++) {
            int count = 0;
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 'S') {
                    count++;
                }
            }
            if (count != rowHints[i]) {
                return false;
            }
        }

        // Validate column hints
        for (int j = 0; j < gridSize; j++) {
            int count = 0;
            for (int i = 0; i < gridSize; i++) {
                if (grid[i][j] == 'S') {
                    count++;
                }
            }
            if (count != colHints[j]) {
                return false;
            }
        }

        return true;
    }

    public int[] getRowHints() {
        return rowHints;
    }

    public int[] getColHints() {
        return colHints;
    }

    public void setCellValue(int row, int col, char value) {
        if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
            // Check if this is a fixed cell from the initial grid
            if (initialGrid[row][col] != ' ') {
                return; // Cannot modify fixed cells
            }

            grid[row][col] = value;

            // Record the move for undo functionality
            if (value == 'S') {
                // For simplicity, we'll create a dummy ship for the move
                Ship dummyShip = new Ship(1, "Cell");
                moves.push(new Move(row, col, dummyShip, true));
            }
        }
    }

    public void validateAndMarkMistakes() {
        // First, make a copy of the current grid
        char[][] tempGrid = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(grid[i], 0, tempGrid[i], 0, gridSize);
        }

        // Check row hints
        for (int row = 0; row < gridSize; row++) {
            int shipCount = 0;
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] == 'S') {
                    shipCount++;
                }
            }

            // If the count doesn't match the hint, mark all ships in this row as mistakes
            if (shipCount != rowHints[row]) {
                for (int col = 0; col < gridSize; col++) {
                    if (grid[row][col] == 'S') {
                        tempGrid[row][col] = 'X'; // Mark as mistake
                    }
                }
            }
        }

        // Check column hints
        for (int col = 0; col < gridSize; col++) {
            int shipCount = 0;
            for (int row = 0; row < gridSize; row++) {
                if (grid[row][col] == 'S') {
                    shipCount++;
                }
            }

            // If the count doesn't match the hint, mark all ships in this column as mistakes
            if (shipCount != colHints[col]) {
                for (int row = 0; row < gridSize; row++) {
                    if (grid[row][col] == 'S') {
                        tempGrid[row][col] = 'X'; // Mark as mistake
                    }
                }
            }
        }

        // Update the grid with the mistakes
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(tempGrid[i], 0, grid[i], 0, gridSize);
        }
    }
}
