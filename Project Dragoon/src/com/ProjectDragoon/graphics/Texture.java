package com.ProjectDragoon.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ProjectDragoon.util.interfaces.Utility;

public class Texture implements Utility{
	
	private static final long serialVersionUID = 1L;

	private static int IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	private BufferedImage image;
	
	/*
	 * Constructors
	 */
	
	public Texture()
	{
		image = null;
	}
	
	public Texture(String filename)
	{
		this();
		loadImage(filename);
	}
	
	public Texture(int width, int height)
	{
		image = new BufferedImage(width, height, IMAGE_TYPE);
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public BufferedImage getImage()
	{
		return image;
	}
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	/* -- End Selectors and Mutators -- */
	
	/**
	 * Reads and creates a texture image from a given resource file
	 * @param filename
	 * @return
	 */
	public boolean loadImage(String filename)
	{
		BufferedImage img;
		try
		{
			img = ImageIO.read(Object.class.getResource(filename));
		}
		catch(IOException ioe)
		{
			System.err.println("An error occured when reading the file: " + filename);
			return false;
		}
		
		/*
		 * Instead of directly loading the input image onto the BufferedImage,
		 * create a new BI based on the input image's width and height. This,
		 * ensures the Texture's image has the correct BufferedImage type. 
		 */
		int w = img.getWidth();
		int h = img.getHeight();
		image = new BufferedImage(w, h, IMAGE_TYPE);
		image.getGraphics().drawImage(img, 0, 0, null);
		
		return true;
	}
	
	/**
	 * Fills in the entire image with the given color
	 * @param c
	 */
	public void fillImage(Color c)
	{
		image.getGraphics().setColor(c);
		image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
	}
	
	/*
	 * Drawing methods
	 */
	
	/**
	 * Basic draw method. 
	 * Draws Texture onto the given Graphics context with location parameters
	 * @param g
	 */
	public void draw(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
	
	/**
	 * Draws the Texture at the given coordinates
	 * @param g
	 * @param x
	 * @param y
	 */
	public void draw(Graphics g, int x, int y)
	{
		g.drawImage(image, x, y, null);
	}
	
	/**
	 * Draws a piece of the texture onto the Graphics context.
	 * @param g
	 * @param desX The x coordinate on the given Graphics context where the image will be drawn
	 * @param desY The y coordinate on ''
	 * @param srcX The x coordinate on the image from where to start drawing
	 * @param srcY The y coordinate on the image ''
	 * @param w The width of the resulting drawing
	 * @param h The height of the resulting drawing
	 */
	public void draw(Graphics g, int desX, int desY, int srcX, int srcY, int w, int h)
	{
		draw(g, desX, desY, srcX, srcY, w, h, 1.0);
	}
	
	/**
	 * 
	 * Draws a scaled piece of the texture onto the Graphics context.
	 * @param g
	 * @param desX The x coordinate on the given Graphics context where the image will be drawn
	 * @param desY The y coordinate on ''
	 * @param srcX The x coordinate on the image from where to start drawing
	 * @param srcY The y coordinate on the image ''
	 * @param w The width of the resulting drawing
	 * @param h The height of the resulting drawing
	 * @param scale
	 */
	public void draw(Graphics g, int desX, int desY, int srcX, int srcY, int w, int h, double scale)
	{
		g.drawImage(image, desX, desY, desX + (int)(w * scale), desY + (int)(h * scale), srcX, srcY, srcX+w, srcY+h, null);
	}
	
	/* -- End Drawing methods -- */
	
	
	/*
	 * Utility Methods
	 */
	
	@Override
	public Texture copy()
	{
		Texture t = new Texture(image.getWidth(), image.getHeight());
		this.draw(t.getImage().getGraphics());
		return t;
	}
	
	/* -- End Utility -- */

}
