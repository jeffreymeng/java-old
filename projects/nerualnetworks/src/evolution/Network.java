package evolution;

public class Network {
	Layer[] layer;
	public Network(int inputs, int[] hidden, int outputs) {
		layer = new Layer[2 + hidden.length];
		//input layer
		layer[0] = new Layer(inputs, 0);
		//hidden layer
		int previous = inputs;
		for (int i = 1; i < layer.length - 1; i ++) {
			layer[i] = new Layer(hidden[i - 1], previous);
			previous = hidden[i - 1];
		}
		//output layer
		
		layer[layer.length - 1] = new Layer(outputs, previous);
	}
	public Network(Network other) {
		layer = new Layer[other.layer.length];
		
		for (int i = 0; i < layer.length; i ++) {
			layer[i] = new Layer(other.layer[i]);
		}
	}
	
	public double[] compute(double[] inputs) {
		for (int i = 0; i < inputs.length; i ++) {
			layer[0].neuron[i].value = inputs[i];
		}
		for (int i = 1; i < layer.length; i ++) {
			Layer previous = layer[i - 1];
			for (int n = 0; n < layer[i].neuron.length; n ++) {
				Neuron neuron = layer[i].neuron[n];
				double sum = 0;
				for (int j = 0; j < previous.size(); j ++) {
					sum += previous.neuron[j].value * neuron.weight[j];
				}
				
				neuron.value = sum;
			}
		}
		Layer outputLayer = layer[layer.length - 1];
		double[] output = new double[outputLayer.size()];
		for (int i = 0; i < outputLayer.size(); i ++) {
			output[i] = outputLayer.neuron[i].value;
		}
		return output;
	}
	
	private double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
}
