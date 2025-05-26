package dev.jugecko;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class HighscoreManagerTest {

    @Test
    public void testAddHighscore_withMockedDatabase() throws IOException {
        IDatabase mockDb = mock(IDatabase.class);
        HighscoreManager manager = new HighscoreManager(mockDb);

        manager.addHighscore("Alice", 3);

        verify(mockDb, times(1)).saveGame("Alice", 3);
    }

    @Test
    public void testDisplayHighscores_outputCheck() throws IOException {
        IDatabase mockDb = mock(IDatabase.class);
        List<HighscoreManager.ScoreEntry> fakeScores = List.of(
                new HighscoreManager.ScoreEntry("Bob", 4),
                new HighscoreManager.ScoreEntry("Alice", 3)
        );
        when(mockDb.loadTopScores(3)).thenReturn(fakeScores);

        HighscoreManager manager = new HighscoreManager(mockDb);

        // Mock System.out
        PrintStream mockOut = mock(PrintStream.class);
        System.setOut(mockOut);

        manager.displayHighscores();

        verify(mockOut).println("--- Highscores ---");
        verify(mockOut).println("Bob: 4 attempts");
    }

    @Test
    public void testDatabaseThrowsException() throws IOException {
        IDatabase mockDb = mock(IDatabase.class);
        doThrow(new IOException("No connection")).when(mockDb).saveGame(any(), anyInt());

        HighscoreManager manager = new HighscoreManager(mockDb);

        manager.addHighscore("Eve", 2);

        // Would log error, no exception thrown
        verify(mockDb).saveGame("Eve", 2);
    }
}
