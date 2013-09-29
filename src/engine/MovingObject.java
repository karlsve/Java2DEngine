package engine;

import java.awt.Color;

public class MovingObject extends BasicObject 
{
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	private int speed = 0;
	private int frame = 0;
	
	public MovingObject(int x, int y, Color color, Engine engine) {
		super(x, y, color, engine);
	}
	
	public void setSpeed(int value) {
		this.speed = value;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setUp(boolean value) {
		this.up = value;
	}
	
	public boolean getUp() {
		return this.up;
	}
	
	public void setDown(boolean value) {
		this.down = value;
	}
	
	public boolean getDown() {
		return this.down;
	}
	
	public void setLeft(boolean value) {
		this.left = value;
	}
	
	public boolean getLeft() {
		return this.left;
	}
	
	public void setRight(boolean value) {
		this.right = value;
	}
	
	public boolean getRight() {
		return this.right;
	}
	
	@Override
	public void update() {
		if(this.frame == this.speed) {
			move();
			this.frame = 0;
		} else {
			this.frame++;
		}
	}
	
	private void move() {
		if(collisionDetection()) {
			return;
		}
		if(this.up) {
			this.y--;
		}
		if(this.down) {
			this.y++;
		}
		if(this.left) {
			this.x--;
		}
		if(this.right) {
			this.x++;
		}
	}
	
	private boolean collisionDetection() {
		int newx = x;
		int newy = y;
		if(this.up) {
			newy--;
		}
		if(this.down) {
			newy++;
		}
		if(this.left) {
			newx--;
		}
		if(this.right) {
			newx++;
		}
		return collision(newx, newy);
	}

	private boolean collision(int newx, int newy) {
		for(BasicObject object : engine.getObjects()) {
			if(object != this) {
				if(object.x == newx && object.y == newy) {
					this.engine.onCollision(this, object);
					return true;
				}
			}
		}
		return false;
	}

}
