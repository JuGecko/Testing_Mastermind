package dev.jugecko;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GUITest {

    private GUI gui;
    private GameMechanics mockGameMechanics;
    private HighscoreManager mockHighscoreManager;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        mockGameMechanics = mock(GameMechanics.class);
        mockHighscoreManager = mock(HighscoreManager.class);

        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testStartGame_SingleRoundWithCorrectGuess() {
        // Given
        String simulatedInput = "1234\nTestPlayer\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        when(mockGameMechanics.isValidInput("1234")).thenReturn(true);
        when(mockGameMechanics.checkGuess("1234")).thenReturn("BBBB");
        when(mockGameMechanics.isCorrectGuess("1234")).thenReturn(true);

        gui = new GUI(mockGameMechanics, mockHighscoreManager);

        // When
        gui.startGame();

        // Then
        verify(mockGameMechanics).startNewGame();
        verify(mockGameMechanics).isValidInput("1234");
        verify(mockGameMechanics).checkGuess("1234");
        verify(mockGameMechanics).isCorrectGuess("1234");
        verify(mockHighscoreManager).addHighscore("TestPlayer", 1);
        verify(mockHighscoreManager).displayHighscores();

        String output = outputStream.toString();
        assertTrue(output.contains("Welcome to MasterMind!"));
        assertTrue(output.contains("Congratulations!"));
        assertTrue(output.contains("Dzieki za gre!"));
    }

    @Test
    void testStartGame_InvalidInputThenCorrectGuess() {
        // Given
        String simulatedInput = "abcd\n1234\nPlayer2\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        when(mockGameMechanics.isValidInput("abcd")).thenReturn(false);
        when(mockGameMechanics.isValidInput("1234")).thenReturn(true);
        when(mockGameMechanics.checkGuess("1234")).thenReturn("BBBB");
        when(mockGameMechanics.isCorrectGuess("1234")).thenReturn(true);

        gui = new GUI(mockGameMechanics, mockHighscoreManager);

        // When
        gui.startGame();

        // Then
        verify(mockGameMechanics).isValidInput("abcd");
        verify(mockGameMechanics).isValidInput("1234");
        verify(mockHighscoreManager).addHighscore("Player2", 1);

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Try again."));
    }

    @Test
    void testStartGame_MultipleAttemptsBeforeSuccess() {
        // Given
        String simulatedInput = "1234\n5678\n1356\nPlayer3\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        when(mockGameMechanics.isValidInput(anyString())).thenReturn(true);
        when(mockGameMechanics.checkGuess("1234")).thenReturn("B--W");
        when(mockGameMechanics.checkGuess("5678")).thenReturn("--WW");
        when(mockGameMechanics.checkGuess("1356")).thenReturn("BBBB");

        when(mockGameMechanics.isCorrectGuess("1234")).thenReturn(false);
        when(mockGameMechanics.isCorrectGuess("5678")).thenReturn(false);
        when(mockGameMechanics.isCorrectGuess("1356")).thenReturn(true);

        gui = new GUI(mockGameMechanics, mockHighscoreManager);

        // When
        gui.startGame();

        // Then
        verify(mockHighscoreManager).addHighscore("Player3", 3);
    }

    @Test
    void testStartGame_PlayMultipleRounds() {
        // Given
        String simulatedInput = "1234\nPlayer1\ny\n5678\nPlayer2\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        when(mockGameMechanics.isValidInput(anyString())).thenReturn(true);
        when(mockGameMechanics.isCorrectGuess(anyString())).thenReturn(true);

        gui = new GUI(mockGameMechanics, mockHighscoreManager);

        // When
        gui.startGame();

        // Then
        verify(mockGameMechanics, times(2)).startNewGame();
        verify(mockHighscoreManager).addHighscore("Player1", 1);
        verify(mockHighscoreManager).addHighscore("Player2", 1);
    }
}