package dev.jugecko;

import java.util.Random;

// GameMechanics class - handles the core game logic
public class GameMechanics {
    private String secretCode;
    private final Random random = new Random();

    public void startNewGame() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(6) + 1);
        }
        secretCode = code.toString();
    }

    public boolean isValidInput(String input) {
        if (input.length() != 4) return false;
        for (char c : input.toCharArray()) {
            if (c < '1' || c > '6') return false;
        }
        return true;
    }

    public String checkGuess(String guess) {
        int correctPlace = 0;
        int correctDigit = 0;
        boolean[] codeUsed = new boolean[4];
        boolean[] guessUsed = new boolean[4];

        // First pass: correct digits in correct places
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                correctPlace++;
                codeUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Second pass: correct digits in wrong places
        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue;
            for (int j = 0; j < 4; j++) {
                if (codeUsed[j]) continue;
                if (guess.charAt(i) == secretCode.charAt(j)) {
                    correctDigit++;
                    codeUsed[j] = true;
                    guessUsed[i] = true;
                    break;
                }
            }
        }

        return correctPlace + " correct place, " + correctDigit + " correct number but wrong place";
    }

    public boolean isCorrectGuess(String guess) {
        return secretCode.equals(guess);
    }
}
