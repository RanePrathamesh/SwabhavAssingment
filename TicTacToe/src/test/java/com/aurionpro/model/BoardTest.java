package com.aurionpro.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	//private  Symbol[][] grid;
	//private static final int SIZE = 3;
	Board board ;
	
	@BeforeEach
	void init() {
		//grid = new Symbol[SIZE][SIZE];
		board = new Board();
	}

	@Test
	void testBoard() {
		assertEquals(Symbol.EMPTY, board.getParticularCell(1, 1) );
	}

	@Test
	void testPlaceSymbol() {
		assertEquals(true, board.placeSymbol(1, 1, Symbol.X));
		assertEquals(true, board.placeSymbol(1, 2, Symbol.O));
	}
	
	@Test
	void testPlaceSymbolRepitation() {
		assertEquals(true, board.placeSymbol(1, 1, Symbol.X));
		assertEquals(false, board.placeSymbol(1, 1, Symbol.O));
	}

}
