import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 100;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        String playAgain;

        do {
            if (playRound(scanner)) {
                score++;
            }
            System.out.println("Your current score is: " + score);
            System.out.print("Do you want to play again? (y/n): ");
            playAgain = scanner.next();
        } while (playAgain.equalsIgnoreCase("y"));

        System.out.println("Game Over! Your final score is: " + score);
        scanner.close();
    }

    private static boolean playRound(Scanner scanner) {
        int numberToGuess = generateRandomNumber(MIN_VAL, MAX_VAL);
        int attempts = 0;

        System.out.println("Guess a number between " + MIN_VAL + " and " + MAX_VAL + ". You have " + MAX_ATTEMPTS + " attempts.");

        while (attempts < MAX_ATTEMPTS) {
            int guess = getUserGuess(scanner);
            attempts++;

            if (guess < numberToGuess) {
                System.out.println("Too low.");
            } else if (guess > numberToGuess) {
                System.out.println("Too high.");
            } else {
                System.out.println("Congratulations! You've guessed the number " + numberToGuess + " correctly in " + attempts + " attempts.");
                return true;
            }
        }

        System.out.println("Sorry, you've used all " + MAX_ATTEMPTS + " attempts. The number was " + numberToGuess + ".");
        return false;
    }

    private static int generateRandomNumber(int minVal, int maxVal) {
        Random random = new Random();
        return random.nextInt(maxVal - minVal + 1) + minVal;
    }

    private static int getUserGuess(Scanner scanner) {
        int guess = -1;
        while (guess < MIN_VAL || guess > MAX_VAL) {
            System.out.print("Enter your guess: ");
            try {
                guess = Integer.parseInt(scanner.next());
                if (guess < MIN_VAL || guess > MAX_VAL) {
                    System.out.println("Please enter a number between " + MIN_VAL + " and " + MAX_VAL + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return guess;
    }
}
