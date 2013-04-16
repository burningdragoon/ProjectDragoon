package com.ProjectDragoon.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ProjectDragoon.GameComponent;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.entity.Entity;
import com.ProjectDragoon.entity.NumberSprite;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.testpanels.SimpleAnimatedBattlePanel;
import com.ProjectDragoon.unit.BattleUnit;
import com.ProjectDragoon.util.KeyTimer;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.data.DataSaver;

public class SimpleAnimBattle extends GameComponent {

	private static final long serialVersionUID = 1L;

	private final int GOOD = 0;
	private final int BAD = 1;
	
	State state;

	CopyOnWriteArrayList<Entity> battleEntities;
	
	Sprite sprite;
	
	int animLoops;
	int animLoopCount;
	boolean loopRegistered;
	boolean animating;
	
	//ArrayList<BattleUnit> guys;
	BattleUnit[] guys;
	BattleUnit[] badguys;
	
	Sprite badguy;
	
	int curTurn;
	
	// 0 - good guys
	// 1 - bad guys
	int curTeam;
	
	//Time between the end of the attack (animation) and the damage state
	Timer attackTimer;
	int attackTime;
	int attackAnimLoop;
	
	Timer damageTimer;
	int damageTime;
	
	Timer waitTimer;
	int waitTime;
	
	Timer badTimer;
	int badTime;
	
	KeyTimer keyTimer;

	Texture numbers;
	//NumberSprite number;
	
	Random random = new Random();
	
	public SimpleAnimBattle()
	{
		state = State.INIT;
		
		battleEntities = new CopyOnWriteArrayList<Entity>(); 
		
		guys = new BattleUnit[5];
		badguys = new BattleUnit[1];
		
		curTurn = 0;
		curTeam = GOOD;
		
		animLoops = 0;
		animLoopCount = 0;
		loopRegistered = false;
		animating = true;
		
		attackTimer = new Timer();
		attackTimer.setActive(false);
		attackTime = 500;
		attackAnimLoop = 2;
		
		damageTimer = new Timer();
		damageTimer.setActive(false);
		damageTime = 500;
		
		waitTimer = new Timer();
		waitTimer.setActive(false);
		waitTime = 1000;
		
		badTimer = new Timer();
		badTimer.setActive(false);
		badTime = 2000;
		
		keyTimer = new KeyTimer(500, 80);
		
		numbers = new Texture("/images/sprites/numbers.png");
		//number = new NumberSprite(numbers, 10);
	}
	
	public void initBattle()
	{
		//sprite = new Sprite();
		sprite = DataSaver.LoadSprite("res/sprites/kain.sprite");

		for(int i = 0; i < guys.length; i++)
		{
			BattleUnit guy = new BattleUnit("Kain" + (i+1), 100, 50, 10, 5, 0, 0, 0, 0, 0, 0);
			guy.sprite = sprite.copy();
			guys[i] = guy;
		}
		
		badguy = DataSaver.LoadSprite("res/sprites/badguy.sprite");
		BattleUnit guy = new BattleUnit("Soldier", 100, 50, 10, 5, 0, 0, 0, 0, 0, 0);
		guy.setPosition(200, 250);
		guy.sprite = badguy.copy();
		badguys[0] = guy;
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return ((GamePanel)this.getParent()).getKeyboard().keyPressed(keyCode);
	}
	
	public KeyValues getKeys()
	{
		return ((SimpleAnimatedBattlePanel)this.getParent()).getKeys();

	}
	
	@Override
	public void update() {
		//if(animating)
		//	sprite.animate();
		
		for(int i = 0; i < guys.length; i++)
		{
			if(i != curTurn || animating)
				guys[i].sprite.animate();
		}
		
		boolean actionKey = isKeyPressed(getKeys().ActionKey());
		
		switch(state)
		{
			case INIT:
			{
				int x = 400;
				int y = 150;
				int dy = 50;
				for(int i = 0; i < guys.length; i++)
				{
					guys[i].setPosition(x, y + dy*i);
				}
				curTurn = -1;
				state = State.WAITING;
			}
			break;
				
			case ATTACKING:
				
				if(curTeam == GOOD)
				{
					if(!attackTimer.isActive())
					{
						if(animLoopCount == animLoops)
						{
							animating = false;
							attackTimer.activate();
							animLoopCount = 0;
							/*
							int x = (int)(badguy.getXPos() + badguy.getWidth()/2);
							int y = (int)(badguy.getYPos() + badguy.getHeight());
							NumberSprite dmg = new NumberSprite(numbers, random.nextInt(1000));
							dmg.setPosition(x, y);
							dmg.setLifetimer(1000);
							battleEntities.add(dmg);
							*/
						}
						else
						{
							int curFrame = guys[curTurn].sprite.getCurrentAnimFrame();
							int animLength = guys[curTurn].sprite.getCurrentAnimation().getLength();
							
							if(curFrame == animLength - 1)
							{
								if(!loopRegistered)
								{
									animLoopCount++;
									loopRegistered = true;
								}
							}
							else
							{
								loopRegistered = false;
							}
						}
					}
					else if(attackTimer.stopwatch(attackTime))
					{
						//*
						int x = (int)(badguys[0].getXPos() + badguys[0].sprite.getWidth()/2);
						int y = (int)(badguys[0].getYPos() + badguys[0].sprite.getHeight());
						NumberSprite dmg = new NumberSprite(numbers, random.nextInt(1000));
						dmg.setPosition(x, y);
						dmg.setLifetimer(1000);
						battleEntities.add(dmg);
						//*/
						
						state = State.DAMAGE;
						animating = true;
						attackTimer.deactivate();
						//sprite.setCurrentAnimation("Rest");
						guys[curTurn].sprite.setCurrentAnimation("Rest");
					}
				}
				else
				{
					if(!attackTimer.isActive())
					{
						attackTimer.activate();
					}
					else if(attackTimer.stopwatch(attackTime))
					{
						int target = random.nextInt(5);
						int x = (int)(guys[target].getXPos() + guys[target].sprite.getWidth()/2);
						int y = (int)(guys[target].getYPos() + guys[target].sprite.getHeight()/2);
						NumberSprite dmg = new NumberSprite(numbers, random.nextInt(100));
						dmg.setPosition(x,y);
						dmg.setLifetimer(1000);
						battleEntities.add(dmg);
						
						attackTimer.deactivate();
						state = State.DAMAGE;
					}
				}
				break;
				
			case DAMAGE:
				/*
				if(!damageTimer.isActive())
				{
					damageTimer.activate();
				}
				else if(damageTimer.stopwatch(damageTime))
				{
					state = State.WAITING;
					damageTimer.deactivate();
				}
				*/
				//System.out.println("Entity size: " + battleEntities.size());
				if(battleEntities.isEmpty())
				{
					state = State.WAITING;
				}
				break;
				
			case WAITING:
				if(!waitTimer.isActive())
				{
					waitTimer.activate();
				}
				else if(waitTimer.stopwatch(waitTime))
				{
					waitTimer.deactivate();
					/*
					//int length = curTeam == GOOD ? guys.length : badguys.length;
					//update turn info.
					curTurn++;
					if(curTurn >= guys.length)
					{
						curTurn = 0;
					}
					guys[curTurn].sprite.setCurrentAnimation("Prep");
					*/
					curTurn++;
					int length = 0;
					switch(curTeam)
					{
						case GOOD:
							length = guys.length;
							if(curTurn >= length)
							{
								curTeam = BAD;
								curTurn = 0;
							}
							else
							{
								guys[curTurn].sprite.setCurrentAnimation("Prep");
							}
							break;
							
						case BAD:
							length = badguys.length;
							if(curTurn >= length)
							{
								curTeam = GOOD;
								curTurn = 0;
							}
							break;
					}
					
					state = State.TURN;
				}
				break;
				
			case TURN:
				
				switch(curTeam)
				{
					case GOOD:
						if(actionKey)
						{
							if(keyTimer.stopwatch())
							{
								//sprite.setCurrentAnimation("Attack");
								guys[curTurn].sprite.setCurrentAnimation("Attack");
								animLoops = attackAnimLoop;
								animLoopCount = 0;
								state = State.ATTACKING;
							}
						}
						else
						{
							keyTimer.resetDelay();
						}
						
						break;
						
					case BAD:
						if(!badTimer.isActive())
						{
							badTimer.activate();
						}
						else if(badTimer.stopwatch(badTime))
						{
							state = State.ATTACKING;
							badTimer.deactivate();
						}
						
						break;
				}
				
				/*
				if(actionKey)
				{
					if(keyTimer.stopwatch())
					{
						//sprite.setCurrentAnimation("Attack");
						guys[curTurn].sprite.setCurrentAnimation("Attack");
						animLoops = attackAnimLoop;
						animLoopCount = 0;
						state = State.ATTACKING;
					}
				}
				else
				{
					keyTimer.resetDelay();
				}
				*/
				
				break;
		}
		
		//update battle entities
		for(Entity e : battleEntities)
		{
			if(!e.isAlive())
			{
				battleEntities.remove(e);
			}
			else
			{
				if(e.lifetimeExpired())
				{
					e.setAlive(false);
				}
				else
				{
					e.update();
					e.animate();
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		
		//sprite.draw(g);
		//number.draw(g);
		
		boolean actionKey = isKeyPressed(getKeys().ActionKey());
		if(actionKey)
			g.drawString("Action Key!", 50, 50);
		
		String curState = "";
		
		badguy.draw(g, badguys[0].position);
		
		switch(state)
		{
			case INIT:
				//curState = "Init";
				break;
				
			case ATTACKING:
				//curState = "Attack";
				break;
				
			case DAMAGE:
			{
				String msg = guys[curTurn].NAME + " doing damage!";
				g.drawString(msg, 400, 80);
				
				//g.setColor(Color.white);
				
				//String dmg = "999";
				//int x = (int)(badguy.getXPos() + badguy.getWidth()/2);
				//int y = (int)(badguy.getYPos() + badguy.getHeight());;
				
				//g.drawString(dmg, x, y);
				
				//g.setColor(Color.black);
				//curState = "Damage";
			}
			break;
				
			case WAITING:
				//curState = "Waiting";
				break;
				
			case TURN:
			{
				//curState = "Turn";
				//Sprite s = null;
				//if(curTeam == GOOD)
				//{
				//	s = guys[curTurn].sprite;
				//}
				//else
				//{
				//	s = badguys[curTurn].sprite;
				//}
				g.setColor(Color.white);
				//int x = (int)s.getXPos();
				//int y = (int)s.getYPos();
				//int w = s.getWidth();
				//int h = s.getHeight();
				//g.drawRect(x, y, w, h);
				g.setColor(Color.black);
			}
			break;
		}
		curState = state.toString();
		g.drawString(curState, 400, 50);
		
		for(int i = 0; i < guys.length; i++)
		{
			Sprite curSprite = guys[i].sprite;
			curSprite.draw(g, guys[i].position);
			/*
			if(i == curTurn)
			{
				g.setColor(Color.white);
				int x = (int)curSprite.getXPos();
				int y = (int)curSprite.getYPos();
				int w = curSprite.getWidth();
				int h = curSprite.getHeight();
				g.drawRect(x, y, w, h);
				g.setColor(Color.black);
			}
			*/
		}
		
		//draw battle entities
		for(Entity entity : battleEntities)
		{
			entity.draw(g);
		}
		
		
	}

public enum State
{
	INIT,
	ATTACKING,
	DAMAGE,
	WAITING,
	TURN
}
	
}
