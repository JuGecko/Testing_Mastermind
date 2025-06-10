package dev.jugecko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighscoreManager {
    private List<Score> scores;

    public HighscoreManager() {
        this.scores = new ArrayList<>();
    }

    public void displayHighscores() {
        System.out.println("===== NAJLEPSZE WYNIKI =====");

        if (scores.isEmpty()) {
            System.out.println("Brak zapisanych wyników");
        } else {
            // Sortowanie wynikow
            List<Score> sortedScores = new ArrayList<>(scores);
            Collections.sort(sortedScores, Comparator.comparingInt(Score::getAttempts));

            int position = 1;
            for (Score score : sortedScores) {
                System.out.println(position + ". " + score.getName() + " - " + score.getAttempts() + " prób");
                position++;
                if (position > 10) break;
                // Pokazuj tylko top 10
            }
        }

        System.out.println("===========================");
    }

    public void addHighscore(String name, int attempts) {
        scores.add(new Score(name, attempts));
        System.out.println("Zapisano wynik dla: " + name + " - " + attempts + " prób");
    }

    private static class Score {
        private String name;
        private int attempts;

        public Score(String name, int attempts) {
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
