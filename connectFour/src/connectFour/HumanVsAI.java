package connectFour;

import apcs.Window;

public class HumanVsAI {
	
	public static void main(String[] args) {
		int depth = 2;
		// TODO Auto-generated method stub
		ConnectFourAI game = new ConnectFourAI();
		
		while(true) {
			game.draw();
			
			if (game.player == 1) {
				game.getHumanMove();
				
			} else {
				int best = game.getBestMove(depth);
				game.drop(best);
			}
			
			if (game.getWinner() > 0) {
				game.draw();
				Window.sleep(1000);
				game = new ConnectFourAI();
			}
		}
	}

}
