package war;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import apcs.Window;

public class War {
	// n x n board
	int[][] board;
	int[][] value;
	int[][] mm;
	int size;
	int moves = 0;

	// score of each player
	int[] score = new int[3];

	// 0 - empty
	// 1, 2 - player number
	int player = 1;

	public War(String map) {
		try {
			// Read all lines of input from the scanner
			Scanner s = new Scanner(new File(map));
			int y = 0;

			while (s.hasNextLine()) {
				String line = s.nextLine();

				// Condition to initialize the board in the
				// first iteration of the loop
				if (board == null) {
					size = line.split(" ").length;
					board = new int[size][size];
					value = new int[size][size];
					mm = new int[size][size];
					moves = size * size;
				}

				// Put the values into the board squares
				int x = 0;
				for (String v : line.split(" ")) {
					value[x][y] = Integer.parseInt(v);
					x++;
				}
				y++;
			}
			s.close();
		}
		// If the file isn't found, print an error
		catch (FileNotFoundException e) {
			System.out.println("Could not find map: " + map);
		}
	}

	public War(int n) {
		size = n;
		board = new int[n][n];
		value = new int[n][n];
		mm = new int[n][n];
		moves = n * n;
	}

	/**
	 * Copy constructor
	 * @return
	 */
	public War(War other) {
		size = other.size;
		player = other.player;
		moves = other.moves;

		// Deep copy
		board = new int[size][size];
		for (int x = 0 ; x < size ; x++) {
			for (int y = 0 ; y < size ; y++) {
				board[x][y] = other.board[x][y];
			}
		}

		// Soft copy
		value = other.value;

		// Deep copy score array
		score[1] = other.score[1];
		score[2] = other.score[2];
	}

	/**
	 * 
	 * @param x - valid x coordinate
	 * @param y - valid y coordinate
	 */
	public void drop(int x, int y) {
		// Set the board and add to the player's score
		board[x][y] = player;
		score[player] += value[x][y];
		moves--;

		// Switch to the next player
		player = 1 + player % 2;
	}

	/**
	 * 
	 * @param x - valid x coordinate
	 * @param y - valid y coordinate
	 */
	public boolean canDrop(int x, int y) {
		return board[x][y] == 0;
	}

	/**
	 * Takes over an adjacent square as well as all enemy-controlled
	 * squares that are adjacent to the blitzed square.
	 * 
	 * @param x - valid x coordinate
	 * @param y - valid y coordinate
	 */
	public void blitz(int x, int y) {
		int opponent = 1 + player % 2;
		board[x][y] = player;
		score[player] += value[x][y];
		moves--;

		// Check horizontal squares
		if (x > 0 && board[x - 1][y] == opponent) {
			board[x - 1][y] = player;
			score[player] += value[x - 1][y];
			score[opponent] -= value[x - 1][y];
		}
		if (x + 1 < size && board[x + 1][y] == opponent) {
			board[x + 1][y] = player;
			score[player] += value[x + 1][y];
			score[opponent] -= value[x + 1][y];
		}

		// Check vertical squares
		if (y > 0 && board[x][y - 1] == opponent) {
			board[x][y - 1] = player;
			score[player] += value[x][y - 1];
			score[opponent] -= value[x][y - 1];
		}
		if (y + 1 < size && board[x][y + 1] == opponent) {
			board[x][y + 1] = player;
			score[player] += value[x][y + 1];
			score[opponent] -= value[x][y + 1];
		}

		// Switch the player
		player = opponent;
	}

	/**
	 * Only true if the square is empty and the player controls a horizontally
	 * or vertically adjacent square.
	 * 
	 * @param x - valid x coordinate
	 * @param y - valid y coordinate
	 */
	public boolean canBlitz(int x, int y) {
		// Can't blitz a non-empty square
		if (board[x][y] != 0) {
			return false;
		}
		// Horizontally adjacent squares
		if (x > 0 && board[x - 1][y] == player) {
			return true;
		}
		if (x + 1 < size && board[x + 1][y] == player) {
			return true;
		}
		// Vertically adjacent squares
		if (y > 0 && board[x][y - 1] == player) {
			return true;
		}
		if (y + 1 < size && board[x][y + 1] == player) {
			return true;
		}
		return false;
	}

	public void draw() {
		draw(null);
	}
	
	/**
	 * Draws the board on the window.
	 */
	public void draw(Move highlight) {
		int length = Window.width() / size;

		Window.out.background("white");
		Window.out.font("Courier", length / 4);

		for (int x = 0 ; x < size ; x++) {
			for (int y = 0 ; y < size ; y++) {

				// Draw the square
				if (highlight != null && highlight.x == x && highlight.y == y) {
					Window.out.color("yellow");
				}
				else {
					Window.out.color("gray");
				}
				Window.out.square(x * length + length / 2, y * length + length / 2, length - 5);

				// Draw who controls the square
				if (board[x][y] == 1) {
					Window.out.color("red");
					Window.out.circle(x * length + length / 2, y * length + length / 2, length / 4);
				}
				if (board[x][y] == 2) {
					Window.out.color("blue");
					Window.out.square(x * length + length / 2, y * length + length / 2, length / 2);
				}

				// Draw the value of the square if greater than 0
				if (value[x][y] > 0) {
					Window.out.color("black");
					Window.out.print(value[x][y], x * length + 10, y * length + length - 10);
				}
				
				// Draw the minimax value in the top left corner
				Window.out.color((mm[x][y] > 0) ? "green" : "red");
				Window.out.print(mm[x][y], x * length + 10, y * length + 30);
			}
		}

		Window.out.font("Courier", 50);

		// Draw the score, current player, etc
		Window.out.color("red");
		Window.out.print(score[1], 50, Window.height() - 25);
		if (player == 1) {
			Window.out.circle(25, Window.height() - 50, 10);
		}

		Window.out.color("blue");
		Window.out.print(score[2], Window.width() - 100, Window.height() - 25);
		if (player == 2) {
			Window.out.circle(Window.width() - 25, Window.height() - 50, 10);
		}

		Window.frame();
	}

	public int getSize() {
		return size;
	}

	public boolean isValid(int x, int y) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}

	public boolean canMove() {
		return moves > 0;
	}

	public Move getBestMove(int depth) {
		mm = new int[size][size];
		Move bestMove = null;
		int bestValue = Integer.MIN_VALUE;

		for (Move move : getMoves()) {
			War nextState = new War(this);
			nextState.make(move);

			//int value = minimax(nextState, player, false, depth);
			int value = minimax_ab(nextState, player, false, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
			mm[move.x][move.y] = value;
			
			if (value > bestValue) {
				bestValue = value;
				bestMove = move;
			}
		}
		
		// return the best move
		return bestMove;
	}

	public void make(Move move) {
		if (move.drop) {
			drop(move.x, move.y);
		}
		else {
			blitz(move.x, move.y);
		}
	}

	private int minimax(War state, int player, boolean findBest, int depth) {
		// If there are no possible moves, or the max depth is reached
		if (! state.canMove() || depth == 0) {
			// return value of the state
			return state.score[player] - state.score[1 + player % 2];
		}

		int best = 0;
		// Find the best outcome for the player 
		if (findBest) {
			best = Integer.MIN_VALUE;

			// For every possible move
			for (Move m : state.getMoves()) {
				// Make a copy that has played the move
				War nextState = new War(state);
				nextState.make(m);

				// Call minimax on the next state
				int value = minimax(nextState, player, false, depth - 1);
				if (value > best) {
					best = value;
				}
			}
		}
		// Find the worst outcome for the player
		else {
			best = Integer.MAX_VALUE;

			// For every possible move
			for (Move m : state.getMoves()) {
				// Make a copy that has played the move
				War nextState = new War(state);
				nextState.make(m);

				// Call minimax on the next state
				int value = minimax(nextState, player, true, depth - 1);
				if (value < best) {
					best = value;
				}
			}
		}

		return best;
	}
	
	private int minimax_ab(War state, int player, boolean findBest, int depth, int alpha, int beta) {
		// If there are no possible moves, or the max depth is reached
		if (! state.canMove() || depth == 0) {
			// return value of the state
			return state.score[player] - state.score[1 + player % 2];
		}

		int best = 0;
		// Find the best outcome for the player 
		if (findBest) {
			best = Integer.MIN_VALUE;

			// For every possible move
			for (Move m : state.getMoves()) {
				// Make a copy that has played the move
				War nextState = new War(state);
				nextState.make(m);

				// Call minimax on the next state
				int value = minimax_ab(nextState, player, false, depth - 1, alpha, beta);
				if (value > best) {
					best = value;
				}
				if (best > alpha) {
					alpha = best;
				}
				if (beta <= alpha) {
					break;
				}
			}
		}
		// Find the worst outcome for the player
		else {
			best = Integer.MAX_VALUE;

			// For every possible move
			for (Move m : state.getMoves()) {
				// Make a copy that has played the move
				War nextState = new War(state);
				nextState.make(m);

				// Call minimax on the next state
				int value = minimax_ab(nextState, player, true, depth - 1, alpha, beta);
				if (value < best) {
					best = value;
				}
				if (best < beta) {
					beta = best;
				}
				if (beta <= alpha) {
					break;
				}
			}
		}

		return best;
	}

	private Move[] getMoves() {
		ArrayList <Move> moves = new ArrayList <Move> ();

		// go to every empty square
		for (int x = 0 ; x < size ; x++) {
			for (int y = 0 ; y < size ; y++) {
				if (canDrop(x, y)) {
					moves.add(new Move(x, y, true));
				}
				if (canBlitz(x, y)) {
					moves.add(new Move(x, y, false));
				}
			}
		}

		return moves.toArray(new Move[moves.size()]);
	}
}

class Move {
	int x;
	int y;
	boolean drop;

	public Move(int x, int y, boolean drop) {
		this.x = x;
		this.y = y;
		this.drop = drop;
	}
}