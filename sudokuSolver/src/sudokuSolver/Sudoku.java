package sudokuSolver;

import apcs.Window;

/**
 * Sudoku solver.
 * 
 * @author ( your name )
 */
public class Sudoku {

    int[][] original;
    int[][] board;

    /**
     * Creates a Sudoku board from the given board string.
     * @param board
     */
    public Sudoku(String board) {
        this.board = new int[9][9];
        this.original = new int[9][9];

        for (int i = 0 ; i < board.length() ; i++) {
            this.board[i % 9][i / 9] = board.charAt(i) - '0';
            this.original[i % 9][i / 9] = board.charAt(i) - '0';
        }
    }

    /**
     * Creates a Sudoku board with the given integer grid.
     * @param board
     */
    public Sudoku(int[][] board) {
        this.board = board;
        this.original = new int[9][9];

        for (int x = 0 ; x < 9 ; x++)
            for (int y = 0 ; y < 9 ; y++)
                original[x][y] = board[x][y];
    }

    /**
     * Returns true if the given Sudoku puzzle has a solution, and enters the
     * solution into the given integer array.
     * @return true if the puzzle has been solved, false otherwise
     */
    public boolean solve() {
        //draw();
        // Base case
        if (isFilled()) {
            return true;
        }

        // with the first available choice:
        for (int x = 0 ; x < board.length ; x++) {
            for (int y = 0 ; y < board.length ; y++) {
                if (board[x][y] == 0) {

                    // for every valid choice c that I can make for this choice:
                    for (int choice = 1 ; choice <= 9 ; choice++) {
                        if (isValid(choice, x, y)) {

                            // make the choice
                            board[x][y] = choice;

                            // if I can recursively solve the rest of board, return true
                            if (solve()) {
                                return true;
                            }
                            else {
                                // unmake the choice (backtrack)
                                board[x][y] = 0;
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
    
    private boolean isValid(int choice, int x, int y) {
        
        // Check column
        for (int i = 0 ; i < board.length ; i++) {
            if (board[x][i] == choice || board[i][y] == choice) {
                return false;
            }
        }
        
        // Get the 3x3 subsquare
        for (int x2 = x - x % 3 ; x2 < x - x % 3 + 3 ; x2++) {
            for (int y2 = y - y % 3 ; y2 < y - y % 3 + 3 ; y2++) {
                if (board[x2][y2] == choice) {
                    return false;
                }
            }
        }
        
        // If you get through all cases and don't find a reason to return false
        return true;
    }

    private boolean isFilled() {
        for (int x = 0 ; x < board.length ; x++) {
            for (int y = 0 ; y < board.length ; y++) {
                // If this cell is empty
                if (board[x][y] == 0) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Draws the given Sudoku board.
     * @param board - the Sudoku board to draw.
     */
    public void draw() {
        Window.out.background("white");
        Window.out.font("Courier", Window.width() / 9 - 5);

        Window.out.color("black");
        for (int i = 0 ; i < 9 ; i++) {
            Window.out.line(i * Window.width() / 9, 0, i * Window.width() / 9, Window.width());
            Window.out.line(0, i * Window.width() / 9, Window.width(), i * Window.width() / 9);
        }

        Window.out.rectangle(Window.width() / 2, 2, Window.width(), 4);
        Window.out.rectangle(Window.width() / 2, Window.width() / 3 - 1, Window.width(), 4);
        Window.out.rectangle(Window.width() / 2, Window.width() * 2 / 3 - 1, Window.width(), 4);
        Window.out.rectangle(Window.width() / 2, Window.width() - 2, Window.width(), 4);
        Window.out.rectangle(2, Window.width() / 2, 4, Window.width());
        Window.out.rectangle(Window.width() / 3 - 1, Window.width() / 2, 4, Window.width());
        Window.out.rectangle(Window.width() * 2 / 3 - 1, Window.width() / 2, 4, Window.width());
        Window.out.rectangle(Window.width() - 2, Window.width() / 2, 4, Window.width());

        for (int x = 0 ; x < 9 ; x++) {
            for (int y = 0 ; y < 9 ; y++) {
                if (board[x][y] > 0) {
                    if (board[x][y] == original[x][y])
                        Window.out.color("black");
                    else
                        Window.out.color("blue");

                    Window.out.print(board[x][y], x * Window.width() / 9 + Window.width() / 40, (y + 1) * Window.width() / 9 - Window.width() / 50);
                }
            }
        }
        Window.frame();
    }
}