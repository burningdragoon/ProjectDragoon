package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.Options;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteAnimation;
import com.ProjectDragoon.util.Timer;

public class ImagePanel extends GamePanel{

	private static final long serialVersionUID = 1L;

	private Font font;
	private FontMetrics fontInfo;

	private Options options;
	
	private Texture image;
	private Sprite sprite;
	
	private Timer stopTimer;
	
	
	public ImagePanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
	}
	
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit() 
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		
		options = new Options();
		
		image = new Texture();
		image.loadImage("/images/kain_icon.png");
		//image.loadImage("/images/basic_window_frame.png");
		
		sprite = new Sprite();
		sprite.loadImage("/images/sprites/kain_spritesheet.png");
		sprite.setNumColumns(14);
		sprite.setTotalFrames(14);
		sprite.configureDimensions();
		//sprite.setPosition(50, 100);
		
		SpriteAnimation anim = new SpriteAnimation();
		int[] frames = {1};
		anim.setFrames(frames);
		sprite.AddAnimations("Rest", anim);
		sprite.setCurrentAnimation("Rest");
		
		SpriteAnimation walkAnim = new SpriteAnimation(2);
		int[] walkFrames = {1,2};
		walkAnim.setFrames(walkFrames);
		walkAnim.setDelay(200);
		sprite.AddAnimations("Walk", walkAnim);
		
		stopTimer = new Timer();
		stopTimer.setActive(false);
		
	}
	
	@Override
	public void gameUpdate() 
	{
		boolean walking = sprite.getCurrentAnimationName().equals("Walk");
		
		if(keyHandler.keyPressed(options.keys.ActionKey()))
		{
			if(!walking)
				sprite.setCurrentAnimation("Walk");
			stopTimer.setActive(false);
		}
		else
		{
			if(walking)
			{
				if(!stopTimer.isActive())
				{
					stopTimer.setActive(true);
					stopTimer.reset();
				}
				else if(stopTimer.stopwatch(1000))
				{
					sprite.setCurrentAnimation("Rest");
				}
			}
		}
		
		sprite.animate();
	}
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		
		dbg.setFont(font);
		
		int x = 50;
		int y = 100;
		//int w = image.getImage().getWidth();
		//int h = image.getImage().getHeight();
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		int line = fontInfo.getHeight();
		
		//image.draw(dbg, x, y);
		sprite.draw(dbg, 50, 100);
		
		dbg.setColor(Color.white);
		dbg.drawString("Width: " + w, x+w+20, y);
		dbg.drawString("Height: " + h, x+w+20, y + line);
	}
	
	/* -- End life cycle methods -- */
}
