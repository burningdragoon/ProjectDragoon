package com.ProjectDragoon.gui;

import java.awt.Graphics;

import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.util.Vector;

/**
 * A simple user interface window
 * 
 * For now I will assume the window frame Texture with the window image will look like so:
 * [0,0] [0,1] [0,2]
 * [1,0] [1,1] [1,2]
 * [2,0] [2,1] [2,2]
 * 
 * where:
 * 0,0 : Top-left corner
 * 0,1 : Top border
 * 0,2 : Top-right corner
 * 
 * 1,0 : Left border
 * 1,1 : plain borderless part -- this part isn't drawn since it should be blank.
 * 1,2 : Right border
 * 
 * 2,0 : Bottom-left corner
 * 2,1 : Bottom border
 * 2,2 : Bottom-right corner
 * 
 * @author Alex
 *
 */
public class Window {

	private Texture windowFrame;
	private Texture windowPattern;
	
	private int width; //overall width of the window
	private int height; //overall height of the window
	private int minWidth;
	private int minHeight;
	
	private int colWidth; // the width of the columns from the frame texture
	private int rowHeight; // the height of each from the frame texture
	
	// the offset from top corner from where the pattern is drawn.
	// this is useful for frames that are without square corners
	// the offset is the same from all sides.
	//private Vector patternOffset;
	private int patternOffsetX;
	private int patternOffsetY;
	
	private Vector position;
	
	/*
	 * Constructors
	 */
	
	public Window()
	{
		windowFrame = new Texture();
		windowPattern = new Texture();
		
		width = 0;
		height = 0;
		colWidth = 0;
		rowHeight = 0;
		
		//patternOffset = new Vector();
		patternOffsetX = 0;
		patternOffsetY = 0;
		
		position = new Vector();
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors & Mutators
	 */
	
	public Texture getWindowFrame() { return windowFrame; }
	public void setWindowFrame(Texture image) { windowFrame = image; }
	
	public Texture getWindowPattern() { return windowPattern; }
	public void setWindowPattern(Texture image) { windowPattern = image; }
	
	public int getWindowWidth() { return width; }
	public void setWindowWidth(int w) { width = w; }
	
	public int getMinimumWindowHeight() { return minHeight; }
	public void setMinimumWindowHeight(int h) { minHeight = h; }
	
	public int getMinimumWindowWidth() { return minWidth; }
	public void setMinimumWindowWidth(int w) { minWidth = w; }
	
	public int getWindowHeight() { return height; }
	public void setWindowHeight(int h) { height = h; }
	
	public int getFrameColumnWidth() { return colWidth; }
	public void setFrameColumnWidth(int colW) { colWidth = colW; }
	
	public int getFrameRowHeight() { return rowHeight; }
	public void setFrameRowHeight(int rowH) { rowHeight = rowH; }
	
	public int getPatternOffsetX() { return patternOffsetX; }
	public void setPatternOffsetX(int x) { patternOffsetX = x; }
	
	public int getPatternOffsetY() { return patternOffsetY; }
	public void setPatternOffsetY(int y) { patternOffsetY = y; }
	
	//screen position
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
	
	
	/* -- End S&M -- */
	
	/*
	 * Image setup
	 */
	
	public boolean loadWindowFrame(String filename)
	{
		return windowFrame.loadImage(filename);
	}
	
	public boolean loadWindowPattern(String filename)
	{
		return windowPattern.loadImage(filename);
	}
	
	public boolean loadWindowTextures(String frameFilename, String patternFilename)
	{
		return loadWindowFrame(frameFilename) && loadWindowPattern(patternFilename);
	}
	
	// Updates/sets the window frame's column and row dimenions values
	public void configureWindowInfo(int frameCols, int frameRows)
	{
		colWidth = windowFrame.getImage().getWidth() / frameCols;
		rowHeight = windowFrame.getImage().getHeight() / frameRows;
		minWidth = colWidth * 2;
		minHeight = rowHeight * 2;
	}
	
	public void configureWindowInfo()
	{
		configureWindowInfo(3,3);
	}
	
	public void resize(int w, int h)
	{
		if(w < minWidth)
		{
			w = minWidth;
		}
		if(h < minHeight)
		{
			h = minHeight;
		}
		
		setWindowWidth(w);
		setWindowHeight(h);
	}
	
	/* -- End Image setup -- */
	
	public void draw(Graphics g)
	{
		//draw pattern at offset
		int patternWidth = windowPattern.getImage().getWidth();
		int patternHeight = windowPattern.getImage().getHeight();
		
		//determine how many times the pattern repeats in either direction
		int repeatX = (width - (2 * patternOffsetX)) / patternWidth;
		int repeatY = (height - (2 * patternOffsetY)) / patternHeight;
		
		//determine left over pieces to have partial patterns:
		int extraX = (width - (2 * patternOffsetX)) % patternWidth;
		int extraY = (height - (2 * patternOffsetY)) % patternHeight;
		
		for(int col = 0; col < repeatX + 1; col++)
		{
			for(int row = 0; row < repeatY + 1; row++)
			{
				int desX = (col * patternWidth) + patternOffsetX;
				int desY = (row * patternHeight) + patternOffsetY;
				
				int srcX = 0;
				int srcY = 0;
				
				int w = 0;
				int h = 0;
				
				// if in the "extra" (aka last) column, draw only necessary width
				if(col == repeatX)
				{
					w = extraX;
				}
				else
				{
					w = patternWidth;
				}
				
				// if in the "extra" (aka last) row, draw only necessary height
				if(row == repeatY)
				{
					h = extraY;
				}
				else
				{
					h = patternHeight;
				}
				
				desX += (int)position.getX();
				desY += (int)position.getY();
				
				windowPattern.draw(g, desX, desY, srcX, srcY, w, h);
			}
		}
		
		//draw frame over top the pattern
		
		//determine how many times over and down the frame repeats
		int colCount = width / colWidth;
		int rowCount = height / rowHeight;
		
		int extraWidth = width % colWidth;
		int extraHeight = height % rowHeight;
		
		for(int iRow = 0; iRow < rowCount; iRow++)
		{
			for(int iCol = 0; iCol < colCount; iCol++)
			{
				int desX = (int)position.getX();
				int desY = (int)position.getY();
				int srcX = 0;
				int srcY = 0;
				
				boolean draw = true;
				
				// top row
				if(iRow == 0)
				{
					desY += 0;
					//srcY = 0;
					// left columns
					if(iCol == 0)
					{
						//desX += 0;
						//srcX = 0;
					}
					// middle columns
					else if(iCol > 0 && iCol < colCount-1)
					{
						desX += iCol * colWidth;
						srcX = colWidth;
					}
					// right columns
					else if(iCol == colCount-1)
					{
						
						desX += iCol * colWidth;
						srcX = colWidth*2;
						
						//if there is any extra width to the window, do some magic
						if(extraWidth > 0)
						{
							windowFrame.draw(g, desX, desY, colWidth, srcY, extraWidth, rowHeight);
							desX += extraWidth;
							
						}
					}
				}
				// middle rows
				else if(iRow > 0 && iRow < rowCount-1)
				{
					desY += iRow*rowHeight;
					srcY = rowHeight;
					//left column
					if(iCol == 0)
					{
						//desX += 0;
						//srcX = 0;
					}
					// middle columns
					else if(iCol > 0 && iCol < colCount-1)
					{
						//Don't draw if this is the case
						draw = false;
						//desX = iCol*colWidth;
						//srcX = colWidth;
					}
					// right column
					else if(iCol == colCount-1)
					{
						desX += iCol*colWidth;
						srcX = colWidth*2;
						if(extraWidth > 0)
						{
							desX += extraWidth;
						}
					}
				}
				// bottom row
				else if(iRow == rowCount-1)
				{
					desY += iRow*rowHeight;
					srcY = rowHeight*2;
					
					//left column
					if(iCol == 0)
					{
						//desX += 0;
						//srcX = 0;
						
						if(extraHeight > 0)
						{
							windowFrame.draw(g, desX, desY, srcX, rowHeight, colWidth, extraHeight);
						}
					}
					// middle columns
					else if(iCol > 0 && iCol < colCount-1)
					{
						desX += iCol*colWidth;
						srcX = colWidth;
					}
					// right column
					else if(iCol == colCount-1)
					{
						desX += iCol*colWidth;
						srcX = colWidth*2;
						
						if(extraWidth > 0)
						{
							windowFrame.draw(g, desX, desY+extraHeight, colWidth, srcY, extraWidth, rowHeight);
							desX += extraWidth;
						}
						
						if(extraHeight > 0)
						{
							windowFrame.draw(g, desX, desY, srcX, rowHeight, colWidth, extraHeight);
						}
					}
					
					desY += extraHeight;
				}
				
				if(draw)
					windowFrame.draw(g, desX, desY, srcX, srcY, colWidth, rowHeight);
			}
		}
	}
}
