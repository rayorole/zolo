package com.kdg.zolo.mvp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PuzzleLoader {

    private static final String PUZZLES_DIR = "puzzles/";
    private Map<String, List<Puzzle>> puzzlesByDifficulty;

    public PuzzleLoader() {
        puzzlesByDifficulty = new HashMap<>();
        loadPuzzles();
    }

    private void loadPuzzles() {
        // Initialize difficulty levels
        puzzlesByDifficulty.put("easy", new ArrayList<>());
        puzzlesByDifficulty.put("medium", new ArrayList<>());
        puzzlesByDifficulty.put("hard", new ArrayList<>());

        // Try to load puzzles from files
        try {
            loadPuzzlesFromFiles();
        } catch (IOException e) {
            System.err.println("Error loading puzzles from files: " + e.getMessage());
            // Fall back to hardcoded puzzles if file loading fails
            createHardcodedPuzzles();
        }
    }

    private void loadPuzzlesFromFiles() throws IOException {
        File puzzlesDir = new File(PUZZLES_DIR);
        if (!puzzlesDir.exists() || !puzzlesDir.isDirectory()) {
            // Create directory and example puzzles if it doesn't exist
            puzzlesDir.mkdirs();
            createExamplePuzzleFiles();
            return;
        }

        // Load all puzzle files
        for (File file : puzzlesDir.listFiles((dir, name) -> name.endsWith(".txt"))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String difficulty = reader.readLine().trim().toLowerCase();
                int size = Integer.parseInt(reader.readLine().trim());

                // Read row hints
                String rowHintsLine = reader.readLine().trim();
                int[] rowHints = Arrays.stream(rowHintsLine.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                // Read column hints
                String colHintsLine = reader.readLine().trim();
                int[] colHints = Arrays.stream(colHintsLine.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                // Read ships
                List<Ship> ships = new ArrayList<>();
                String shipsLine = reader.readLine().trim();
                for (String shipInfo : shipsLine.split(";")) {
                    String[] parts = shipInfo.split(",");
                    int length = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    ships.add(new Ship(length, type));
                }

                // Read initial cells (if any)
                char[][] initialGrid = new char[size][size];
                for (int i = 0; i < size; i++) {
                    Arrays.fill(initialGrid[i], ' ');
                }

                String initialCellsLine = reader.readLine();
                if (initialCellsLine != null && !initialCellsLine.trim().isEmpty()) {
                    for (String cellInfo : initialCellsLine.trim().split(";")) {
                        String[] parts = cellInfo.split(",");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        char value = parts[2].charAt(0);
                        initialGrid[row][col] = value;
                    }
                }

                // Create and add the puzzle
                Puzzle puzzle = new Puzzle(size, rowHints, colHints, ships, initialGrid);

                // Add to appropriate difficulty list
                if (puzzlesByDifficulty.containsKey(difficulty)) {
                    puzzlesByDifficulty.get(difficulty).add(puzzle);
                } else {
                    // Default to medium if difficulty not recognized
                    puzzlesByDifficulty.get("medium").add(puzzle);
                }
            }
        }
    }

    private void createExamplePuzzleFiles() throws IOException {
        // Create example puzzles
        createExamplePuzzle("easy", 5);
        createExamplePuzzle("medium", 7);
        createExamplePuzzle("hard", 9);
    }

    private void createExamplePuzzle(String difficulty, int size) throws IOException {
        File file = new File(PUZZLES_DIR + "example_" + difficulty + ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(difficulty);
            writer.println(size);

            // Row hints
            StringBuilder rowHints = new StringBuilder();
            for (int i = 0; i < size; i++) {
                rowHints.append(i % 3 + 1);
                if (i < size - 1) {
                    rowHints.append(",");
                }
            }
            writer.println(rowHints.toString());

            // Column hints
            StringBuilder colHints = new StringBuilder();
            for (int i = 0; i < size; i++) {
                colHints.append(i % 3 + 1);
                if (i < size - 1) {
                    colHints.append(",");
                }
            }
            writer.println(colHints.toString());

            // Ships
            writer.println("4,Battleship;3,Cruiser;2,Destroyer;1,Submarine");

            // Initial cells (for example, 5 cells for medium difficulty)
            if (difficulty.equals("easy")) {
                writer.println("0,0,S;1,1,S;2,2,S");
            } else if (difficulty.equals("medium")) {
                writer.println("0,0,S;1,1,S;2,2,S;3,3,S;4,4,S");
            } else {
                writer.println("0,0,S;2,2,S");
            }
        }
    }

    private void createHardcodedPuzzles() {
        // Create hardcoded puzzles for each difficulty
        createHardcodedPuzzle("easy", 5);
        createHardcodedPuzzle("medium", 7);
        createHardcodedPuzzle("hard", 9);
    }

    private void createHardcodedPuzzle(String difficulty, int size) {
        // Create row and column hints
        int[] rowHints = new int[size];
        int[] colHints = new int[size];

        for (int i = 0; i < size; i++) {
            rowHints[i] = i % 3 + 1;
            colHints[i] = i % 3 + 1;
        }

        // Create ships
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(4, "Battleship"));
        ships.add(new Ship(3, "Cruiser"));
        ships.add(new Ship(2, "Destroyer"));
        ships.add(new Ship(1, "Submarine"));

        // Create initial grid
        char[][] initialGrid = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(initialGrid[i], ' ');
        }

        // Add some initial cells based on difficulty
        int initialCells = difficulty.equals("easy") ? 5
                : (difficulty.equals("medium") ? 3 : 1);

        Random random = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < initialCells; i++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            initialGrid[row][col] = 'S';
        }

        // Create and add the puzzle
        Puzzle puzzle = new Puzzle(size, rowHints, colHints, ships, initialGrid);
        puzzlesByDifficulty.get(difficulty).add(puzzle);
    }

    public Puzzle getRandomPuzzle(String difficulty) {
        List<Puzzle> puzzles = puzzlesByDifficulty.getOrDefault(difficulty, puzzlesByDifficulty.get("medium"));
        if (puzzles.isEmpty()) {
            // Fall back to medium if no puzzles for requested difficulty
            puzzles = puzzlesByDifficulty.get("medium");
        }

        Random random = new Random();
        return puzzles.get(random.nextInt(puzzles.size()));
    }

    public List<String> getAvailableDifficulties() {
        List<String> difficulties = new ArrayList<>();
        for (String difficulty : puzzlesByDifficulty.keySet()) {
            if (!puzzlesByDifficulty.get(difficulty).isEmpty()) {
                difficulties.add(difficulty);
            }
        }
        return difficulties;
    }

    public static class Puzzle {

        private final int size;
        private final int[] rowHints;
        private final int[] colHints;
        private final List<Ship> ships;
        private final char[][] initialGrid;

        public Puzzle(int size, int[] rowHints, int[] colHints, List<Ship> ships, char[][] initialGrid) {
            this.size = size;
            this.rowHints = rowHints;
            this.colHints = colHints;
            this.ships = ships;
            this.initialGrid = initialGrid;
        }

        public int getSize() {
            return size;
        }

        public int[] getRowHints() {
            return rowHints;
        }

        public int[] getColHints() {
            return colHints;
        }

        public List<Ship> getShips() {
            return ships;
        }

        public char[][] getInitialGrid() {
            return initialGrid;
        }
    }
}
