package gravity;

import apcs.Window;

public class Mass {
	Vector position;
	Vector velocity;
	double mass;
	double radius = 20;
	String color;
	static double G = 0.01;

	public Mass(double x, double y, double dx, double dy, double newMass, String color, double size) {
		position = new Vector(x, y);
		velocity = new Vector(dx, dy);
		this.radius = size;
		this.color = color;
		mass = newMass;
	}

	public void draw() {
		Window.out.color(this.color);
		Window.out.circle(position.x, position.y, radius);
	}

	public void move() {
		position.add(velocity);
	}

	public Vector gravitationalForce(Mass other) {

		double distance = Math
				.sqrt(Math.pow(position.x - other.position.x, 2) + Math.pow(position.y - other.position.y, 2));
		double angle = Math.atan2(position.y - other.position.y, position.x - other.position.x);
		double force = G * mass * other.mass / (Math.pow(distance, 2));

		return new Vector(-force * Math.cos(angle), -force * Math.sin(angle));
	}

	public boolean isTouching(Mass other) {
		double distance = Math
				.sqrt(Math.pow(position.x - other.position.x, 2) + Math.pow(position.y - other.position.y, 2));
		if (distance <= radius + other.radius) {
			return true;

		} else {
			return false;
		}
	}

	public void accelerate(Mass[] list) {

		Vector netForce = new Vector();

		for (Mass m : list) {
			if (m != this && !isTouching(m)) {
				netForce.add(gravitationalForce(m));
			}
		}

		netForce.scaleDown(mass);
		netForce.scaleDown(G);
		velocity.add(netForce);
	}
}
