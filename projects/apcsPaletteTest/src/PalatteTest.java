import apcs.Window;

public class PalatteTest {

	public static void main(String[] args) {

		Window.size(500, 500);
		
		Window.out.background("green");
		Window.out.color("red");
		Window.out.rectangle(100, 100, 50, 50);
		Window.clearColors();
		Window.out.color("red");
		Window.out.rectangle(200, 200, 50, 50);
		Window.addPalette("fluorescent.apcsp");
		Window.out.color("light blue");
		Window.out.rectangle(300, 300, 50, 50);
		
	}

}
