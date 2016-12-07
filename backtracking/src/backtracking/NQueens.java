package backtracking;
import apcs.Window;

public class NQueens {

	public static void main(String[] args) {
		boolean[][] chessboard = new boolean[8][8];
		// placeQueens(chessboard, 8);
		// draw(chessboard, "♛");
		placeKnights(chessboard, 10);
		draw(chessboard, "♞");
		

	}
	
	public static boolean placeKnights(boolean[][] board, int knights) {
		if (knights == 0) {
    		return true;
    	}
    	for (int x = 0; x < board.length; x ++) {
    		for (int y = 0; y < board.length; y ++) {
        		if (canPlaceKnight(board, x, y)) {
        			board[x][y] = true;
        			if (placeKnights(board, knights - 1)) {
        				return true;
        			} else {
        				board[x][y] = false;
        			}
        		}
        	}
    	}
    	
    	return false;
		
	}
	public static boolean canPlaceKnight(boolean[][] board, int x, int y) {
		if (board[x][y]) {
			return false;
		}
		if (x + 2 < board.length && y - 1 >= 0 && board[x - 2][y + 1]) {
			return false;
		}
		if (x + 2 < board.length && y + 1 < board.length && board[x + 2][y + 1]) {
			return false;
		}
		if (x + 1 < board.length && y - 2 >= 0 && board[x + 1][y - 2]) {
			return false;
		}
		if (x + 1 < board.length && y + 2 < board.length && board[x + 1][y + 2]) {
			return false;
		}
		if (x + 2 < board.length && y - 1 >= 0 && board[x - 2][y + 1]) {
			return false;
		}
		if (x + 2 < board.length && y - 1 >= 0 && board[x - 2][y + 1]) {
			return false;
		}
		if (x + 2 < board.length && y - 1 >= 0 && board[x - 2][y + 1]) {
			return false;
		}
		
		return true;
	}
	private static boolean drawn = false;
    private static int scale = 100;
    private static int maxWidth = 800;
    public static void draw(boolean[][] board, String peice) {
        if (! drawn) {
            drawn = true;
            scale = maxWidth / board.length;
            Window.size(scale * board.length , scale * board.length);
            Window.out.font("Arial", scale - scale / 5);
            Window.frame();
        }
        for (int x = 0 ; x < board.length ; x++) {
            for (int y = 0 ; y < board.length ; y++) {
                Window.out.color((x % 2 == y % 2) ? "tan" : "brown");
                Window.out.square(x * scale + scale / 2, y * scale + scale / 2, scale);
                
                Window.out.color("white");
                if (board[x][y])
                    Window.out.print(peice, x * scale + 5, (y + 1) * scale - scale / 5);
            }
        }
        Window.frame();
    }
    public static boolean canPlaceQueen(boolean[][] board, int x, int y) {
    	//can't place a queen on top of another queen
    	if (board[x][y] == true) {
    		return false;
    	}
    	for (int i = 0; i < board.length; i ++) {
    		//row
    		if (board [i][y]) {
    			return false;
    		}
    		
    		//Column
    		if (board[x][i]) {
    			return false;
    		}
    		//downward right diagonal
    		if (x + i < board.length && y + i < board.length && board[x+i][y+i]) {
    			return false;
    		}
    		//upward right diagonal
    		if (x + i < board.length && y - i >= 0 && board[x+i][y-i]) {
    			return false;
    		}
    		//downward left diagonal
    		if (x - i >= 0 && y + i < board.length && board[x-i][y+i]) {
    			return false;
    		}
    		
    		//upwards left diagonal
    		if (x - i >= 0 && y - i >= 0 && board[x-i][y-i]) {
    			return false;
    		}
    		
    	}
    	return true;
    }
    public static boolean placeQueens(boolean[][] board, int queens) {
    	if (queens == 0) {
    		return true;
    	}
    	for (int x = 0; x < board.length; x ++) {
    		for (int y = 0; y < board.length; y ++) {
        		if (canPlaceQueen(board, x, y)) {
        			board[x][y] = true;
        			if (placeQueens(board, queens - 1)) {
        				return true;
        			} else {
        				board[x][y] = false;
        			}
        		}
        	}
    	}
    	
    	return false;
    }
}
