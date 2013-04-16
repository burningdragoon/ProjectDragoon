package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Random;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.Tile;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.physics.CollisionMap;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.data.DataSaver;

public class TileMapTestPanel extends GamePanel {

	private static final long serialVersionUID = 1L;

	private final int ONGROUND = 0;
	private final int JUMPING = 1;
	private final int FALLING = 2;
	private final int HITWALL = 3;
	
	//super simple testing variables...
	Texture background;
	int tilew;
	int tileh;
	Map map;
	Random random;
	
	TileSet tileset;
	
	SpriteEntity guy;
	String guyTile;
	int guyState;
	boolean onGround;
	boolean isJumping;
	Timer jumpTimer;
	int jumpLength;
	
	boolean[][] tileLights;
	
	
	Font font;
	FontMetrics fontInfo;
	
	KeyValues keys;
	//...
	
	public TileMapTestPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		
		background = new Texture();
		//tilew = width / 10;
		//tileh = height / 10;
		tilew = 32;
		tileh = 32;
		random = new Random();
		
		guyTile = "";
	}

	
	/*
	 * Selectors and Mutators
	 */
	
	/* -- End of selectors and mutators -- */
	
	/*
	 * Other shit
	 */
	
	public void clearLights()
	{
		for(int r = 0; r < tileLights.length; r++)
		{
			for(int c = 0; c < tileLights[r].length; c++)
			{
				tileLights[r][c] = false;
			}
		}
	}
	
	/**
	 * Lights up any tiles touching the sprite entity -- assumes a rectangle/square
	 */
	public void lightTiles()
	{
		// top left x,y & w,h of the guy
		int guyX = (int)guy.getXPos();
		int guyY = (int)guy.getYPos();
		int guyW = guy.getWidth();
		int guyH = guy.getHeight();
		
		//dimensions of each tile
		int tileW = map.tileWidth();
		int tileH = map.tileHeight();
		
		//
		int topRow = guyY / tileH;
		int bottomRow = (guyY + guyH) / tileH;
		int leftColumn = guyX / tileW;
		int rightColumn = (guyX + guyW) / tileW;

		for(int r = topRow; r <= bottomRow; r++)
		{
			for(int c = leftColumn; c <= rightColumn; c++)
			{
				tileLights[r][c] = true;
			}
		}
		
		
	}
	
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
		//tileset.loadTiles("/images/sprites/kain_spritesheet.png");
		
		map = new Map(tileset, 20, 25);
		map.readMap("res/maps/testmap.txt");
		
		tileLights = new boolean[map.getRows()][map.getColumns()];
		
		//Sprite stuffs
		//Sprite sprite = DataSaver.LoadSprite("res/sprites/kain.sprite");
		//sprite.setCurrentAnimation("Attack");
		Sprite badguy = DataSaver.LoadSprite("res/sprites/soldier.sprite");
		
		Vector vector = new Vector();
		
		//guy = new SpriteEntity(sprite, vector);
		guy = new SpriteEntity(badguy, vector);
		
		/*
		CollisionMap colMap = guy.getCollisionMap();
		colMap.setLeftPoints(3, 8, guy.getHeight()-5);
		colMap.setRightPoints(guy.getWidth()-5, 8, guy.getHeight()-5);
		colMap.setHeadPoints(3, guy.getWidth()-5, 5);
		colMap.setFeetPoints(3, guy.getWidth()-5, guy.getHeight());
		*/
		
		guyState = ONGROUND;
		onGround = false;
		isJumping = false;
		
		jumpTimer = new Timer();
		jumpLength = 500;
	}
	
	@Override
	public void gameUpdate() 
	{
		/*
		if(this.getKeyHandler().keyPressed(keys.DownKey()))
		{
			//guy.setYVel(2);
		}
		else if(this.getKeyHandler().keyPressed(keys.UpKey()))
		{
			//guy.setYVel(-2);
		}
		else
		{
			if(!onGround)
			{
				guy.setYVel(3);
			}
			else
			{
				guy.setYVel(0);
			}
		}
		//*/
		
		if(guyState == ONGROUND)
		{
			if(this.getKeyboard().keyPressed(keys.ActionKey()))
			{
				guyState = JUMPING;
				jumpTimer.reset();
			}
		}
		else if(guyState == JUMPING)
		{
			if(!this.getKeyboard().keyPressed(keys.ActionKey()))
			{
				guyState = FALLING;
			}
		}
		
		switch(guyState)
		{
			case ONGROUND:
				guy.setYVel(0);
				break;
			case JUMPING:
				if(jumpTimer.stopwatch(jumpLength))
				{
					guyState = FALLING;
				}
				else
					guy.setYVel(-3);
				break;
			case HITWALL:
				guy.setYVel(0);
				if(jumpTimer.stopwatch(jumpLength))
				{
					guyState = FALLING;
				}
				break;
			case FALLING:
				guy.setYVel(3);
				break;
			default:
		}
		
		if(getKeyboard().keyPressed(keys.RightKey()))
		{
			guy.setXVel(2);
		}
		else if(getKeyboard().keyPressed(keys.LeftKey()))
		{
			guy.setXVel(-2);
		}
		else
		{
			guy.setXVel(0);
		}
		
		guy.update();
		guy.animate();
		
		//clearLights();
		//lightTiles();
		
		//find sprite
		// top left x,y of the guy
		int guyX = (int)guy.getXPos();
		int guyY = (int)guy.getYPos();
		int guyW = guy.getWidth();
		int guyH = guy.getHeight();
		
		//dimensions of each tile
		int tileW = map.tileWidth();
		int tileH = map.tileHeight();
		
		/*
		// Collision detection using collision points
		//CollisionMap colMap = guy.getCollisionMap();		
		
		// Going right -->
		boolean collideWall = false;
		if(guy.getXVel() > 0)
		//if(true)
		{
			int xRight = (int)colMap.RIGHT_TOP().getX();
			int yRightTop = (int)colMap.RIGHT_TOP().getY();
			int yRightBot = (int)colMap.RIGHT_BOTTOM().getY();
			Tile tileTop = map.getTile(yRightTop / tileH, xRight / tileW);
			Tile tileBot = map.getTile(yRightBot / tileH, xRight / tileW);
			for(int r = tileTop.getMapRow(); r <= tileBot.getMapRow(); r++)
			{
				Tile tile = map.getTile(r, tileTop.getMapColumn());
				if(tile.hasTile())
				{
					int mx = ((xRight / tileW) * tileW) - xRight - 1;
					guy.moveX(mx);
					collideWall = true;
					//after collision handled, beak out of loop since there is no reason to keep checking.
					break;
				}
			}
		}
		// Going left <--
		else if(guy.getXVel() < 0)
		{
			int xLeft = (int)colMap.LEFT_TOP().getX();
			int yLeftTop = (int)colMap.LEFT_TOP().getY();
			int yLeftBot = (int)colMap.LEFT_BOTTOM().getY();
			Tile tileTop = map.getTile(yLeftTop / tileH, xLeft / tileW);
			Tile tileBot = map.getTile(yLeftBot / tileH, xLeft / tileW);
			for(int r = tileTop.getMapRow(); r <= tileBot.getMapRow(); r++)
			{
				Tile tile = map.getTile(r, tileTop.getMapColumn());
				if(tile.hasTile())
				{
					int mx = (((xLeft / tileW) * tileW) + tileW) - xLeft;
					guy.moveX(mx);
					collideWall = true;
					// after collision is handled, break out of the loop since there is no reason to keep checking.
					break;
				}
			}
		}
		
		if(collideWall && guyState == JUMPING)
		{
			guyState = HITWALL;
		}
		else if(!collideWall && guyState == HITWALL)
		{
			guyState = FALLING;
		}
		
		//falling / on ground
		int xFeetLeft = (int)colMap.FEET_LEFT().getX();
		int xFeetRight = (int)colMap.FEET_RIGHT().getX();
		int yFeet = (int)colMap.FEET_LEFT().getY();
		Tile tileLeft = map.getTile(yFeet / tileH, xFeetLeft / tileW);
		Tile tileRight = map.getTile(yFeet / tileH, xFeetRight / tileW);
		boolean collideGround = false;
		for(int c = tileLeft.getMapColumn(); c <= tileRight.getMapColumn(); c++)
		{
			Tile tile = map.getTile(tileLeft.getMapRow(), c);
			if(tile.hasTile())
			{
				int my = ((yFeet / tileH) * tileH) - yFeet;
				guy.moveY(my);
				collideGround = true;
				break;
			}
		}
		//onGround = collideGround;
		if(collideGround)
		{
			guyState = ONGROUND;
		}
		else
		{
			if(guyState != JUMPING && guyState != HITWALL)
			{
				guyState = FALLING;
			}
		}
		
		// Upper collision:
		int xHeadLeft = (int)colMap.HEAD_LEFT().getX();
		int xHeadRight = (int)colMap.HEAD_RIGHT().getX();
		int yHead = (int)colMap.HEAD_LEFT().getY();
		tileLeft = map.getTile(yHead / tileH, xHeadLeft / tileW);
		tileRight = map.getTile(yHead / tileH, xHeadRight / tileW);
		for(int c = tileLeft.getMapColumn(); c <= tileRight.getMapColumn(); c++)
		{
			Tile tile = map.getTile(tileLeft.getMapRow(), c);
			if(tile.hasTile())
			{
				int my = (((yHead / tileH) * tileH) + tileH) - yHead;
				guy.moveY(my);
				if(guyState == JUMPING)
					guyState = FALLING;
				break;
			}
		}
		*/
	} // End of gameUpdate()
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		dbg.setFont(font);
		
		//background.draw(dbg);
		dbg.drawImage(background.getImage(), 0, 0, 800, 640, null);
		map.draw(dbg);
		map.drawGrid(dbg);
		
		dbg.setColor(Color.yellow);
		for(int r = 0; r < tileLights.length; r++)
		{
			for(int c = 0; c < tileLights[r].length; c++)
			{
				if(tileLights[r][c])
				{
					int x = map.tileWidth() * c;
					int y = map.tileHeight() * r;
					int w = map.tileWidth();
					int h = map.tileHeight();
					dbg.fillRect(x, y, w, h);
				}
			}
		}
		
		guy.draw(dbg);
		
		dbg.setColor(Color.black);
		dbg.drawString(guyTile, 0, 20);

	}
	
	/* -- End life cycle methods -- */

}
