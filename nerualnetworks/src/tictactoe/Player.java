package tictactoe;

import apcs.Window;
import evolution.Genome;

public class Player {
	
	Genome genome;
	
	public Player() {}
	
	public Player(Genome genome) {
		this.genome = genome;
	}
	
	public void move(Game game, int piece) {
		double[] input = new double[9];
		
		// iterate over the game's board
		for (int x = 0 ; x < 3 ; x++) {
			for (int y = 0 ; y < 3 ; y++) {
				
				// set each input element to value of the square on the board
				// in the perspective of the player
				if (game.valueOf(x, y) == piece) {
					input[y * 3 + x] = 1;
				}
				else if (game.valueOf(x, y) == 0) {
					input[y * 3 + x] = 0;
				}
				else {
					input[y * 3 + x] = -1;
				}
			}
		}
		
		// Compute the output from the network
		double[] output = genome.network.compute(input);
		
		// Find the maximum index of a valid square that can have a piece placed on it
		int max = -1;
		for (int i = 0 ; i < output.length ; i++) {
			if (game.valid(i % 3, i / 3) && 
			   (max < 0 || output[max] < output[i])) {
				max = i;
			}
		}
		
		// Place a piece on the maximum output
		game.place(max % 3, max / 3, piece);
	}
	
}

class HumanPlayer extends Player {
	static boolean windowInitialized = false;
	
	public HumanPlayer() {
		if (! windowInitialized) {
			windowInitialized = true;
			Window.size(600, 600);
			Window.out.font("Courier", 150);
			Window.frame();
		}
	}
	
	public void move(Game game, int piece) {
		while (true) {
			Window.out.background("white");
			
			Window.out.color("gray");
			Window.out.rectangle(200, 300, 5, 580);
			Window.out.rectangle(400, 300, 5, 580);
			Window.out.rectangle(300, 200, 580, 5);
			Window.out.rectangle(300, 400, 580, 5);
			
			// Draw the board, wait for a click
			Window.out.color("black");
			for (int x = 0 ; x < 3 ; x++) {
				for (int y = 0 ; y < 3 ; y++) {
					if (game.valueOf(x, y) == 1) {
						Window.out.print("X", x * 200 + 50, y * 200 + 150);
					}
					if (game.valueOf(x, y) == 2) {
						Window.out.print("O", x * 200 + 50, y * 200 + 150);
					}
				}
			}
			
			// Check the mouse position
			int x = Window.mouse.getX() / 200;
			int y = Window.mouse.getY() / 200;
			
			// Draw a shadow of a piece on the square
			if (game.valid(x, y)) {
				Window.out.color("light gray");
				if (piece == 1) {
					Window.out.print("X", x * 200 + 50, y * 200 + 150);
				}
				if (piece == 2) {
					Window.out.print("O", x * 200 + 50, y * 200 + 150);
				}
			}
			
			if (Window.mouse.clicked() && game.valid(x, y)) {
				 game.place(x, y, piece);
				 Window.mouse.waitForRelease();
				 break;
			}
			Window.frame();
		}
	}
}