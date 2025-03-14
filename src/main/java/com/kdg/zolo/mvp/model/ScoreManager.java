package com.kdg.zolo.mvp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreManager {

    private static final String SCORES_FILE = "score.txt";
    private static ScoreManager instance;
    private Map<String, List<Score>> scoresByDifficulty;

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    private ScoreManager() {
        scoresByDifficulty = new HashMap<>();
        scoresByDifficulty.put("easy", new ArrayList<>());
        scoresByDifficulty.put("medium", new ArrayList<>());
        scoresByDifficulty.put("hard", new ArrayList<>());
        loadScores();
    }

    public void addScore(String playerName, int score, long time, String difficulty) {
        List<Score> scores = scoresByDifficulty.get(difficulty.toLowerCase());
        if (scores == null) {
            scores = new ArrayList<>();
            scoresByDifficulty.put(difficulty.toLowerCase(), scores);
        }

        scores.add(new Score(playerName, score, time));
        Collections.sort(scores);

        // Keep only top 20 scores per difficulty
        if (scores.size() > 20) {
            scores = scores.subList(0, 20);
            scoresByDifficulty.put(difficulty.toLowerCase(), scores);
        }

        saveScores();
    }

    public List<Score> getHighScores() {
        // Return all scores from all difficulties
        List<Score> allScores = new ArrayList<>();
        for (List<Score> scores : scoresByDifficulty.values()) {
            allScores.addAll(scores);
        }
        Collections.sort(allScores);
        return allScores;
    }

    public List<Score> getHighScores(String difficulty) {
        return new ArrayList<>(scoresByDifficulty.getOrDefault(difficulty.toLowerCase(), new ArrayList<>()));
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            String currentDifficulty = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("Difficulty:")) {
                    currentDifficulty = line.substring("Difficulty:".length()).trim().toLowerCase();
                    if (!scoresByDifficulty.containsKey(currentDifficulty)) {
                        scoresByDifficulty.put(currentDifficulty, new ArrayList<>());
                    }
                } else if (currentDifficulty != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        long time = Long.parseLong(parts[2]);

                        scoresByDifficulty.get(currentDifficulty).add(new Score(playerName, score, time));
                    }
                }
            }

            // Sort all score lists
            for (List<Score> scores : scoresByDifficulty.values()) {
                Collections.sort(scores);
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's fine
        } catch (IOException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
        }
    }

    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE))) {
            for (Map.Entry<String, List<Score>> entry : scoresByDifficulty.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    writer.println("Difficulty: " + entry.getKey());

                    for (Score score : entry.getValue()) {
                        writer.println(score.getPlayerName() + "," + score.getScore() + "," + score.getTime());
                    }

                    writer.println(); // Empty line between difficulties
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    public static class Score implements Serializable, Comparable<Score> {

        private static final long serialVersionUID = 1L;
        private final String playerName;
        private final int score;
        private final long time;

        public Score(String playerName, int score, long time) {
            this.playerName = playerName;
            this.score = score;
            this.time = time;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        public long getTime() {
            return time;
        }

        @Override
        public int compareTo(Score other) {
            // First sort by score (descending)
            int scoreCompare = Integer.compare(other.score, this.score);
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            // Then by time (ascending)
            return Long.compare(this.time, other.time);
        }
    }
}
