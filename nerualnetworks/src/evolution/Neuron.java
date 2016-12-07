package evolution;

public class Neuron {
	double value;
	double[] weight;
	public Neuron(int inputs) {
		weight = new double[inputs];
		
		//randomize
		for (int i = 0; i < inputs; i ++) {
			weight[i] = Math.random() * 2 -1;
		}
		
	}
	
	public Neuron(Neuron other) {
			weight = new double[other.weight.length];
		
		//randomize
		for (int i = 0; i < other.weight.length; i ++) {
			weight[i] = Math.random() * 2 -1;
		} 
	}
}
