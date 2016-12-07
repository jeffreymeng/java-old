package recursion;

import apcs.Window;

public class Carpet {
	public static void main(String[] args) {
		carpet();
	}
	private static void carpet() {
		
		Window.size(800, 800);

		while (true) {
			Window.frame();
			Window.out.background("blue");
			Window.out.color("red");
			cutCarpet(400, 400, 800 / 3, Window.mouse.getX() / 100);
		}

	}

	private static void cutCarpet(int x, int y, int size, int depth) {
		if (size > 0 && depth > 0) {
			Window.out.square(x, y, size);
			// right & left
			cutCarpet(x + size, y, size / 3, depth - 1);
			cutCarpet(x - size, y, size / 3, depth - 1);
			// top & bottom
			cutCarpet(x, y + size, size / 3, depth - 1);
			cutCarpet(x, y - size, size / 3, depth - 1);
			// corners
			cutCarpet(x + size, y + size, size / 3, depth - 1);
			cutCarpet(x + size, y - size, size / 3, depth - 1);
			cutCarpet(x - size, y + size, size / 3, depth - 1);
			cutCarpet(x - size, y - size, size / 3, depth - 1);
		}

	}
}
