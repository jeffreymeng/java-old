
import java.io.File;
import java.util.Scanner;

import apcs.Window;

public class MagicSquare {

    public static void main(String[] args) {
        // I want to generate an n x n magic square
        int n = 6;
        int[][] square = new int[n][n];
        
        solve(square);
        //print(square);
        
        Window.size(500, 500);
        draw(square);
    }
    
    private static void draw(int[][] square) {
        Window.frame();
        int scale = Window.width() / square.length;
        Window.out.background("gray");
        Window.out.font("Courier", scale - 10);
        for (int x = 0 ; x < square.length ; x++) {
            for (int y = 0 ; y < square.length ; y++) {
                Window.out.color("white");
                Window.out.square(x * scale + scale / 2, y * scale + scale / 2, scale - 5);
                
                if (square[x][y] > 0) {
                    Window.out.color("black");
                    Window.out.print(square[x][y], x * scale + 10, y * scale + scale - 15);
                }
            }
        }
    }

    private static void print(int[][] square) {
        for (int x = 0 ; x < square.length ; x++) {
            for (int y = 0 ; y < square.length ; y++) {
                System.out.print(square[x][y] + " ");
            }
            System.out.println();
        }
    }

    private static boolean solve(int[][] square) {
        // Base case
        if (isFilled(square)) {
            return true;
        }
        
        // with the first available choice:
        for (int x = 0 ; x < square.length ; x++) {
            for (int y = 0 ; y < square.length ; y++) {
                if (square[x][y] == 0) {
                    
                    // for every valid choice c that I can make for this choice:
                    for (int choice = 1 ; choice <= square.length ; choice++) {
                        if (isValid(square, choice, x, y)) {
                            
                            // make the choice
                            square[x][y] = choice;
                            
                            // if I can recursively solve the rest of board, return true
                            if (solve(square)) {
                                return true;
                            }
                            else {
                                // unmake the choice (backtrack)
                                square[x][y] = 0;
                            }
                        }
                    }
                    
                    // If I went through choice and didn't find one that worked (returned true),
                    // then return false.
                    return false; 
                }
            }
        }
        
        return false;
    }

    private static boolean isValid(int[][] square, int choice, int x, int y) {
        
        // Check column
        for (int i = 0 ; i < square.length ; i++) {
            if (square[x][i] == choice) {
                return false;
            }
        }
        
        // Check row
        for (int i = 0 ; i < square.length ; i++) {
            if (square[i][y] == choice) {
                return false;
            }
        }
        
        // Check diagonals
        if (x == y) {
            for (int i = 0 ; i < square.length ; i++) {
                if (square[i][i] == choice) {
                    return false;
                }
            }
        }
        if (x == square.length - y - 1) {
            for (int i = 0 ; i < square.length ; i++) {
                if (square[i][square.length - i - 1] == choice) {
                    return false;
                }
            }
        }
        
        // If you get through all cases and don't find a reason to return false
        return true;
    }

    private static boolean isFilled(int[][] square) {
        for (int x = 0 ; x < square.length ; x++) {
            for (int y = 0 ; y < square.length ; y++) {
                // If this cell is empty
                if (square[x][y] == 0) {
                    return false;
                }
            }
        }
        
        return true;
    }

}
