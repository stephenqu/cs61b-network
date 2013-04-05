package player;

/**
 * A class for representing a single position on a Board.
 * 
 * @author Allen W. Li
 * 
 */
class Piece {

	private int x;
	private int y;
	private int color;
	
	//These need to be FINAL!
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	public static final int EMPTY = -1;

	/**
	 * Creates a new Piece (for use with player.board).
	 * 
	 * @param x
	 *            the x-coordinate of the position (left to right, from 0)
	 * @param y
	 *            the y-coordinate of the position (top to bottom, from 0)
	 * @param color
	 *            the color of the piece at that position
	 */
	public Piece(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean equals(Piece p) {
		return p.x == this.x && p.y == this.y && p.color == this.color;
	}
	
	public String toString() {
		String colorString = "";
		if (color == BLACK)
			colorString = "BLACK";
		else if (color == WHITE)
			colorString = "WHITE";
		else if (color == EMPTY)
			colorString = "EMPTY";
		return colorString + " Piece at (" + x + ", " + y + ")";
	}

}
