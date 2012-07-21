package com.ProjectDragoon.util.data;

import java.util.ArrayList;


/**
 * The BulkResourceLoader class is used to created a bunch of files manually.
 * @author Alex
 *
 */
public class BulkResourceLoader {
	
	private ResourceManager resources = new ResourceManager();
	
	public BulkResourceLoader() {}
	
	public void loadSprites(ArrayList<String> files)
	{
		for(String filename : files)
		{
			resources.createSprites(filename);
		}
	}
	
	public static void main(String[] args)
	{		
		BulkResourceLoader loader = new BulkResourceLoader();
		
		ArrayList<String> sprites = new ArrayList<String>();
		sprites.add("res/sprites/xml/kain.xml");
		sprites.add("res/sprites/xml/arrows.xml");
		sprites.add("res/sprites/xml/badguys.xml");
		
		loader.loadSprites(sprites);
		
		
	}
}
