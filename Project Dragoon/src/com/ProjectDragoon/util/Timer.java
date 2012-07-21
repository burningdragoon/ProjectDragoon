package com.ProjectDragoon.util;

import com.ProjectDragoon.MyMath;
import com.ProjectDragoon.util.interfaces.Utility;

public class Timer implements Utility{

	private static final long serialVersionUID = 1L;
	
	private long timerStart;
	private long stopwatchStart;
	private boolean active;
	
	public Timer()
	{
		timerStart = MyMath.nanoToMilli(System.nanoTime());
		active = true;
		reset();
	}

	public long getTimer()
	{
		return (long) MyMath.nanoToMilli(System.nanoTime());
	}
	
	public long getStartTimeMillis()
	{
		return (long) (MyMath.nanoToMilli(System.nanoTime()) - timerStart);
	}
	
	public boolean isActive()
	{
		return active;
	}
	public void setActive(boolean value)
	{
		active = value;
	}
	
	/**
	 * Sets the Timer to active and resets the start time
	 */
	public void activate()
	{
		setActive(true);
		reset();
	}
	
	public void deactivate()
	{
		setActive(false);
	}
	
	public void sleep (int ms)
	{
		long start = getTimer();
		while(start + ms > getTimer())
		{
			//do nothing
		}
	}
	
	public void reset()
	{
		stopwatchStart = getTimer();
	}
	
	public boolean stopwatch(int ms)
	{
		if(MyMath.nanoToMilli(System.nanoTime()) > stopwatchStart + ms)
		{
			reset();
			return true;
		}
		else
			return false;
	}

	/*
	 * Utility Methods
	 */
	
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* -- End Utility -- */

}