package tictactoe2;

import java.util.ArrayList;

import apcs.Window;
import neuralnet.Network;

public class TicTacToe {
    
    public static void main(String[] args) {
        Network net = new Network(9, 81, 9);
        
        TicTacToe start = new TicTacToe();
        enumerateAll(start, net);
        
        TicTacToe game = new TicTacToe();
        while (true) {
            game.draw();
            
            // If it is the network's turn to move
            if (game.player == 1) {
                game.move(decode(game, net.compute(encode(game, 1))));
            }
            else {
                // consult a human player
                game.move( game.getHumanMove() );
            }
            
            if (game.getWinner() > 0 || game.isDraw()) {
                game.draw();
                Window.sleep(1000);
                game = new TicTacToe();
            }
        }
    }
    
    private static double[] encode(TicTacToe game, int player) {
        double[] input = new double[9];
        for (int x = 0 ; x < 3 ; x++) {
            for (int y = 0 ; y < 3 ; y++) {
                if (game.board[x][y] == player)
                    input[y * 3 + x] = 1;
                else if (game.board[x][y] != 0)
                    input[y * 3 + x] = -1;
            }
        }
        return input;
    }
    
    private static Move decode(TicTacToe game, double[] output) {
        int max = -1;
        for (int i = 0 ; i < 9 ; i++) {
            int x = i % 3;
            int y = i / 3;
            if (game.board[x][y] == 0 &&
                (max < 0  || output[i] > output[max])) {
                max = i;
            }
        }
        
        return new Move(max % 3, max / 3);
    }
    
    static int count = 0;
    private static void enumerateAll(TicTacToe state, Network network) {
        // If there is no choice that the network could possibly make in this state
        if (state.getWinner() > 0 || state.isDraw()) {
            System.out.println("Completed branch " + count);
            count++;
            return;
        }
        
        // Train with this state
        double[] output = new double[9];
        for (Move move : state.getMoves()) {
            TicTacToe next = new TicTacToe(state);
            next.move(move);
            
            int value = state.minimax(next, false, 1);
            output[move.y * 3 + move.x] = (value + 1.0) / 2.0;
            
            enumerateAll(next, network);
        }
        
        // Put the state into the network
        double[] input = encode(state, 1);
        
        for (int i = 0 ; i < 5 ; i++) {
            network.compute(input);
            network.propagate(output, 0.3);
        }
        
    }

    // State of the board
    int[][] board;
    int[][] value;
    int player;
    
    public TicTacToe() {
        board = new int[3][3];
        player = 1;
    }
    
    public TicTacToe(TicTacToe other) {
        board = new int[3][3];
        player = other.player;
        
        // Copy the board over
        for (int x = 0 ; x < 3 ; x++) {
            for (int y = 0 ; y < 3 ; y++) {
                board[x][y] = other.board[x][y];
            }
        }
    }
    
    /**
     * Assumption: x and y are valid coordinates
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        board[x][y] = player;
        player = 1 + player % 2;
    }
    
    public void move(Move m) {
        move(m.x, m.y);
    }
    
    public int getWinner() {
        for (int i = 0 ; i < 3 ; i++) {
            // Columns (x position changes, y position is 0, 1, 2)
            if (board[i][0] > 0 && 
                board[i][0] == board[i][1] && 
                board[i][0] == board[i][2]) {
                return board[i][0];
            }
            // Rows (y position changes, x position is 0, 1, 2)
            if (board[0][i] > 0 && 
                board[0][i] == board[1][i] && 
                board[0][i] == board[2][i]) {
                return board[0][i];
            }
        }
        
        // Diagonals
        if (board[0][0] > 0 && 
            board[0][0] == board[1][1] && 
            board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] > 0 &&
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0]) {
            return board[0][2];
        }
        
        return 0;
    }
    
    public boolean isDraw() {
        for (int x = 0 ; x < 3 ; x++) {
            for (int y = 0 ; y < 3 ; y++) {
                if (board[x][y] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public ArrayList<Move> getMoves() {
        ArrayList <Move> moves = new ArrayList <Move> ();
        
        for (int x = 0 ; x < 3 ; x++) {
            for (int y = 0 ; y < 3 ; y++) {
                if (board[x][y] == 0) {
                    moves.add(new Move(x, y));
                }
            }
        }
        
        return moves;
    }
    
    public Move getHumanMove() {
        Move move = null;
        while (move == null) {
            Window.mouse.waitForClick();
            
            int x = (Window.mouse.getX() - 25) / 150;
            int y = (Window.mouse.getY() - 25) / 150;
            
            // Coordinate is valid, and the square is empty
            if (x >= 0 && x < 3 && y >= 0 && y < 3 && board[x][y] == 0) {
                move = new Move(x, y);
            }
            
            Window.mouse.waitForRelease();
        }
        return move;
    }
    
    public Move getBestMove() {
        ArrayList <Move> moves = getMoves();
        Move best = null;
        int bestValue = -2;
        value = new int[3][3];
        
        for (Move move : moves) {
            // Make a new state of the board that's played that move
            TicTacToe next = new TicTacToe(this);
            next.move(move);
            
            int value = minimax(next, false, player);
            this.value[move.x][move.y] = value;
            
            if (value > bestValue) {
                bestValue = value;
                best = move;
            }
        }
        
        return best;
    }
    
    /**
     * 
     * @param state - the state of the board
     * @param player - which player we're finding the best move for
     * @return
     */
    public int minimax(TicTacToe state, boolean findBest, int player) {
        if (state.isDraw()) {
            return 0;
        }
        
        int winner = state.getWinner();
        if (winner > 0) {
            if (winner == player) {
                return 1;
            }
            else {
                return -1;
            }
        }
        
        //state.draw();
        
        // Start best as an outlier value
        int best = -2;
        if (! findBest) {
            best = 2;
        }
        
        // Go to every move
        for (Move move : state.getMoves()) {
            TicTacToe nextState = new TicTacToe(state);
            nextState.move(move);
            
            int value = minimax(nextState, !findBest, player);
            
            if (findBest) {
                if (value > best) {
                    best = value;
                }
            }
            else {
                if (value < best) {
                    best = value;
                }
            }
        }
        
        return best;
    }
    
    public void draw() {
        Window.out.background("white");
        
        // Draw the four lines
        Window.out.color("gray");
        Window.out.rectangle(250, 175, 450, 5);
        Window.out.rectangle(250, 325, 450, 5);
        Window.out.rectangle(175, 250, 5, 450);
        Window.out.rectangle(325, 250, 5, 450);
        
        
        Window.out.font("Courier", 150);
        for (int x = 0 ; x < 3 ; x++) {
            for (int y = 0 ; y < 3 ; y++) {
                Window.out.color("black");
                Window.out.fontSize(150);
                if (board[x][y] == 1) {
                    Window.out.print("X", 50 + x * 150, 150 + y * 150);
                }
                if (board[x][y] == 2) {
                    Window.out.print("O", 50 + x * 150, 150 + y * 150);
                }
                
                if (value != null) {
                    Window.out.color("blue");
                    Window.out.fontSize(20);
                    Window.out.print(value[x][y], 150 + x * 150, 170 + y * 150);
                }
            }
        }
        Window.frame();
    }
}

class Move {
    int x;
    int y;
    
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}