package gravity;

import apcs.Window;

public class BasicSimulation {

    public static void main(String[] args) {
        // One mass in space
        double x = 100, y = 100, m1 = 100000,
               dx = 0, dy = 10;

        // Another mass in space
        double a = 400, b = 400, m2 = 100000,
               da = 0, db = -10;
        while (true) {
            Window.frame();
            
            // Draw the two masses
            Window.out.color("red");
            Window.out.circle(x, y, 20.0);
            Window.out.color("blue");
            Window.out.circle(a, b, 20.0);
            
            // Move the masses
            x += dx;
            y += dy;
            a += da;
            b += db;
            
            // Calculate the force between the two objects
            double distance = Math.sqrt(Math.pow(x - a, 2) + Math.pow(y - b, 2));
            double angle = Math.atan2(y - b, x - a);
            double force = m1 * m2 / (distance * distance);
            
            double a1 = -force / m1;
            dx += a1 * Math.cos(angle);
            dy += a1 * Math.sin(angle);
            
            double a2 = force / m2;
            da += a2 * Math.cos(angle);
            db += a2 * Math.sin(angle);
            
            
            
        }
    }

}
