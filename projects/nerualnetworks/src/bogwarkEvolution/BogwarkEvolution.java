/*
 * package bogwarkEvolution;
 

import java.util.ArrayList;

import apcs.Window;
import evolution.Evolution;

public class BogwarkEvolution {
    
    // inputs
    // - number of creatures of this species nearby
    // - health
    // - engaged in combat
    // - distance to enemy
    // - 
    
    // outputs
    // - whether to attack, flee, or wander
    // - 
    
    public static void main(String[] args) {
        Bogworld world = new Bogworld();
        
        Window.size(1000, 800);
        
        while (true) {
            Window.frame();
            world.draw();
            world.move();
        }
    }   
}

class Bogworld {
    
    ArrayList <Bogwark> bogwarks;
    ArrayList <Steroid> steroids;
    Evolution evolution;
    
    public Bogworld() {
        bogwarks = new ArrayList <Bogwark> ();
        steroids = new ArrayList <Steroid> ();
        evolution = new Evolution(3, new int[] { 5 }, 3);
    }
    
    public void draw() {
        for (Bogwark b : bogwarks) {
            b.draw();
        }
        for (Steroid s : steroids) {
            s.draw();
        }
    }
    
    public void move() {
        if (Window.rollDice(6) == 1) {
            bogwarks.add(new Bogwark(evolution.createGenome()));
        }
        
        for (int b = 0 ; b < bogwarks.size() ; b++) {
            Bogwark bogwark = bogwarks.get(b);
            bogwark.move();
            
            if (bogwark.canReproduce()) {
                bogwark.reproduce();
                bogwarks.add(new Bogwark(bogwark));
            }
            
            for (int s = 0 ; s < steroids.size() ; s++) {
                if (bogwark.canEat(steroids.get(s))) {
                    bogwark.eat(steroids.get(s));
                    steroids.remove(s);
                    s--;
                }
            }
        }
    }
}

class Bogwark {
    static int size = 20;
    static int friendliness = 30;
    
    int r = 0, g = 0, b = 0;
    
    double x;
    double y;
    double angle = Math.random() * 2 * Math.PI;
    double speed = 0;
    double maxSpeed = 5;
    
    double health = 1;
    
    Genome genome;
    
    public Bogwark(Genome genome) {
        this.genome = genome;
    }
    
    public Bogwark(Bogwark parent) {
        this.genome = new Genome(parent.genome, parent.genome);
    }
    
    public void eat(Steroid steroid) {
        health = health + 0.05;
    }
    
    public void reproduce() {
        health = health - 1;
    }
    
    public boolean canReproduce() {
        return health >= 2;
    }

    public boolean canEat(Steroid steroid) {
        if (Math.abs(x - steroid.x) < size / 2 && Math.abs(y - steroid.y) < size / 2) {
            return true;
        }
        return false;
    }

    public void draw() {
        Window.out.color(r, g, b);
        Window.out.square((int) x, (int) y, (int) (health * size));
    }
    
    public void move() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        
        // Pass inputs to the network for the genome
        double[] input = new double[5];
        
        // - health
        input[0] = Math.min(health, 1);
        
        // - number of creatures of this species nearby
        // TODO: implement world
        
        // - distance to closest enemy
        // TODO: need world
        
        // Based on the outputs
        double[] output = genome.network.compute(input);
        
        // attack
        if (output[0] >= output[1] && output[0] >= output[2]) {
            
        }
        // flee
        else if (output[1] > output[2]) {
            
        }
        // wander
        else {
            
        }
    }
    
    public void pointToward(Bogwark other) {
        angle = Math.atan2(other.y - y, other.x - x);
    }
    
    public boolean friendly(Bogwark other) {
        return Math.abs(r - other.r) + Math.abs(g - other.g) + Math.abs(b - other.b) < friendliness;
    }
    
    public void attack(Bogwark other) {
        if (Math.random() < Math.min(health, 0.5)) {
            health -= 0.01;
        }
        else {
            other.health -= 0.01;
        }
    }
}

class Steroid {
    int x = Window.random(0, Window.width());
    int y = Window.random(0, Window.height());
    
    public void draw() {
        Window.out.color("green");
        Window.out.circle(x, y, 2);
    }
}
*/
