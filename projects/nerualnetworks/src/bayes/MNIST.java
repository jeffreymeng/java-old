package bayes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MNIST {
    // Size of each image.
    private int width;
    private int height;
    
    // Training data.
    private byte[] imageData;
    private byte[] labelData;
    private int length;
    
    /**
     * Reads the image and label data from the given paths, and stores their data.
     * @param imagePath
     * @param labelPath
     */
    public MNIST(String imagePath, String labelPath) {
        try {
            System.out.println("Loading MNIST data.");
            
            // Append a trailing marker.
            if (! imagePath.endsWith("/")) imagePath = imagePath + "/";
            
            // Read all training images and labels.
            imageData = Files.readAllBytes(Paths.get(imagePath));
            labelData = Files.readAllBytes(Paths.get(labelPath));
            
            // Get the length, width, and height.
            length = readInt(imageData, 4);
            width = readInt(imageData, 8);
            height = readInt(imageData, 12);
            
            // Show size.
            System.out.println("Read " + length + " digits (" + width + " x " + height + ").");
        }
        catch (IOException e) {
            System.err.println("Could not find MNIST data at " + imagePath);
        }
    }
    
    /**
     * Returns an integer array representing the handwriting sample at the given index.
     *  
     * @param index
     * @return
     */
    public int[][] getImage(int index) {
        int[][] export = new int[width][height];
        for (int y = 0, xy = 16 + index * width * height ; y != height ; y++) {
            for (int x = 0 ; x != width ; x++) {
                int g = 255 - (imageData[xy++] & 0xff);
                export[x][y] = (0xff000000 | g | ( g << 8 ) | ( g << 16 )) * -1;
            }
        }
        return export;
    }
    
    /**
     * Returns an integer array representing the handwriting sample at the given index.
     *  
     * @param index
     * @return
     */
    public boolean[][] getBinaryImage(int index) {
        boolean[][] export = new boolean[width][height];
        for (int y = 0, xy = 16 + index * width * height ; y != height ; y++) {
            for (int x = 0 ; x != width ; x++) {
                int g = 255 - (imageData[xy++] & 0xff);
                export[x][y] = (0xff000000 | g | ( g << 8 ) | ( g << 16 )) < -1;
            }
        }
        return export;
    }
    
    /**
     * Reads an integer from the byte buffer at the given offset.
     * @param image - image byte array
     * @param offset - byte offset
     * @return the integer at the given offset.
     */
    private int readInt(byte[] image, int offset) {
        return (image[offset + 3] & 0xff) | ((image[offset + 2] & 0xff) << 8) | 
               ((image[offset + 1] & 0xff) << 16) | ((image[offset] & 0xff) << 24);
    }
    
    /**
     * Returns the label for the sample at the given index.
     * @param index
     * @return
     */
    public int getLabel(int index) {
        return labelData[index+8] & 0xff; 
    }
    
    /**
     * Returns the number of samples in this MNIST dataset.
     * @return
     */
    public int size() {
        return length;
    }
    
    /**
     * Returns the width of each sample.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of each sample.
     */
    public int getHeight() {
        return height;
    }
}