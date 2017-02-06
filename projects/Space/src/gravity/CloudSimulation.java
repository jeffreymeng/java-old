package gravity;

import apcs.Window;

public class CloudSimulation {
		public static void main(String[] args) {
			Mass[] list = new Mass[1000];
			for (int i = 0; i < list.length; i ++) {
				list[i] = new Mass(Window.random(0, 1000), Window.random(0,  800), 0, 0, 1000, "white", 2);
			}
			
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
