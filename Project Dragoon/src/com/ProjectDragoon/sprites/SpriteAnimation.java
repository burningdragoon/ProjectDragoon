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
	//private long delay;
	private long[] frameDelay;
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
		//delay = 0;
		frameDelay = new long[length];
		
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
	
	//public long getDelay() {	return delay;	}
	//public void setDelay(long delay) {	this.delay = delay;	}
	//public void setDelay(int delay) { this.delay = (long)delay;	}
	
	public long getFrameDelay(int index) { return frameDelay[index]; }
	public void setFrameDelay(long time)
	{
		for(int i = 0; i < frameDelay.length; i++)
		{
			frameDelay[i] = time;
		}
	}
	public void setFrameDelay(int time)
	{
		setFrameDelay((long)time);
	}
	public void setFrameDelay(long[] times)
	{
		if(times.length < frameDelay.length)
		{
			System.err.println("Error: Less time values than frames");
			return;
		}
		
		for(int i = 0; i < frameDelay.length; i++)
		{
			frameDelay[i] = times[i];
		}
	}
	
	public void setFrameDelay(int[] times)
	{
		if(times.length < frameDelay.length)
		{
			System.err.println("Error: Less time values than frames");
			return;
		}
		
		for(int i = 0; i < frameDelay.length; i++)
		{
			frameDelay[i] = times[i];
		}
	}
	
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
		//anim.delay = delay;
		for(int i = 0; i < length; i++)
		{
			anim.frameDelay[i] = frameDelay[i];
		}
		
		return anim;
	}

	@Override
	public void restore() {
		// no effect.
	}
	
	/* -- End Utility -- */
}
