package com.ProjectDragoon.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.ProjectDragoon.MyMath;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.interfaces.Utility;

public class Sprite extends SimpleSprite { //implements Utility {

	private static final long serialVersionUID = 1L;
	
	// Spritesheet Image variables
	//private transient Texture spritesheet;
	//private String spritesheetFile;
	//private int width, height;
	//private int columns;
	//private int curFrame, totalFrames;
	//private boolean imageLoaded;
	
	// Animation variables
	private HashMap<String, SpriteAnimation> animations;
	private SpriteAnimation currentAnim;
	private String currentAnimName;
	private int currentAnimFrame;
	private long framestart, frametimer;
	private int animDir;
	/*
	 * the difference between curFrame and currentAnimFrame:
	 * curFrame: the current specific frame of the spritesheet
	 * currentAnimFrame: the index of the animation's current frame
	 * 
	 * the curFrame is the value of the currentFrameIndex in the animation's "frames" array.
	 */
	
	/*
	 * Constructors
	 */
	public Sprite()
	{	
		//spritesheet = new Texture();
		//spritesheetFile = "";
		//width = 1;
		//height = 1;
		//columns = 1;
		//curFrame = 0;
		//totalFrames = 1;
		//imageLoaded = false;
		super();
		
		animations = new HashMap<String, SpriteAnimation>();
		currentAnim = null;
		currentAnimName = "";
		currentAnimFrame = 0;
		framestart = 0L;
		frametimer = 0L;
		animDir = 1;
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	// Screen position
	/*
	 * Note: Position & Velocity should be held within a different class to keep sprite code neater
	 * 
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
	
	
	// Movement Velocity
	public Vector getVelocity()	{	return velocity;	}
	public void setVelocity(Vector v) {	velocity.set(v);	}
	public void setVelocity(double x, double y) {	velocity.set(x, y, 0);	}
	public void setVelocity(int x, int y) {	velocity.set(x, y, 0);	}
	
	public double getVelocityX() {	return velocity.getX();	}
	public void setVelocityX(double x) {	velocity.setX(x);	}
	public void setVelocityX(int x) {	velocity.setX(x);	}
	
	public double getVelocityY() {	return velocity.getY();	}
	public void setVelocityY(double y) {	velocity.setY(y);	}
	public void setVelocityY(int y) {	velocity.setY(y);	}
	*/
	
	// Spritesheet image
	/*
	public Texture getSpritesheet() {	return spritesheet;	}
	public void setSpritesheet(Texture sheet) {	spritesheet = sheet;	}
	public void setSpritesheet(BufferedImage sheet) {	spritesheet.setImage(sheet);	}
	
	public int getWidth() {	return width;	}
	public void setWidth(int w) {	width = w;	}
	
	public int getHeight() {	return height;	}
	public void setHeight(int h) {	height = h;	}
	
	public int getNumColumns() {	return columns;	}
	public void setNumColumns(int cols) {	columns = cols; }
	
	public int getCurrentFrame() {	return curFrame;	}
	public void setCurrentFrame(int frame) {	curFrame = frame;	}
	
	public int getTotalFrames() {	return totalFrames;	}
	public void setTotalFrames(int frames) {	totalFrames = frames;	}
	
	public boolean isImageLoaded() {	return imageLoaded;	}
	public void setImageLoaded(boolean value) {	imageLoaded = value;	}
	*/
	
	// Animation
	public HashMap<String, SpriteAnimation> getAnimations() {	return animations;	}
	
	public SpriteAnimation getCurrentAnimation() {	return currentAnim;	}
	//public void setCurrentAnimation(String name) {}
	
	public String getCurrentAnimationName() {	return currentAnimName;	}
	//public void setCurrentAnimationName() {}
	
	public int getCurrentAnimFrame() {	return currentAnimFrame;	}
	
	public long getFrameTimer() {	return frametimer;	}
	public void setFrameTimer(long time) { frametimer = time;	}
	
	public int getAnimDirection() {	return animDir;	}
	public void setAnimDirection(int dir) {	animDir = dir;	}
	
	/* -- End S&M -- */
	
	/*
	 * Sprite configuring methods
	 */
	/*
	public boolean loadImage(String filename)
	{
		spritesheetFile = filename;
		imageLoaded = spritesheet.loadImage(filename);
		return imageLoaded;
	}
	
	public boolean reloadImage()
	{
		//System.out.println(spritesheetFile);
		spritesheet = new Texture();
		return spritesheet.loadImage(spritesheetFile);
	}
	
	public void configureDimensions()
	{
		if (spritesheet == null)
			return;
		
		int imgWidth = spritesheet.getImage().getWidth();
		int imgHeight = spritesheet.getImage().getHeight();
		
		int rows = (int)Math.ceil((double)totalFrames / columns);
		
		width = imgWidth / columns;
		height = imgHeight / rows;
	}
	*/
	/* -- End Sprite config -- */
	
	/*
	 * Animation methods
	 */
	
	public void AddAnimations(String name, SpriteAnimation anim)
	{
		animations.put(name.toLowerCase(),  anim);
	}
	
	public void setCurrentAnimation(String name)
	{
		name = name.toLowerCase();
		if(currentAnimName.equals(name))
			return;
		if(!animations.containsKey(name))
		{
			System.err.println("Animation Does Not Exist: " + name);
			return;
		}
		currentAnimName = name;
		currentAnim = animations.get(name);
		curFrame = currentAnim.getFrame(0);
		currentAnimFrame = 0;
		framestart = MyMath.nanoToMilli(System.nanoTime());
		//frametimer = currentAnim.getDelay();
		frametimer = currentAnim.getFrameDelay(currentAnimFrame);
	}
	
	public void resetAnimation()
	{
		if(animations.size() <= 0)
		{
			curFrame = 0;
		}
		else
		{
			curFrame = currentAnim.getFrame(0);
			currentAnimFrame = 0;
		}
		
		framestart = MyMath.nanoToMilli(System.nanoTime());
	}

	public void animate() 
	{
		// if there are any custom animations in the hash/list
		if(animations.size() > 0 && currentAnim != null)
		{
			// if the current animation is a single-frame "animation" just return without updated
			if(!currentAnim.hasMotion())
				return;
			
			// update the frame based on animation direction and delay
			if(frametimer > 0)
			{
				long time = System.nanoTime();
				if(MyMath.nanoToMilli(time) > (framestart + frametimer))
				{
					currentAnimFrame += animDir;
					
					// keep the frame within bounds
					if(currentAnimFrame < 0)
						currentAnimFrame = currentAnim.getLength()-1;
					else if(currentAnimFrame > currentAnim.getLength()-1)
						currentAnimFrame = 0;
					
					//reset animation timer
					framestart = MyMath.nanoToMilli(System.nanoTime());
					frametimer = currentAnim.getFrameDelay(currentAnimFrame);
				}
			}
			else
			{
				// no animation timer -- update as fast as possible
				currentAnimFrame += animDir;
				if(currentAnimFrame < 0)
					currentAnimFrame = currentAnim.getLength()-1;
				else if(currentAnimFrame > currentAnim.getLength()-1)
					currentAnimFrame = 0;	
			}
			
			curFrame = currentAnim.getFrame(currentAnimFrame);
		}
		else
		{
			// if there are no custom animations set up, just loop through every frame
			if(frametimer > 0)
			{
				long time = System.nanoTime();
				if(MyMath.nanoToMilli(time) > (framestart + frametimer))
				{
					//reset animation timer
					framestart = MyMath.nanoToMilli(System.nanoTime());
					curFrame += animDir;
					
					// keep frame within bounds
					if(curFrame < 0)
						curFrame = totalFrames -1;
					else if(curFrame > totalFrames -1)
						curFrame = 0;
				}
			}
			else
			{
				curFrame += animDir;
				if(curFrame < 0)
					curFrame = totalFrames - 1;
				else if(curFrame > totalFrames - 1)
					curFrame = 0;
			}
		}
	}
	
	/* -- End Animation -- */

	/*
	 * Drawing Methods
	 */
	/*
	public void draw(Graphics g, int xPos, int yPos, boolean forward)
	{
		// get the x,y for the current frame
		int fx = (curFrame % columns) * width;
		int fy = (curFrame / columns) * height;
		
		int desX = xPos;
		int desY = yPos;
		
		spritesheet.draw(g, desX, desY, fx, fy, width, height, forward);
		
		//g.setColor(Color.red);
		//g.drawString(""+curFrame+"/"+(totalFrames-1), desX, desY);
		//g.setColor(Color.black);
	}
	
	public void draw(Graphics g, int xPos, int yPos) 
	{
		/*
		// get the x,y for the current frame
		int fx = (curFrame % columns) * width;
		int fy = (curFrame / columns) * height;
		
		//int desX = (int)position.getX();
		//int desY = (int)position.getY();
		int desX = xPos;
		int desY = yPos;
		
		spritesheet.draw(g, desX, desY, fx, fy, width, height);
		g.setColor(Color.red);
		g.drawString(""+curFrame+"/"+(totalFrames-1), desX, desY);
		g.setColor(Color.black);
		* /
		draw(g, xPos, yPos, true);
	}
	
	public void draw(Graphics g, Vector position)
	{
		draw(g, (int)position.getX(), (int)position.getY());
	}
	
	public void draw(Graphics g, Vector position, boolean forward)
	{
		draw(g, (int)position.getX(), (int)position.getY(), forward);
	}
	
	public void draw(Graphics g, double xPos, double yPos)
	{
		draw(g, (int)xPos, (int)yPos);
	}
	*/
	/* -- End Drawing -- */
	
	/*
	 * Utility Methods
	 */
	
	@Override
	public Sprite copy()
	{
		Sprite s = new Sprite();
		
		s.spritesheet = spritesheet.copy();
		s.spritesheetFile = spritesheetFile;
		s.imageLoaded = imageLoaded;
		
		s.width = width;
		s.height = height;
		s.columns = columns;
		s.curFrame = curFrame;
		s.totalFrames = totalFrames;

		
		
		s.animations = new HashMap<String, SpriteAnimation>();
		for(String name : animations.keySet())
		{
			SpriteAnimation anim = animations.get(name).copy();
			s.animations.put(name, anim);
		}
		s.currentAnimName = currentAnimName;
		s.currentAnim = s.animations.get(s.currentAnimName);
		s.currentAnimFrame = currentAnimFrame;
		s.framestart = framestart;
		s.frametimer = frametimer;
		s.animDir = animDir;
		
		return s;
	}
	
	/*
	@Override
	public void restore() 
	{
		reloadImage();
	}
	*/
	
	/* -- End Utility -- */
	
	/*
	 * Saving Methods
	 */
	
	/* -- End Saving -- */
	
	@Override
	public String toString()
	{
		String result = "";
		result += "Spritesheet: " + spritesheetFile + "\n";
		result += "Image Loaded: " + imageLoaded + "\n";
		result += "Width: " + width + "\n";
		result += "Height: " + height + "\n";
		result += "Number of Columns: " + columns + "\n";
		result += "Total Frames: " + totalFrames + "\n";
		result += "Current Frame: " + curFrame + "\n";
		result += "Current Animation: " + currentAnimName + "\n";
		result += "Current Animation Frame: " + currentAnimFrame + "\n";
		result += "Animation Direction: " + animDir + "\n";
		result += "Number of Animations: " + animations.size() + "\n";
		for(String name : animations.keySet())
		{
			result += "  ";
			result += name + ": ";
			int[] frames = animations.get(name).getFrames();
			for(int i = 0; i < frames.length; i++)
			{
				result += frames[i] + "";
				if(i < frames.length-1)
					result += ", ";
			}
			result += "\n";
		}
		return result;
	}
}
