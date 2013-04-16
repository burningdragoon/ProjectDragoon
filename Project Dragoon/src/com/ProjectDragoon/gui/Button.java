package com.ProjectDragoon.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.sprites.SimpleSprite;
import com.ProjectDragoon.util.MouseInputHandler;

public class Button {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected ButtonState state;
	protected SimpleSprite sprite;
	
	/*
	 * Constructors
	 */
	
	public Button()
	{
		
	}
	
	public Button(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		state = ButtonState.NORMAL;
		sprite = new SimpleSprite();
	}
	
	/* -- End of Constructors -- */
	
	/*
	 * Selectors & Mutators
	 */
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
	public ButtonState getState() { return state; }
	public void setState(ButtonState state) { this.state = state; }
	public void setState(int id) { state = state.convert(id); }
	
	public SimpleSprite getSprite() { return sprite; }
	
	/* -- End of Selectors and Mutators -- */
	
	public void draw(Graphics g)
	{
		switch(state)
		{
			case NORMAL:
				sprite.setCurrentFrame(0);
				break;
			case HOVER:
				sprite.setCurrentFrame(1);
				break;
			case PRESSED:
				sprite.setCurrentFrame(2);
				break;
			case ACTIVE:
				sprite.setCurrentFrame(3);
				break;
		}
		sprite.draw(g, x, y);
	}
	
	public boolean isMouseOver(MouseInputHandler mouse)
	{
		int mouseX = mouse.getX();
		int mouseY = mouse.getY();
		
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}
	
	public void readMouse(MouseInputHandler mouse)
	{
		int mouseX = mouse.getX();
		int mouseY = mouse.getY();
		int pressedX = mouse.getPressedX();
		int pressedY = mouse.getPressedY();
		
		boolean mouseOver = mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
		boolean pressedOver = pressedX >=0 && pressedY >= 0 && pressedX > x && pressedX < x + width && pressedY > y && pressedY < y + height;
		
		switch(state)
		{
			case NORMAL:
				if(mouseOver)
				{
					if(!mouse.isPressed(0))
					{
						state = ButtonState.HOVER;
					}

				}
				break;
			case HOVER:
				if(!mouseOver)
				{
					if(mouse.isPressed(0) && pressedOver)
					{
						state = ButtonState.HOVER;
					}
					else
					{
						state = ButtonState.NORMAL;
					}
				}
				else if(mouse.isPressed(0) && pressedOver)
				{
					state = ButtonState.PRESSED;
				}
				break;
			case PRESSED:
				if(!mouseOver)
				{
					state = ButtonState.HOVER;
				}
				else
				{
					if(!mouse.isPressed(0))
					{
						state = ButtonState.ACTIVE;
					}
				}
				break;
			case ACTIVE:
				break;
		}
		
	}
	
}
