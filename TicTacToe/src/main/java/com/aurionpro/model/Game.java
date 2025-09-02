package com.aurionpro.model;

import java.util.Scanner;
import java.util.Random;

public class Game {
    private final Board board;
    private Player player1;
    private Player player2;
    private final Scanner scanner;

    public Game() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to  Tic Tac Toe!");

        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();

        System.out.print("Enter Player 2 name: ");
        String name2 = scanner.nextLine();

        assignSymbols(name1, name2);
        boolean playAgain;

        do {
            playGame();
            String input;
            while (true) {
                System.out.print("Do you want to play again? (yes/no): ");
                input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("yes")) {
                    playAgain = true;
                    board.reset();
                    break;
                } else if (input.equals("no")) {
                    playAgain = false;
                    break;
                } else {
                    System.out.println("Invalid input! Please enter 'yes' or 'no'.");
                }
            }
        } while (playAgain);

    }

    private void assignSymbols(String name1, String name2) {
        Random random = new Random();
        if (random.nextBoolean()) {
            player1 = new Player(name1, Symbol.X);
            player2 = new Player(name2, Symbol.O);
        } else {
            player1 = new Player(name1, Symbol.O);
            player2 = new Player(name2, Symbol.X);
        }
        System.out.println(player1.getName() + " is " + player1.getSymbol());
        System.out.println(player2.getName() + " is " + player2.getSymbol());
    }

    private void playGame() {
        Player currentPlayer = player1;
        while (true) {
            board.display();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getSymbol() + ")");
            int cell;
            do {
                cell = getInput("Enter a position (1-9): ");
            } while (cell < 1 || cell > 9);

            //maping is done here
            int row = (cell - 1) / 3;
            int col = (cell - 1) % 3;


            if (!board.placeSymbol(row, col, currentPlayer.getSymbol())) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            Symbol winner = board.checkWinner();
            if (winner != Symbol.EMPTY) {
                board.display();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            } else if (board.isFull()) {
                board.display();
                System.out.println("It is draw!");
                break;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    private int getInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

}

