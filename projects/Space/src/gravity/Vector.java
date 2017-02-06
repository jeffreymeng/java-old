package gravity;

public class Vector {
	double x;
	double y;
	
	public Vector() {
		x = 0;
		y = 0;
		
	}
	
	public Vector(double newX, double newY) {
		x = newX;
		y = newY;
	}
	
	public Vector(Vector other) {
		x = other.x;
		y = other.y;
	}
	public void add(Vector other) {
		x += other.x;
		y += other.y;
	}
	public void invert() {
		x = -x;
		y = -y;
		
	}
	public void scaleDown(double factor) {
		x = x / factor;
		y = y / factor;
	}
}