package com.ProjectDragoon.states;

import java.awt.Graphics;

import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.testpanels.StatesPanel;

public class Test extends GameState {

	public Test(int id) {
		super(id);
	}

	@Override
	public void init(GamePanel game) 
	{
		if(game instanceof StatesPanel)
		{
			//System.out.println(((StatesPanel) game).message);
			init((StatesPanel)game);
		}
	}
	public void init(StatesPanel game)
	{
		System.out.println(game.message);
	}

	@Override
	public void update(GamePanel game) 
	{
	}

	@Override
	public void render(GamePanel game, Graphics g) 
	{	
	}

	@Override
	public void enter(GamePanel game) {}

	@Override
	public void exit(GamePanel game) {}

}
