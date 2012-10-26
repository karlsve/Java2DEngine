package testing;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import engine.BasicObject;
import engine.CollisionEventListener;
import engine.Engine;
import engine.MovingObject;

public class Snake implements KeyListener
{
	Engine engine = null;
	MovingObject player;
	

	static final int CELL_SIZE = 10;
	static final int ROWS = 50;
	static final int COLUMNS = 50;
	static final int SCREEN_WIDTH = COLUMNS*CELL_SIZE;
	static final int SCREEN_HEIGHT = ROWS*CELL_SIZE;
	static final Color SCREEN_COLOR = Color.LIGHT_GRAY;
	
	static final int UPDATE_RATE = 15;
	
	
	CollisionEventListener collisionListener = new CollisionEventListener()
	{

		@Override
		public void collision(BasicObject first, BasicObject second, Collision collision) {
			if(first instanceof MovingObject)
			{
				MovingObject moving = ((MovingObject)first);
				switch(collision)
				{
				case TOP: moving.upPossible = false; break;
				case BOT: moving.downPossible = false; break;
				case LEFT: moving.leftPossible = false; break;
				case RIGHT: moving.rightPossible = false; break;
				case BOTLEFT: break;
				case BOTRIGHT: break;
				case TOPLEFT: break;
				case TOPRIGHT: break;
				}
			}
			if(second instanceof MovingObject)
			{
				MovingObject moving = ((MovingObject)second);
				switch(collision)
				{
				case TOP: moving.upPossible = false; break;
				case BOT: moving.downPossible = false; break;
				case LEFT: moving.leftPossible = false; break;
				case RIGHT: moving.rightPossible = false; break;
				case BOTLEFT: break;
				case BOTRIGHT: break;
				case TOPLEFT: break;
				case TOPRIGHT: break;
				}
			}
		}

	};
	
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Snake snake = new Snake();
				snake.setEngine(new Engine("Snake", snake.getKeyListener(), snake.getCollisionListener(), ROWS, COLUMNS, CELL_SIZE, UPDATE_RATE, SCREEN_COLOR));
				snake.getEngine().addObject(snake.getPlayer());
				snake.getEngine().addObject(new BasicObject(10, 10, Color.RED, CELL_SIZE, CELL_SIZE));
				snake.getEngine().addObject(new BasicObject(20, 20, Color.RED, CELL_SIZE, CELL_SIZE));
				snake.getEngine().addObject(new BasicObject(30, 30, Color.RED, CELL_SIZE, CELL_SIZE));
				snake.getEngine().addObject(new BasicObject(40, 40, Color.RED, CELL_SIZE, CELL_SIZE));
				snake.getEngine().addObject(new BasicObject(45, 45, Color.RED, CELL_SIZE, CELL_SIZE));
				snake.getEngine().engineStart();
			}
		});
	}
	
	protected void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	protected Engine getEngine()
	{
		return this.engine;
	}

	protected CollisionEventListener getCollisionListener() {
		return collisionListener;
	}

	public Snake()
	{
		player = new MovingObject(4, 6, Color.GREEN, CELL_SIZE, CELL_SIZE);
	}
	
	public MovingObject getPlayer()
	{
		return player;
	}
	
	public KeyListener getKeyListener()
	{
		return this;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			player.setUp(true);
			break;
		case KeyEvent.VK_DOWN:
			player.setDown(true);
			break;
		case KeyEvent.VK_LEFT:
			player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(true);
			break;
		case KeyEvent.VK_ESCAPE:
			this.getEngine().engineShutdown();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			player.setUp(false);
			break;
		case KeyEvent.VK_DOWN:
			player.setDown(false);
			break;
		case KeyEvent.VK_LEFT:
			player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
