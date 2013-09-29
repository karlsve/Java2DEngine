package testing;

import java.awt.Color;

import engine.Engine;
import engine.MovingObject;

public class RandomObject extends MovingObject {

    public RandomObject(int x, int y, Color color, Engine engine) {
        super(x, y, color, engine);
    }
    
    @Override
    public void update() {
        super.update();
        if(Math.random() > .5) {
            this.setUp(true);
            this.setDown(false);
        } else {
            this.setUp(false);
            this.setDown(true);
        }
        if(Math.random() > .5) {
            this.setLeft(true);
            this.setRight(false);
        } else {
            this.setLeft(false);
            this.setRight(true);
        }
    }

}
