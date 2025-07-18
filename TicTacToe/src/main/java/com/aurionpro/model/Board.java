package com.aurionpro.model;

public class Board {
	private final Symbol[][] grid;
	private static final int SIZE = 3;

	public Board() {
		grid = new Symbol[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				grid[i][j] = Symbol.EMPTY;
	}

	public boolean placeSymbol(int row, int col, Symbol symbol) {
		if (grid[row][col] == Symbol.EMPTY) {
			grid[row][col] = symbol;
			return true;
		}
		return false;
	}

	public boolean isFull() {
		for (Symbol[] row : grid)
			for (Symbol cell : row)
				if (cell == Symbol.EMPTY)
					return false;
		return true;
	}

	public Symbol checkWinner() {
		// Rows & Columns
		for (int i = 0; i < SIZE; i++) {
			if (grid[i][0] != Symbol.EMPTY && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2])
				return grid[i][0];
			if (grid[0][i] != Symbol.EMPTY && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i])
				return grid[0][i];
		}
		// Diagonals
		if (grid[0][0] != Symbol.EMPTY && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
			return grid[0][0];
		if (grid[0][2] != Symbol.EMPTY && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
			return grid[0][2];

		return Symbol.EMPTY;
	}

	
	
	public void display() {
	    System.out.println("\nBoard:");
	    int cellNumber = 1;
	    for (int i = 0; i < SIZE; i++) {
	        for (int j = 0; j < SIZE; j++) {
	            if (grid[i][j] == Symbol.EMPTY)
	                System.out.print(" " + cellNumber);
	            else
	                System.out.print(" " + grid[i][j]);
	            cellNumber++;
	        }
	        System.out.println();
	    }
	}


	public void reset() {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				grid[i][j] = Symbol.EMPTY;
	}

	public Symbol[][] getGrid() {
		return grid;
	}

	public static int getSize() {
		return SIZE;
	}
	
	public Symbol getParticularCell(int i,int j){
		return grid[i][j];
	}
}
