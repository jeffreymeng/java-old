package evolution;

public class Genome {
	static double mutationRate = 0.2;
	static double mutationRange = 0.5;
	
	public Network network;
	public int score = 0;
	
	public Genome(int inputs, int[] hiddens, int outputs) {
		network = new Network(inputs, hiddens, outputs);
	}
	public Genome(int inputs, int[] hiddens, int outputs, double mutationRate, double mutationRange) {
		network = new Network(inputs, hiddens, outputs);
	}
	public Genome(Genome parent1, Genome parent2) {
		network = new Network(parent1.network);
		
		// Go to every layer
		for (int l = 0 ; l < network.layer.length ; l++) {
			Layer layer = network.layer[l];
			
			// Go to every neuron in the layer
			for (int n = 0 ; n < layer.size() ; n++) {
				Neuron neuron = layer.neuron[n];
				
				// Go to every connection
				for (int w = 0 ; w < neuron.weight.length ; w++) {
					
					// With a 50% chance, pick parent 2's weight
					if (Math.random() < 0.5) {
						neuron.weight[w] = parent2.network.layer[l].neuron[n].weight[w];
					}
					
					// TODO: random mutations
					if (Math.random() < mutationRate) {
						neuron.weight[w] += mutationRange * (Math.random() * 2 - 1);
					}
				}
			}
		}
	}
}