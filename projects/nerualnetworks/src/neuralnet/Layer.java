package neuralnet;

public class Layer {
	Neuron[] neuron;
	
	public Layer(int neurons, int inputs) {
		neuron = new Neuron[neurons];
		
		for (int i = 0; i < neurons; i ++) {
			neuron[i] = new Neuron(inputs);
		}
	}
}
