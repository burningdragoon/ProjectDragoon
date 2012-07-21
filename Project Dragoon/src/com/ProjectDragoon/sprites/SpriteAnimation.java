package com.ProjectDragoon.sprites;

import com.ProjectDragoon.util.interfaces.Utility;

/**
 * The Sprite Animation class is a simple way of holding 
 * sprite animations more complicated than a simple loop 
 * from the first frame to the last frame
 * 
 * @author Alex
 *
 */
public class SpriteAnimation implements Utility {

	private static final long serialVersionUID = 1L;

	private int length;
	private int[] frames;
	private long delay;
	private boolean hasMotion;
	
	/*
	 * Constructors
	 */
	
	public SpriteAnimation()
	{
		this(1);
	}
	
	public SpriteAnimation(int length)
	{
		// There has to be at least one frame
		if(length < 1)
			length = 1;
		this.length = length;
		frames = new int[length];
		delay = 0;
		
		// if there is only one frame in the animation, then there is no motion.
		hasMotion = (length != 1);
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	public int getLength() {	return length;	}
	public void setLength(int length) {	this.length = length;	}
	
	public int[] getFrames() {	return frames;	}
	public void setFrames(int[] frames)	
	{ 
		this.frames = frames;
		length = frames.length;
	}
	
	public int getFrame(int index) {	return frames[index];	}
	
	public long getDelay() {	return delay;	}
	public void setDelay(long delay) {	this.delay = delay;	}
	public void setDelay(int delay) { this.delay = (long)delay;	}
	
	public boolean hasMotion() {	return hasMotion;	}
	public void setHasMotion(boolean value) { hasMotion = value;	}
	
	/* -- End S&M -- */
	
	/*
	 * Utility Methods
	 */
	
	@Override
	public SpriteAnimation copy()
	{
		SpriteAnimation anim = new SpriteAnimation(length);
		
		for(int i = 0; i < length; i++)
		{
			anim.frames[i] = frames[i];
		}
		anim.delay = delay;
		
		return anim;
	}
	
	/* -- End Utility -- */
}
