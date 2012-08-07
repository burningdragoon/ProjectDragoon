package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;

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
	
	int cameraX;
	int cameraY;
	int maxCameraX;
	int minCameraX;
	int maxCameraY;
	int minCameraY;
	
	Rectangle rect;
	//...
	
	public ScrollingTilesPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		
		background = new Texture();
		tilew = 32;
		tileh = 32;
		random = new Random();
		cameraX = 0;
		cameraY = 0;
		//maxCameraX = 0;
		//minCameraX = 0;
		//maxCameraY = 0;
		//minCameraY = 0;
		
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
		
		//maxCameraX = 0;
		//minCameraX = getScreenWidth() - map.getWidth();
		maxCameraX = map.getWidth() - getScreenWidth();
		minCameraX = 0;
		
		maxCameraY = map.getHeight() - getScreenHeight();;
		minCameraY = 0;
	}
	
	@Override
	public void gameUpdate() 
	{
		if(this.getKeyHandler().keyPressed(keys.LeftKey()))
		{	
			boolean inCenter = rect.x + (rect.width / 2) - cameraX == (getScreenWidth() / 2);
			boolean leftFree = cameraX > minCameraX;
			if(inCenter && leftFree)
			{
				rect.x -= 2;
				cameraX -= 2;
			}
			else
				rect.x -= 2;
		}
		else if(this.getKeyHandler().keyPressed(keys.RightKey()))
		{

			// move position of rectangle
			//update camera position if possible
			// if rectangle is in the center and there is space available to the right, update both rect and camera. Otherwise, just update rect.
			boolean inCenter = rect.x + (rect.width / 2) - cameraX == (getScreenWidth() / 2);
			boolean rightFree = cameraX < maxCameraX;
			if(inCenter && rightFree)
				cameraX += 2;
			rect.x += 2;
			
		}
		
		if(this.getKeyHandler().keyPressed((keys.UpKey())))
		{
			boolean inCenter = rect.y + (rect.height / 2) - cameraY == (getScreenHeight() / 2);
			boolean upFree = cameraY > minCameraY;
			if(inCenter && upFree)
				cameraY -= 2;
			rect.y -= 2;
		}
		else if(this.getKeyHandler().keyPressed((keys.DownKey())))
		{
			boolean inCenter = rect.y + (rect.height / 2) - cameraY == (getScreenHeight() / 2);
			boolean downFree = cameraY < maxCameraY;
			if(inCenter && downFree)
				cameraY += 2;
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
		//int tilesX = mapTiles[0].length;//25;
		//int tilesY = mapTiles.length;//20;
		
		// total number of tiles the fit on the screen horizontally (tilesX) & vertically (tilesY)
		// add 1 to the value for a buffer tile to stop tiles from popping in
		int tilesX = (getScreenWidth() / tileW) + 1;
		int tilesY = (getScreenHeight() / tileH) + 1;
		
		// Only draw tiles that are in the view of the 'camera'...
		int firstTileX = cameraX / tileW;
		int firstTileY = cameraY / tileH;
		for(int r = firstTileY; r < tilesY + firstTileY; r++)
		{
			if(r >= mapTiles.length)
				break;
			for(int c = firstTileX; c < tilesX + firstTileX; c++)
			{
				if(c >= mapTiles[r].length)
					break;
				Tile tile = mapTiles[r][c];
				int x = c * tileW - cameraX;
				int y = r * tileH - cameraY;
				tile.draw(dbg, x, y);
			}
		}
		
		//((Graphics2D)dbg).draw(rect);
		dbg.setColor(Color.red);
		dbg.fillRect(rect.x - cameraX, rect.y - cameraY, rect.width, rect.height);
		
		// Display 'camera' position info
		dbg.setColor(Color.WHITE);
		dbg.drawString(String.format("Camera: (%d,%d)", cameraX, cameraY), 0, fontInfo.getHeight());
		dbg.drawString(String.format("X Tiles: (%d -> %d)", firstTileX, firstTileX + tilesX - 1), 0, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Y Tiles: (%d -> %d)", firstTileY, firstTileY + tilesY - 1), 0, fontInfo.getHeight() * 3);
		
	} // End of gameRender()
	
	/* -- End life cycle methods -- */

}