package com.ProjectDragoon.testpanels;

import java.util.Random;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.gui.Button;
import com.ProjectDragoon.gui.ButtonState;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteAnimation;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.Vector;

public class CoinFlipPanel extends GamePanel {

	
	private SpriteEntity coin;
	private boolean flipping;
	private Timer flipTimer;
	private int flipTime;
	
	private String message;
	
	private Button button;
	
	private Random random = new Random();
	
	public CoinFlipPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);

	}

	
	@Override
	public void gameInit()
	{
		super.gameInit();
		
		Sprite coinSprite = new Sprite();
		coinSprite.loadImage("/images/sprites/shittycoin.png");
		coinSprite.setTotalFrames(3);
		coinSprite.setNumColumns(3);
		coinSprite.configureDimensions();
		
		SpriteAnimation flip = new SpriteAnimation(4);
		int[] frames = {0,1,2,1};
		flip.setFrames(frames);
		flip.setFrameDelay(75);
		coinSprite.AddAnimations("flip", flip);
		coinSprite.setCurrentAnimation("flip");
		
		coin = new SpriteEntity(coinSprite, new Vector(0,0,0));
		
		flipping = false;
		
		flipTimer = new Timer();
		flipTime = 2000;
		
		message = "";
		
		button = new Button(50, 150, 100, 50);
		button.getSprite().loadImage("/images/sprites/simple_button.png");
		button.getSprite().setNumColumns(1);
		button.getSprite().setTotalFrames(4);
		button.getSprite().configureDimensions();
	}
	
	@Override
	public void gameUpdate()
	{
		button.readMouse(this.getMouse());
		switch(button.getState())
		{
			case NORMAL:
				break;
			case HOVER:
				break;
			case PRESSED:
				break;
			case ACTIVE:
				if(!flipping)
				{
					flipping = true;
					flipTimer.reset();
					flipTime = random.nextInt(3000) + 1000;
				}
				button.setState(ButtonState.NORMAL);
				break;
		}
		
		
		coin.update();
		if(flipping)
		{
			coin.animate();
		}
		else
		{
			SpriteAnimation anim = coin.getSprite().getCurrentAnimation();
			if(anim.getFrame(coin.getSprite().getCurrentAnimFrame()) == 1)
			{
				coin.animate();
			}
		}
		if(flipTimer.stopwatch(flipTime))
			flipping = false;
	}
	
	@Override
	public void gameRender()
	{
		super.gameRender();
		
		coin.draw(dbg);
		button.draw(dbg);
	}
}
