package com.ProjectDragoon.util.interfaces;

import java.io.Serializable;

public interface Utility extends Serializable{
	
	public abstract Object copy();
	//public abstract void save();
	//public abstract Object load();
	
	/**
	 * When loading an serialized object, fields that are not themselves serializable must be restored through this method.
	 * Also any time-based fields should be reset to match the current time, not the time of original creation.
	 */
	public abstract void restore();

}
