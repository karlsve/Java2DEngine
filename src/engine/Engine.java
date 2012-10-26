package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.CollisionEventListener.Collision;


public class Engine extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8054078025904310863L;
	
	// Define variables for the game
	private int rows = 0;
	private int columns = 0;
	private int cell_size = 0;
	
	private Color color;

	private long updateRate = 0;
	
	
	// Game state enum
	static enum State
	{
		INITIALIZED, PLAYING, PAUSED, GAMEOVER, DESTROYED
	}
	
	// State
	static State state;
	
	// Define instance variables
	private List<BasicObject> objects = new ArrayList<BasicObject>();
	private CollisionEventListener collisionListener = null;
	
	// Handle for custom game panel
	private Screen screen;
	
	public Engine(String title, KeyListener keyListener, CollisionEventListener collisionListener, int rows, int columns, int cell_size, int updateRate, Color color)
	{
		// Initialize the objects and the screen
		this.collisionListener = collisionListener;
		this.rows = rows;
		this.columns = columns;
		this.cell_size = cell_size;
		this.updateRate = updateRate;
		this.color = color;
		engineInit();
		
		// UI components
		screen = new Screen(keyListener, color);
		screen.setPreferredSize(new Dimension(this.getScreenWidth(), this.getScreenHeight()));
		this.setContentPane(screen);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle(title);
		this.setVisible(true);
		
	}

	private int getScreenHeight() {
		return this.rows*this.cell_size;
	}

	private int getScreenWidth() {
		return this.columns*this.cell_size;
	}

	public void engineInit() 
	{
		state = State.INITIALIZED;
	}
	
	public void engineStart()
	{
		
		Thread engineThread = new Thread()
		{
			
			@Override
			public void run()
			{
				engineLoop();
			}
			
		};
		
		engineThread.start();
		
	}
	
	public void engineLoop()
	{
		state = State.PLAYING;
		long beginTime, timeTaken, timeLeft;
		
		while(state != State.GAMEOVER)
		{
			beginTime = System.nanoTime();
			if(state == State.PLAYING)
			{
				engineUpdate();
			}
			
			repaint();
			
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (this.getUpdatePeriod() - timeTaken) / 1000000;
			if (timeLeft < 10) timeLeft = 10;
			try
			{
				Thread.sleep(timeLeft);
			}
			catch(InterruptedException e) { }
		}
	}
	
	private long getUpdatePeriod() {
		long updatePeriod = 1000000000L / this.updateRate;
		return updatePeriod;
	}

	public void engineShutdown()
	{
		
	}
	
	public void engineUpdate()
	{
		this.collisionDetection();
		for(BasicObject object : objects)
		{
			object.update();
		}
	}
	
	public void engineDraw(Graphics2D g)
	{
		switch(state)
		{
		case INITIALIZED:
	        // ......
	        break;
	     case PLAYING:
	 		for(BasicObject object : objects)
	 		{
	 			object.draw(g);
	 		}
	        break;
	     case PAUSED:
	        // ......
	        break;
	     case GAMEOVER:
	        // ......
	        break;
	     case DESTROYED:
	    	 // ....
	    	 break;
		default:
			break;
		}
	}
	
	private void collisionDetection()
	{
		for(int i = 0; i<objects.size(); i++)
		{
			BasicObject first = objects.get(i);
			if(first.x < 1)
			{
				collisionListener.collision(first, new WallObject(0, 0, this.color, 0, 0), Collision.LEFT);
			}
			if(first.x >= getColumns())
			{
				collisionListener.collision(first, new WallObject(getColumns(), 0, this.color, 0, 0), Collision.RIGHT);
			}
			if(first.y < 1)
			{
				collisionListener.collision(first, new WallObject(0, 0, this.color, 0, 0), Collision.TOP);
			}
			if(first.y >= getRows())
			{
				collisionListener.collision(first, new WallObject(0, getRows(), this.color, 0, 0), Collision.BOT);
			}
			for(int j = i+1; j<objects.size(); j++)
			{
				BasicObject second = objects.get(j);
				if(first.nextTo(second))
				{
					collisionListener.collision(first, second, first.getCollisionDirection(second));
				}
			}
		}
	}
	
	public void addObject(BasicObject object)
	{
		objects.add(object);
	}
	
	public void removeObject(BasicObject object)
	{
		objects.remove(object);
	}
	
	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.rows;
	}

	class Screen extends JPanel
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 8054078035904310863L;
		
		private Color color;
		
		public Screen(KeyListener keyListener, Color color)
		{
			this.color = color;
			this.setFocusable(true);
			this.requestFocus();
			this.addKeyListener(keyListener);
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			super.paintComponent(g2d);
			setBackground(this.color);
			
			// Draw objects
			engineDraw(g2d);
		}
		
		public boolean contains(int x, int y)
		{
			if((x < 0) || (x >= getRows()))
			{
				return false;
			}
			if((y < 0) || (y >= getColumns()))
			{
				return false;
			}
			return true;
		}

	}
	
}
