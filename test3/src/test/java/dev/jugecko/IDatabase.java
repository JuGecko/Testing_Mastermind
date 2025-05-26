package dev.jugecko;

import java.io.IOException;
import java.util.List;

public interface IDatabase {
    void saveGame(String name, int attempts) throws IOException;
    List<HighscoreManager.ScoreEntry> loadTopScores(int limit) throws IOException;
}
