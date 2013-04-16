package com.ProjectDragoon.physics;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.interfaces.Utility;

/**
 * An Axis-Aligned Bounding Box (AABB) used for collision detection. "HitBox" has a much nicer, quick sound to it.
 * An important detail to note is that the HitBox has two (2) positions: one for where the parent entity is and one for the AABB position in relation to the entity position. This is mainly to always move the hitbox with the entity.
 * @author Alex
 *
 */
public class HitBox implements Utility {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * The Host Position is the position of the entity that owns the HitBox. This vector is not a copy, but a reference.
	 */
	private Vector hostPosition;
	/**
	 * The Position is the vector location of the HitBox in relation to the vector location of the host entity (from HostPosition)
	 */
	private Vector position;
	private int height;
	private int width;
	
	/* 
	 * -- Constructors -- 
	 */
	
	public HitBox()
	{
		hostPosition = new Vector();
		position = new Vector();
		height = 0;
		width = 0;
	}
	
	public HitBox(Vector hostPos)
	{
		hostPosition = hostPos;
		position = new Vector();
		height = 0;
		width = 0;
	}
	
	public HitBox(Vector hostPos, Vector pos, int width, int height)
	{
		hostPosition = hostPos;
		position = new Vector(pos);
		this.height = height;
		this.width = width;
	}
	
	/* -- End of Constructors -- */
	
	/*
	 * Selectors & Mutators
	 */
	
	public Vector getHostPosition() { return hostPosition; }
	public Vector getPosition() { return position; }
	public double getXPos() { return position.getX(); }
	public double getYPos() { return position.getY(); }
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	
	public void setHostPosition(Vector hostPos) { hostPosition = hostPos; }
	public void setPosition(Vector pos) { position.set(pos); }
	public void setPosition(int x, int y) { position.set(x, y, 0); }
	public void setPosition(double x, double y) { position.set(x, y, 0.0); }
	public void setHeight(int h) { height = h; }
	public void setWidth(int w) { width = w; }
	
	/* -- End S&M -- */
	
	/*
	 * Collision Detection methods
	 */
	
	public Vector TopLeft()
	{
		Vector v = new Vector(hostPosition);
		v.Add(position);
		return v;
	}
	public Vector TopRight()
	{
		Vector v = new Vector(hostPosition);
		v.Add(position);
		v.moveX(width);
		return v;
	}
	public Vector BottomLeft()
	{
		Vector v = new Vector(hostPosition);
		v.Add(position);
		v.moveY(height);
		return v;
	}
	public Vector BottomRight()
	{
		Vector v = new Vector(hostPosition);
		v.Add(position);
		v.move(width, height);
		return v;
	}
	
	public int top()
	{
		return (int)position.getY() + (int)hostPosition.getY();
	}
	public int left()
	{
		return (int)position.getX() + (int)hostPosition.getX();
	}
	public int right()
	{
		return (int)position.getX() + width + (int)hostPosition.getX();
	}
	public int bottom()
	{
		return (int)position.getY() + height + (int)hostPosition.getY();
	}
	
	/* -- End CD methods -- */
	
	/*
	 * Drawing Methods
	 */
	
	public void draw(Graphics g, Camera camera)
	{
		g.setColor(Color.gray);
		
		//Vector v = this.TopLeft();
		int x = left();// (int)v.getX();
		int y = top();//(int)v.getY();
		//g.fillRect(x - camera.x, y - camera.y, width, height);
	}

	/* -- End Drawing methods -- */
	
	/*
	 * Utility Interface Overrides
	 */
	@Override
	public HitBox copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/* -- End of Utility Overrides -- */

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		// nothing to see here.
	}
	
}
