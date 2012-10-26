package engine;

import java.awt.Color;

public class MovingObject extends BasicObject 
{
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	public boolean upPossible = true;
	public boolean downPossible = true;
	public boolean leftPossible = true;
	public boolean rightPossible = true;
	
	public MovingObject(int x, int y, Color color, int width, int height) {
		super(x, y, color, width, height);
	}
	
	public void setUp(boolean value)
	{
		this.up = value;
	}
	
	public void setDown(boolean value)
	{
		this.down = value;
	}
	
	public void setLeft(boolean value)
	{
		this.left = value;
	}
	
	public void setRight(boolean value)
	{
		this.right = value;
	}
	
	@Override
	public void update()
	{
		if (this.up && upPossible)
		{
			y--;
			setAllPossible();
		}
		if (this.down && downPossible)
		{
			y++;
			setAllPossible();
		}
		if (this.left && leftPossible)
		{
			x--;
			setAllPossible();
		}
	    if(this.right && rightPossible)
	    {
	    	x++;
	    	setAllPossible();
	    }
	}

	private void setAllPossible() {
		leftPossible = true;
		rightPossible = true;
		upPossible = true;
		downPossible = true;
	}

}
