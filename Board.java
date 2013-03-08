/* Board.java */

/**
 * A public class for interfacing with a network board.
 */

public class Board {

    private int[][] b;
    public static int BLACK = 0;
    public static int WHITE = 1;
    public static int EMPTY = -1;

    public Board() {
        this.b = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j] = EMPTY; //initialize all spaces to EMPTY
            }
        }
    }

    public Board(int length) {
        this.b = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                b[i][j] = EMPTY; //initialize all spaces to EMPTY
            }
        }
    }

    //Decides if the Move m is a valid move on this board
    public boolean validMove(Move m) {}

    //Returns true if a player has won, false if not.
    public boolean winner() {}

    //Executes the Move m in the parameter if it is valid.
    public void doMove(Move m) {}

    public String toString {}

    public static void main(String[] args) {}
}
    
    

