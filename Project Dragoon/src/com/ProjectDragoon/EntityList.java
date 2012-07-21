package com.ProjectDragoon;

import java.util.concurrent.CopyOnWriteArrayList;

public class EntityList {

	private CopyOnWriteArrayList<Entity> entities;
	
	public EntityList()
	{
		entities = new CopyOnWriteArrayList<Entity>();
	}
	
	public CopyOnWriteArrayList<Entity> list()
	{
		return entities;
	}
	
	public boolean add(Entity entity)
	{
		return entities.add(entity);
	}
	
	public void remove(int index)
	{
		entities.remove(index);
	}
	
	public void remove(Entity e)
	{
		entities.remove(e);
	}
	
	public int size()
	{
		return entities.size();
	}
	
	public boolean isEmpty()
	{
		return entities.isEmpty();
	}
	
	public void clear()
	{
		entities.clear();
	}
}
