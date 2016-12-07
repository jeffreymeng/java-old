package connectFour;

import apcs.Window;

public class ConnectFourAI extends ConnectFour {

    public void undrop(int column) {
        int y = 0;

        // Keep increasing the assumed row until a piece is reached
        while (board[column][y] == 0) {
            y++;
        }

        // Remove the piece from the board
        board[column][y] = 0;
        player = 1 + player % 2;
    }

    public int getBestMove(int depth) {
        int best = -1;
        int bestValue = Integer.MIN_VALUE;
        int forPlayer = player;

        // Figure out the actual best
        for (int column = 0 ; column < 7 ; column++) {
            if (canDrop(column)) {

                drop(column);
                int value = minimax(false, forPlayer, depth);
                undrop(column);
                
                System.out.println(column + " - " + value);

                // If a best column hasn't been picked yet, or
                // the value from minimax is the best so far
                if (best < 0 || value > bestValue) {
                    best = column;
                    bestValue = value;
                }

            }
        }

        return best;
    }

    private int minimax(boolean findBest, int player, int depth) {
        int winner = getWinner();

        if (depth == 0 || winner > 0) {

            // how is the player doing?
            if (winner == player) {
                return Integer.MAX_VALUE;
            }
            else if (winner == 0) {
                return rank(player) - rank(1 + player % 2);
            }
            else {
                return Integer.MIN_VALUE;
            }
        }

        int best = 0;
        if (findBest) {
            best = Integer.MIN_VALUE;
        }
        else {
            best = Integer.MAX_VALUE;
        }

        // Check every possible next column
        for (int c = 0 ; c < 7 ; c++) {
            if (canDrop(c)) {
                drop(c);
                int value = minimax(! findBest, player, depth - 1);
                undrop(c);

                // If you're trying to maximize
                if (findBest) {
                    if (value > best) {
                        best = value;
                    }
                }
                // If you're trying to minimize
                else {
                    if (value < best) {
                        best = value;
                    }
                }
            }
        }

        return best;
    }

    private int rank(int player) {
        return adjacencies(player) + 10 * threeInARows(player);
    }
    
    private int threeInARows(int player) {
        int score = 0;
        
        // Horizontal four in a row
        for (int x = 0 ; x < width - 2 ; x++) {
            for (int y = 0 ; y < height ; y++) {

                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == player &&
                    board[x][y] == board[x + 1][y] &&
                    board[x][y] == board[x + 2][y]) {
                    
                    score++;
                
                }
            }
        }

        // Vertical four in a row
        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height - 2 ; y++) {

                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == player &&
                    board[x][y] == board[x][y + 1] &&
                    board[x][y] == board[x][y + 2]) {
                    
                    score++;
                }
            }
        }

        // Diagonal four in a row
        for (int x = 0 ; x < width - 2 ; x++) {
            for (int y = 0 ; y < height - 2 ; y++) {

                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                    board[x][y] == player &&
                    board[x][y] == board[x + 1][y + 1] &&
                    board[x][y] == board[x + 2][y + 2]) {
                    
                    score++;
                }
            }
        }

        for (int x = 0 ; x < width - 3 ; x++) {
            for (int y = 3 ; y < height ; y++) {

                // If all pieces in this four in a row are the same and not empty
                if (board[x][y] > 0 &&
                        board[x][y] == board[x + 1][y - 1] &&
                        board[x][y] == board[x + 2][y - 2]) {
                    
                    score++;
                    
                }
            }
        }

        // There was no winner (default)
        return score;
    }

    public int adjacencies(int player) {
        int score = 0;
        // Iterated to every square
        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height ; y++) {
                
                // If this square has my piece on it
                if (board[x][y] == player) {
                    int squareValue = Math.abs(width / 2 - x) + Math.abs(height / 2 - y);
                    
                    // Horizontal and vertical
                    if (y + 1 < height && board[x][y + 1] == player) score += squareValue;
                    if (y > 0 && board[x][y - 1] == player) score += squareValue;
                    if (x + 1 < width && board[x + 1][y] == player) score += squareValue;
                    if (x > 0 && board[x - 1][y] == player) score += squareValue;
                    
                    // Diagonals
                    if (x + 1 < width && y + 1 < height && board[x + 1][y + 1] == player) score += squareValue;
                    if (x + 1 < width && y > 0 && board[x + 1][y - 1] == player) score += squareValue;
                    if (x > 0 && y + 1 < height && board[x - 1][y + 1] == player) score += squareValue;
                    if (x > 0 && y > 0 && board[x - 1][y - 1] == player) score += squareValue;
                }
            }
        }
        return score;
    }

}
