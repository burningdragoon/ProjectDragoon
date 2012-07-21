package com.ProjectDragoon.unit;

import java.util.Random;

public class Creature {

	public String NAME;
	
	/* -- Main Stats -- */
	public int HEALTH; 		// Reflects how much damage you can take
	public int MANA; 		// Reflects how many spells you can cast
	public int STRENGTH;	// Effects how much damage you can inflict
	public int ENDURANCE;	// Effects how much damage you can resist
	public int DEXTERITY;	// Effects chance of landing an attack
	public int AGILITY;		// Effects chance of evading an attack
	public int SPEED;		// Effects combat speed
	public int MAGIC;		// Effects power of dark magic spells
	public int FAITH;		// Effects power of light magic spells
	public int LUCK;		// Effects... things
	/* ---------------- */
	
	public int curHP;
	public int curMP;
	
	public boolean alive;
	
	public Creature(String name,int hp, int mp, int str, int end, int dex, int agi, int spd, int magic, int faith, int luck)
	{
		NAME = name;
		
		HEALTH    = hp;
		MANA      = mp;
		STRENGTH  = str;
		ENDURANCE = end;
		DEXTERITY = dex;
		AGILITY   = agi;
		SPEED 	  = spd;
		MAGIC     = magic;
		FAITH     = faith;
		LUCK      = luck;
		
		curHP = HEALTH;
		curMP = MANA;
		
		alive = true;
	}
	
	public int Attack(Creature target)
	{
		Random random = new Random();
		int attack = STRENGTH / 2;
		int def = target.ENDURANCE;
		
		int damage = Math.max(0, random.nextInt(attack+1) + attack) - def;
		
		System.out.println(NAME + " does " + damage + " damage to " + target.NAME + "!");
		
		target.curHP -= damage;
		
		/*
		if (target.curHP <= 0)
		{
			target.curHP = 0;
			target.alive = false;
		}
		*/
		
		return damage;
	}
	
	public void checkAlive()
	{
		if (curHP <= 0)
		{
			curHP = 0;
			alive = false;
		}
	}
	
	public void reset()
	{
		curHP = HEALTH;
		curMP = MANA;
		alive = true;
	}
}
