package flappyBird;

import apcs.Window;

public class Pipe {
	public static int width = 50;
	public static int minGap = 100;
	public static int gapSize = 100;
	public static int speed = 3;
	
	private int x;
	private double gap;
	
	public Pipe() {
		x = Window.width() + width / 2;
		gap = minGap + Math.random() * (Window.height() - minGap * 2);
	}
	
	public void draw() {
		Window.out.color("green");
		Window.out.rectangle(x, Window.height() / 2, width, Window.height());
		Window.out.color("light blue");
		Window.out.rectangle(x, (int) gap, width, gapSize);
	}
	
	public void move() {
		x = x - speed;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public double getGap() {
		return gap;
	}
}