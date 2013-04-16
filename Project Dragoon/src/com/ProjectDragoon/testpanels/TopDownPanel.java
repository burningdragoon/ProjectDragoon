package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.entity.Entity;
import com.ProjectDragoon.entity.EntityList;
import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.maps.TileType;
import com.ProjectDragoon.physics.HitBox;
import com.ProjectDragoon.physics.Rectangle;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.data.DataSaver;

public class TopDownPanel extends GamePanel {

	private Font font;
	private FontMetrics fontInfo;
	private KeyValues keys;
	
	private Texture background;
	
	private Map map;
	private Camera camera;
	
	private EntityList entities;
	
	private SpriteEntity player;
	
	private SpriteEntity enemy;
	private SpriteEntity enemy2;
	private int enemyVel = 10;
	private int enemyDist;
	private int enemyDistMoved;
	private int enemy2DistMoved;
	private Timer enemyMoveTimer;
	private int enemyMoveTime;
	
	private SpriteEntity missileResource;
	private int maxMissiles;
	private int totalMissiles;
	private boolean canFire;
	
	private int defaultXVelocity = 5;
	private int defaultYVelocity = 5;
	
	private String message;
	
	public TopDownPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Collision Detection
	 */
	
	/**
	 * Main Collision Detection method. Fires all appropriate, focused collision detection methods.
	 */
	public void collisionDetection()
	{
		mapCollision(map, player);
		mapCollision(map, enemy);
		mapCollision(map, enemy2);
		for(Entity e: entities.list())
		{
			mapCollision(map, (SpriteEntity)e);
			entityCollision((SpriteEntity)e, enemy);
			entityCollision((SpriteEntity)e, enemy2);
		}
		/*
		if(entityCollision(player, enemy))
		{
			entityCollisionHandle(player, enemy);
		}
		if(entityCollision(player, enemy2))
		{
			entityCollisionHandle(player, enemy2);
		}
		*/
		entityCollision(player, enemy);
		//entityCollision(enemy, player);
		entityCollision(player, enemy2);
		entityCollision(enemy, enemy2);
	}
	
	/**
	 * Determines if the given tile is collidable
	 * @param tile
	 * @return returns true if the tile is to be considered for collision detection.
	 */
	public boolean isCollidable(Tile tile)
	{
		return tile != null && tile.hasTile() && tile.getTileType() == TileType.SOLID;
	}
	
	/**
	 * This more focused Collision Detection method is used for base environment colisions between a single SpriteEntity and a Map.
	 * @param map
	 * @param entity
	 */
	public void mapCollision(Map map, SpriteEntity entity)
	{
		HitBox hitbox = entity.getHitBox();
		Vector curPos = hitbox.getPosition();
		int curXLeft = hitbox.left();//(int)hitbox.TopLeft().getX();
		int curXRight = hitbox.right();//(int)hitbox.TopRight().getX();
		int curYTop = hitbox.top();//(int)hitbox.TopLeft().getY();
		int curYBottom = hitbox.bottom();//(int)hitbox.BottomLeft().getY();
		
		Vector destPos = new Vector(curPos);
		destPos.Add(entity.getVelocity());
		int destXLeft = (int)(curXLeft + entity.getXVel());
		int destXRight = (int)(curXRight + entity.getXVel());
		int destYTop = (int)(curYTop + entity.getYVel());
		int destYBottom = (int)(curYBottom + entity.getYVel());
		
		//Tile curTileLeft = null;
		//Tile curTileRight = null;
		//Tile curTileTop = null;
		//Tile curTileBottom = null;
		
		Tile destTileLeft = null;
		Tile destTileRight = null;
		Tile destTileTop = null;
		Tile destTileBottom = null;
		
		/*
		 * Check if the entity's current position is colliding with the map environment
		 * if so, move it to the top-left most free tile
		 */
		// um... later.
		
		/*
		 * check if the entity's horizontal movement will collide with the environment
		 * if yes, move as far as possible in the current direction.
		 * if no, move the entity the full distance.
		 * 
		 * +note+ Current process is a nested for-loop that covers all possible tiles covered in a single move.
		 * It's a very dirty O(n^2) for each direction, but considering it'd be unlikely to check more than a handful of tiles, so not a huge deal. 
		 */
		
		// if moving RIGHT (-->)
		if(entity.getXVel() > 0)
		{
			int curColumn = curXRight / map.tileWidth();
			int curRow = curYTop / map.tileHeight();
			destTileTop = map.getTile(curYTop / map.tileHeight(), destXRight / map.tileWidth());
			destTileBottom = map.getTile((curYBottom-1) / map.tileHeight(), destXRight / map.tileWidth());
			
			if(!(destTileTop == null && destTileBottom == null))
			{
				if(destTileTop == null)
				{
					destTileTop = map.getFirstTileInColumn(curXRight / map.tileWidth());
				}
				else if(destTileBottom == null)
				{
					destTileBottom = map.getLastTileInColumn(curXRight / map.tileWidth());
				}
				
				for(int row = curRow; row <= destTileBottom.getMapRow(); row++)
				{
					boolean collide = false;
					
					for(int col = curColumn; col <= destTileTop.getMapColumn(); col++)
					{
						Tile tile = map.getTile(row, col);
						if(isCollidable(tile))
						{
							// COLLISION!
							// Congratulations on detecting DAT COLLISION.
							// Determine how much space between the entity and the point of collision
							// This is equal to the difference the far right of the current position, and the far left of the destination tile.
							int difx = (tile.getMapColumn() * map.tileWidth()) - curXRight;// - 1;
							entity.setXVel(difx);
							collide = true;
							break;
						}
					}
					if(collide)
						break;
				}
			}
		}
		
		// if moving LEFT (<--)
		else if(entity.getXVel() < 0)
		{
			int curColumn = curXLeft / map.tileWidth();
			destTileTop = map.getTile(curYTop / map.tileHeight(), destXLeft / map.tileWidth());
			destTileBottom = map.getTile((curYBottom-1) / map.tileHeight(), destXLeft / map.tileWidth());
			
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
						if(isCollidable(tile))
						{
							//Collision!
							int difx = (tile.getMapColumn() * map.tileWidth()) + map.tileWidth() - curXLeft;
							entity.setXVel(difx);
							collide = true;
							break;
						}
					}
					if(collide)
					{
						break;
					}
				}
			}
		}
		// if moving DOWN
		if(entity.getYVel() > 0)
		{
			int curRow = curYBottom / map.tileHeight();
			destTileLeft = map.getTile(destYBottom / map.tileHeight(), curXLeft / map.tileWidth());
			destTileRight = map.getTile(destYBottom / map.tileHeight(), (curXRight-1) / map.tileWidth());
			
			if(!(destTileLeft == null && destTileRight == null))
			{
				if(destTileLeft == null)
				{
					destTileLeft = map.getFirstTileInRow(curYBottom / map.tileHeight());
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
						if(isCollidable(tile))
						{
							int dify = (tile.getMapRow() * map.tileHeight()) - curYBottom;// - 1;
							entity.setYVel(dify);
							collide = true;
							break;
						}
					}
					if(collide)
					{
						break;
					}
				}
			}
		}
		// if moving UP
		else if(entity.getYVel() < 0)
		{
			int curRow = curYTop / map.tileHeight();
			destTileLeft = map.getTile(destYTop / map.tileHeight(), curXLeft / map.tileWidth());
			destTileRight = map.getTile(destYTop / map.tileHeight(), (curXRight-1) / map.tileWidth());
			
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
						if(isCollidable(tile))
						{
							int dify = (tile.getMapRow() * map.tileHeight()) + map.tileHeight() - curYTop;
							entity.setYVel(dify);
							collide = true;
							break;
						}
					}
					if(collide)
					{
						break;
					}
				}
			}
		}
	}
	
	public void entityCollision(SpriteEntity e1, SpriteEntity e2)
	{
		//+note+ test version. Check for collision between player and enemy.
		/*
		HitBox e1HB = e1.getHitBox();
		HitBox e2HB = e2.getHitBox();
		
		return !(
				e1HB.left() > e2HB.right() ||
				e1HB.right() < e2HB.left() ||
				e1HB.top() > e2HB.bottom() ||
				e1HB.bottom() < e2HB.top()
				);
		*/
		
		// +note+ v2: test for future collision.
		/*
		HitBox e1HB = e1.getHitBox();
		HitBox e2HB = e2.getHitBox();
		
		int e1Right = e1HB.right() + (int)e1.getXVel();
		int e1Left = e1HB.left() + (int)e1.getXVel();
		int e1Top = e1HB.top() + (int)e1.getYVel();
		int e1Bottom = e1HB.bottom() + (int)e1.getYVel();
		
		int e2Right = e2HB.right() + (int)e2.getXVel();
		int e2Left = e2HB.left() + (int)e2.getXVel();
		int e2Top = e2HB.top() + (int)e2.getYVel();
		int e2Bottom = e2HB.bottom() + (int)e2.getYVel();
		
		return !(
				e1Left > e2Right ||
				e1Right < e2Left ||
				e1Top > e2Bottom ||
				e1Bottom < e2Top
				);
		//*/
		
		// +note+ v3: getting broad pass box via entity
		//*
		/*
		Rectangle rect1 = e1.getBroadBoundingRectangle();
		Rectangle rect2 = e2.getBroadBoundingRectangle();
		return rect1.collides(rect2);
		*/

		if(entityBroadCollide(e1, e2))
		{
			entityCollisionHandle(e1, e2);
		}
		
	}
	
	public boolean entityBroadCollide(SpriteEntity e1, SpriteEntity e2)
	{
		Rectangle rect1 = e1.getBroadBoundingRectangle();
		Rectangle rect2 = e2.getBroadBoundingRectangle();
		return rect1.collides(rect2);
	}
	
	public void entityCollisionHandle(SpriteEntity e1, SpriteEntity e2)
	{
		/*
		 * +Note+ Currently assumes both objects are equal in terms of collidability.
		 * 
		 * Handles each collision by adjusting the entities' velocities so at next update they will meet instead of pass through each other.
		 */
		
		int difx = (int)e1.getXVel();
		int dify = (int)e1.getYVel();
		int difx2 = (int)e2.getXVel();
		int dify2 = (int)e2.getYVel();
		
		HitBox e1HB = e1.getHitBox();
		HitBox e2HB = e2.getHitBox();
		
		int e1Right = e1HB.right();
		int e1Left = e1HB.left();
		int e1Top = e1HB.top();
		int e1Bottom = e1HB.bottom();
		
		int e2Right = e2HB.right();
		int e2Left = e2HB.left();
		int e2Top = e2HB.top();
		int e2Bottom = e2HB.bottom();
		
		boolean alreadyIntersects = !(
				e1Left > e2Right ||
				e1Right < e2Left ||
				e1Top > e2Bottom ||
				e1Bottom < e2Top
				);
		
		if(alreadyIntersects)
		{
			// probably should do stuff here.
			System.out.println("I'm inside you.");
		}
		else
		{
			double e1XVel = e1.getXVel();
			double e1YVel = e1.getYVel();
			double e2XVel = e2.getXVel();
			double e2YVel = e2.getYVel();
			
			// entity 1, entity 2:
			
			/*
			 * Horizontal Collision
			 * To avoid catching horiz collision when on the entities are on top of each other
			 * only handling collision when the first entity is +not+ completely above or below the second entity
			 */
			if(!e1.isAbove(e2) && !e1.isBelow(e2))
			{
				// right
				if(e1XVel > 0)
				{
					// right, right
					if(e2XVel > 0)
					{
						if(e1.isToLeft(e2))
						{
							difx = e2Left - e1Right - 1;
						}
						else if(e1.isToRight(e2))
						{
							// leave e1's x-velocity alone
							//difx2 = e1Left + (int)e1XVel - e2Right - 1;
							difx2 = e1Left - e2Right - 1;
						}
						else
						{
							// do nothing?
						}
					}
					// right, left
					else if(e2XVel < 0)
					{
						if(Math.abs(e1XVel) > Math.abs(e2XVel))
						{
							difx = e2Left - e1Right - 1;
							difx2 = 0;
						}
						else if(Math.abs(e1XVel) < Math.abs(e2XVel))
						{
							difx = 0;
							difx2 = e1Right - e2Left + 1;
						}
						else
						{
							int dif = e2Left - e1Right - 1;
							difx = dif / 2;
							if(dif % 2 == 0)
							{
								difx2 = -difx;
							}
							else
							{
								difx2 = -difx - 1;
							}
						}
					}
					// right, not
					else
					{	
						difx = e2Left - e1Right - 1;
						//difx2 = 0;
					}
				}
				//left
				else if(e1XVel < 0)
				{
					// left, right
					if(e2XVel > 0)
					{
						if(Math.abs(e1XVel) > Math.abs(e2XVel))
						{
							difx = 0;
							difx2 = e1Left - e2Right - 1;
						}
						else if(Math.abs(e1XVel) < Math.abs(e2XVel))
						{
							difx = e2Right - e1Left + 1;
							difx2 = 0;
						}
						else
						{
							int dif = e2Right - e1Left + 1;
							difx = dif / 2;
							if(dif % 2 == 0)
							{
								difx2 = -difx;
							}
							else
							{
								difx2 = -difx - 1;
							}
						}
					}
					// left, left
					else if(e2XVel < 0)
					{
						if(e1.isToRight(e2))
						{
							difx = e2Right - e1Left + 1;
						}
						else if(e1.isToLeft(e2))
						{
							difx2 = e1Right - e2Left + 1;
						}
						else
						{
							// do nothing?
						}
					}
					// left, not
					else if(e2XVel == 0)
					{
						difx = e2Right - e1Left + 1;
						difx2 = 0;
					}
				}
				// not
				else
				{
					// not, right
					if(e2XVel > 0)
					{
						difx = 0;
						difx2 = e1Left - e2Right -1;
					}
					// not, left
					else if(e2XVel < 0)
					{
						difx = 0;
						difx2 = e1Right - e2Left + 1;
					}
				}
			}
			
			/*
			 * Vertical Collision
			 * 
			 * Before checking for y-axis collisions, update the x-axis velocities and recheck for broad collision. If still colliding, handle vertical collisions as well.
			 * This will give priority to x-axis collisions, but (should) prevent adjacent velocity collisions from not catching. I'm okay with that.
			 */
			e1.setXVel(difx);
			e2.setXVel(difx);
			if(entityBroadCollide(e1, e2))
			{
				// down
				if(e1YVel > 0)
				{
					// down, down
					if(e2YVel > 0)
					{
						if(e1.isAbove(e2))
						{
							dify = e2Top - e1Bottom - 1;
						}
						else if(e1.isBelow(e2))
						{
							dify2 = e1Top - e2Bottom - 1;
						}
					}
					// down, up
					else if(e2YVel < 0)
					{
						if(Math.abs(e1YVel) > Math.abs(e2YVel))
						{
							dify = e2Top - e1Bottom - 1;
							dify2 = 0;
						}
						else if(Math.abs(e1YVel) < Math.abs(e2YVel))
						{
							dify = 0;
							dify2 = e1Bottom - e2Top + 1;
						}
						else 
						{
							int dif = e2Top - e1Bottom - 1;
							dify = dif / 2;
							if(dif % 2 == 0)
							{
								dify2 = -dify;
							}
							else
							{
								dify2 = -dify - 1;
							}
						}
					}
					// down, not
					else
					{
						dify = e2Top - e1Bottom - 1;
						dify2 = 0;
					}
				}
				// up
				else if(e1YVel < 0)
				{
					// up, down
					if(e2YVel > 0)
					{
						if(Math.abs(e1YVel) > Math.abs(e2YVel))
						{
							dify = e2Bottom - e1Top + 1;
							dify2 = 0;
						}
						else if(Math.abs(e1YVel) < Math.abs(e2YVel))
						{
							dify = 0;
							dify2 = e1Top - e2Bottom - 1;
						}
						else 
						{
							int dif = e2Bottom - e1Top + 1;
							dify = dif / 2;
							if(dif % 2 == 0)
							{
								dify2 = -dify;
							}
							else
							{
								dify2 = -dify - 1;
							}
						}
					}
					// up, up
					else if(e2YVel < 0)
					{
						if(e1.isAbove(e2))
						{
							dify = e2Bottom - e1Top + 1;
						}
						else if(e1.isBelow(e2))
						{
							dify2 = e1Bottom - e2Top + 1;
						}
					}
					// up, not
					else
					{
						dify = e2Bottom - e1Top + 1;
						dify2 = 0;
					}
				}
				// not
				else
				{
					// not, down
					if(e2YVel > 0)
					{
						dify = 0;
						dify2 = e1Top - e2Bottom - 1;
					}
					// not, up
					else if(e2YVel < 0)
					{
						dify = 0;
						dify2 = e1Bottom - e2Top + 1;
					}
				}
			}
		}
	
		e1.setVelocity(difx, dify);
		e2.setVelocity(difx2, dify2);
	}
	
	/* -- End of Collision Detection -- */
	
	/*
	 * 
	 */
	
	@Override
	public void handleInput()
	{
		// Reset Everything
			if(keyPressed(keys.ResetKey()))
				gameInit();
			
			/* Player Control Input */
			// Horizontal movement
			if(keyPressed(keys.RightKey()))
			{
				player.setXVel(defaultXVelocity);
			}
			else if(keyPressed(keys.LeftKey()))
			{
				player.setXVel(-defaultXVelocity);
			}
			else
			{
				player.setXVel(0);	
			}
			// Vertical movement
			if(keyPressed(keys.DownKey()))
			{
				player.setYVel(defaultYVelocity);
			}
			else if(keyPressed(keys.UpKey()))
			{
				player.setYVel(-defaultYVelocity);
			}
			else
			{
				player.setYVel(0);
			}
			
			// +TEST+ Missile firing.
			if(keyPressed(keys.ActionKey()))
			{
				enemy.setXVel(enemyVel * 2);
				//enemy2.setYVel(enemyVel);
				enemy2.setXVel(-enemyVel);
				if(false)//if(canFire)
				{
					canFire = false;
					SpriteEntity missile = missileResource.copy();
					missile.setPosition(player.getPosition());
					int velX = 0;
					int velY = 0;
					
					String playerDirection = player.sprite.getCurrentAnimationName().split("_")[1];
					if(playerDirection.equals("right"))
						velX = defaultXVelocity;
					else if(playerDirection.equals("left"))
						velX = -defaultXVelocity;
					else if(playerDirection.equals("down"))
						velY = defaultYVelocity;
					else if(playerDirection.equals("up"))
						velY = -defaultYVelocity;
					
					missile.setVelocity(velX, velY);
					entities.add(missile);
				}
			}
			else
			{
				canFire = true;
			}
			
			// mouse testing
			if(mouse.isPressed(0))
			{
				int mouseX = mouse.getX();
				int mouseY = mouse.getY();
				int playerCenterX = ((int)player.getXPos() + player.getWidth() / 2) - camera.x;
				int playerCenterY = ((int)player.getYPos() + player.getHeight() / 2) - camera.y;
				int playerLeft = (int)player.getXPos() - camera.x;
				int playerRight = (int)player.getXPos() + player.getWidth() - camera.x;
				int playerTop = (int)player.getYPos() - camera.y;
				int playerBottom = (int)player.getYPos() + player.getHeight() - camera.y;
				
				if(mouseX > playerCenterX + defaultXVelocity)
				{
					player.setXVel(defaultXVelocity);
				}
				else if(mouseX < playerCenterX - defaultXVelocity)
				{
					player.setXVel(-defaultXVelocity);
				}
				
				if(mouseY > playerCenterY + defaultYVelocity)
				{
					player.setYVel(defaultYVelocity);
				}
				else if(mouseY < playerCenterY - defaultYVelocity)
				{
					player.setYVel(-defaultYVelocity);
				}
			}
			
	}
	
	/* */
	
	/*
	 * Main game life cycle methods
	 */
	
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
		
		entities = new EntityList();
		
		player = DataSaver.LoadSpriteEntity("res/entities/dragoon.spriteentity");
		player.sprite.setCurrentAnimation("rest_down");
		
		enemy = DataSaver.LoadSpriteEntity("res/entities/dragoon.spriteentity");
		enemy.sprite.setCurrentAnimation("rest_down_nohelmet");
		enemy.setPosition(450, 250);
		//enemyVel = 1;
		enemyDist = 2000;
		enemyDistMoved = 0;
		enemy2DistMoved = 0;
		//enemy.setYVel(enemyVel);
		enemy.setVelocity(0, 0);
		enemyMoveTimer = new Timer();
		enemyMoveTime = 2000;
		
		enemy2 = enemy.copy();
		//enemy2.setPosition(enemy.getXPos() + 200, enemy.getYPos() + enemy2.getHeight());
		//enemy2.setPosition(enemy.getXPos() + enemy2.getHitBox().getWidth() + 1, enemy.getYPos() - enemy2.getHitBox().getHeight() - 1);
		//enemy2.setPosition(enemy.getXPos(), enemy.getYPos() - enemy2.getHitBox().getHeight() - 1);
		enemy2.setPosition(enemy.getXPos(), enemy.getYPos());
		enemy2.setVelocity(0, 0);
		
		int dist = 100;
		enemy.moveX(-2 *dist);
		enemy2.moveX(2* dist);
		//enemy2.moveY(-dist);
		
		
		missileResource = DataSaver.LoadSpriteEntity("res/entities/missile_test.spriteentity");
		missileResource.setAlive(true);
		maxMissiles = 3;
		totalMissiles = 0;
		canFire = true;
		
		message = "";
	}
	
	@Override
	public void gameUpdate() 
	{
		handleInput();
		
		/* Update Player Animation */
		/*
		 * The basic idea is:
		 * 1 - If moving in a direction, update animation to walking in that direction.
		 * 2 - If already moving and animating in the adjacent axis, don't change the animation
		 */
		String curAnim = player.sprite.getCurrentAnimationName();
		String newAnim = "";
		double xVel = player.getXVel();
		double yVel = player.getYVel();
		if(xVel > 0)
		{
			if(yVel > 0)
			{
				if(curAnim.equalsIgnoreCase("walk_down"))
					newAnim = curAnim;
				else
					newAnim = "walk_right";
			}
			else if(yVel < 0)
			{
				if(curAnim.equalsIgnoreCase("walk_up"))
					newAnim = curAnim;
				else
					newAnim = "walk_right";
			}
			else
			{
				newAnim = "walk_right";
			}
		}
		else if(xVel < 0)
		{
			if(yVel > 0)
			{
				if(curAnim.equalsIgnoreCase("walk_down"))
					newAnim = curAnim;
				else
					newAnim = "walk_left";
			}
			else if(yVel < 0)
			{
				if(curAnim.equalsIgnoreCase("walk_up"))
					newAnim = curAnim;
				else
					newAnim = "walk_left";
			}
			else
			{
				newAnim = "walk_left";
			}
		}
		else // x velocity is 0
		{
			if(yVel > 0)
			{
				newAnim = "walk_down";
			}
			else if(yVel < 0)
			{
				newAnim = "walk_up";
			}
			else
			{
				// sprite is not moving in any direction --> set to rest version of current animation
				if(curAnim.equalsIgnoreCase("walk_right"))
					newAnim = "rest_right";
				else if(curAnim.equalsIgnoreCase("walk_left"))
					newAnim = "rest_left";
				else if(curAnim.equalsIgnoreCase("walk_down"))
					newAnim = "rest_down";
				else if(curAnim.equalsIgnoreCase("walk_up"))
					newAnim = "rest_up";
				else
					newAnim = curAnim;
			}
		}
		if(!newAnim.equals("") && !newAnim.equalsIgnoreCase(curAnim))
		{
			//System.out.println("Animation Change!");
			player.sprite.setCurrentAnimation(newAnim);
		}
		
		collisionDetection();
		
		player.update();
		player.animate();
		
		enemy.update();
		enemy2.update();
		/*
		if(enemyMoveTimer.stopwatch(enemyMoveTime))
		{
			//enemy.setXVel(-enemy.getXVel());
			enemyVel *= -1;
		}
		if((int)enemy.getXVel() != enemyVel)
		{
			enemy.setXVel(enemyVel);
		}
		*/
		enemyDistMoved += Math.abs((int)enemy.getYVel());
		if(enemyDistMoved >= enemyDist)
		{
			enemyDistMoved = 0;
			enemyVel *= -1;
		}
		if((int)enemy.getYVel() != enemyVel)
		{
			//enemy.setYVel(enemyVel);
		}
		
		for(Entity e : entities.list())
		{
			e.update();
			if(e.lifetimeExpired())
			{
				e.setAlive(false);
				//entities.remove(e);
			}
			else if(!e.isAlive())
			{
				entities.remove(e);
			}
			else
			{
				e.animate();
			}
		}
		
		camera.adjust(player);
	}
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		
		map.draw(dbg, camera);
		map.drawGrid(dbg, camera);
		
		enemy.draw(dbg, camera);
		enemy2.draw(dbg, camera);
		
		player.draw(dbg, camera);
		
		
		for(Entity e : entities.list())
		{
			e.draw(dbg, camera);
		}
		
		//missile.draw(dbg, camera);
		
		// display fps
		dbg.setColor(Color.white);
		dbg.drawString(String.format("FPS: %.2f", this.getFPS()), 5, fontInfo.getHeight());
		
		dbg.drawString(String.format("Camera: (%d, %d)", camera.x, camera.y), 5, fontInfo.getHeight() * 2);
		dbg.drawString(String.format("Player: (%d, %d)", (int)player.getXPos(), (int)player.getYPos()), 5, fontInfo.getHeight() * 3);
		dbg.drawString(String.format("Mouse: (%d, %d)", mouse.getX(), mouse.getY()), 5, fontInfo.getHeight() * 4);
		dbg.drawString(message, 5, fontInfo.getHeight() * 5);
	}
	
	/* -- End life cycle methods -- */

}
