package engine;

import java.util.EventListener;

public interface CollisionEventListener extends EventListener
{
	static enum Collision
	{
		TOP, LEFT, RIGHT, BOT, TOPRIGHT, TOPLEFT, BOTRIGHT, BOTLEFT
	}
	
	public void collision(BasicObject first, BasicObject second, Collision collision);
}

