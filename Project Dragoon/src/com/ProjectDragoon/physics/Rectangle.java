package com.ProjectDragoon.physics;

public class Rectangle {

	public int x, y, width, height;
	
	public Rectangle() {}
	
	public boolean collides(Rectangle r)
	{
		return !(
				x > r.x + r.width ||
				x + width < r.x ||
				y > r.y + r.height ||
				y + height < r.y
				);
	}
	
}
