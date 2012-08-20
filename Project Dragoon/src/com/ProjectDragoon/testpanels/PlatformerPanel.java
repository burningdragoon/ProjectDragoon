package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.physics.CollisionMap;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteEntity;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.data.DataSaver;

public class PlatformerPanel extends GamePanel {

	private static final long serialVersionUID = 1L;
	
	private final int ONGROUND = 0;
	private final int JUMPING = 1;
	private final int FALLING = 2;
	private final int HITWALL = 3;
	
	private Font font;
	private FontMetrics fontInfo;
	private KeyValues keys;
	
	private Camera camera;
	
	private Texture background;
	
	private Map map;
	private SpriteEntity player;
	int playerState;
	
	SpriteEntity dragon;
	
	public PlatformerPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
	}
	
	/*
	 * Selectors and Mutators
	 */
	/* -- End of selectors and mutators -- */
	
	
	/*
	 * Other Utility methods
	 */
	
	/**
	 * Adjusts the camera position to keep the player centered on the screen when possible
	 */
	public void adjustCamera()
	{		
		// determine center of player
		int playerX = (int)player.getXPos() + (player.getWidth() / 2);
		
		// the distance between the player and the edge of the camera
		int playerCameraOffsetX = playerX - camera.x;
		
		// the distance between the player and the center of the camera -- also how much the camera needs to move to center on the player
		int playerCenterOffsetX = playerCameraOffsetX - (camera.getWidth() / 2);
		
		// only center the camera if not at either edge of the map
		if( !(playerCenterOffsetX < 0 && !camera.leftFree()) && !(playerCenterOffsetX > 0 && !camera.rightFree()) )
			camera.x += playerCenterOffsetX;
		
		/*
		// Y axis ignored for now
		int playerY = (int)player.getYPos() + (player.getHeight() / 2); 
		int playerCameraOffsetY = playerY - camera.y;
		int playerCenterOffsetY = playerCameraOffsetY - (camera.getHeight() / 1);
		if( !(playerCenterOffsetY < 0 && !camera.upFree()) && !(playerCenterOffsetY > 0 && !camera.downFree()) )
			camera.y += playerCenterOffsetY;
		*/
	}
	/* -- */
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit() 
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		keys = new KeyValues();
		
		// ---
		
		background = new Texture();
		background.loadImage("/images/sky.png");
		
		//set up map stuff
		TileSet tileset = new TileSet(10,10,32,32);
		tileset.loadTiles("/images/test_tiles.png");
		map = new Map(tileset, 20,50);
		map.readMap("res/maps/testmap3.txt");
		
		// Set up camera.
		camera = new Camera(this.getScreenWidth(), this.getScreenHeight());
		camera.setXRange(0, map.getWidth() - getScreenWidth());
		camera.setYRange(0, map.getHeight() - getScreenHeight());
		
		// Set up the player stuff
		Sprite sprite = DataSaver.LoadSprite("res/sprites/kain.sprite");
		sprite.setCurrentAnimation("Rest");
		player = new SpriteEntity(sprite, new Vector());
		playerState = ONGROUND;
		// init collision points
		CollisionMap colmap = player.getCollisionMap();
		int colLeftX = 25;
		int colRightX = 38;
		int colTopY = 24;
		int colBotY = 49;
		int colHeadY = 20;
		int colFeetY = 53;
		colmap.setRightPoints(colRightX, colTopY, colBotY);
		colmap.setLeftPoints(colLeftX, colTopY, colBotY);
		colmap.setHeadPoints(colLeftX, colRightX, colHeadY);
		colmap.setFeetPoints(colLeftX, colRightX, colFeetY);
		
		//test thingy
		sprite = DataSaver.LoadSprite("res/sprites/dragoon.sprite");
		sprite.setCurrentAnimation("Walk_Down");
		dragon = new SpriteEntity(sprite, new Vector());
		dragon.setPosition(100, 100);
	}
	
	@Override
	public void gameUpdate() 
	{		
		if(keyPressed(keys.RightKey()))
		{
			player.setXVel(2);
			player.forward = false;
		}
		else if(keyPressed(keys.LeftKey()))
		{
			player.setXVel(-1);
			player.forward = true;
		}
		else
		{
			player.setXVel(0);
		}
		
		
		if(playerState == FALLING)
		{
			player.setYVel(3);
			//player.sprite.setCurrentAnimation("");
		}
		else if(playerState == ONGROUND)
		{
			if(keyPressed(keys.ActionKey()))
			{
				player.setYVel(-2);
				playerState = JUMPING;
				player.sprite.setCurrentAnimation("Jump");
			}
			else
			{
				player.setYVel(0);
				player.sprite.setCurrentAnimation("Rest");
			}
		}
		else if(playerState == JUMPING)
		{
			if(!keyPressed(keys.ActionKey()))
				playerState = FALLING;
		}
		
		player.update();
		player.animate();
		dragon.animate();
		
		// Collision Detection:
		//TODO : Collision Detection: Out of Bounds checking.
		CollisionMap colMap = player.getCollisionMap();
		boolean collideWall = false;
		// Going right -->
		if(player.getXVel() > 0)
		{
			int xRight = (int)colMap.RIGHT_TOP().getX();
			int yRightTop = (int)colMap.RIGHT_TOP().getY();
			int yRightBot = (int)colMap.RIGHT_BOTTOM().getY();
			
			Tile tileTop = map.getTile(yRightTop / map.tileHeight(), xRight / map.tileWidth());
			Tile tileBot = map.getTile(yRightBot / map.tileHeight(), xRight / map.tileWidth());
			
			if(tileTop == null && tileBot == null)
			{
				// do nothing
			}
			else
			{
				if(tileTop == null)
				{
					//tileTop = map.getTile(0, xRight / map.tileWidth());
					tileTop = map.getFirstTileInColumn(xRight / map.tileWidth());
				}
				else if(tileBot == null)
				{
					//tileBot = map.getTile(map.getRows()-1, xRight / map.tileWidth());
					tileBot = map.getLastTileInColumn(xRight / map.tileWidth());
				}
			
				for(int row = tileTop.getMapRow(); row <= tileBot.getMapRow(); row++)
				{
					Tile tile = map.getTile(row, tileTop.getMapColumn());
					if(tile.hasTile())
					{
						int mx = ((xRight / map.tileWidth()) * map.tileWidth()) - xRight - 1;
						player.moveX(mx);
						collideWall = true;
						//after collision is handled, there is no reason to keeep checking.
						break;
					}
				}
			}
		}
		// Going left;
		else if(player.getXVel() < 0)
		{
			int xLeft = (int)colMap.LEFT_TOP().getX();
			int yLeftTop = (int)colMap.LEFT_TOP().getY();
			int yLeftBot = (int)colMap.LEFT_BOTTOM().getY();
			Tile tileTop = map.getTile(yLeftTop / map.tileHeight(), xLeft / map.tileWidth());
			Tile tileBot = map.getTile(yLeftBot / map.tileHeight(), xLeft / map.tileWidth());
			
			if(tileTop == null && tileBot == null)
			{
				// do nothing
			}
			else
			{
				if(tileTop == null)
				{
					//tileTop = map.getTile(0, xLeft / map.tileWidth());
					tileTop = map.getFirstTileInColumn(xLeft / map.tileHeight());
				}
				else if(tileBot == null)
				{
					//tileBot = map.getTile(map.getRows()-1, xLeft / map.tileWidth());
					tileBot = map.getLastTileInColumn(xLeft / map.tileWidth());
					
				}
				
				for(int row = tileTop.getMapRow(); row <= tileBot.getMapRow(); row++)
				{
					Tile tile = map.getTile(row, tileTop.getMapColumn());
					if(tile.hasTile())
					{
						int mx = (((xLeft / map.tileWidth()) * map.tileWidth()) + map.tileWidth()) - xLeft;
						player.moveX(mx);
						collideWall = true;
						break;
					}
				}
			}
		}
		
		//falling / on ground
		Tile tileLeft;
		Tile tileRight;
		
		int xFeetLeft = (int)colMap.FEET_LEFT().getX();
		int xFeetRight = (int)colMap.FEET_RIGHT().getX();
		int yFeet = (int)colMap.FEET_LEFT().getY();
		tileLeft = map.getTile(yFeet / map.tileHeight(), xFeetLeft / map.tileWidth());
		tileRight = map.getTile(yFeet / map.tileHeight(), xFeetRight / map.tileWidth());
		boolean collideGround = false;
		
		if(tileLeft == null && tileRight == null)
		{
			
		}
		else
		{
			if(tileLeft == null)
			{
				//tileLeft = map.getTile(yFeet / map.tileHeight(), 0);
				tileLeft = map.getFirstTileInRow(yFeet / map.tileHeight());
			}
			else if(tileRight == null)
			{
				//tileRight = map.getTile(yFeet / map.tileHeight(), map.getColumns()-1);
				tileRight = map.getLastTileInRow(yFeet / map.getHeight());
			}
			
			for(int column = tileLeft.getMapColumn(); column <= tileRight.getMapColumn(); column++)
			{
				Tile tile = map.getTile(tileLeft.getMapRow(), column);
				if(tile.hasTile())
				{
					int my = ((yFeet / map.tileHeight()) * map.tileHeight()) - yFeet;
					player.moveY(my);
					collideGround = true;
					break;
				}
			}
		}	
			if(collideGround)
			{
				playerState = ONGROUND;
			}
			else
			{
				if(playerState != JUMPING)
				{
					playerState = FALLING;
				}
			}
		
		// Upper collision
		int xHeadLeft = (int)colMap.HEAD_LEFT().getX();
		int xHeadRight = (int)colMap.HEAD_RIGHT().getX();
		int yHead = (int)colMap.HEAD_LEFT().getY();
		tileLeft = map.getTile(yHead / map.tileHeight(), xHeadLeft / map.tileWidth());
		tileRight = map.getTile(yHead / map.tileHeight(), xHeadRight / map.tileWidth());
		
		if(tileLeft == null && tileRight == null)
		{
			// do nothing
		}
		else
		{
			if(tileLeft == null)
			{
				//tileLeft = map.getTile(yHead / map.tileHeight(), 0);
				tileLeft = map.getFirstTileInRow(yHead / map.tileHeight());
			}
			else if (tileRight == null)
			{
				//tileRight = map.getTile(yHead / map.tileHeight(), map.getColumns()-1);
				tileRight = map.getLastTileInRow(yHead / map.tileHeight());
			}
			
			for(int column = tileLeft.getMapColumn(); column <= tileRight.getMapColumn(); column++)
			{
				Tile tile = map.getTile(tileLeft.getMapRow(), column);
				if(tile.hasTile())
				{
					int my = (((yHead / map.tileHeight()) * map.tileHeight()) + map.tileHeight()) - yHead;
					player.moveY(my);
					if(playerState == JUMPING)
						playerState = FALLING;
					break;
				}
			}
		}
		
		//update camera position.
		adjustCamera();
	}
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		dbg.setFont(font);
		
		dbg.drawImage(background.getImage(), 0, 0, 800, 640, null);
		
		map.draw(dbg, camera);
		
		player.draw(dbg, camera);
		
		dragon.draw(dbg, camera);
		
		// display fps
		dbg.setColor(Color.white);
		dbg.drawString(String.format("FPS: %.2f", this.getFPS()), 5, fontInfo.getHeight());
		
		dbg.drawString(String.format("Camera: (%d, %d)", camera.x, camera.y), 5, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Player: (%d, %d)", (int)player.getXPos(), (int)player.getYPos()), 5, fontInfo.getHeight() * 3);
	}
	
	/* -- End life cycle methods -- */

}
