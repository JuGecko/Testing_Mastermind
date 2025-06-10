package dev.jugecko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
//HIGHSCORE_MANAGER TESTS
    private HighscoreManager highscoreManager;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        highscoreManager = new HighscoreManager();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testAddHighscore() {
        // When
        highscoreManager.addHighscore("Gracz1", 5);

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("Zapisano wynik dla: Gracz1 - 5 prób"));
    }

    @Test
    public void testDisplayHighscoresEmpty() {
        // When
        highscoreManager.displayHighscores();

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("Brak zapisanych wyników"));
    }

    @Test
    public void testDisplayHighscoresWithScores() {
        // Given
        highscoreManager.addHighscore("Gracz1", 5);
        highscoreManager.addHighscore("Gracz2", 3);
        outputStream.reset(); // Czyszczenie poprzednich komunikatów

        // When
        highscoreManager.displayHighscores();

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("1. Gracz2 - 3 prób"));
        assertTrue(output.contains("2. Gracz1 - 5 prób"));
    }

    @Test
    public void testDisplayOnlyTop10Scores() {
        // Given
        for (int i = 1; i <= 15; i++) {
            highscoreManager.addHighscore("Gracz" + i, i);
        }
        outputStream.reset(); // Czyszczenie poprzednich komunikatów

        // When
        highscoreManager.displayHighscores();

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("10. Gracz10 - 10 prób"));
        assertFalse(output.contains("11. Gracz11 - 11 prób"));
    }

    @Test
    public void testScoreSorting() {
        // Given
        highscoreManager.addHighscore("Dobry", 3);
        highscoreManager.addHighscore("Najlepszy", 1);
        highscoreManager.addHighscore("Średni", 5);
        outputStream.reset(); // Czyszczenie poprzednich komunikatów

        // When
        highscoreManager.displayHighscores();

        // Then
        String output = outputStream.toString();
        int pos1 = output.indexOf("1.");
        int pos2 = output.indexOf("2.");
        int pos3 = output.indexOf("3.");

        assertTrue(output.substring(pos1, pos2).contains("Najlepszy - 1 prób"));
        assertTrue(output.substring(pos2, pos3).contains("Dobry - 3 prób"));
        assertTrue(output.substring(pos3).contains("Średni - 5 prób"));
    }

    // Po każdym teście przywracamy oryginalny strumień wyjścia
    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

//GUI TESTS






//GAME_MECHANICS TESTS
}