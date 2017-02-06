package gravity;

import apcs.Window;

public class Simulation {
	public static void main(String[] args) {
		/*
		Mass[] list = { 
		
			new Mass(100, 100, 0, 20, 100000, "blue", 20),
			new Mass(800, 400, 0, -10, 100000, "red", 20),
			new Mass(300, 600, 0, -20, 100000, "green", 20)
		};
		*/
		//sun and earth
		Mass[] list = { 
				new Mass(500, 400, 0, 0, 10000000, "yellow", 30),
				new Mass(100, 400, 0, -50, 400000, "blue", 5)
				
			};
		
		Window.size(1000, 700);
		while (true) {
			Window.frame(30);
			
			//draw each mass
			for (Mass m : list) {
				m.draw();
				m.accelerate(list);
				//calculate the net force
				
			}
			
			for (Mass m : list) {
				m.move();
				
				
				
			}
		}
	}
}
