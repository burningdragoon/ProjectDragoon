package com.ProjectDragoon.maps;

import java.awt.Graphics;

public class Tile {
	
	//Tile types:
	private static final int FREE = 0;
	private static final int SOLID = 1;
	// --
	
	private TileSet tileset;
	private int tilesetRow;
	private int tilesetColumn;
	private int mapRow;
	private int mapColumn;
	private boolean hasTile;
	private int tileType;
	
	public Tile()
	{
		tileset = null;
		tilesetRow = 0;
		tilesetColumn = 0;
		mapRow = 0;
		mapColumn = 0;
		hasTile = true;
		tileType = 0;
	}
	
	public Tile(TileSet tiles, int tsRow, int tsCol)
	{
		this(tiles, tsRow, tsCol, 0, 0, 0);
	}
	
	public Tile(TileSet tiles, int tsRow, int tsCol, int mapRow, int mapCol)
	{
		/*
		tileset = tiles;
		tilesetRow = tsRow;
		tilesetColumn = tsCol;
		if(tsRow < 0 || tsCol < 0)
			hasTile = false;
		else
			hasTile = true;
		this.mapRow = mapRow;
		this.mapColumn = mapCol;
		tileType = 0;
		*/
		this(tiles, tsRow, tsCol, mapRow, mapCol, 0);
	}
	
	public Tile(TileSet tiles, int tsRow, int tsCol, int mapRow, int mapCol, int type)
	{
		tileset = tiles;
		tilesetRow = tsRow;
		tilesetColumn = tsCol;
		if(tsRow < 0 || tsCol < 0)
			hasTile = false;
		else
			hasTile = true;
		this.mapRow = mapRow;
		this.mapColumn = mapCol;
		tileType = type;
	}
	
	// Selectors & Mutators
	
	public void setTileSet(TileSet tileset) { this.tileset = tileset; }
	
	public int getTileSetColumn() { return tilesetColumn; }
	public void setTileSetColumn(int c) { tilesetColumn = c; }
	
	public int getTileSetRow() { return tilesetRow; }
	public void setTileSetRow(int r) { tilesetRow = r; }
	
	public int getMapColumn() { return mapColumn; }
	public void setMapColumn(int c) { mapColumn = c; }
	
	public int getMapRow() { return mapRow; }
	public void setMapRow(int r) { mapRow = r; }
	
	public boolean hasTile() { return hasTile; }
	
	// End S&M
	
	public void setTile(int value)
	{
		if(value < 0)
		{
			tilesetRow = -1;
			tilesetColumn = -1;
			hasTile = false;
		}
		else
		{
			tilesetRow = value / tileset.getColumns();
			tilesetColumn = value % tileset.getColumns();
			hasTile = true;
		}
	}
	
	public void draw(Graphics g, int x, int y)
	{
		if(hasTile)
			tileset.drawTile(g, this, x, y);
	}
}
