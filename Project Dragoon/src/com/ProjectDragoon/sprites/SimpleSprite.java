package com.ProjectDragoon.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.interfaces.Utility;

public class SimpleSprite implements Utility {

	private static final long serialVersionUID = 1L;
	
	protected transient Texture spritesheet;
	protected String spritesheetFile;
	protected int width, height;
	protected int columns;
	protected int curFrame, totalFrames;
	protected boolean imageLoaded;
	
	/*
	 * Constructors
	 */
	
	public SimpleSprite()
	{
		spritesheet = new Texture();
		spritesheetFile = "";
		width = 1;
		height = 1;
		columns = 1;
		curFrame = 0;
		totalFrames = 1;
		imageLoaded = false;
	}
	
	/* -- End of Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public Texture getSpritesheet() {	return spritesheet;	}
	public void setSpritesheet(Texture sheet) {	spritesheet = sheet;	}
	public void setSpritesheet(BufferedImage sheet) {	spritesheet.setImage(sheet);	}
	
	public int getWidth() {	return width;	}
	public void setWidth(int w) {	width = w;	}
	
	public int getHeight() {	return height;	}
	public void setHeight(int h) {	height = h;	}
	
	public int getNumColumns() {	return columns;	}
	public void setNumColumns(int cols) {	columns = cols; }
	
	public int getCurrentFrame() {	return curFrame;	}
	public void setCurrentFrame(int frame) {	curFrame = frame;	}
	
	public int getTotalFrames() {	return totalFrames;	}
	public void setTotalFrames(int frames) {	totalFrames = frames;	}
	
	public boolean isImageLoaded() {	return imageLoaded;	}
	public void setImageLoaded(boolean value) {	imageLoaded = value;	}
	
	/* -- End of Selectors and Mutators -- */
	
	/*
	 * Sprite Configuring
	 */
	
	public boolean loadImage(String filename)
	{
		spritesheetFile = filename;
		imageLoaded = spritesheet.loadImage(filename);
		return imageLoaded;
	}
	
	public boolean reloadImage()
	{
		spritesheet = new Texture();
		return loadImage(spritesheetFile);
	}
	
	public void configureDimensions()
	{
		if(spritesheet == null)
		{
			return;
		}
		
		int imgWidth = spritesheet.getImage().getWidth();
		int imgHeight = spritesheet.getImage().getHeight();
		
		int rows = (int)Math.ceil((double)totalFrames / columns);
		
		width = imgWidth / columns;
		height = imgHeight / rows;
	}
	
	/* -- End of Sprite Configuring -- */
	
	/*
	 * Drawing
	 */
	
	public void draw(Graphics g, int xPos, int yPos, boolean forward)
	{
		// get the x,y for the current frame
		int fx = (curFrame % columns) * width;
		int fy = (curFrame / columns) * height;
		
		int desX = xPos;
		int desY = yPos;
		
		spritesheet.draw(g, desX, desY, fx, fy, width, height, forward);
	}
	
	public void draw(Graphics g, int xPos, int yPos)
	{
		draw(g, xPos, yPos, true);
	}
	
	public void draw(Graphics g, Vector position, boolean forward)
	{
		draw(g, (int)position.getX(), (int)position.getY(), forward);
	}
	
	public void draw(Graphics g, Vector position)
	{
		draw(g, position, true);
	}
	
	/* -- End of Drawing -- */
	
	/*
	 * Utility
	 */
	
	@Override
	public SimpleSprite copy()
	{
		SimpleSprite s = new SimpleSprite();
		
		s.spritesheet = spritesheet.copy();
		s.spritesheetFile = spritesheetFile;
		s.imageLoaded = imageLoaded;
		
		s.width = width;
		s.height = height;
		s.columns = columns;
		s.curFrame = curFrame;
		s.totalFrames = totalFrames;
		
		return s;
	}
	
	@Override
	public void restore()
	{
		reloadImage();
	}

	
	/* -- End of Utility -- */
	
}







