package neuralnet;

public class Network {
    Layer inputLayer;
    Layer hiddenLayer;
    Layer outputLayer;
    
    public Network(int inputs, int hiddens, int outputs) {
        inputLayer = new Layer(inputs, 0);
        hiddenLayer = new Layer(hiddens, inputs);
        outputLayer = new Layer(outputs, hiddens);
    }
    
    public double[] compute(double[] input) {
        // Apply inputs to the input neurons
        for (int i = 0 ; i < input.length ; i++) {
            inputLayer.neuron[i].value = input[i];
        }
        
        // Forward-propagate the values
        
        // Go to every neuron in the hidden layer
        for (int i = 0 ; i < hiddenLayer.neuron.length ; i++) {
            Neuron hiddenNeuron = hiddenLayer.neuron[i];
            
            // set its value to the sum of the weights of the previous neurons
            double sum = 0;
            for (int w = 0 ; w < hiddenNeuron.weight.length ; w++) {
                double weight = hiddenNeuron.weight[w];
                double value = inputLayer.neuron[w].value;
                sum = sum + weight * value;
            }
            hiddenNeuron.value = sigmoid(sum);
        }
        
        // Go to every neuron in the output layer
        for (int i = 0 ; i < outputLayer.neuron.length ; i++) {
            Neuron outputNeuron = outputLayer.neuron[i];
            
            // set its value to the sum of the weights of the previous neurons
            double sum = 0;
            for (int w = 0 ; w < outputNeuron.weight.length ; w++) {
                double weight = outputNeuron.weight[w];
                double value = hiddenLayer.neuron[w].value;
                sum = sum + weight * value;
            }
            outputNeuron.value = sigmoid(sum);
        }
        
        // Get the outputs from the output neurons
        double[] output = new double[outputLayer.neuron.length];
        for (int i = 0 ; i < output.length ; i++) {
            output[i] = outputLayer.neuron[i].value;
        }
        return output;
    }
    
    // Propagate an expected output backward
    public void propagate(double[] output, double learningRate) {
        
        // Compute values for the weights going to the output layer
        for (int i = 0 ; i < output.length ; i++) {
            Neuron outputNeuron = outputLayer.neuron[i];
            double value = outputNeuron.value;
            double error = output[i] - value;
            
            for (int w = 0 ; w < outputNeuron.weight.length ; w++) {
                Neuron hiddenNeuron = hiddenLayer.neuron[w];
                double hiddenValue = hiddenNeuron.value;
                double gradient = -value * (1 - value) * hiddenValue * error;
                
                outputNeuron.weight[w] -= learningRate * gradient;
            }
        }
        
        // Calculate hidden neuron values
        for (int i = 0 ; i < hiddenLayer.neuron.length ; i++) {
            Neuron hiddenNeuron = hiddenLayer.neuron[i];
            double value = hiddenNeuron.value;
            
            // Go to every input neuron
            for (int w = 0 ; w < hiddenNeuron.weight.length ; w++) {
                Neuron inputNeuron = inputLayer.neuron[w];
                double inputValue = inputNeuron.value;
                
                // Sum the gradients of all output neurons
                double sum = 0;
                for (int o = 0 ; o < outputLayer.neuron.length ; o++) {
                    Neuron outputNeuron = outputLayer.neuron[o];
                    double outputWeight = outputNeuron.weight[i];
                    double outputValue = outputNeuron.value;
                    double outputError = output[o] - outputValue;
                    
                    double outputGradient = outputError * -outputValue * (1 - outputValue) * outputWeight;
                    sum = sum + outputGradient;
                }
                
                double gradient = value * (1 - value) * inputValue * sum;
                hiddenNeuron.weight[w] -= learningRate * gradient;
            }
        }
        
    }

    private double sigmoid(double a) {
        return 1 / (1 + Math.exp(-a));
    }
}
