package dev.jugecko;

public class Main {
    public static void main(String[] args) {
        HighscoreManager highscoreManager = new HighscoreManager();
        GameMechanics gameMechanics = new GameMechanics();
        GUI gui = new GUI(gameMechanics, highscoreManager);
        gui.startGame();
    }
}