package com.ProjectDragoon.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.ProjectDragoon.util.Camera;

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
	
	public Tile getTile(int row, int col) 
	{ 
		if(row >= rows || col >= columns || row < 0 || col < 0)
			return null;
		return tiles[row][col]; 
	}
	public Tile[][] getTiles() { return tiles; }
	
	// End S&M
	
	//Other
	
	/**
	 * Returns the first tile (from the left) in the given row
	 * @param row
	 * @return
	 */
	public Tile getFirstTileInRow(int row)
	{
		return tiles[row][0];
	}
	
	/**
	 * Returns the last tile (from the left) in the given row 
	 * @param row
	 * @return
	 */
	public Tile getLastTileInRow(int row)
	{
		return tiles[row][columns-1];
	}
	
	/**
	 * Returns the first tile (from the top) in the given column
	 * @param col
	 * @return
	 */
	public Tile getFirstTileInColumn(int col)
	{
		return tiles[0][col];
	}
	
	/**
	 * Returns the last tile (from the top) in the given column
	 * @param col
	 * @return
	 */
	public Tile getLastTileInColumn(int col)
	{
		return tiles[rows-1][col];
	}
	
	public int tileWidth()
	{
		//return width / columns;
		return tileset.getTileWidth();
	}
	public int tileHeight()
	{
		//return height / rows;
		return tileset.getTileHeight();
	}
	
	public void setTile(int row, int col, int value)
	{
		tiles[row][col].setTile(value); 
	}
	
	public void newTile(int row, int col, int value, TileType type)
	{
		int rowValue = value / tileset.getColumns();
		int colValue = value % tileset.getColumns();
		tiles[row][col] = new Tile(tileset, rowValue, colValue, row, col, type);
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
					//int value = Integer.parseInt(colValues[colCount]);
					//this.newTile(rowCount, colCount, value, 0);
					String[] colValue = colValues[colCount].split("/");
					int value = Integer.parseInt(colValue[0]);
					int type = 0;
					if(colValue.length > 1)	
						type = Integer.parseInt(colValue[1]);
					
					TileType tileType = TileType.NONE;
					tileType = tileType.convert(type);
					
					this.newTile(rowCount, colCount, value, tileType);
					
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
	
	/**
	 * Draw the map within the camera's view.
	 * @param g
	 * @param camera
	 */
	public void draw(Graphics g, Camera camera)
	{
		// Determine what tiles fall under the camera's view (it's offset from the screen)
		// Only draw the tiles visible to the camera (with some buffered tiles to prevent pop-in)
		
		// the number of tiles that will fit within the camera in both directions (with a single tile buffer)
		int tilesX = (camera.getWidth() / tileWidth()) + 1;
		int tilesY = (camera.getHeight() / tileHeight()) + 1;
		
		// Determine the first tile of the map from the upper-left
		int firstTileX = camera.x / tileWidth();
		int firstTileY = camera.y / tileHeight();
		
		// Don't try to draw tiles that can't exist.
		if(firstTileX < 0)
			firstTileX = 0;
		if(firstTileY < 0)
			firstTileY = 0;
		
		// Draw...
		for(int curRow = firstTileY; curRow < tilesY + firstTileY; curRow++)
		{
			// Because of the 1 tile buffer, there will be an error if trying to access a tile
			// after the last one, so end the loop early if at the last row or column of the map.
			if(curRow >= tiles.length)
				break;
			for(int curCol = firstTileX; curCol < tilesX + firstTileX; curCol++)
			{
				if (curCol >= tiles[0].length)
					break;
				Tile tile = tiles[curRow][curCol];
				int x = curCol * tileWidth() - camera.x;
				int y = curRow * tileHeight() - camera.y;
				tile.draw(g, x, y);
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
	
	public void drawGrid(Graphics g, Camera camera)
	{
		g.setColor(Color.white);
		int dx = width / columns;
		int dy = height / rows;
		for(int x = 0-camera.x; x < width-camera.x; x += dx)
			g.drawLine(x, 0, x, height);
		for(int y = 0-camera.y; y < height-camera.y; y += dy)
			g.drawLine(0, y, width, y);
	}
}
