package com.ProjectDragoon;

import java.awt.Component;
import java.awt.Graphics;

import com.ProjectDragoon.util.KeyInputHandler;

/**
 * A Game Component is used for the different game modes possible.
 * By extending the Java Component class, a GameComponent will have access to the parent GamePanel
 * @author Alex
 *
 */
public abstract class GameComponent extends Component{


	private static final long serialVersionUID = 1L;

	public GameComponent() {}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	public KeyInputHandler getKeyHandler()
	{
		return ((GamePanel)this.getParent()).keyHandler;
	}
}
