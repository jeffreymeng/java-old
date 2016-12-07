package flappyBird;
import apcs.Window;
import evolution.Genome;

public class Bird {
	public static final double GRAVITY = 1.0;
	public static final double FLAP_ACCELERATION = -6.0;
	
	public static final int width = 40;
	public static final int height = 30;
	
	// All birds have the same x position, used only for displaying
	public static int x = 50;
	// A random x offset so all the birds don't overlap
	private int xoffset = Window.random(-5, 5);
	
	private double y = Window.height() / 2;
	private double speed = 0;
	private boolean dead = false;
	
	private Genome genome;
	private Pipe closest;
	
	public Bird(Genome genome) {
		this.genome = genome;
	}
	
	public void draw() {
		Window.out.color("yellow");
		Window.out.oval(x, (int) y, width, height);
		//Window.out.image("flappy.png", x - 20 + xoffset, (int) y - height / 2);
	}
	
	public void move() {
		y = y + speed;
		speed = speed + GRAVITY;
	}
	
	public void flap() {
		speed = FLAP_ACCELERATION;
	}
	
	public void update(Pipe closest, int score) {
		this.closest = closest;
		
		if (! dead &&
			((y < height / 2 || y > Window.height() - height / 2) ||
			 (Math.abs(x - closest.getX()) < width / 2 + Pipe.width / 2 &&
			  Math.abs(y - closest.getGap()) >= Pipe.gapSize / 2))) {
			
			// Update bird state and set the genome score
			die(score);
		}
	}
	
	public void die(int score) {
		dead = true;
		genome.score = score - (int) Math.abs(y - closest.getGap());
	}
	
	public boolean isDead() {
		return dead;
	}
	
	// Human control of the bird
	public void humanControl() {
		if (Window.key.pressed("space")) {
			flap();
		}
	}
	
	// Genome controls the bird
	public void genomeControl() {
		double[] inputs = { y / Window.height(), closest.getGap() / Window.height() };
		double[] output = genome.network.compute(inputs);
		
		if (output[0] > 0.5) {
			flap();
		}
	}
}