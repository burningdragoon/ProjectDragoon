package com.ProjectDragoon.util.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
			if(ext.equals(SPRITE_EXT))
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
			sprite.reloadImage();
		}
		return sprite;
	}
	
	/* END SPRITE */
	
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
		String ext = "";
		
		return ext;
	}
	
	/* -- End Helper -- */
	
}
