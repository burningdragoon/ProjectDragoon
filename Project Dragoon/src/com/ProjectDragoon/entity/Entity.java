package com.ProjectDragoon.entity;

import java.awt.Graphics;

import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.Vector;
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
	
	protected Vector position;
	protected Vector velocity;
	
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
		
		position = new Vector();
		velocity = new Vector();
	}
	
	public Entity(Vector position)
	{
		id = -1;
		name = "";
		visible = true;
		alive = true;
		objectType = 0;
		lifetimeLength = 0;
		lifetimeTimer = new Timer();
		
		this.position = new Vector(position);
		velocity = new Vector();
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public void setID(int value) {	id = value;	}
	public int getID() {	return id;	}
	
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
	
	
	public Vector getPosition()	{	return position;	}
	public void setPosition(Vector v) {	position.set(v);	}
	public void setPosition(double x, double y) {	position.set(x, y, 0);	}
	public void setPosition(int x, int y) {	position.set(x, y, 0);	}
	
	public double getXPos() {	return position.getX();	}
	public void setXPos(double x) {	position.setX(x);	}
	public void setXPos(int x) {	position.setX(x);	}
	
	public double getYPos()	{	return position.getY();	}
	public void setYPos(double y) {	position.setY(y);	}
	public void setYPos(int y) {	position.setY(y);	}
	
	public Vector getVelocity() {	return velocity;	}
	public void setVelocity(Vector v) { velocity.set(v);	}
	public void setVelocity(double x, double y) { velocity.set(x, y, 0);	}
	public void setVelocity(int x, int y) { velocity.set(x, y, 0);	}
	
	public double getXVel() { return velocity.getX();	}
	public void setXVel(double x) { velocity.setX(x);	}
	public void setXVel(int x) { velocity.setX(x);	}
	
	public double getYVel() { return velocity.getY();	}
	public void setYVel(double y) { velocity.setY(y);	}
	public void setYVel(int y) { velocity.setY(y);	}
	
	
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
	public abstract void draw(Graphics g, Camera c);
	
	/* -- */
	
	/*
	 * Utility Methods
	 * +NOTE+ Not necessary to declare here, since Entity is also abstract, but in some cases it is useful
	 */
	
	public void restore()
	{
		lifetimeTimer.reset();
	}
	
	/* -- End Utility Methods -- */
	
}
