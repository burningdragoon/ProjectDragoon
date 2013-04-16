package com.ProjectDragoon.states;

import java.awt.Graphics;

import com.ProjectDragoon.GamePanel;

public abstract class GameState {

	private int id;
	
	public GameState(int id)
	{
		this.id = id;
	}
	
	public int getId() { return id; }
	
	public String getName()
	{
		return State.values()[0].convert(id).toString();
	}
	
	public abstract void init(GamePanel game);
	public abstract void update(GamePanel game);
	public abstract void render(GamePanel game, Graphics g);
	public abstract void enter(GamePanel game);
	public abstract void exit(GamePanel game);

}
