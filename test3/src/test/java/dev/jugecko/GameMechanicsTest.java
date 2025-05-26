package dev.jugecko;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameMechanicsTest {

    @Test
    public void testSystemOut_withMockito() {
        PrintStream mockOut = mock(PrintStream.class);
        System.setOut(mockOut);
        System.out.println("Test Masterminda!");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockOut).println(captor.capture());

        assertEquals("Hello, world!", captor.getValue());
    }

    @Test
    public void testSystemIn_ownImplementation() throws IOException {
        String inputText = "1234\n";
        InputStream in = new ByteArrayInputStream(inputText.getBytes());
        InputStream originalIn = System.in;
        System.setIn(in);

        Scanner scanner = new Scanner(System.in);
        assertEquals("1234", scanner.nextLine());

        System.setIn(originalIn);
    }

    @Test
    public void testDatabaseFailure() {
        IDatabase brokenDb = mock(IDatabase.class);
        doThrow(new IOException("Baza danych nie jest osiagalna.")).when(brokenDb).saveGame(any(), anyInt());
        brokenDb.saveGame("User", 3);
        fail("Expected IOException");
    }

    @Test
    public void testInvalidUserInput() {
        GameMechanics gm = new GameMechanics();
        assertFalse(gm.isValidInput("abcd"));
        assertFalse(gm.isValidInput("7890"));
        assertTrue(gm.isValidInput("1234"));
    }
}
