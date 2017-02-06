package nerualMnist;

import bayes.MNIST;
import neuralnet.Network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import apcs.Window;



public class NerualMnist {
	public static void main(String[] args) {
		double learningRate = 0.26;
		MNISTResult result = nerualMNIST(learningRate,true);
		double correct = result.correct;
		double incorrect = result.incorrect;
		Network network = result.network;
		MNIST test = result.test;
		writeToFile("MNISTresults.txt",
				"Accuracy was " + (correct / test.size()) * 100 + "% with " + learningRate + " learning rate.");
		System.out.println("Done!");
		System.out.println("Accuracy:" + (correct / test.size()) * 100 + "%. ");
		System.out.println((int) correct + " correct out of " + test.size());
		System.out.println((int) incorrect + " incorrect out of " + test.size());
		runApp(network);
	}

	private static void runApp(Network network) {
		// Draw an interactive app to recognize numbers
		Window.size(560, 280);
		Window.out.font("Courier", 200);
		boolean[][] draw = new boolean[28][28];

		while (true) {
			Window.frame();
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					if (draw[x][y]) {
						Window.out.color("white");
					} else {
						Window.out.color("black");
					}
					Window.out.square(x * 10 + 5, y * 10 + 5, 10);
				}
			}

			if (Window.mouse.clicked()) {
				int x = Window.mouse.getX() / 10;
				int y = Window.mouse.getY() / 10;
				if (x >= 0 && x < 28 && y >= 0 && y < 28) {
					draw[x][y] = true;
				}
			}
			if (Window.key.pressed("space")) {
				draw = new boolean[28][28];
			}

			double[] input = toInput(draw);
			int max = getMax(network.compute(input));

			Window.out.color("white");
			Window.out.print(max, 300, 240);
		}

	}

	public static void writeToFile(String filename, String text) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

			writer.newLine();
			writer.write(text);

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getMax(double[] output) {
		int max = 0;
		for (int j = 1; j < output.length; j++) {
			if (output[j] > output[max]) {
				max = j;
			}
		}
		return max;
	}
	/*
	 * learningRate is learningRate for neural network. Must be between 1 and 0
	 * set printResultsForEach to true if you want it to print out correct or incorrect for each test image from t10k-images-idx3-ubyte
	 */
	public static MNISTResult nerualMNIST(double learningRate, boolean printResultsForEach) {

		MNIST train = new MNIST("train-images-idx3-ubyte", "train-labels-idx1-ubyte");
		MNIST test = new MNIST("t10k-images-idx3-ubyte", "t10k-labels-idx1-ubyte");
		Network network = new Network(28 * 28, 28, 10);
		for (int i = 0; i < train.size(); i++) {
			if (i % 1000 == 0) {
				System.out.println("trained " + i + " images out of 59000.");
			}
			boolean[][] image = train.getBinaryImage(i);
			int label = train.getLabel(i);
			// set up input array
			double[] input = new double[28 * 28];

			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					if (image[x][y]) {
						input[x + y * 28] = 1;
					}

				}
			}
			// set up expected output
			double[] output = new double[10];
			output[label] = 1.0;

			network.compute(input);
			network.propagate(output, learningRate);

		}

		double correct = 0;
		double incorrect = 0;

		for (int i = 0; i < test.size(); i++) {
			boolean[][] image = test.getBinaryImage(i);
			int label = test.getLabel(i);
			// set up input array
			double[] input = new double[28 * 28];

			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					if (image[x][y]) {
						input[x + y * 28] = 1;
					}

				}
			}
			// set up expected output
			double[] output = network.compute(input);
			int max = 0;
			for (int j = 1; j < output.length; j++) {
				if (output[j] > output[max]) {
					max = j;
				}
			}

			if (max == label) {
				correct = correct + 1;
				if (printResultsForEach) {
					System.out.println("Correct");
				}
			} else {
				incorrect = incorrect + 1;
				if (printResultsForEach) {
					System.out.println("Incorrect");
				}
			}

		}
		MNISTResult result = new MNISTResult(correct, incorrect, network, test);
		return result;
	}

	public static double[] toInput(boolean[][] image) {
		// Set up the input array
		double[] input = new double[28 * 28];

		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 28; y++) {
				if (image[x][y]) {
					input[x + y * 28] = 1;
				}
			}
		}

		return input;
	}

}
class MNISTResult {
	double correct = 0;
	double incorrect = 0;
	Network network = new Network(28 * 28, 28, 10);
	MNIST test = new MNIST("t10k-images-idx3-ubyte", "t10k-labels-idx1-ubyte");

	public MNISTResult(double newCorrect, double newIncorrect, Network newNetwork, MNIST newTest) {
		correct = newCorrect;
		incorrect = newIncorrect;
		network = newNetwork;
		test = newTest;
	}

}