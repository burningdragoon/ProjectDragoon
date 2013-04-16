package com.ProjectDragoon.states;

import java.util.ArrayList;

/**
 * 
 * @author Alex
 *
 */
public class GameStateList {

	private ArrayList<GameState> list;
	
	public GameStateList()
	{
		list = new ArrayList<GameState>();
	}
	
	public GameState getState(int id)
	{
		return list.get(id);
	}
	
	public void add(int id, GameState state)
	{
		list.add(id, state);
	}
}
