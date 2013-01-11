package com.ProjectDragoon.sprites;

public class AnimationHandler {

	private SpriteAnimation anim;
	private int loops;
	private int loopCount;
	private boolean loopRegistered;
	private boolean animating;
	
	public AnimationHandler()
	{
		anim = null;
		loops = 0;
		loopCount = 0;
		animating = false;
	}
	
	// Selectors & mutators
	
	public void setAnimation(SpriteAnimation anim)
	{
		this.anim = anim;
	}
	public SpriteAnimation getAnimation()
	{
		return anim;
	}
	
	public void setLoops(int loops)
	{
		this.loops = loops;
	}
	public int getLoops()
	{
		return loops;
	}
	
	public void setAnimating(boolean value)
	{
		animating = value;
	}
	public boolean isAnimating()
	{
		return animating;
	}
	
	// End S&M
	
	// Other Methods
	
	public void reset(SpriteAnimation anim, int loops)
	{
		this.setAnimation(anim);
		this.setLoops(loops);
		this.setAnimating(true);
		loopCount = 0;
	}
	
	public void stopAnimating()
	{
		setAnimating(false);
	}
	
	public void update(int curFrame)
	{
		if(!loopRegistered)
		{
			if(curFrame >= anim.getLength()-1)
			{
				loopCount++;
				if(loopCount == loops)
				{
					stopAnimating();
				}
				else
				{
					loopRegistered = true;
				}
			}
		}
		else if(curFrame == 0)
		{
			loopRegistered = false;
		}
	}
}
