package com.ProjectDragoon.util.data;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ProjectDragoon.physics.HitBox;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteAnimation;
import com.ProjectDragoon.sprites.SpriteEntity;
import com.ProjectDragoon.util.Vector;

public class ResourceManager {
	
	private final String SPRITE_PATH = "res/sprites/";
	private final String ENTITY_PATH = "res/entities/";
	
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	
	public ResourceManager() 
	{
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Document getXMLDoc(String filename)
	{
		Document doc = null;
		
		try
		{
			doc = dBuilder.parse(filename);
			doc.getDocumentElement().normalize();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public Sprite createSprites(String filename, boolean save)
	{
		Document xml = getXMLDoc(filename);
		
		NodeList resList = xml.getElementsByTagName("sprite");
		for(int res = 0; res < resList.getLength(); res++)
		{
			System.out.println("New Sprite.");
			
			Sprite sprite = new Sprite();
			
			Node spriteNode = resList.item(res);
			Element spriteElem = (Element)spriteNode;
			//name
			String spriteName = spriteElem.getElementsByTagName("name").item(0).getTextContent(); 
			
			//image
			String imagefile = spriteElem.getElementsByTagName("image-file").item(0).getTextContent();
			sprite.loadImage(imagefile);
			
			//columns
			int cols = Integer.parseInt(
					spriteElem.getElementsByTagName("columns").item(0).getTextContent()
					);
			sprite.setNumColumns(cols);
			
			//current frame
			int curFrame = Integer.parseInt(
					spriteElem.getElementsByTagName("current-frame").item(0).getTextContent()
					);
			sprite.setCurrentFrame(curFrame);
			
			//total frame
			int totalFrames = Integer.parseInt(
					spriteElem.getElementsByTagName("total-frames").item(0).getTextContent()
					);
			sprite.setTotalFrames(totalFrames);
			
			//dimension
			boolean autoConfig = Boolean.parseBoolean(
					((Element)spriteElem.getElementsByTagName("dimensions").item(0)).getAttribute("auto")
					);
			if(autoConfig)
			{
				sprite.configureDimensions();
			}
			else
			{
				int w = Integer.parseInt(
						((Element)spriteElem.getElementsByTagName("dimensions").item(0)).getAttribute("width")
						);
				int h = Integer.parseInt(
						((Element)spriteElem.getElementsByTagName("dimensions").item(0)).getAttribute("height")
						);
				sprite.setWidth(w);
				sprite.setHeight(h);
			}
			
			//animations
			Node animAttributes = spriteElem.getElementsByTagName("animations").item(0);
			Element animElement = (Element)animAttributes;
			
			// actual animations
			NodeList animations = ((Element)animElement.getElementsByTagName("animation-list").item(0)).getElementsByTagName("sprite-animation");
			for(int animIndex = 0; animIndex < animations.getLength(); animIndex++)
			{
				//name
				Element animation = (Element)animations.item(animIndex);
				String animName = animation.getElementsByTagName("name").item(0).getTextContent();
				
				//frame
				String[] stringFrames = animation.getElementsByTagName("frames").item(0).getTextContent().split(",");
				int[] frames = new int[stringFrames.length];
				for(int i = 0; i < frames.length; i++)
				{
					frames[i] = Integer.parseInt(stringFrames[i]);
				}
				
				//delay
				int delay = Integer.parseInt(
						animation.getElementsByTagName("delay").item(0).getTextContent()
						);
				
				SpriteAnimation spriteAnim = new SpriteAnimation(frames.length);
				spriteAnim.setFrames(frames);
				spriteAnim.setDelay(delay);
				
				sprite.AddAnimations(animName, spriteAnim);
			}
			
			// frame-timer
			long frametimer = Long.parseLong(
					animElement.getElementsByTagName("frame-timer").item(0).getTextContent()
					);
			sprite.setFrameTimer(frametimer);
			
			// direction
			int animDir = Integer.parseInt(
					animElement.getElementsByTagName("direction").item(0).getTextContent()
					);
			sprite.setAnimDirection(animDir);
			
			System.out.println("Sprite Name: " + spriteName);
			System.out.println(sprite.toString());
			
			if(save)
				DataSaver.Save(sprite, SPRITE_PATH, spriteName);
			else
				return sprite;
		}
		return null;
	}
	
	public SpriteEntity createSpriteEntities(String filename, boolean save)
	{
		Document xml = getXMLDoc(filename);
		
		NodeList resList = xml.getElementsByTagName("sprite-entity");
		for(int res = 0; res < resList.getLength(); res++)
		{
			System.out.println("New Sprite-Entity.");
			SpriteEntity entity;
			Sprite sprite;
			
			Node entityNode = resList.item(res);
			Element entityElem = (Element)entityNode;
			
			//sprite
			String spritefile = entityElem.getElementsByTagName("sprite").item(0).getTextContent();
			String spritefiletype = entityElem.getElementsByTagName("sprite").item(0).getAttributes().getNamedItem("type").getTextContent();
			if(spritefiletype.equals("xml"))
				sprite = this.createSprites(SPRITE_PATH + "xml\\" + spritefile, false);
			else if(spritefiletype.equals("sprite"))
				sprite = DataSaver.LoadSprite(SPRITE_PATH + spritefile);
			else
				sprite = new Sprite();
			
			entity = new SpriteEntity(sprite);
			
			//id
			String id = entityElem.getElementsByTagName("id").item(0).getTextContent();
			
			//name
			String name = entityElem.getElementsByTagName("name").item(0).getTextContent();
			entity.setName(name);
			
			//object type
			String type = entityElem.getElementsByTagName("object-type").item(0).getTextContent();
			
			//lifetime
			String lifetime = entityElem.getElementsByTagName("lifetime").item(0).getTextContent();
			try{
				entity.setLifetimer(Integer.parseInt(lifetime));
			}
			catch(Exception e)
			{
				entity.setLifetimer(0);
			}
			
			
			//hitboxes
			NodeList aabbList = ((Element)(entityElem.getElementsByTagName("hitboxes").item(0))).getElementsByTagName("hitbox");
			for(int aabb = 0; aabb < aabbList.getLength(); aabb++)
			{
				Element aabbElem = (Element)aabbList.item(aabb);
				
				String aabbName = aabbElem.getElementsByTagName("name").item(0).getTextContent();
				
				String xStr = aabbElem.getElementsByTagName("x").item(0).getTextContent();
				String yStr = aabbElem.getElementsByTagName("y").item(0).getTextContent();
				String zStr = aabbElem.getElementsByTagName("z").item(0).getTextContent();
				
				String wStr = aabbElem.getElementsByTagName("width").item(0).getTextContent();
				String hStr = aabbElem.getElementsByTagName("height").item(0).getTextContent();
				
				int xPos = Integer.parseInt(xStr);
				int yPos = Integer.parseInt(yStr);
				int zPos = Integer.parseInt(zStr);
				Vector boxPos = new Vector(xPos, yPos, zPos);
				
				int width = Integer.parseInt(wStr);
				int height = Integer.parseInt(hStr);
				
				entity.addNewHitBox(aabbName, boxPos, width, height);
			}
			
			
			// print
			//System.out.printf("id: %s\nname: %s\ntype: %s\nlifetime: %s\nsprite file: %s\nsprite filetype: %s\n", id,name,type,lifetime,spritefile,spritefiletype);
			
			if(save)
				DataSaver.Save(entity, ENTITY_PATH, name);
			else
				return entity;
		}
		
		return null;
	}

}
