package engine;

import java.util.EventListener;

public interface CollisionEventListener extends EventListener
{
	public void collision(BasicObject first, BasicObject second);
}

