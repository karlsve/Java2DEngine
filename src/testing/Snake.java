package testing;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import engine.BasicObject;
import engine.CollisionEventListener;
import engine.Engine;

public class Snake implements KeyListener
{
	Engine engine = null;
	SnakeElement player;
	
	static final int ROWS = 50;
	static final int COLUMNS = 50;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final Color SCREEN_COLOR = Color.LIGHT_GRAY;
	
	static final int UPDATE_RATE = 15;
	
	
	CollisionEventListener collisionListener = new CollisionEventListener()
	{

		@Override
		public void collision(BasicObject first, BasicObject second) {
			System.out.println("Collision");
			onGameOver();
		}

	};
	
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Snake();
			}
		});
	}
	
	protected void onGameOver() {
		engine.setTitle("Snake - GameOver");
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
		this.setEngine(new Engine("Snake", this.getKeyListener(), this.getCollisionListener(), SCREEN_WIDTH, SCREEN_HEIGHT, COLUMNS, ROWS, true, UPDATE_RATE, SCREEN_COLOR));
		this.getEngine().addObject(this.getPlayer());
		this.getEngine().addObject(new BasicObject(10, 10, Color.RED, engine));
        this.getEngine().addObject(new BasicObject(15, 15, Color.RED, engine));
		this.getEngine().addObject(new BasicObject(20, 20, Color.RED, engine));
        this.getEngine().addObject(new BasicObject(25, 25, Color.RED, engine));
		this.getEngine().addObject(new BasicObject(30, 30, Color.RED, engine));
        this.getEngine().addObject(new BasicObject(35, 35, Color.RED, engine));
		this.getEngine().addObject(new BasicObject(40, 40, Color.RED, engine));
		this.getEngine().addObject(new BasicObject(45, 45, Color.RED, engine));
        this.getEngine().addObject(new BasicObject(50, 50, Color.RED, engine));
		this.getEngine().engineStart();
	}
	
	public SnakeElement getPlayer()
	{
		if(player == null)
			player = new SnakeElement(5, 5, Color.BLACK, engine);
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
			player.upPressed();
			break;
		case KeyEvent.VK_DOWN:
			player.downPressed();
			break;
		case KeyEvent.VK_LEFT:
			player.leftPressed();
			break;
		case KeyEvent.VK_RIGHT:
			player.rightPressed();
			break;
		case KeyEvent.VK_ESCAPE:
			this.getEngine().engineShutdown();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
