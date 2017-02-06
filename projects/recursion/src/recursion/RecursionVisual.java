package recursion;

import apcs.Window;
import apcs.Window.out;

public class RecursionVisual {

	public static void main(String[] args) {

		// carpet();

		// triangle();
		System.out.println(invert(0));
		tree();

	}

	private static void tree() {
		Window.size(1000, 800);
		int depth = -1;
		while (true) {
			
			
			if (Window.mouse.getX() / 100 != depth) {
				Window.frame();
				Window.out.background("white");
				Window.out.color("black");
				depth = Window.mouse.getX() / 100;
				drawTree(500, 780, 150, -Math.PI / 2, Window.mouse.getX() / 100);
			}
			
		}

	}
	//inverts a positive integer
	private static int invert(int number) {
		if (number <= 0) {
			return 0;
		}
		String result = "";
		for (int i = 0; number > 0; i++) {
			result += Integer.toString(number % 10);
			number = number / 10;
		}
		return Integer.parseInt(result);
	}

	// mouse controls depth, changes thickness
	private static void drawTree(int x, int y, int length, double angle, int depth) {
		if (depth <= 0) {
			return;
		}
		int endx = (int) (x + Math.cos(angle) * length);
		int endy = (int) (y + Math.sin(angle) * length);
		int thickness = (int) length * 1 / 10;
		Window.out.line(x, y, endx, endy);
		//int r = invert(139 - (depth * 5));
		//int g = invert(69 + (depth * 10));
		//int b = invert(19 - (depth ));
		//Window.out.color(r, g, b);
		Window.out.randomColor();
		Window.out.rectangle((x + endx) / 2, (y + endy) / 2, thickness, length, Math.toDegrees(angle) - 90);

		drawTree(endx, endy, length * 4 / 5, angle + Math.PI / 10, depth - 1);
		drawTree(endx, endy, length * 4 / 5, angle - Math.PI / 10, depth - 1);
	}

	private static void triangle() {
		Window.size(1000, 800);

		while (true) {
			Window.frame();
			Window.out.background("white");
			Window.out.color("black");
			drawTriangle(500, 10, 10, 790, 990, 790, Window.mouse.getX() / 100);
		}

	}

	private static void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int depth) {
		if (depth <= 0) {
			return;
		} else {
			Window.out.polygon(x1, y1, x2, y2, x3, y3);
			int x12 = (x1 + x2) / 2;
			int y12 = (y1 + y2) / 2;
			int x23 = (x2 + x3) / 2;
			int y23 = (y2 + y3) / 2;
			int x13 = (x1 + x3) / 2;
			int y13 = (y1 + y3) / 2;

			drawTriangle(x1, y1, x12, y12, x13, y13, depth - 1);
			drawTriangle(x2, y2, x12, y12, x23, y23, depth - 1);
			drawTriangle(x3, y3, x13, y13, x23, y23, depth - 1);

			// drawTriangle(x12, y12, x23, y23, x13, y13, depth - 1);
		}
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
