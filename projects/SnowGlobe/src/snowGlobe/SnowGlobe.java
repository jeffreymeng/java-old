package snowGlobe;

// apcs.io
import apcs.Window;

public class SnowGlobe {
	public static void main(String[] args) {

		// create a new window
		Window.size(400, 400);

		// Create a list of Ball objects.


		Snow[] list = new Snow[10];
		for (int i = 0; i < 10; i++) {
			list[i] = new Snow(150, 150, 1, 2);
		}

		while (true) {

			// set the background color
			Window.out.background("light green");

			// draw the outer circle
			Window.out.color("blue");
			Window.out.circle(200, 300, 100);
			// draw a circle inside it to make it only show a rim, and make the
			// inside color light blue
			Window.out.color("light blue");
			Window.out.circle(200, 300, 80);
			// cover the bottom so it is only half a circle
			Window.out.color("light green");
			Window.out.rectangle(200, 360, 250, 80);
			
			// draw the bottom rim of the snow globe
			Window.out.color("blue");
			Window.out.rectangle(200, 320, 250, 40);

			// print out the text on the bottom rim
			Window.out.color("white");
			Window.out.font("Helvetica", 12);
			Window.out.print("Have a great Christmas!", 133, 328);

			// Draw and move each ball.
			for (Snow snow : list) {
				snow.draw();
				snow.move();
			}

			Window.frame();
		}

	}
}
