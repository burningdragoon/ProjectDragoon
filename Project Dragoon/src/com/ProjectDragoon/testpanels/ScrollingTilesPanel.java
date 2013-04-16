package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.Random;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.util.Camera;

/**
 * Simple demo program for a scrolling tile-based map.
 * @author Alex
 *
 */
public class ScrollingTilesPanel extends GamePanel {

	private static final long serialVersionUID = 1L;
	
	private Font font;
	private FontMetrics fontInfo;
	
	private KeyValues keys;
	
	//super simple testing variables...
	Texture background;
	int tilew;
	int tileh;
	Map map;
	Random random;
	
	TileSet tileset;
	
	Camera camera;
	
	Rectangle rect;
	//...
	
	public ScrollingTilesPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		
		background = new Texture();
		tilew = 32;
		tileh = 32;
		random = new Random();
		
		camera = new Camera();
		
		rect = new Rectangle();
		rect.x = 0;
		rect.y = 0;
		rect.width = 16;
		rect.height = 16;
	}

	
	/*
	 * Selectors and Mutators
	 */
	
	/* -- End of selectors and mutators -- */
	
	/*
	 * Other shit
	 */
	/* -- -- */
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit() 
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		keys = new KeyValues();
		
		background.loadImage("/images/sky.png");
		tileset = new TileSet(10,10,32,32);
		tileset.loadTiles("/images/test_tiles.png");
		map = new Map(tileset, 20, 50);
		map.readMap("res/maps/testmap2.txt");
		
		camera.setDimensions(getScreenWidth(), getScreenHeight());
		camera.setXRange(0, map.getWidth() - getScreenWidth());
		camera.setYRange(0, map.getHeight() - getScreenHeight());
	}
	
	@Override
	public void gameUpdate() 
	{
		if(this.getKeyboard().keyPressed(keys.LeftKey()))
		{	
			boolean inCenter = rect.x + (rect.width / 2) - camera.x == (getScreenWidth() / 2);
			if(inCenter && camera.leftFree())
				camera.x -=2;
			rect.x -= 2;	
		}
		else if(this.getKeyboard().keyPressed(keys.RightKey()))
		{

			// move position of rectangle
			//update camera position if possible
			// if rectangle is in the center and there is space available to the right, update both rect and camera. Otherwise, just update rect.
			boolean inCenter = rect.x + (rect.width / 2) - camera.x == (getScreenWidth() / 2);
			if(inCenter && camera.rightFree())
				camera.x += 2;
			rect.x += 2;
			
		}
		
		if(this.getKeyboard().keyPressed((keys.UpKey())))
		{
			boolean inCenter = rect.y + (rect.height / 2) - camera.y == (getScreenHeight() / 2);
			if(inCenter && camera.upFree())
				camera.y -= 2;
			rect.y -= 2;
		}
		else if(this.getKeyboard().keyPressed((keys.DownKey())))
		{
			boolean inCenter = rect.y + (rect.height / 2) - camera.y == (getScreenHeight() / 2);
			if(inCenter && camera.downFree())
				camera.y += 2;
			rect.y += 2;
		}
		
	} // End of gameUpdate()
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		dbg.setFont(font);
		
		dbg.drawImage(background.getImage(), 0, 0, 800, 640, null);
		
		//map.draw(dbg);
		//map.drawGrid(dbg);
		
		// Determine which tiles to draw based on 'camera' offset.
		Tile[][] mapTiles = map.getTiles();
		int tileW = tileset.getTileWidth();
		int tileH = tileset.getTileHeight();
		
		// total number of tiles the fit on the screen horizontally (tilesX) & vertically (tilesY)
		// add 1 to the value for a buffer tile to stop tiles from popping in
		int tilesX = (getScreenWidth() / tileW) + 1;
		int tilesY = (getScreenHeight() / tileH) + 1;
		
		// Only draw tiles that are in the view of the 'camera'...
		int firstTileX = camera.x / tileW;
		int firstTileY = camera.y / tileH;
		for(int r = firstTileY; r < tilesY + firstTileY; r++)
		{
			if(r >= mapTiles.length)
				break;
			for(int c = firstTileX; c < tilesX + firstTileX; c++)
			{
				if(c >= mapTiles[r].length)
					break;
				Tile tile = mapTiles[r][c];
				int x = c * tileW - camera.x;
				int y = r * tileH - camera.y;
				tile.draw(dbg, x, y);
			}
		}
		
		//((Graphics2D)dbg).draw(rect);
		dbg.setColor(Color.red);
		dbg.fillRect(rect.x - camera.x, rect.y - camera.y, rect.width, rect.height);
		
		// Display 'camera' position info
		dbg.setColor(Color.WHITE);
		dbg.drawString(String.format("Camera: (%d,%d)", camera.x, camera.y), 0, fontInfo.getHeight());
		dbg.drawString(String.format("X Tiles: (%d -> %d)", firstTileX, firstTileX + tilesX - 1), 0, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Y Tiles: (%d -> %d)", firstTileY, firstTileY + tilesY - 1), 0, fontInfo.getHeight() * 3);
		
	} // End of gameRender()
	
	/* -- End life cycle methods -- */

}