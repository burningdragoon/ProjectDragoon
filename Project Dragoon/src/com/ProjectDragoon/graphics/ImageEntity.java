package com.ProjectDragoon.graphics;

import java.awt.Graphics;

import com.ProjectDragoon.entity.Entity;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;

/**
 * Simple entity that holds a plain texture image and a position
 * @author Alex
 *
 */
public class ImageEntity extends Entity {

	private static final long serialVersionUID = 1L;	
	
	private Vector position;
	private Texture image;
	
	public ImageEntity()
	{
		position = new Vector();
		image = new Texture();
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
	
	public boolean loadImage(String filename)
	{
		return image.loadImage(filename);
	}

	@Override
	public Object copy() {
		return null;
	}

	@Override
	public void update() {}

	@Override
	public void animate() {}

	@Override
	public void draw(Graphics g) {
		image.draw(g, (int)position.getX(), (int)position.getY());
	}
	@Override
	public void draw(Graphics g, Camera c)
	{
		
	}
}
