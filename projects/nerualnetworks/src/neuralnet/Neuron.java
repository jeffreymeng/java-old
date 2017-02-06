package neuralnet;

public class Neuron {
	double value;
	double[] weight;
	
	public Neuron(int inputs) {
		this.value = 0;
		this.weight = new double[inputs];
		
		for (int i = 0; i < inputs; i ++) {
			weight[i] = Math.random() * 2 - 1;
		}
		
	}
}
