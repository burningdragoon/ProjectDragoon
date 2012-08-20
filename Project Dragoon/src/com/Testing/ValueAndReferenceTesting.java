package com.Testing;

import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteEntity;
import com.ProjectDragoon.util.Camera;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.data.DataSaver;

public class ValueAndReferenceTesting {

	public static void tweakCameraStatic(Camera c)
	{
		c.setX(100);
	}
	public void tweakCamera(Camera c)
	{
		c.setX(200);
	}
	
	public static void tweakVectorStatic(Vector v)
	{
		v.set(1, 2, 3);
	}
	public void tweakVector(Vector v)
	{
		v.set(4,5,6);
	}
	
	public static void main(String[] args)
	{
		ValueAndReferenceTesting test = new ValueAndReferenceTesting();
		
		/*
		Camera camera = new Camera(200, 200);
		System.out.println(camera.x);
		
		tweakCameraStatic(camera);
		System.out.println(camera.x);
		
		test.tweakCamera(camera);
		System.out.println(camera.x);
		*/
		
		/*
		Vector v = new Vector();
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		
		tweakVectorStatic(v);
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		
		test.tweakVector(v);
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		*/
		
		/*
		Vector v = new Vector();
		Thing thing = new Thing(v);
		Thing thing2 = new Thing(thing.v);
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("(%d, %d, %d)\n", (int)thing.v.getX(), (int)thing.v.getY(), (int)thing.v.getZ());
		System.out.printf("(%d, %d, %d)\n\n", (int)thing2.v.getX(), (int)thing2.v.getY(), (int)thing2.v.getZ());
		tweakVectorStatic(v);
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("(%d, %d, %d)\n", (int)thing.v.getX(), (int)thing.v.getY(), (int)thing.v.getZ());
		System.out.printf("(%d, %d, %d)\n\n", (int)thing2.v.getX(), (int)thing2.v.getY(), (int)thing2.v.getZ());
		test.tweakVector(v);
		System.out.printf("(%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("(%d, %d, %d)\n", (int)thing.v.getX(), (int)thing.v.getY(), (int)thing.v.getZ());
		System.out.printf("(%d, %d, %d)\n\n", (int)thing2.v.getX(), (int)thing2.v.getY(), (int)thing2.v.getZ());
		*/
		
		/*
		Vector v = new Vector();
		Sprite sprite = DataSaver.LoadSprite("res/sprites/soldier.sprite");
		SpriteEntity se = new SpriteEntity(sprite, v);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		tweakVectorStatic(v);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		test.tweakVector(v);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		se.moveX(10);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		se.setPosition(45, 77);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		se.getCollisionMap().setPosition(v);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		tweakVectorStatic(v);
		System.out.printf("Vector: (%d, %d, %d)\n", (int)v.getX(), (int)v.getY(), (int)v.getZ());
		System.out.printf("Entity: (%d, %d, %d)\n", (int)se.getPosition().getX(), (int)se.getPosition().getY(), (int)se.getPosition().getZ());
		System.out.printf("ColMap: (%d, %d, %d)\n\n", (int)se.getCollisionMap().getPosition().getX(), (int)se.getCollisionMap().getPosition().getY(), (int)se.getCollisionMap().getPosition().getZ());
		*/
	}
	
}

class Thing
{
	public Vector v;
	
	public Thing(Vector v)
	{
		this.v = v;
	}
}
