package com.ProjectDragoon.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {

	private TileSet tileset;
	private Tile[][] tiles;
	private int width;
	private int height;
	private int rows;
	private int columns;
	
	public Map()
	{
	}
	
	public Map(TileSet tileset, int rows, int columns)
	{
		this.tileset = tileset;
		this.rows = rows;
		this.columns = columns;
		
		tiles = new Tile[rows][columns];
		width = columns * tileset.getTileWidth();
		height = rows * tileset.getTileHeight();
		for(int r = 0; r < tiles.length; r++)
		{
			for(int c = 0; c < tiles[r].length; c++)
			{
				tiles[r][c] = new Tile(tileset, 0, 0);
			}
		}
	}
	
	// Selectors & Mutators
	
	public int getRows() { return rows; }
	public int getColumns() { return columns; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public Tile getTile(int row, int col) { return tiles[row][col]; }
	public Tile[][] getTiles() { return tiles; }
	
	// End S&M
	
	//Other
	
	public int tileWidth()
	{
		return width / columns;
	}
	public int tileHeight()
	{
		return height / rows;
	}
	
	public void setTile(int row, int col, int value)
	{
		tiles[row][col].setTile(value); 
	}
	
	public void newTile(int row, int col, int value)
	{
		int rowValue = value / tileset.getColumns();
		int colValue = value % tileset.getColumns();
		tiles[row][col] = new Tile(tileset, rowValue, colValue, row, col);
	}
	
	public void readMap(String filename)
	{
		try {
			Scanner scan = new Scanner(new FileInputStream(filename));
			String meta = scan.nextLine();
			String[] metaArray = meta.split(",");
			rows = Integer.parseInt(metaArray[0]);
			columns = Integer.parseInt(metaArray[1]);
			tiles = new Tile[rows][columns];
			width = columns * tileset.getTileWidth();
			height = rows * tileset.getTileHeight();
			int rowCount = 0;
			while(scan.hasNextLine())
			{
				String rowValues = scan.nextLine();
				String[] colValues = rowValues.split(",");
				for(int colCount = 0; colCount < colValues.length; colCount++)
				{
					int value = Integer.parseInt(colValues[colCount]);
					this.newTile(rowCount, colCount, value);
				}
				rowCount++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g)
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < columns; c++)
			{
				int x = c * tileset.getTileWidth();
				int y = r * tileset.getTileHeight();
				tiles[r][c].draw(g, x, y);
			}
		}
	}
	
	public void drawGrid(Graphics g)
	{
		g.setColor(Color.white);
		int dx = width / columns;
		int dy = height / rows;
		for(int x = 0; x < width; x += dx)
			g.drawLine(x, 0, x, height);
		for(int y = 0; y < height; y += dy)
			g.drawLine(0, y, width, y);
	}
}
