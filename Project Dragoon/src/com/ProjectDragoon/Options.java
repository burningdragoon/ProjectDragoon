package com.ProjectDragoon;


public class Options {

	public KeyValues keys;
	
	//public Font font;
	//public FontMetrics fontInfo;
	
	
	/*
	 * Constructors
	 */
	
	public Options()
	{
		keys = new KeyValues();
	}
	
	public Options(String filename)
	{
		// read in a file to set up the options
		setOptions(filename);
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	/* -- End Selectors and Mutators -- */
	
	/*
	 * Other Methods
	 */
	
	/**
	 * Set options based on the given file
	 * @param filename
	 */
	public void setOptions(String filename)
	{
		
	}
	
	public void saveOptions() {}
	
	
	
	
	
}
