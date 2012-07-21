package com.ProjectDragoon.util;


/**
 * Enhanced timer for keystrokes with 3 tiers of built in delay
 * Tier 1: no delay for when the Timer hasn't been used yet
 * Tier 2: a longer delay time for after the first key register and before the second
 * Tier 3: a shorted delay for after the second key register and all following regsters until released.
 * @author Alex
 *
 */
public class KeyTimer extends Timer{
	
	private static final long serialVersionUID = 1L;
	
	private int curDelay;
	private int longDelay;
	private int shortDelay;
	
	public KeyTimer()
	{
		 this(0, 0);
	}

	public KeyTimer(int longDelay, int shortDelay)
	{
		super();
		curDelay = 0;
		this.longDelay = longDelay;
		this.shortDelay = shortDelay;
	}
	
	public void resetDelay()
	{
		curDelay = 0;
	}
	
	@Override
	public void activate()
	{
		super.activate();
		resetDelay();
	}
	
	@Override
	public void deactivate()
	{
		super.deactivate();
		resetDelay();
	}
	
	public boolean stopwatch()
	{
		// first check if the delay has been reached
		boolean timeReached = stopwatch(curDelay);
		
		// if so, update the current delay time accordingly
		if (timeReached)
		{
			if(curDelay == 0)
				curDelay = longDelay;
			else if(curDelay == longDelay)
				curDelay = shortDelay;
		}
		
		return timeReached;
	}
}
