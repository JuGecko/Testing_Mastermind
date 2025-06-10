package dev.jugecko;

import java.util.Scanner;

public class GUI {
    private final GameMechanics gameMechanics;
    private final HighscoreManager highscoreManager;
    private final Scanner scanner = new Scanner(System.in);

    public GUI(GameMechanics gameMechanics, HighscoreManager highscoreManager) {
        this.gameMechanics = gameMechanics;
        this.highscoreManager = highscoreManager;
    }

    public void startGame() {
        System.out.println("Welcome to MasterMind!");
        boolean playing = true;

        while (playing) {
            gameMechanics.startNewGame();
            int attempts = 0;
            boolean guessed = false;

            while (!guessed) {
                System.out.print("Enter your guess (4 digits between 1-6): ");
                String input = scanner.nextLine();

                if (!gameMechanics.isValidInput(input)) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                attempts++;
                String feedback = gameMechanics.checkGuess(input);
                System.out.println("Feedback: " + feedback);

                if (gameMechanics.isCorrectGuess(input)) {
                    guessed = true;
                    System.out.println("Congratulations! You've guessed the code in " + attempts + " attempts.");
                    System.out.print("Enter your name for the highscore: ");
                    String name = scanner.nextLine();
                    highscoreManager.addHighscore(name, attempts);
                }
            }

            System.out.print("Play again? (y/n): ");
            playing = scanner.nextLine().equalsIgnoreCase("y");
        }

        highscoreManager.displayHighscores();
        System.out.println("Dzieki za gre!");

        //database.saveGame(name, attempts); // możliwy wyjątek
    }
}