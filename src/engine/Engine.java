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


public class Engine extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8054078025904310863L;
	
	// Define variables for the game
	private int width = 0;
	private int height = 0;
	private int rows = 0;
	private int columns = 0;
	private int cell_width = 0;
	private int cell_height = 0;
	private int fps = 0;
	private String titleText = "";
	private boolean showFPS = false;
	
	private Color color;

	private long updateRate = 0;
	
	
	// Game state enum
	static enum State
	{
		INITIALIZED, PLAYING, PAUSED, GAMEOVER, DESTROYED, UPDATING
	}
	
	// State
	static State state;
	
	// Define instance variables
	private List<BasicObject> objects = new ArrayList<BasicObject>();
	private List<BasicObject> objectsToAdd = new ArrayList<BasicObject>();
	private List<BasicObject> objectsToRemove = new ArrayList<BasicObject>();
	private CollisionEventListener collisionListener = null;
	
	// Handle for custom game panel
	private Screen screen;
	
	public Engine(String title, KeyListener keyListener, CollisionEventListener collisionListener, int width, int height, int columns, int rows, boolean showFPS, int updateRate, Color color)
	{
	    this.showFPS = showFPS;
		this.collisionListener = collisionListener;
		this.width = width;
		this.height = height;
		this.columns = columns;
		this.rows = rows;
		this.cell_width = width / columns;
		this.cell_height = height / rows;
		this.updateRate = updateRate;
		this.color = color;
		this.titleText = title;
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
    
    public Engine(String title, int width, int height, int columns, int rows, boolean showFPS, int updateRate, Color color)
    {
        // Initialize the objects and the screen
        this.showFPS = showFPS;
        this.width = width;
        this.height = height;
        this.columns = columns;
        this.rows = rows;
        this.cell_width = width / columns;
        this.cell_height = height / rows;
        this.updateRate = updateRate;
        this.color = color;
        this.titleText = title;
        engineInit();
        
        // UI components
        screen = new Screen(color);
        screen.setPreferredSize(new Dimension(this.getScreenWidth(), this.getScreenHeight()));
        this.setContentPane(screen);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle(title);
        this.setSize(width, height);
        this.setVisible(true);
    }
	
	private void updateTitle() {
	    if(this.showFPS) {
	        this.setTitle(this.titleText + " " + this.fps);
	    } else {
            this.setTitle(this.titleText);
	    }
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getScreenColumns() {
		return this.columns;
	}
	
	public int getScreenRows() {
		return this.rows;
	}
	
	public int getScreenWidth() {
		return this.width;
	}

	public int getScreenHeight() {
		return this.height;
	}
	
	public int getCellWidth() {
		return this.cell_width;
	}
	
	public int getCellHeight() {
		return this.cell_height;
	}

	public void engineInit() 
	{
		for(int x = -1; x<this.columns; x++) {
			objects.add(new WallObject(x, -1, this));
		}
		for(int x = -1; x<this.columns; x++) {
			objects.add(new WallObject(x, this.columns, this));
		}
		for(int y = -1; y<this.rows; y++) {
			objects.add(new WallObject(-1, y, this));
		}
		for(int y = -1; y<this.rows; y++) {
			objects.add(new WallObject(this.rows, y, this));
		}
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
		int frames = 0;
		int startSecond = (int)((System.currentTimeMillis() / 1000) % 60);
		while(state != State.GAMEOVER)
		{
			beginTime = System.nanoTime();
			if(state == State.PLAYING)
			{
				state = State.UPDATING;
				engineUpdate();
				engineUpdateObjects();
				state = State.PLAYING;
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
			frames++;
			if(startSecond < (int)((System.currentTimeMillis() / 1000) % 60)) {
			    this.fps = frames;
			    frames = 0;
		        startSecond = (int)((System.currentTimeMillis() / 1000) % 60);
			}
		}
	}
	
	private long getUpdatePeriod() {
		long updatePeriod = 1000000000L / this.updateRate;
		return updatePeriod;
	}

	public void engineShutdown()
	{
		
	}
	
	public void engineUpdateScreenSize(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
		this.cell_width = this.width / this.columns;
		this.cell_height = this.height / this.columns;
		for(BasicObject object : objects) {
			object.width = this.cell_width;
			object.height = this.cell_height;
		}
	}
	
	public void engineUpdate()
	{
		for(BasicObject object : objects)
		{
			object.update();
		}
	}
	
	public void engineUpdateObjects() {
		objects.addAll(objectsToAdd);
		objectsToAdd.clear();
		
		objects.removeAll(objectsToRemove);
		objectsToRemove.clear();
	}
	
	public void engineDraw(Graphics2D g)
	{
	    this.updateTitle();
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
	
	public void onCollision(BasicObject first, BasicObject second) {
		this.collisionListener.collision(first, second);
	}
	
	public List<BasicObject> getObjects() {
		return objects;
	}
	
	public void addObject(BasicObject object)
	{
	    if((object.getX() > 0 && object.getX() < this.rows) && (object.getY() > 0 && object.getY() < this.columns)) {
	        if(state == State.UPDATING) {
	            objectsToAdd.add(object);
	        } else {
	            objects.add(object);
	        }
	    }
	}
	
	public void removeObject(BasicObject object)
	{
		if(state == State.UPDATING) {
			objectsToRemove.add(object);
		} else {
			objects.remove(object);
		}
	}
	
	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.rows;
	}

	public boolean isShowFPS() {
        return showFPS;
    }

    public void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
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
        
        public Screen(Color color)
        {
            this.color = color;
            this.setFocusable(true);
            this.requestFocus();
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
