package dev.jugecko;

import java.io.IOException;
import java.util.List;

public class HighscoreManager {
    private final IDatabase database;

    public HighscoreManager(IDatabase database) {
        this.database = database;
    }

    public void addHighscore(String name, int attempts) {
        try {
            database.saveGame(name, attempts);
        } catch (IOException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }

    public void displayHighscores() {
        try {
            List<ScoreEntry> highscores = database.loadTopScores(3);
            System.out.println("--- Highscores ---");
            for (ScoreEntry entry : highscores) {
                System.out.println(entry.getName() + ": " + entry.getAttempts() + " attempts");
            }
        } catch (IOException e) {
            System.err.println("Error loading highscores: " + e.getMessage());
        }
    }

    public static class ScoreEntry {
        private final String name;
        private final int attempts;

        public ScoreEntry(String name, int attempts) {
            this.name = name;
            this.attempts = attempts;
        }

        public String getName() {
            return name;
        }

        public int getAttempts() {
            return attempts;
        }
    }
}
