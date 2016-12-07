package evolution;

public class Layer {
	Neuron[] neuron;
	public Layer(int neurons, int inputs) {
		neuron = new Neuron[neurons];
		for (int i = 0; i < neuron.length; i ++) {
			neuron[i] = new Neuron(inputs);
		}
	}
	
	public Layer(Layer other) {
		neuron = new Neuron[other.neuron.length];
		for (int i = 0; i < neuron.length; i ++) {
			neuron[i] = new Neuron(other.neuron[i]);
		}
	}
	public int size() {
		return neuron.length;
	}
}
