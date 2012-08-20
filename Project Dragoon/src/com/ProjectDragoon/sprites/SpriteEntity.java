package com.ProjectDragoon.sprites;

import java.awt.Graphics;

import com.ProjectDragoon.Entity;
import com.ProjectDragoon.physics.CollisionMap;
import com.ProjectDragoon.util.Camera;
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
	private Vector velocity;
	private CollisionMap collisionMap;
	
	public boolean forward;
	
	public int hotspotX;
	
	public SpriteEntity(Sprite sprite, Vector position)
	{
		this.sprite = sprite.copy();
		this.position = new Vector(position);
		velocity = new Vector();
		collisionMap = new CollisionMap(this.position);
		
		forward = true;
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
	
	public Vector getVelocity() {	return velocity;	}
	public void setVelocity(Vector v) { velocity.set(v);	}
	public void setVelocity(double x, double y) { velocity.set(x, y, 0);	}
	public void setVelocity(int x, int y) { velocity.set(x, y, 0);	}
	
	public double getXVel() { return velocity.getX();	}
	public void setXVel(double x) { velocity.setX(x);	}
	public void setXVel(int x) { velocity.setX(x);	}
	
	public double getYVel() { return velocity.getY();	}
	public void setYVel(double y) { velocity.setY(y);	}
	public void setYVel(int y) { velocity.setY(y);	}
	
	public int getWidth() { return sprite.getWidth(); }
	public int getHeight() { return sprite.getHeight(); }
	
	public CollisionMap getCollisionMap() { return collisionMap; }
	
	/* -- End S&M -- */
	
	/**
	 * resetCollisionMap sets/resets the position of the collision points map to the position of the parent entity
	 */
	public void resetCollisionMap()
	{
		collisionMap.setPosition(this.position);
	}
	
	public void move()
	{
		double vx = getXVel();
		double vy = getYVel();
		position.move(vx,  vy);
	}
	
	public void moveX(int mx)
	{
		position.moveX(mx);
	}
	public void moveY(int my)
	{
		position.moveY(my);
	}
	
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		move();
	}

	@Override
	public void animate() {
		sprite.animate();
	}

	@Override
	public void draw(Graphics g) {
		sprite.draw(g, position, forward);
		//collisionMap.draw(g);
	}
	
	public void draw(Graphics g, Camera camera)
	{
		sprite.draw(g, (int)position.getX() - camera.x, (int)position.getY() - camera.y, forward);
		collisionMap.draw(g, camera);
	}

}
