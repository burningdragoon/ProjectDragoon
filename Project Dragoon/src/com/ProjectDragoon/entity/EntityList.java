package com.ProjectDragoon.entity;

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
	
	public Entity remove(int index)
	{
		return entities.remove(index);
	}
	
	public boolean remove(Entity e)
	{
		return entities.remove(e);
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
