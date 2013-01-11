package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Entity;
import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.physics.CollisionMap;
import com.ProjectDragoon.physics.HitBox;
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
	SpriteEntity badguy;
	
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
	
	/**
	 * Performs any collision detection
	 */
	public void collisionDetection()
	{
		// Collision Detection:
		//TODO : Collision Detection: Out of Bounds checking.
		//TODO : Collision Detection between entities.

		/* -- Player vs Map collisions */
		HitBox hitbox = player.getHitBox();
		Vector curPos = hitbox.getPosition();
		int curXLeft = (int)hitbox.TopLeft().getX();
		int curXRight = (int)hitbox.TopRight().getX();
		int curYTop = (int)hitbox.TopLeft().getY();
		int curYBottom = (int)hitbox.BottomLeft().getY();
		
		Vector destPos = new Vector(curPos);
		destPos.Add(player.getVelocity());
		int destXLeft = (int)(curXLeft + player.getXVel());
		int destXRight = (int)(curXRight + player.getXVel());
		int destYTop = (int)(curYTop + player.getYVel());
		int destYBottom = (int)(curYBottom + player.getYVel());
		
		Tile curTileLeft = null;
		Tile curTileRight = null;
		Tile curTileTop = null;
		Tile curTileBottom = null;
		
		Tile destTileLeft = null;
		Tile destTileRight = null;
		Tile destTileTop = null;
		Tile destTileBottom = null;
		
		// check if the player's current position is colliding with the environment
		// if so, move it to the top-left most free tile
		
		// check if player's horizontal movement will collide with the environment
		// if yes, move as far as possible in the current direction.
		// if no, move the player the full distance.
		// Current process is a double for-loop that covers all possible tiles covered in a single move. 
		// It's a very dirty O(n^2), but considering it'd be unlikely to check more than a handful of tiles, it's not a big deal. 
		
		// if moving Right (-->)		
		if(player.getXVel() > 0)
		{	
			int curColumn = curXRight / map.tileWidth();
			destTileTop = map.getTile(curYTop / map.tileHeight(), destXRight / map.tileWidth());
			destTileBottom = map.getTile(curYBottom / map.tileHeight(), destXRight / map.tileWidth());
			
			if(!(destTileTop == null & destTileBottom == null))
			{
				if(destTileTop == null)
				{
					destTileTop = map.getFirstTileInColumn(curXRight / map.tileWidth());
				}
				else if(destTileBottom == null)
				{
					destTileBottom = map.getLastTileInColumn(curXRight / map.tileWidth());
				}
				
				for(int row = destTileTop.getMapRow(); row <= destTileBottom.getMapRow(); row++)
				{
					boolean collide = false;
					//System.out.println("HERPA");
					for(int col = curColumn; col <= destTileTop.getMapColumn(); col++)
					{
						Tile tile = map.getTile(row, col);
						if(tile.hasTile())
						{
							// COLLISION!
							// determine how much space before collision:
							// this is equal to the difference between the far right of the current position, and the far left of the destination tile.
							int difx = (tile.getMapColumn() * map.tileWidth()) - curXRight - 1;
							player.setXVel(difx);
							collide = true;
							break;
						}
						if(collide)
						{
							break;
						}
					}
					/*
					Tile tile = map.getTile(row, destTileTop.getMapColumn());
					if(tile.hasTile())
					{
						// COLLISION!
						// determine how much space before collision:
						// this is equal to the difference between the far right of the current position, and the far left of the destination tile.
						int difx = (tile.getMapColumn() * map.tileWidth()) - curXRight - 1;
						player.setXVel(difx);
						break;
					}
					*/
				}
			}
			
		}
		// if moving Left (<--)
		else if(player.getXVel() < 0)
		{
			int curColumn = curXLeft / map.tileWidth();
			destTileTop = map.getTile(curYTop / map.tileHeight(), destXLeft / map.tileWidth());
			destTileBottom = map.getTile(curYBottom / map.tileHeight(), destXLeft / map.tileWidth());
			
			if(!(destTileTop == null && destTileBottom == null))
			{
				if(destTileTop == null)
				{
					destTileTop = map.getFirstTileInColumn(curXLeft / map.tileWidth());
				}
				else if(destTileBottom == null)
				{
					destTileBottom = map.getLastTileInColumn(curXLeft / map.tileWidth());
				}
				
				for(int row = destTileTop.getMapRow(); row <= destTileBottom.getMapRow(); row++)
				{
					boolean collide = false;
					for(int col = curColumn; col >= destTileTop.getMapColumn(); col--)
					{
						Tile tile = map.getTile(row, col);
						if(tile.hasTile())
						{
							//Collision!
							int difx = (tile.getMapColumn() * map.tileWidth()) + map.tileWidth() - curXLeft + 1;
							player.setXVel(difx);
							collide = true;
							break;
						}
						if(collide)
						{
							break;
						}
					}
				}
			}
		}	
		
		// check if the player's vertical movement will collide with the environment.
		// if yes, move as far as possible
		// if no, move the full distance
		boolean collideGround = false;
		
		if(player.getYVel() > 0)
		{
			int curRow = curYBottom / map.tileHeight();
			destTileLeft = map.getTile(destYBottom / map.tileHeight(), curXLeft / map.tileWidth());
			destTileRight = map.getTile(destYBottom / map.tileHeight(), curXRight / map.tileWidth());
			
			if(!(destTileLeft == null && destTileRight == null))
			{
				if(destTileLeft == null)
				{
					destTileLeft = map.getLastTileInRow(curYBottom / map.tileHeight());
				}
				else if(destTileRight == null)
				{
					destTileRight = map.getLastTileInRow(curYBottom / map.tileHeight());
				}
				
				for(int column = destTileLeft.getMapColumn(); column <= destTileRight.getMapColumn(); column++)
				{
					boolean collide = false;
					for(int row = curRow; row <= destTileLeft.getMapRow(); row++)
					{
						Tile tile = map.getTile(row, column);
						if(tile != null && tile.hasTile())
						{
							int dify = (tile.getMapRow() * map.tileHeight()) - curYBottom - 1;
							player.setYVel(dify);
							collideGround = true;
							collide = true;
							break;
						}
						if(collide)
						{
							break;
						}
					}
				}
			}
		}
		else if(player.getYVel() < 0)
		{
			int curRow = curYTop / map.tileHeight();
			destTileLeft = map.getTile(destYTop / map.tileHeight(), curXLeft / map.tileWidth());
			destTileRight = map.getTile(destYTop / map.tileHeight(), curXRight / map.tileWidth());
			
			if(!(destTileLeft == null && destTileRight == null))
			{
				if(destTileLeft == null)
				{
					destTileLeft = map.getFirstTileInRow(curYTop / map.tileHeight());
				}
				else if(destTileRight == null)
				{
					destTileRight = map.getLastTileInRow(curYTop / map.tileHeight());
				}
			
				for(int column = destTileLeft.getMapColumn(); column <= destTileRight.getMapColumn(); column++)
				{
					boolean collide = false;
					for(int row = curRow; row >= destTileLeft.getMapRow(); row--)
					{
						Tile tile = map.getTile(row, column);
						if(tile.hasTile())
						{
							int dify = (tile.getMapRow() * map.tileHeight()) + map.tileHeight() - curYTop + 1;
							player.setYVel(dify);
							if(playerState == JUMPING)
								playerState = FALLING;
							break;
						}
					}
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
		
		/* -- Player vs. Other -- */
		// Testing Version
		if(entityCollision(player, badguy))
		{
			System.out.println("Yo man, get out of my way!");
		}
		
	}
	
	public boolean entityCollision(Entity e1, Entity e2)
	{
		return false;
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
		map.readMap("res/maps/testmap5.txt");
		
		// Set up camera.
		camera = new Camera(this.getScreenWidth(), this.getScreenHeight());
		camera.setXRange(0, map.getWidth() - getScreenWidth());
		camera.setYRange(0, map.getHeight() - getScreenHeight());
		
		// Set up the player stuff
		Sprite sprite = DataSaver.LoadSprite("res/sprites/kain.sprite");
		sprite.setCurrentAnimation("Rest");
		player = new SpriteEntity(sprite, new Vector());
		playerState = ONGROUND;
		
		HitBox hitbox = player.getHitBox();
		hitbox.setPosition(25, 20);
		hitbox.setWidth(13);
		hitbox.setHeight(33);
		
		//test badguy thingy
		badguy = new SpriteEntity(sprite, new Vector());
		//CollisionMap colmap2 = badguy.getCollisionMap();
		//colmap2.setAllPoints(colmap);
		badguy.setPosition(100, 350);
		
		//test thingy
		sprite = DataSaver.LoadSprite("res/sprites/dragoon.sprite");
		sprite.setCurrentAnimation("Walk_Down");
		dragon = new SpriteEntity(sprite, new Vector());
		dragon.setPosition(100, 100);

		
	}
	
	@Override
	public void gameUpdate() 
	{		
		// Reset everything
		if(keyPressed(keys.ResetKey()))
			gameInit();
		
		if(keyPressed(keys.RightKey()))
		{
			player.setXVel(40);
			player.forward = false;
		}
		else if(keyPressed(keys.LeftKey()))
		{
			//player.setXVel(-1);
			player.setXVel(-40);
			player.forward = true;
		}
		else
		{
			player.setXVel(0);
		}
		
		/*
		if(keyPressed(keys.UpKey()))
		{
			player.setYVel(-2);
		}
		else if(keyPressed(keys.DownKey()))
		{
			player.setYVel(2);
		}
		else
		{
			player.setYVel(0);
		}
		//*/
		
		//*
		if(playerState == FALLING)
		{
			player.setYVel(40);
			//player.sprite.setCurrentAnimation("");
		}
		else if(playerState == ONGROUND)
		{
			if(keyPressed(keys.ActionKey()))
			{
				player.setYVel(-40);
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
		//*/
		
		
		
		collisionDetection();
		
		player.update();
		player.animate();
		dragon.animate();
		badguy.animate();
		
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
		map.drawGrid(dbg, camera);
		
		dragon.draw(dbg, camera);
		
		badguy.draw(dbg, camera);
		
		player.draw(dbg, camera);
		
		// display fps
		dbg.setColor(Color.white);
		dbg.drawString(String.format("FPS: %.2f", this.getFPS()), 5, fontInfo.getHeight());
		
		dbg.drawString(String.format("Camera: (%d, %d)", camera.x, camera.y), 5, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Player: (%d, %d)", (int)player.getXPos(), (int)player.getYPos()), 5, fontInfo.getHeight() * 3);
	}
	
	/* -- End life cycle methods -- */

}
