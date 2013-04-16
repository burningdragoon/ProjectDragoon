package com.ProjectDragoon.util.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.sprites.Sprite;

/**
 * DataSaver class provides save/load methods for all savable/serializable classes
 * @author Alex
 *
 */
public final class DataSaver {

	/*
	 * Extensions:
	 */
	private static final String SPRITE_EXT = "sprite";
	private static final String SPRITE_ENTITY_EXT = "spriteentity";
	
	/* 
	 * SPRITE 
	 * Extension: .sprite
	 */
	public static void Save(Sprite sprite, String location, String filename)
	{
		String savedFile = "";
		
		if(hasExtension(filename))
		{
			String ext = getExtension(filename);
			if(ext.equalsIgnoreCase(SPRITE_EXT))
				savedFile = filename;
			else
				savedFile = filename + "." + SPRITE_EXT;
		}
		else
		{
			savedFile = filename + "." + SPRITE_EXT;
		}
		
		savedFile = location + savedFile;
		
		try
		{
			FileOutputStream fos = new FileOutputStream(savedFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(sprite);
			oos.close();
			fos.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public static Sprite LoadSprite(String file)
	{
		Sprite sprite = null;
		
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			sprite = (Sprite)ois.readObject();
		}
		catch(IOException ioe)
		{
			System.err.println("Error loading file: " + file);
		}
		catch(ClassNotFoundException cnfe)
		{
			System.err.println("Class 'Sprite' Not Found.");
		}
		
		if(sprite != null)
		{
			// Texture won't save as is, so reload the spritesheet
			//sprite.reloadImage();
			sprite.restore();
		}
		return sprite;
	}
	
	/* END SPRITE */
	
	/*
	 * Sprite Entity
	 */
	
	public static void Save(SpriteEntity entity, String location, String filename) 
	{
		String savedFile = "";
		
		if(hasExtension(filename))
		{
			String ext = getExtension(filename);
			if(ext.equalsIgnoreCase(SPRITE_ENTITY_EXT))
				savedFile = filename;
			else
				savedFile = filename + "." + SPRITE_ENTITY_EXT;
		}
		else
		{
			savedFile = filename + "." + SPRITE_ENTITY_EXT;
		}
		
		savedFile = location + savedFile;
		
		try
		{
			FileOutputStream fos = new FileOutputStream(savedFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(entity);
			oos.close();
			fos.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public static SpriteEntity LoadSpriteEntity(String file)
	{
		SpriteEntity entity = null;
		
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			entity = (SpriteEntity)ois.readObject();
		}
		catch(IOException ioe)
		{
			System.err.println("Error loading file: " + file);
		}
		catch(ClassNotFoundException cnfe)
		{
			System.err.println("Class 'SpriteEntity' Not Found.");
		}
		
		// fix/load/restore any unserializable data.
		if(entity != null)
		{
			//entity.getSprite().reloadImage();
			entity.restore();
		}
		
		return entity;
	}
	
	/* -- END SPRITE ENTITY -- */
	
	/*
	 * Helper Methods
	 */
	
	private static boolean hasExtension(String filename)
	{
		//if there are no .'s in the file name, there are no extensions.
		return filename.split(".").length >= 2;
	}
	
	private static String getExtension(String filename)
	{
		String[] strings = filename.split(".");
		return strings[strings.length-1];
	}
	
	/* -- End Helper -- */
	
}
