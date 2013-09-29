package testing;

import java.awt.Color;

import engine.Engine;
import engine.MovingObject;

public class SnakeElement extends MovingObject {
	
	boolean lastUp, lastDown, lastLeft, lastRight;
	boolean stepDone = true;
	boolean addElement = false;
	
	SnakeElement follower = null;

	public SnakeElement(int x, int y, Color color, Engine engine) {
		super(x, y, color, engine);
		this.setSpeed(1);
	}
	
	public void setFollower(SnakeElement element) {
		follower = element;
	}
	
	@Override
	public void update() {
		if(stepDone) {
			updateFollower();
			updateLastDirection();
			stepDone = false;
		} else {
			stepDone = true;
		}
		super.update();
	}
	
	public void toggleAddElement() {
		this.addElement = this.addElement == false;
	}

	private void updateFollower() {
		if(addElement && follower != null) {
			follower.toggleAddElement();
			this.toggleAddElement();
		} else if(addElement) {
			follower = new SnakeElement(this.getX()-(this.getUp() ? 1 : this.getDown() ? -1 : 0), this.getY()-(this.getLeft() ? 1 : this.getRight() ? -1 : 0), Color.GREEN, this.engine);
			engine.addObject(follower);
			this.toggleAddElement();
		}
		if(follower != null) {
			if(lastUp) {
				follower.upPressed();
			} else if(lastDown) {
				follower.downPressed();
			} else if(lastLeft) {
				follower.leftPressed();
			} else if(lastRight) {
				follower.rightPressed();
			}
		}
	}
	
	public void updateLastDirection() {
		this.lastUp = this.getUp();
		this.lastDown = this.getDown();
		this.lastLeft = this.getLeft();
		this.lastRight = this.getRight();
	}

	public void upPressed() {
		if(!this.getUp() && !this.getDown()) {
			this.setUp(true);
			this.setLeft(false);
			this.setRight(false);
		}
	}

	public void downPressed() {
		if(!this.getUp() && !this.getDown()) {
			this.setDown(true);
			this.setLeft(false);
			this.setRight(false);
		}
	}

	public void leftPressed() {
		if(!this.getLeft() && !this.getRight()) {
			this.setLeft(true);
			this.setUp(false);
			this.setDown(false);
		}
	}

	public void rightPressed() {
		if(!this.getLeft() && !this.getRight()) {
			this.setRight(true);
			this.setUp(false);
			this.setDown(false);
		}
	}

}
