package com.ProjectDragoon.states;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.gui.Button;
import com.ProjectDragoon.gui.ButtonState;
import com.ProjectDragoon.sprites.SimpleSprite;

public class Play extends GameState {

	private String message;
	private Button button;
	
	public Play(int id) 
	{
		super(id);
	}

	@Override
	public void init(GamePanel game) 
	{
		message = "Initial Message";
		button = new Button(100, 150, 100, 50);
		
		//button.sprite = new SimpleSprite();
		button.getSprite().loadImage("/images/sprites/simple_button.png");
		button.getSprite().setNumColumns(1);
		button.getSprite().setTotalFrames(4);
		button.getSprite().configureDimensions();
	}

	@Override
	public void update(GamePanel game) 
	{
		message = "Mouse Position: (" + game.getMouse().getX() + "," + game.getMouse().getY() + ")";
		
		button.readMouse(game.getMouse());

		
		switch(button.getState())
		{
			case NORMAL:
				break;
			case HOVER:
				break;
			case PRESSED:
				break;
			case ACTIVE:
				//button.state = ButtonState.NORMAL;
				//button.setState(ButtonState.NORMAL);
				game.enterState(State.MENU);
				break;
		}
	}

	@Override
	public void render(GamePanel game, Graphics g) 
	{
		g.setColor(Color.black);
		g.drawString("PLAY!!", 50, 50);
		g.drawString(message, 50, 100);
		button.draw(g);
	}

	@Override
	public void enter(GamePanel game) {}
	@Override
	public void exit(GamePanel game) 
	{
		button.setState(ButtonState.NORMAL);
	}
	
}
