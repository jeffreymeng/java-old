package war;

import apcs.Window;

public class WarTest {

    public static void main(String[] args) {
        Window.size(500, 600);
        Window.frame();
        War game = new War("board.txt");
        int scale = Window.width() / game.getSize();
        
        while (true) {
            game.draw();
            //computers turn
            if (game.player == 2) {
            	game.getBestMove(3);
            }
            // If the keys for playing a move are pressed
            else if (Window.key.pressed("d") || Window.key.pressed("b")) {
                
                // Get the x, y coordinate on the board where a move should be played
                int x = Window.mouse.getX() / scale;
                int y = Window.mouse.getY() / scale;
                
                // If we have a valid x, y coordinate
                if (game.isValid(x, y)) {
                    // Check if the intention was to drop a piece
                    if (Window.key.pressed("d") && game.canDrop(x, y)) {
                        game.drop(x, y);
                    }
                    // Check if the intention was to blitz
                    else if (Window.key.pressed("b") && game.canBlitz(x, y)) {
                        game.blitz(x, y);
                    }
                }
            }
        }
    }

}
