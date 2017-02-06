package connectFour;

import apcs.Window;

public class ConnectFour {

    int[][] board;
    
    // current player
    int player = 1;
    
    // Determine the look and size of the game
    static boolean visible = false;
    int size = 50;
    int width = 7;
    int height = 6;
    int active = 1;
    
    /**
     * Constructs a Connect4 board. 
     */
    public ConnectFour() {
        // Show the window if it has not been shown yet
        if (! visible) {
            Window.size(width * size, height * size);
            visible = true;
        }
        
        // Initialize an empty board
        board = new int[width][height];
    }

    /**
     * Returns the player number of the winner on the board.
     * @return the winner (1 or 2), or 0 if there is no winner
     */
    public int getWinner() {
        // Horizontal four in a row
        for (int x = 0 ; x < width - 3 ; x++) {
            for (int y = 0 ; y < height ; y++) {
                
                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == board[x + 1][y] &&
                    board[x][y] == board[x + 2][y] &&
                    board[x][y] == board[x + 3][y]) {
                    return board[x][y];
                }
            }
        }
        
        // Vertical four in a row
        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height - 3 ; y++) {
                
                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == board[x][y + 1] &&
                    board[x][y] == board[x][y + 2] &&
                    board[x][y] == board[x][y + 3]) {
                    return board[x][y];
                }
            }
        }
        
        // Diagonal four in a row
        for (int x = 0 ; x < width - 3 ; x++) {
            for (int y = 0 ; y < height - 3 ; y++) {
                
                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == board[x + 1][y + 1] &&
                    board[x][y] == board[x + 2][y + 2] &&
                    board[x][y] == board[x + 3][y + 3]) {
                    return board[x][y];
                }
            }
        }
        
        for (int x = 0 ; x < width - 3 ; x++) {
            for (int y = 3 ; y < height ; y++) {
                
                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == board[x + 1][y - 1] &&
                    board[x][y] == board[x + 2][y - 2] &&
                    board[x][y] == board[x + 3][y - 3]) {
                    return board[x][y];
                }
            }
        }
        
        // There was no winner (default)
        return 0;
    }

    /**
     * Drops a piece in the given column.
     * @param column - the column to drop the piece in
     */
    public void drop(int column) {
        int y = 0;
        while (y + 1 < height && board[column][y + 1] == 0)
            y++;
        board[column][y] = player;
        
        // Switch the current player
        player = 1 + player % 2;
    }

    /**
     * Returns true if there are any columns that can be dropped in.
     * @return
     */
    public boolean canDrop() {
        for (int i = 0 ; i < width ; i++) {
            if (board[i][0] == 0)
                return true;
        }
        return false;
    }
    
    /**
     * Returns true if a piece can be dropped in the given column.
     * @param column - the column to drop a piece in
     * @return true or false
     */
    public boolean canDrop(int column) {
        return board[column][0] == 0;
    }
    
    /**
     * Draw the Connect4 grid.
     */
    public void draw() {
        Window.out.background("yellow");
        
        // Go to every x, y position
        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height ; y++) {
                
                // Pick the drawing color based on what is in the board position.
                if (board[x][y] == 0) {
                    if (active == x) {
                        Window.out.color("gray");
                    }
                    else {
                        Window.out.color("black");
                    }
                }
                else if (board[x][y] == 1) {
                    Window.out.color("red");
                }
                else {
                    Window.out.color("blue");
                }
                
                // Draw a black circle
                Window.out.circle(x * size + size / 2, y * size + size / 2, size * 4 / 9);
            }
        }
        Window.frame();
    }
    
    /**
     * Waits for a human to click into the board and drop a piece.
     */
    public void getHumanMove() {
        active = Window.mouse.getX() / size;
        if (Window.mouse.clicked()) {
            Window.mouse.waitForRelease();
            int x = Window.mouse.getX() / size;
            if (canDrop(x)) {
                drop(x);
            }
        }
    }
}