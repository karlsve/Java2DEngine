package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import engine.CollisionEventListener.Collision;

public class BasicObject {
	
	protected int x, y;
	protected int width, height;
	protected Color color;
	
	public BasicObject(int x, int y, Color color, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(this.color);
		g.fill3DRect(x * width, y * height,
				width - 1, height - 1, true);
	}
	
	public void destroy()
	{
		
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public boolean intersects(BasicObject object)
	{
		return ((this.y == object.y) && (this.x == object.x));
	}
	
	public boolean contains(int x, int y)
	{
		return ((this.y == y) && (this.x == x));
	}
	
	public Shape getBounds2D()
	{
		return null;
	}

	public boolean nextTo(BasicObject second) 
	{
		if(this.x+1 == second.x && this.y == second.y)
			return true;
		else if(this.x-1 == second.x && this.y == second.y)
			return true;
		else if(this.x-1 == second.x && this.y-1 == second.y)
			return true;
		else if(this.x-1 == second.x && this.y+1 == second.y)
			return true;
		else if(this.x+1 == second.x && this.y-1 == second.y)
			return true;
		else if(this.x+1 == second.x && this.y+1 == second.y)
			return true;
		else if(this.x == second.x && this.y+1 == second.y)
			return true;
		else if(this.x == second.x && this.y-1 == second.y)
			return true;
		else
			return false;
	}
	
	public Collision getCollisionDirection(BasicObject second)
	{
		if(this.x+1 == second.x && this.y == second.y)
			return Collision.RIGHT;
		else if(this.x-1 == second.x && this.y == second.y)
			return Collision.LEFT;
		else if(this.y+1 == second.y && this.x == second.x)
			return Collision.BOT;
		else if(this.y-1 == second.y && this.x == second.x)
			return Collision.TOP;
		else
			return Collision.BOTLEFT;
	}
	
}
