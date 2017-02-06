package connectFour;

import apcs.Window;

public class HumanVsHuman {
	public static void main(String[] args) {
		ConnectFour game = new ConnectFour();
		
		while(true) {
			game.draw();
			game.getHumanMove();
			if (game.getWinner() > 0) {
				System.out.println(game.getWinner());
				Window.sleep(1000);
				game = new ConnectFour();
			}
		}
	}
}
