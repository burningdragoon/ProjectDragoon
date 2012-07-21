package com.ProjectDragoon.unit;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.util.Vector;

public class BattleUnit extends Creature{

	public Sprite sprite;
	public Vector position;
	
	public int team;
	
	public BattleUnit(String name, int hp, int mp, int str, int end, int dex, int agi, int spd, int magic, int faith, int luck) {
		super(name, hp, mp, str, end, dex, agi, spd, magic, faith, luck);
		sprite = null;
		position = new Vector();
		team = -1;
	}
	
	/*
	 * Selectors & Mutators
	 */
	
	public Vector getPosition()	{	return position;	}
	public void setPosition(Vector v) {	position.set(v);	}
	public void setPosition(double x, double y) {	position.set(x, y, 0);	}
	public void setPosition(int x, int y) {	position.set(x, y, 0);	}
	
	public double getXPos() {	return position.getX();	}
	public void setXPos(double x) {	position.setX(x);	}
	public void setXPos(int x) {	position.setX(x);	}
	
	public double getYPos()	{	return position.getY();	}
	public void setYPos(double y) {	position.setY(y);	}
	public void setYPos(int y) {	position.setY(y);	}
	
	/* -- End S&M -- */
	
	public int attack(BattleUnit target)
	{
		int dmg = 0;
		
		int attVal = STRENGTH * Game.random.nextInt(255);
		int defVal = target.ENDURANCE * Game.random.nextInt(255);
		
		if(attVal > defVal)
			dmg = attVal - defVal;
		
		return dmg;
	}
	

}
