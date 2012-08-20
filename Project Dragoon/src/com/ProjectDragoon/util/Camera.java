package com.ProjectDragoon.util;

public class Camera {

	private int width;
	private int height;
	private int xMax;
	private int xMin;
	private int yMax;
	private int yMin;
	
	public int x;
	public int y;
	
	/*
	 * Constructors
	 */
	
	public Camera()
	{
		this(0,0);
	}
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
		x = 0;
		y = 0;
		xMax = 0;
		xMin = 0;
		yMax = 0;
		yMin = 0;
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public int getWidth() { return width; }
	public void setWidth(int w) { width = w; }
	
	public int getHeight() { return height; }
	public void setHeight(int h) { height = h; }
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
	public void setDimensions(int w, int h)
	{
		width = w;
		height = h;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setXRange(int min, int max)
	{
		xMin = min;
		xMax = max;
	}
	public void setYRange(int min, int max)
	{
		yMin = min;
		yMax = max;
	}
	
	/* -- End S&M -- */
	
	/*
	 * Other Methods
	 */
	public void reset()
	{
		x = 0;
		y = 0;
	}
	
	public void move(int mx, int my)
	{
		x += mx;
		y += my;
	}
	public void moveX(int mx)
	{
		x += mx;
	}
	public void moveY(int my)
	{
		y += my;
	}
	
	/*
	 * Free methods check if the camera has space available to the respective directions
	 */
	public boolean leftFree()
	{
		return x > xMin;
	}
	public boolean rightFree()
	{
		return x < xMax;
	}
	public boolean upFree()
	{
		return y > yMin;
	}
	public boolean downFree()
	{
		return y < yMax;
	}
	
	/* -- End Other -- */
	
}
