package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.maps.TileType;
import com.ProjectDragoon.physics.HitBox;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;

public class TileMovementPanel extends GamePanel {
	
	private Font font;
	private FontMetrics fontInfo;
	private KeyValues keys;
	
	private Map map;
	private Camera camera;
	
	
	private final int VELOCITY_X = 4;
	private final int VELOCITY_Y = 4;
	private int blockV = 2;
	
	private SpriteEntity box;
	private SpriteEntity block;
	
	//private boolean moving;
	//private boolean onTile;
	
	public TileMovementPanel(Game game, int width, int height, long period)
	{
		super(game, width, height, period);
	}
	
	@Override
	public void handleInput()
	{
		// Reset Everything
			if(keyPressed(keys.ResetKey()))
				gameInit();
			
			/* Player Control Input */
			// Horizontal movement
			if((int)box.getYVel() == 0)
			{
				if(keyPressed(keys.RightKey()) && !(box.getXVel() < 0))
				{
					box.setXVel(VELOCITY_X);
					box.moving = true;
					//onTile = false;
				}
				else if(keyPressed(keys.LeftKey()) && !(box.getXVel() > 0))
				{
					box.setXVel(-VELOCITY_X);
					box.moving = true;
					//onTile = false;
				}
				else
				{
					//box.setXVel(0);
					box.moving = false;
				}
			}
			
			// Vertical movement
			if((int)box.getXVel() == 0)
			{
				if(keyPressed(keys.DownKey()) && !(box.getYVel() < 0))
				{
					box.setYVel(VELOCITY_Y);
					box.moving = true;
					//onTile = false;
				}
				else if(keyPressed(keys.UpKey()) && !(box.getYVel() > 0))
				{
					box.setYVel(-VELOCITY_Y);
					box.moving = true;
					//onTile = false;
				}
				else
				{
					//box.setYVel(0);
					box.moving = false;
				}
			}
			
			
	}
	
	public void CollisionHandling()
	{
		tileCollision(box);
		tileCollision(block);
	}
	
	public void tileCollision(SpriteEntity entity)
	{
		if(entity.moving && entity.onTile)
		{
			//determine if destination tile is free.
			Tile destination = null;
			int row = 0;
			int col = 0;
			if(entity.getXVel() > 0)
			{
				row = (int)entity.getYPos() / map.tileHeight();
				col = ((int)entity.getXPos() + entity.getWidth() - 1 + (int)entity.getXVel()) / map.tileWidth();
			}
			else if(entity.getXVel() < 0)
			{
				row = (int)entity.getYPos() / map.tileHeight();
				col = ((int)entity.getXPos() + (int)entity.getXVel()) / map.tileWidth();
			}
			else if(entity.getYVel() > 0)
			{
				row = ((int)entity.getYPos() + entity.getHeight() - 1 + (int)entity.getYVel()) / map.tileHeight();
				col = (int)entity.getXPos() / map.tileWidth();
			}
			else if(entity.getYVel() < 0)
			{
				row = ((int)entity.getYPos() + (int)entity.getYVel()) / map.tileHeight();
				col = (int)entity.getXPos() / map.tileWidth();
			}
			destination = map.getTile(row, col);

			//boxTile = destination;
			//if(destination != null && destination.getTileType() == TileType.SOLID)
			if(destination != null && !destination.isFree())
			{
				entity.setVelocity(0, 0);
			}
			else
			{
				destination.setTileType(TileType.FILLED);
			}
			
		}
		else if(!entity.moving && !entity.onTile)
		{
			int xpos = (int)entity.getXPos();
			int ypos = (int)entity.getYPos();
			int col = xpos / map.tileWidth();
			int row = ypos / map.tileHeight();
			if(xpos == col*map.tileWidth() && ypos == row*map.tileHeight())
			{
				entity.onTile = true;
			}
		}
		else if(!entity.moving && entity.onTile)
		{
			entity.setVelocity(0,0);
		}
	}
	
	public void clearTiles()
	{
		Tile[][] tiles = map.getTiles();
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[0].length; j++)
			{
				Tile tile = tiles[i][j];
				if(tile.getTileType() == TileType.FILLED)
				{
					tile.setTileType(TileType.NONE);
				}
			}
		}
	}
	
	public void updateTiles()
	{
		//clear tiles
		clearTiles();
		
		//check for tiles to change
		tileDetection(box);
		tileDetection(block);
	}
	
	public void tileDetection(SpriteEntity entity)
	{
		//check if box is on a tile
		int x1 = (int)entity.getXPos();
		int y1 = (int)entity.getYPos();
		entity.onTile = x1 % map.tileWidth() == 0 && y1 % map.tileHeight() == 0;
		
		//adjust tile types
		//*
		int x2 = x1 + entity.getWidth() - 1;
		int y2 = y1 + entity.getHeight() - 1;
		int row1 = y1 / map.tileHeight();
		int col1 = x1 / map.tileWidth();
		int row2 = y2 / map.tileHeight();
		int col2 = x2 / map.tileWidth();
		Tile tile1 = map.getTile(row1, col1);
		Tile tile2 = map.getTile(row2, col2);
		tile1.setTileType(TileType.FILLED);
		tile2.setTileType(TileType.FILLED);
		//*/
	}
	
	public void updateEntities()
	{
		box.update();
		block.update();
		
		if(block.onTile)
		{
			//blockV *= -1;
			//block.setXVel(blockV);
			//block.moving = true;
		}
		else
		{
			//block.update();
		}
		
		//block.update();
		//block.moving = true;
		//block.setXVel(blockV);
	}
	
	@Override
	public void gameInit()
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		keys = new KeyValues();
		
		TileSet tileset = new TileSet(10,10,32,32);
		tileset.loadTiles("/images/test_tiles.png");
		map = new Map(tileset, 30, 30);
		map.readMap("res/maps/testmap4.txt");
		
		camera = new Camera(getScreenWidth(), getScreenHeight());
		camera.setXRange(0, map.getWidth() - getScreenWidth());
		camera.setYRange(0, map.getHeight() - getScreenHeight());
		camera.reset();
		
		Sprite sprite = new Sprite();
		//sprite.loadImage("/images/sprites/square_test.png");
		sprite.loadImage("/images/sprites/dragoon_32x32.png");
		sprite.configureDimensions();
		box = new SpriteEntity(sprite, new Vector(0,0,0));
		box.addNewHitBox("main", new Vector(0,0,0), map.tileWidth(), map.tileHeight());
		
		sprite = new Sprite();
		sprite.loadImage("/images/sprites/square_test.png");
		sprite.configureDimensions();
		block = new SpriteEntity(sprite, new Vector(0,0,0));
		block.setPosition(10 * map.tileWidth(), 3 * map.tileHeight());
		//block.setXVel(VELOCITY_X/2);
		block.setXVel(-blockV);
		
		box.moving = false;
		box.onTile = false;
		
		block.moving = true;
		block.onTile = false;
	}
	
	@Override
	public void gameUpdate()
	{
		handleInput();
		
		CollisionHandling();
		
		//updateTiles();
		
		//updateEntities();
		
		box.update();
		block.update();
		
		updateTiles();
		
		if(block.onTile)
		{
			blockV *= -1;
			block.setXVel(blockV);
		}
		//block.update();
		//block.setXVel(blockV);
		
		//updateTiles();
		
		camera.adjust(box);
	}
	
	@Override
	public void gameRender()
	{
		super.gameRender();
		
		map.draw(dbg, camera);
		//map.drawGrid(dbg, camera);
		
		box.draw(dbg, camera);
		block.draw(dbg, camera);
		
		dbg.setColor(Color.white);
		
		dbg.drawString(String.format("FPS: %.2f", this.getFPS()), 5, fontInfo.getHeight());
		
		dbg.drawString(String.format("Camera: (%d, %d)", camera.x, camera.y), 5, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Player: (%d, %d)", (int)box.getXPos(), (int)box.getYPos()), 5, fontInfo.getHeight() * 3);
		dbg.drawString(String.format("Mouse: (%d, %d)", mouse.getX(), mouse.getY()), 5, fontInfo.getHeight() * 4);
		
		dbg.drawString(String.format("Box Moving: %s", box.moving), 5, fontInfo.getHeight() * 5);
		dbg.drawString(String.format("Box On Tile: %s", box.onTile), 5, fontInfo.getHeight() * 6);
	}
	
}
