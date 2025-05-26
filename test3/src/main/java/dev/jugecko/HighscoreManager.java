package dev.jugecko;

import java.util.*;
import java.io.*;

// HighscoreManager class - handles saving and loading highscores
class HighscoreManager {
    private final String filename;
    private final List<ScoreEntry> highscores = new ArrayList<>();

    public HighscoreManager(String filename) {
        this.filename = filename;
        loadHighscores();
    }

    public void addHighscore(String name, int attempts) {
        highscores.add(new ScoreEntry(name, attempts));
        highscores.sort(Comparator.comparingInt(ScoreEntry::getAttempts));
        saveHighscores();
    }

    public void displayHighscores() {
        System.out.println("--- Highscores ---");
        for (ScoreEntry entry : highscores) {
            System.out.println(entry.getName() + ": " + entry.getAttempts() + " attempts");
        }
    }

    private void loadHighscores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    highscores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException ignored) {
        }
    }

    private void saveHighscores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (ScoreEntry entry : highscores) {
                writer.write(entry.getName() + "," + entry.getAttempts());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save highscores: " + e.getMessage());
        }
    }

    private static class ScoreEntry {
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

