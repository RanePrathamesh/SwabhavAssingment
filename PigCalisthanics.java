package com.usecase17thjune;

import java.util.*;

public class PigCalisthanics {

    public static void main(String[] args) {
    	System.out.println("Welcome to pig dice game!\n");
    	System.out.println("Goal is to reach 20 points\n");
        new PigCalisthanics().start();
    }

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    
   

    public void start() {
        while (true) {
            playGame();
            if (!userWantsToContinue()) {
                break;
            }
        }
    }

    private void playGame() {
        int totalScore = 0;
        int totalTurns = 0;

        print("Welcome and start game");

        while (totalScore < 20 && totalTurns < 3) {
            totalTurns++;
            int turnScore = playTurn(totalTurns);

            totalScore += turnScore;

            if (totalScore >= 20) {
                break;
            }
        }

        showGameResult(totalScore, totalTurns);
    }

    private int playTurn(int turnNumber) {
        int turnScore = 0;

        print("\nYour turn no. is: " + turnNumber);

        while (true) {
            String choice = getUserChoice("roll or hold (r/h)").toLowerCase();

            if (choice.equals("r")) {
                int roll = rollDice();
                print("Roll no: " + roll);

                if (roll == 1) {
                    print("Your turn over and you lost all your score");
                    return 0;
                }

                turnScore += roll;
                print("Turn score: " + turnScore);
            }

            if (choice.equals("h")) {
                print("Your turn score is: " + turnScore);
                print("You are holding your total score");
                return turnScore;
            }
        }
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }

    private boolean userWantsToContinue() {
        String userChoice = getUserChoice("Do you want to continue (y/n)");
        return userChoice.equalsIgnoreCase("y");
    }

    private void showGameResult(int totalScore, int totalTurns) {
        print("Game over");

        if (totalScore >= 20) {
            print("You won in: " + totalTurns + " turns with score: " + totalScore);
            return;
        }

        print("You lose");
    }

    private String getUserChoice(String message) {
        print(message);
        return scanner.nextLine();
    }

    private void print(String message) {
        System.out.println(message);
    }
  
   
}