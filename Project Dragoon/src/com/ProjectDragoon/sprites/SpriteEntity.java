package com.ProjectDragoon.sprites;

import java.awt.Graphics;

import com.ProjectDragoon.Entity;
import com.ProjectDragoon.util.Vector;

/**
 * SpriteEntity class is a simple Entity-based wrapper for Sprites.
 * Version 0.1 : Just a Sprite and a position Vector. A Vector for Position was previously a part of the Sprite class, but that's dumb.
 * @author Alex
 *
 */
public class SpriteEntity extends Entity {

	private static final long serialVersionUID = 1L;	
	
	public Sprite sprite;
	private Vector position;
	
	public SpriteEntity(Sprite sprite, Vector position)
	{
		this.sprite = sprite.copy();
		this.position = new Vector(position);
	}
	
	/*
	 * Selectors & Mutators
	 */
	
	public Vector getPosition()	{	return position;	}
	public void setPosition(Vector v) {	position.set(v);	}
	public void setPosition(double x, double y) {	position.set(x, y, 0);	}
	public void setPosition(int x, int y) {	position.set(x, y, 0);	}
	
	public double getXPos() {	return position.getX();	}
	public void setXPos(double x) {	position.setX(x);	}
	public void setXPos(int x) {	position.setX(x);	}
	
	public double getYPos()	{	return position.getY();	}
	public void setYPos(double y) {	position.setY(y);	}
	public void setYPos(int y) {	position.setY(y);	}
	
	/* -- End S&M -- */
	
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void animate() {
		sprite.animate();
	}

	@Override
	public void draw(Graphics g) {
		sprite.draw(g, position);
	}

}
