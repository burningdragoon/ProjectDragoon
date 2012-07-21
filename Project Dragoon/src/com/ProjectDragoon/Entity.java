package com.ProjectDragoon;

import java.awt.Graphics;

import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.interfaces.Utility;

public abstract class Entity implements Utility {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private boolean visible;
	private boolean alive;
	private int objectType;
	private int lifetimeLength;
	private Timer lifetimeTimer;
	
	/*
	 * Constructors
	 */

	public Entity()
	{
		id = -1;
		name = "";
		visible = true;
		alive = true;
		objectType = 0;
		lifetimeLength = 0;
		lifetimeTimer = new Timer();
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public void setID(int value)
	{
		id = value;
	}
	public int getID()
	{
		return id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}

	public void setObjectType(int type)
	{
		objectType = type;
	}
	public int getObjectType()
	{
		return objectType;
	}
	
	public void setVisible(boolean value)
	{
		visible = value;
	}
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setAlive(boolean value)
	{
		alive = value;
	}
	public boolean isAlive()
	{
		return alive;
	}
	
	public void setLifetimer(int ms)
	{
		lifetimeLength = ms;
		lifetimeTimer.reset();
	}
	public int getLifetime()
	{
		return lifetimeLength;
	}
	
	/* -- End S&M -- */
	
	/*
	 * Lifetime methods
	 */
	
	public boolean lifetimeExpired()
	{
		return lifetimeTimer.stopwatch(lifetimeLength);
	}
	
	/* -- End Lifetime -- */
	
	/*
	 * Abstract methods to be completed in extending classes
	 */
	public abstract void update();
	public abstract void animate();
	public abstract void draw(Graphics g);
	
	
}
