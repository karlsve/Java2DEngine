package testing.randomcolors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import engine.BasicObject;
import engine.Engine;

public class RandomColorObject extends BasicObject {

    public RandomColorObject(int x, int y, Engine engine) {
        super(x, y, RandomColorObject.generateColor(), engine);
    }
    
    @Override
    public void update() {
        this.setColor(RandomColorObject.generateColor());
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fill3DRect(x*height, y*width, width, height, true);
    }
    
    public static Color generateColor() {
        return new Color(RandomColorObject.generateColorValue(), RandomColorObject.generateColorValue(), RandomColorObject.generateColorValue());
    }
    
    public static int generateColorValue() {
        Random random = new Random();
        return (int)(((double)random.nextInt(100) / 100)*255);
    }

}
