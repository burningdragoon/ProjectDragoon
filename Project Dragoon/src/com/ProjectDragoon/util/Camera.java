package com.ProjectDragoon.util;

import com.ProjectDragoon.sprites.SpriteEntity;

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
	
	/**
	 * Adjust the camera position to focus on the passed Entity.
	 * The Entity e will appear to be at the center (or relative center) of the screen as long as 
	 * there is space between where the Entity is in relation to the edge and center of the camera.
	 * @param e
	 */
	public void adjust(SpriteEntity e)
	{
		/*
		 * For each axis (x,y),
		 * 1 - determine the center of the entity.
		 * 2 - determine the distance between the entity and the edge of the camera
		 * 3 - determine the distance between the entity and the center of the camera
		 * 4 - adjust the camera to keep the entity in the center.
		 * 		-- only adjust if the entity is not near the edge of the camera.
		 */
		
		// x-axis
		// 1
		int spriteX = (int)e.getXPos() + (e.getWidth() / 2);
		// 2
		int cameraOffsetX = spriteX - x;
		// 3
		int centerOffsetX = cameraOffsetX - (width / 2);
		// 4
		x += centerOffsetX;
		if(!leftFree())
		{
			x = xMin;
		}
		else if(!rightFree())
		{
			x = xMax;
		}
		
		// y-axis
		// 1
		int spriteY = (int)e.getYPos() + (e.getHeight() / 2);
		// 2
		int cameraOffsetY = spriteY - y;
		// 3
		int centerOffsetY = cameraOffsetY - (height / 2);
		// 4
		y += centerOffsetY;
		if(!upFree())
		{
			y = yMin;
		}
		else if(!downFree())
		{
			y = yMax;
		}
	}
	
	/* -- End Other -- */
	
}
