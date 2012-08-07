package com.ProjectDragoon.physics;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.util.Vector;

/**
 * A set of collision points for collision detection.
 * Each point (Vector) is to relative to the 'position' of the map.
 * Corresponding
 * @author Alex
 *
 */
public class CollisionMap {

	private Vector position;
	
	// LEFT collision points: horizontal position should be the same
	private Vector left_top;
	private Vector left_bottom;
	
	// RIGHT collision points
	private Vector right_top;
	private Vector right_bottom;
	
	// TOP collision points
	private Vector head_left;
	private Vector head_right;
	
	// BOTTOM collision points
	private Vector feet_left;
	private Vector feet_right;
	
	
	// Constructor(s)
	
	public CollisionMap()
	{
		position = new Vector();
		
		left_top = new Vector();
		left_bottom = new Vector();
		
		right_top = new Vector();
		right_bottom = new Vector();
		
		head_left = new Vector();
		head_right = new Vector();
		
		feet_left = new Vector();
		feet_right = new Vector();
	}
	
	public CollisionMap(Vector position)
	{
		this.position = position;
		
		left_top = new Vector();
		left_bottom = new Vector();
		
		right_top = new Vector();
		right_bottom = new Vector();
		
		head_left = new Vector();
		head_right = new Vector();
		
		feet_left = new Vector();
		feet_right = new Vector();
	}
	
	// --- End Constructos ---
	
	// Selectors and Mutators
	
	public Vector getPosition() { return position; }
	/**
	 * Nevermind?: >>The 'position' should always be a reference to an existing vector. This so the CollisionMap will move along with the entity it's attached too.<<
	 * @param value
	 */
	public void setPosition(Vector value) { position = value; }
	
	public Vector getLeftTop() { return left_top; }
	public Vector getLeftBottom() { return left_bottom; }
	public Vector getRightTop() { return right_top; }
	public Vector getRightBottom() { return right_bottom; }
	public Vector getHeadLeft() { return head_left; }
	public Vector getHeadRight() { return head_right; }
	public Vector getFeetLeft() { return feet_left; }
	public Vector getFeetRight() { return feet_right; }
	
	public void setLeftPoints(int x, int topY, int bottomY)
	{
		left_top = new Vector(x, topY, 0);
		left_bottom = new Vector(x, bottomY, 0);
	}
	public void setLeftPoints(double x, double topY, double bottomY)
	{
		left_top = new Vector(x, topY, 0);
		left_bottom = new Vector(x, bottomY, 0);
	}
	
	public void setRightPoints(int x, int topY, int bottomY)
	{
		right_top = new Vector(x, topY, 0);
		right_bottom = new Vector(x, bottomY, 0);
	}
	public void setRightPoints(double x, double topY, double bottomY)
	{
		right_top = new Vector(x, topY, 0);
		right_bottom = new Vector(x, bottomY, 0);
	}
	
	public void setHeadPoints(int leftX, int rightX, int y)
	{
		head_left = new Vector(leftX, y, 0);
		head_right = new Vector(rightX, y, 0);
	}
	public void setHeadPoints(double leftX, double rightX, double y)
	{
		head_left = new Vector(leftX, y, 0);
		head_right = new Vector(rightX, y, 0);
	}
	
	public void setFeetPoints(int leftX, int rightX, int y)
	{
		feet_left = new Vector(leftX, y, 0);
		feet_right = new Vector(rightX, y, 0);
	}
	public void setFeetPoints(double leftX, double rightX, double y)
	{
		feet_left = new Vector(leftX, y, 0);
		feet_right = new Vector(rightX, y, 0);
	}
	
	// --- End S&M ---
	
	// Other methods...
	
	/**
	 * Returns absolute left-top point
	 * @return
	 */
	public Vector LEFT_TOP()
	{
		Vector v = new Vector(position);
		v.Add(left_top);
		return v;
	}
	
	/**
	 * Returns absolute left-bottom point
	 * @return
	 */
	public Vector LEFT_BOTTOM()
	{
		Vector v = new Vector(position);
		v.Add(left_bottom);
		return v;
	}
	
	/**
	 * Returns absolute right-top point
	 * @return
	 */
	public Vector RIGHT_TOP()
	{
		Vector v = new Vector(position);
		v.Add(right_top);
		return v;
	}
	
	/**
	 * Returns absolute right-bottom point
	 * @return
	 */
	public Vector RIGHT_BOTTOM()
	{
		Vector v = new Vector(position);
		v.Add(right_bottom);
		return v;
	}
	
	/**
	 * Returns absolute head-left point
	 * @return
	 */
	public Vector HEAD_LEFT()
	{
		Vector v = new Vector(position);
		v.Add(head_left);
		return v;
	}
	
	/**
	 * Returns absolute head-right point
	 * @return
	 */
	public Vector HEAD_RIGHT()
	{
		Vector v = new Vector(position);
		v.Add(head_right);
		return v;
	}
	
	/**
	 * Returns absolute feet-left point
	 * @return
	 */
	public Vector FEET_LEFT()
	{
		Vector v = new Vector(position);
		v.Add(feet_left);
		return v;
	}
	
	/**
	 * Returns absolute feet-right point
	 * @return
	 */
	public Vector FEET_RIGHT()
	{
		Vector v = new Vector(position);
		v.Add(feet_right);
		return v;
	}
	
	
	public void draw(Graphics g)
	{
		g.setColor(Color.red);
		
		Vector v;
		int x;
		int y;
		
		v = LEFT_TOP();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = LEFT_BOTTOM();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = RIGHT_TOP();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = RIGHT_BOTTOM();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = HEAD_LEFT();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = HEAD_RIGHT();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = FEET_LEFT();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
		
		v = FEET_RIGHT();
		x = (int)v.getX();
		y = (int)v.getY();
		g.drawLine(x, y, x, y);
	}
	
}
