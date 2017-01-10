package snowGlobe;

import apcs.Window;

public class Snow {
	int x = 0;
	int y = 0;
	int radius = 5;
	int gravity = 1; // pixels per frame
	int life = 60;

	public Snow(int x, int y, int gravity, int radius) {
		this.x = x;
		this.y = y;
		this.gravity = gravity;
		this.radius = radius;
	}

	public void move() {
		if (y <= 300 - (2 * radius)) {
			this.y = y + gravity;
		} else {
			life--;
		}
	}

	public void draw() {
		if (life > 0) {
			Window.out.color("white");
			Window.out.circle(x, y, radius);
		}
	}

}
