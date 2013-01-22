package com.ProjectDragoon.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ProjectDragoon.Entity;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;

public class NumberSprite extends Entity {

	private static final long serialVersionUID = 1L;
	
	private Vector position;
	//private Texture numbers;
	private BufferedImage image;
	private int value;
	//private Color color;
	
	public NumberSprite()
	{
		super();
		position = new Vector();
		image = null;
		value = 0;
	}
	
	public NumberSprite(Texture t, int value)
	{
		this();
		configureImage(t, value);
	}

	/*
	 * Selectors and Mutators
	 */
	
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
	
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}
	
	/* -- End S&M -- */
	
	/*
	 * Image Manipulation
	 */
	
	/**
	 * Sets up the NumberSprite's image to hold the number value
	 * @param numbers
	 * @param value
	 */
	public void configureImage(Texture numbers, int value)
	{
		if(value < 0)
			value = Math.abs(value);
		
		this.value = value;
		String stringVal = Integer.toString(value);
		int digits = stringVal.length();
		int numWidth = numbers.getImage().getWidth() / 10;
		int numHeight = numbers.getImage().getHeight();
		
		image = new BufferedImage(numWidth * digits, numHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = image.getGraphics();
		for(int i = 0; i < digits; i++)
		{
			int digit = Integer.parseInt(stringVal.charAt(i)+"");
			int desX = i * numWidth;
			int srcX = digit * numWidth;
			
			numbers.draw(g, desX, 0, srcX, 0, numWidth, numHeight);
		}
	}
	
	/* -- End Image Manip -- */
	
	/*
	 * Abstract methods from Entity class
	 */

	@Override
	public void update() 
	{
	}

	@Override
	public void animate() 
	{	
	}

	@Override
	public void draw(Graphics g) 
	{
		g.drawImage(image, (int)position.getX(), (int)position.getY(), null);
	}
	@Override
	public void draw(Graphics g, Camera c)
	{
		
	}
	
	/* -- End Entity -- */
	
	/*
	 * Utility Methods
	 */
	
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* -- End Utility -- */

}
