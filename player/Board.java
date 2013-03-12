/* Board.java  */
package player;

/**
 * A public class for storing a Board.
 */

public class Board {

	private Position[][] b;
	private int nextPlayer;
	private int numMoves;
	public static final int BLACK = Position.BLACK;
	public static final int WHITE = Position.WHITE;
	public static final int EMPTY = Position.EMPTY;
	
	/**
	 * Constructs a new board of width 8 and length 8. 
	 */
	public Board() {
		this(8);
	}
	/**
	 * This constructor is private because we don't want to call anything of not length 8. 
	 * @param length
	 */
	private Board(int length) {
		nextPlayer = Position.WHITE;
		numMoves = 0;
		this.b = new Position[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				b[i][j] = new Position(i, j, Position.EMPTY); // initialize all spaces to EMPTY
			}
		}
	}

	/**
	 * Decides if the Move m is a valid move on this board
	 * TODO
	 * @param m
	 * @return Whether the move is valid
	 */
	public boolean validMove(Move m) {
		return false;
	}

	/**
	 * Returns true if a player has won, false if not.
	 * TODO
	 * @return Whether the player has won
	 */
	public boolean winner() {
		return false;
	}

	/**
	 * Executes the Move m in the parameter if it is valid.
	 * TODO
	 * @param m
	 */
	public void doMove(Move m) {
	}
	
	/**
	 * Returns the player's color who will perform the next move.
	 * @return the player's color
	 */
	public int getNextPlayer() {
		return nextPlayer;
	}
	
	/**
	 * Returns the number of moves that have occurred so far in the game. 
	 * @return the number of moves
	 */
	public int getNumMoves() {
		return numMoves;
	}

	/**
	 * Returns a String representation of a board. 
	 * TODO
	 */
	public String toString() {
		return super.toString();
	}
	
	/**
	 * Asserts that our invariants are true. Throws exceptions otherwise. 
	 * @return always returns true
	 */
	@SuppressWarnings("unused")
	private boolean testInvariants() throws AssertionError {
		assert true;
		return true;
	}
}
