package com.ProjectDragoon.maps;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.graphics.Texture;

public class TileSet {

	private Texture tilesheet;
	private int rows;
	private int columns;
	private int tileWidth;
	private int tileHeight;
	
	
	public TileSet()
	{
		tilesheet = new Texture();
		rows = 0;
		columns = 0;
		tileWidth = 32;
		tileWidth = 32;
	}
	
	public TileSet(int rows, int columns, int tileWidth, int tileHeight)
	{
		tilesheet = new Texture();
		this.rows = rows;
		this.columns = columns;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	// Selectors & Mutators
	
	public int getColumns() { return columns; }
	
	public int getTileWidth() { return tileWidth; }
	public int getTileHeight() { return tileHeight; }
	
	// End of S&M
	
	public void loadTiles(String filename)
	{
		tilesheet.loadImage(filename);
	}
	
	public void drawTile(Graphics g, int srcTile, int x, int y)
	{
		int desX = x;
		int desY = y;
		int srcX = (srcTile % columns) * tileWidth;
		int srcY = (srcTile / columns) * tileHeight;
		int w = tileWidth;
		int h = tileHeight;
		tilesheet.draw(g, desX, desY, srcX, srcY, w, h);
		//g.drawImage(tilesheet.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}
	
	public void drawTile(Graphics g, Tile tile, int x, int y)
	{
		int desX = x;
		int desY = y;
		int srcX = tile.getTileSetColumn() * tileWidth;
		int srcY = tile.getTileSetRow() * tileHeight;
		int w = tileWidth;
		int h = tileHeight;
		tilesheet.draw(g, desX, desY, srcX, srcY, w, h);
		
		Color c;
		switch(tile.getTileType())
		{
			case SOLID:
				c = Color.red;
				break;
			case FILLED:
				c = Color.blue;
				break;
			case NONE:
			default:
				c = Color.white;
				break;
		}
		
		g.setColor(c);
		g.drawRect(desX, desY, w-1, h-1);
	}
}
