package testing;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.SwingUtilities;

import engine.BasicObject;
import engine.CollisionEventListener;
import engine.Engine;

public class Randomizer implements KeyListener
{
	Engine engine = null;
	
	static final int ROWS = 90;
	static final int COLUMNS = 160;
	static final int SCREEN_WIDTH = 1920;
	static final int SCREEN_HEIGHT = 1080;
	static final Color SCREEN_COLOR = Color.LIGHT_GRAY;
	
	static final int UPDATE_RATE = 15;
	
	
	CollisionEventListener collisionListener = new CollisionEventListener()
	{

		@Override
		public void collision(BasicObject first, BasicObject second) {
		    first.setColor(second.getColor());
		}

	};
	
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Randomizer();
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

	public Randomizer()
	{
		this.setEngine(new Engine("Randomizer", this.getKeyListener(), this.getCollisionListener(), SCREEN_WIDTH, SCREEN_HEIGHT, COLUMNS, ROWS, true, UPDATE_RATE, SCREEN_COLOR));
		this.makeItRandom();
		this.getEngine().engineStart();
	}
	
	private void makeItRandom() {
	    for(int i = 0; i<COLUMNS; i+=5) {
	        for(int j = 0; j<ROWS; j+=5) {
	            Random rand = new Random();
	            this.getEngine().addObject(new RandomObject(i, j, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), this.getEngine()));
	        }
	    }
	}
	
	public KeyListener getKeyListener()
	{
		return this;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
