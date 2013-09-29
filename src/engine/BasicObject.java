package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class BasicObject {
	
	protected int x, y;
	protected int width, height;
	protected Color color;
	protected Engine engine;
	
	public BasicObject(int x, int y, Color color, Engine engine)
	{
		this.x = x;
		this.y = y;
		this.width = engine.getCellWidth();
		this.height = engine.getCellHeight();
		this.color = color;
		this.engine = engine;
	}
	
	public void update()
	{
	}

	public void draw(Graphics2D g)
	{
		g.setColor(this.color);
		g.fillRect(x*width, y*height, width, height);
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
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
	
}
