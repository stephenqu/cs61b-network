package player;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTests {

	@Test
	public void testWinner() {
		int WHITE = Board.WHITE;
		int BLACK = Board.BLACK;
		int EMPTY = Board.EMPTY;
		Board b = new Board();
		b.addPiece(0, 1, WHITE);
		b.addPiece(2, 1, WHITE);
		b.addPiece(2, 4, WHITE);
		b.addPiece(3, 4, WHITE);
		b.addPiece(5, 2, WHITE);
		b.addPiece(5, 5, WHITE);
		b.addPiece(7, 5, WHITE);
		b.addPiece(4, 2, BLACK);
		b.addPiece(4, 3, BLACK);
		b.addPiece(4, 5, BLACK);
		b.addPiece(4, 7, BLACK);
		b.addPiece(5, 4, BLACK);
		b.addPiece(6, 0, BLACK);
		b.addPiece(3, 1, WHITE);
		b.addPiece(7, 1, WHITE);
		assertEquals("Testing Board.winner()", b.winner(), WHITE);
		
		Board c = new Board();
		c.addPiece(2, 0, BLACK);
		c.addPiece(2, 2, BLACK);
		c.addPiece(2, 3, BLACK);
		c.addPiece(6, 3, BLACK);
		c.addPiece(2, 6, BLACK);
		c.addPiece(1, 7, BLACK);
		assertEquals(c.winner(), EMPTY);
		c.addPiece(6, 2, BLACK);
		assertEquals(c.winner(), BLACK);
		c.addPiece(4, 2, BLACK);
		c.addPiece(3, 1, WHITE);
		System.out.println(c);
		assertEquals(c.winner(), EMPTY);
		c.addPiece(4, 3, BLACK);
		c.addPiece(0, 4, WHITE);
		c.addPiece(3, 4, WHITE);
		c.addPiece(5, 1, WHITE);
		c.addPiece(5, 5, WHITE);
		c.addPiece(7, 3, WHITE);
		assertEquals(c.winner(), c.getNextPlayer());
	}

	@Test
	public void testBoardEval() {
	}

}
