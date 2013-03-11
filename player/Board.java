/* Board.java  */
package player;

import player.Move;

/**
 * A public class for storing a Board.
 */

public class Board {

	private int[][] b;
	private int nextPlayer;
	public static int BLACK = 0;
	public static int WHITE = 1;
	public static int EMPTY = -1;

	public Board() {
		this(8);
	}

	public Board(int length) {
		nextPlayer = WHITE;
		this.b = new int[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				b[i][j] = EMPTY; // initialize all spaces to EMPTY
			}
		}
	}

	/**
	 * Decides if the Move m is a valid move on this board
	 * 
	 * @param m
	 * @return Whether the move is valid
	 */
	public boolean validMove(Move m) {
		return false;
	}

	/**
	 * Returns true if a player has won, false if not.
	 * 
	 * @return Whether the player has won
	 */
	public boolean winner() {
		return false;
	}

	/**
	 * Executes the Move m in the parameter if it is valid.
	 * 
	 * @param m
	 */
	public void doMove(Move m) {
	}

	public int getNextPlayer() {
		return nextPlayer;
	}

	public String toString() {
		return super.toString();
	}
}
