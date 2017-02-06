package tictactoe;

public class Game {
	
	int[][] board;
	
	Player p1, p2;
	int turn = 1;
	
	public Game(Player p1, Player p2) {
		board = new int[3][3];
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void play() {
		int moves = 0;
		int winner = 0;
		
		while (moves < 9) {
			// Tell the current player to make a move
			if (turn == 1) {
				p1.move(this, 1);
			}
			if (turn == 2) {
				p2.move(this, 2);
			}
			
			// Switch the turn
			turn = 1 + turn % 2;
			
			// See if there is a winner or a draw
			winner = winner();
			if (winner != 0) {
				break;
			}
			
			// Increment number of moves
			moves++;
		}
		
		// Assign a score to the players
		if (winner == 1 && p1.genome != null) {
			p1.genome.score++;
		}
		if (winner == 2 && p2.genome != null) {
			p2.genome.score++;
		}
	}
	
	private int winner() {
		// check every row, column
		for (int i = 0 ; i < 3 ; i++) {
			
			// check row i
			if (board[0][i] > 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				return board[0][i];
			}
			
			// check column i
			if (board[i][0] > 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				return board[i][0];
			}
		}
		
		// check diagonals
		if (board[0][0] > 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			return board[0][0];
		}
		if (board[0][2] > 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			return board[0][2];
		}
		
		// No winner, return 0
		return 0;
	}

	public boolean valid(int x, int y) {
		return board[x][y] == 0;
	}

	public void place(int x, int y, int piece) {
		board[x][y] = piece;
	}

	public int valueOf(int x, int y) {
		return board[x][y];
	}
	
}