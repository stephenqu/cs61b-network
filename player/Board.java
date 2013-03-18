/* Board.java  */
package player;

import java.util.ArrayList;

/**
 * A public class for storing a Board.
 */

public class Board {

	private Piece[][] b;
	private int nextPlayer;
	private int numMoves;
	private int boardLength;
	public static final int BLACK = Piece.BLACK;
	public static final int WHITE = Piece.WHITE;
	public static final int EMPTY = Piece.EMPTY;

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
		boardLength = length;
		nextPlayer = Piece.WHITE;
		numMoves = 0;
		this.b = new Piece[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				b[i][j] = new Piece(i, j, Piece.EMPTY); // initialize all spaces to EMPTY
			}
		}
	}

	/**
	 * Returns the row/column length of the board.
	 * 
	 * @return this's dimension
	 */
	public int getDimension() {
		return boardLength;
	}
	
	/**
	 * Adds the Piece p to this Board. Returns true if successful, false if another piece
	 * is already at that location. May throw ArrayIndexOutOfBounds exceptions if an invalid
	 * location is specified. 
	 * @param p the piece
	 * @return whether we were successful in adding the piece
	 */
	public boolean addPiece(Piece p) {
		Piece prev = b[p.getX()][p.getY()];
		if (prev.getColor() != Piece.EMPTY) {
			return false;
		}
		b[p.getX()][p.getY()] = p;
		return true;
	}
	
	/**
	 * Adds the Piece p to this Board. Returns true if successful, false if another piece
	 * is already at that location. May throw ArrayIndexOutOfBounds exceptions if an invalid
	 * location is specified. 
	 * @param x the x-coordinate of the Piece
	 * @param y the y-coordinate of the Piece
	 * @param color the Color of the piece
	 * @return
	 */
	public boolean addPiece(int x, int y, int color) {
		return this.addPiece(new Piece(x, y, color));
	}

	/**
	 * Decides if the Move m is a valid move on this board
	 * TODO
	 * @param m
	 * @return Whether the move is valid
	 */
	public boolean validMove(Move m) {
		Piece to = this.getPiece(m.x2, m.y2);

          //Piece to must be within the bounds of the board
          if (to.getX() < 0 || to.getX() > this.getDimension() || to.getX() < 0 || to.getX() > this.getDimension() || ((to.getX() == 0 || to.getX() == this.getDimension() - 1) && (to.getY() == 0 || to.getY() == this.getDimension() - 1))) {
            return false;
          }

          //On a step, the from must be the player's color
          if (m.moveKind == Move.STEP) {
            Piece from = this.getPiece(m.x1, m.y1);
            if (from.getColor() != nextPlayer) {
              return false;
            }
            //Piece from must be within the bounds of the board
            if (from.getX() < 0 || from.getX() > this.getDimension() || from.getX() < 0 || from.getX() > this.getDimension() || ((from.getX() == 0 || from.getX() == this.getDimension() - 1) && (from.getY() == 0 || from.getY() == this.getDimension() - 1))) {
              return false;
            }
          }

          //Cannot move to an occupied space
          if (to.getColor() != EMPTY) {
            return false;
          }

          //Can't create a group of 3
          int inGroup = 0;
          for (Piece p1: this.getSurroundings(to)) {
            if (p1.getColor() == nextPlayer) {
              inGroup++;
            }
            if (inGroup > 1) {
              return false;
            }
            for (Piece p2: this.getSurroundings(p1)) {
              if (p1.getColor() == nextPlayer && p2.getColor() == nextPlayer) {
                return false;
              }
            }
          }

          //Can't place a piece in the opponent's goal
          if ((this.nextPlayer == WHITE && (to.getY() == 0 || to.getY() == this.getDimension()-1) || (this.nextPlayer == BLACK && (to.getX() == 0 || to.getX() == this.getDimension()-1)))) {
            return false;
          }
          
          return true;
        }

	/**
	 * Returns a reference to the piece at the given coordinates.
	 * 
	 * @param x
	 *            The x coordinate of the piece.
	 * @param y
	 *            The y coordinate of the piece.
	 * @return
	 */
	public Piece getPiece(int x, int y) {
		return b[x][y];
	}
	
	/**
	 * Returns an ArrayList of all the members surrounding Piece p (orthogonally or diagonally) that are not
	 * Piece.EMPTY. Doesn't check if p is a member of the Board.
	 * 
	 * @param p the piece
	 * @return the surrounding pieces
	 */
	public ArrayList<Piece> getSurroundings(Piece p) {
		return new ArrayList<Piece>(8);
	}

	/**
	 * Returns a list of all valid moves. First, makes a list of references to
	 * all Pieces of color == EMPTY, then filters out those that are connected
	 * to two other same-colored pieces, then for each of those, adds to a list
	 * of Moves the "place" move to that Piece and any "step" moves to that
	 * piece. TODO
	 * 
	 * @param m
	 *            , heldPieces
	 * @return array of valid moves.
	 */
	public Move[] validMoves(Move m, Piece[] heldPieces) {
		return null;
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
		String eol = System.getProperty("line.separator");
		String boardBreak = "|";
		for (int a = 0; a < 2 * this.getDimension() - 1; a++){
			boardBreak += "-";
		}
		boardBreak += "|";
		String ans = boardBreak + eol;
		
		for (int i = 0; i < this.getDimension(); i++) {
			ans += "|";
			for (int j = 0; j < this.getDimension(); j++) {
				switch (b[j][i].getColor()) {
				case Piece.BLACK:
					ans += "B|";
					break;
				case Piece.WHITE:
					ans += "W|";
					break;
				default:
					ans += " |";
				}
			}
			ans += eol + boardBreak + eol;
		}
		return ans;
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
	
	public static void main(String[] args) {
		Board testBoard = new Board();
		testBoard.addPiece(new Piece(0, 0, Piece.WHITE));
		testBoard.addPiece(new Piece(1, 1, Piece.BLACK));
		testBoard.addPiece(new Piece(4, 1, Piece.WHITE));
		System.out.println(testBoard);
	}
}
