package com.ProjectDragoon.entity;

import java.awt.Graphics;
import java.util.HashMap;

import com.ProjectDragoon.physics.HitBox;
import com.ProjectDragoon.physics.Rectangle;
import com.ProjectDragoon.sprites.Sprite;
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
	//private Vector position;
	//private Vector velocity;
	private HitBox hitBox;
	private HashMap<String, HitBox> hitboxes;
	
	public boolean forward;
	public boolean moving;
	public boolean onTile;
	
	private SpriteEntity()
	{
		super();
		//position = new Vector();
		//velocity = new Vector();
		hitBox = null;
		hitboxes = new HashMap<String, HitBox>();
		
		forward = true;
		moving = false;
		onTile = false;
	}
	
	public SpriteEntity(Sprite sprite, Vector position)
	{
		super(position);
		this.sprite = sprite.copy();
		//this.position = new Vector(position);
		//velocity = new Vector();
		hitBox = null;
		hitboxes = new HashMap<String, HitBox>();
		
		forward = true;
		moving = false;
		onTile = false;
	}
	
	public SpriteEntity(Sprite sprite)
	{
		this(sprite, new Vector());
	}
	
	/*
	 * Selectors & Mutators
	 */
	
	/*
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
	*/
	
	public int getWidth() { return sprite.getWidth(); }
	public int getHeight() { return sprite.getHeight(); }
	
	public Sprite getSprite() { return sprite; }
	
	public HitBox getHitBox() { return hitBox; }
	
	/* -- End S&M -- */
	
	/*
	 * HitBox Methods
	 */
	
	public void addHitBox(String name, HitBox hb)
	{
		hitboxes.put(name, hb);
	}
	
	/**
	 * Adds a new HitBox to the list. 
	 * If there is currently no main HitBox (held in the 'hitBox' variable), than the new HitBox will be set to it.
	 * @param name
	 * @param pos
	 * @param width
	 * @param height
	 */
	public void addNewHitBox(String name, Vector pos, int width, int height)
	{
		HitBox hb = new HitBox(this.position, pos, width, height);
		addHitBox(name, hb);
		
		if(hitBox == null)
		{
			setMainHitBox(name);
		}
	}
	
	public HitBox getHitBox(String name)
	{
		HitBox hb = null;
		if(hitboxes.containsKey(name))
			hb = hitboxes.get(name);
		return hb;
	}
	
	public void setMainHitBox(String name)
	{
		if(hitboxes.containsKey(name))
		{
			hitBox = hitboxes.get(name);
		}
		else
		{
			System.err.println("There is no HitBox stored under that name. No changes made.");
		}
	}
	
	/**
	 * Returns a Rectangle representing the broad collision detection bounding box.
	 * @return
	 */
	public Rectangle getBroadBoundingRectangle()
	{
		Rectangle rect = new Rectangle();
		rect.x = hitBox.left();
		rect.y = hitBox.top();
		rect.width = hitBox.getWidth() + (int)Math.abs(getXVel());
		rect.height = hitBox.getHeight() + (int)Math.abs(getYVel());
		
		// adjust x,y based on velocity
		if(getXVel() < 0)
		{
			rect.x += getXVel();
		}
		if(getYVel() < 0)
		{
			rect.y += getYVel();
		}
			
		return rect;
	}
	
	/* -- -- */
	
	
	public void move()
	{
		double vx = getXVel();
		double vy = getYVel();
		position.move(vx,  vy);
	}
	
	public void moveX()
	{
		position.moveX(getXVel());
	}
	
	public void moveX(int mx)
	{
		position.moveX(mx);
	}
	
	public void moveY()
	{
		position.moveY(getYVel());
	}
	
	public void moveY(int my)
	{
		position.moveY(my);
	}
	
	
	/*
	 * Sprite collision detection
	 */
	
	public boolean isAbove(SpriteEntity entity)
	{
		HitBox box2 = entity.getHitBox();
		return hitBox.top() < box2.top() && hitBox.bottom() < box2.top();
	}
	public boolean isBelow(SpriteEntity entity)
	{
		HitBox box2 = entity.getHitBox();
		return hitBox.top() > box2.bottom() && hitBox.bottom() > box2.bottom();
	}
	public boolean isToLeft(SpriteEntity entity)
	{
		HitBox box2 = entity.getHitBox();
		return hitBox.left() < box2.left() && hitBox.right() < box2.left();
	}
	public boolean isToRight(SpriteEntity entity)
	{
		HitBox box2 = entity.getHitBox();
		return hitBox.left() > box2.right() && hitBox.right() > box2.right();
	}
	
	/* -- End Sprite Collision Detection -- */
	
	/*
	 * Utility Interface Methods
	 */
	
	@Override
	public SpriteEntity copy() {
		
		SpriteEntity entity = new SpriteEntity();
		
		// Entity fields
		entity.setID(-1);
		entity.setName("");
		entity.setAlive(this.isAlive());
		entity.setVisible(this.isVisible());
		entity.setObjectType(0);
		entity.setLifetimer(this.getLifetime());
		
		// SpriteEntity fields
		entity.sprite = this.sprite.copy();
		entity.position = new Vector(this.position);
		entity.velocity = new Vector(this.velocity);
		entity.forward = this.forward;
		
		entity.hitBox = new HitBox(entity.getPosition(), this.hitBox.getPosition(), this.hitBox.getWidth(), this.hitBox.getHeight());
		
		for(String key : hitboxes.keySet())
		{
			HitBox hb = hitboxes.get(key);
			HitBox newHb = new HitBox(entity.getPosition(), hb.getPosition(), hb.getWidth(), hb.getHeight());
			entity.addHitBox(key, newHb);
		}
		
		
		return entity;
	}
	
	@Override
	public void restore()
	{
		super.restore();
		sprite.restore();
	}
	
	/* -- End of Utility Interface -- */

	@Override
	public void update() {
		move();
	}

	@Override
	public void animate() {
		sprite.animate();
	}

	/*
	 * Drawing methods
	 */
	
	@Override
	public void draw(Graphics g) {
		sprite.draw(g, position, forward);
		//collisionMap.draw(g);
	}
	
	public void draw(Graphics g, Camera camera)
	{
		//sprite.draw(g, (int)position.getX() - camera.x, (int)position.getY() - camera.y, forward);
		if(hitBox != null)
			hitBox.draw(g, camera);
		
		sprite.draw(g, (int)position.getX() - camera.x, (int)position.getY() - camera.y, forward);
		
		//draw broad aabb
		//Rectangle rect = getBroadBoundingRectangle();
		//g.setColor(Color.yellow);
		//g.drawRect(rect.x - camera.x, rect.y - camera.y, rect.width, rect.height);
	}
	
	/* -- End Drawing methods -- */

}
