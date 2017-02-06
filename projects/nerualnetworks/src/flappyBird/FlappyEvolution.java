package flappyBird;

import java.util.ArrayList;

import apcs.Window;
import evolution.Evolution;
import evolution.Genome;

public class FlappyEvolution {

	static Evolution evolution = new Evolution(2, new int[] { 2 }, 1);

	static ArrayList <Bird> birds = new ArrayList <Bird> ();
	static ArrayList <Pipe> pipes = new ArrayList <Pipe> ();

	static int score = 0;
	static int generation = 0;

	public static void main(String[] args) {
		Window.size(500, 600);
		Window.setFrameRate(100);

		reset();

		while (true) {
			if (birds.size() == 0) {
				reset();
			}
			drawAndMove();
			drawStatistics();
			
			for (Bird bird : birds) {
				bird.genomeControl();
			}
			
			// Add to the score in each frame
			score++;
			
			// Add new pipes to the window so there are two in each frame
			if (score % (Window.width() / Pipe.speed / 2) == 0) {
				pipes.add(new Pipe());
			}
		}
	}
	
	/**
	 * Draw statistics about the evolution progress in the bottom right corner.
	 */
	private static void drawStatistics() {
		Window.out.color("black");
		Window.out.print("Score: " + (score * 2 * Pipe.speed / Window.width()), 20, Window.height() - 60);
		Window.out.print("Generation: " + generation, 20, Window.height() - 40);
		Window.out.print("Population: " + birds.size(), 20, Window.height() - 20);
	}

	/**
	 * Draws and moves everything in the game.
	 */
	private static void drawAndMove() {
		// Set the background
		Window.frame();
		Window.out.background("light blue");

		// Draw every pipe, and keep track of the closest pipe to the birds
		Pipe closest = null;
		for (Pipe pipe : pipes) {
			// If this is the first pipe with an x position greater than the birds
			if (closest == null && pipe.getX() + Pipe.width / 2 > Bird.x) {
				closest = pipe;
			}
			
			// Draw and move the pipes
			pipe.draw();
			pipe.move();
		}
		
		// Iterate over all birds
		for (int i = 0 ; i < birds.size() ; i++) {
			Bird bird = birds.get(i);
			
			// If the bird is dead, remove it from the list of birds
			if (bird.isDead()) {
				birds.remove(i);
				i--;
			}
			// Otherwise draw and move the bird
			else {
				bird.draw();
				bird.move();
				
				// Update the bird with the closest pipe and the current game score
				bird.update(closest, score);
			}
		}
	}

	/**
	 * Resets the game
	 */
	public static void reset() {
		// Evolve the genomes
		//System.out.println("Evolving");
		evolution.evolve();
		//System.out.println("Evolved");
		generation++;
		
		// Create a new set of Bird objects for each genome
		birds.clear();
		for (Genome genome : evolution.genomes) {
			birds.add(new Bird(genome));
		}

		// Add the initial two pipes
		pipes.clear();
		Pipe firstPipe = new Pipe();
		firstPipe.setX(Window.width() / 2);
		pipes.add(firstPipe);
		pipes.add(new Pipe());

		// Reset the score
		score = 0;
	}

}