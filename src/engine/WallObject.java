package engine;

import java.awt.Graphics2D;

public class WallObject extends BasicObject {

	public WallObject(int x, int y, Engine engine) {
		super(x, y, engine.getColor(), engine);
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
