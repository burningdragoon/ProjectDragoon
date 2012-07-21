package com.ProjectDragoon.battle;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.GameComponent;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.sprites.SpriteAnimation;
import com.ProjectDragoon.testpanels.SimpleAnimatedBattlePanel;
import com.ProjectDragoon.unit.Creature;
import com.ProjectDragoon.util.KeyTimer;
import com.ProjectDragoon.util.Timer;

public class SimpleAnimatedBattle extends GameComponent {

	private static final long serialVersionUID = 1L;

	Creature[][] teams;
	Sprite[] sprites;
	int curSprite;
	int curTeam;
	int curGuy;
	int curTarget;

	boolean defeated;
	
	String msg;
	String msg2;
	String msg3;
	
	KeyTimer dirKeyTimer;
	
	Timer turnTimer;
	int turnDelay;
	boolean turnActive;
	
	Timer attackTimer;
	int attackTime;
	boolean attacking;
	
	//Sprite sprite;
	//Sprite sprite2;
	int animLoops;
	int animLoopCount;
	boolean loopRegistered;
	
	public void initBattle()
	{
		int numTeams = 2;
		int teamSize = 4;
		teams = new Creature[numTeams][teamSize];
		
		curTeam = 1;
		curGuy = 0;
		curTarget = 0;
		
		for(int team = 1; team <= numTeams; team++)
		{
			for(int i = 0; i < teamSize; i++)
			{
				String name = "Guy " + team + "-" + i;
				// name, hp, mp, str, end, dex, agi, magic, faith, luck
				Creature guy = new Creature(name, 100, 50, 10, 5, 0, 0, 0, 0, 0, 0);
				teams[team-1][i] = guy;
			}
		}
		
		defeated = false;
		
		turnTimer = new Timer();
		turnDelay = 1000;
		turnActive = true;
		
		attackTimer = new Timer();
		attackTime = 1000;
		attacking = false;
		
		dirKeyTimer = new KeyTimer(500,80);
		
		msg = "Yo!";
		msg2 = "Hit '" + getKeys().ActionName() + "' to attack!";
		msg3 = "";
		
		
		sprites = new Sprite[teamSize];
		curSprite = 0;
		
		Sprite sprite = new Sprite();
		sprite.loadImage("/images/sprites/kain_spritesheet.png");
		sprite.setNumColumns(14);
		sprite.setTotalFrames(14);
		sprite.configureDimensions();
		//sprite.setPosition(50, 100);
		
		SpriteAnimation anim = new SpriteAnimation();
		int[] frames = {1};
		anim.setFrames(frames);
		sprite.AddAnimations("Rest", anim);
		sprite.setCurrentAnimation("Rest");
		
		SpriteAnimation walkAnim = new SpriteAnimation(2);
		int[] walkFrames = {1,2};
		walkAnim.setFrames(walkFrames);
		walkAnim.setDelay(200);
		sprite.AddAnimations("Walk", walkAnim);
		
		SpriteAnimation attackAnim = new SpriteAnimation(2);
		int[] attFrames = {6,5};
		attackAnim.setFrames(attFrames);
		attackAnim.setDelay(200);
		sprite.AddAnimations("Attack", attackAnim);
		
		/*
		int sX = 50;
		int sY = 100;
		for(int i = 0; i < teamSize; i++)
		{
			Sprite newSprite = sprite.copy();
			//newSprite.setPosition(sX, sY + (i * 50));
			sprites[i] = newSprite;
		}
		*/
		animLoops = 10;
		animLoopCount = 0;
		loopRegistered = false;
		
		//sprite2 = sprite.copy();
		//sprite2.setPosition(50, 150);
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return ((GamePanel)this.getParent()).getKeyHandler().keyPressed(keyCode);
	}
	
	public KeyValues getKeys()
	{
		return ((SimpleAnimatedBattlePanel)this.getParent()).getKeys();

	}
	
	
	@Override
	public void update() {
		if(!attacking)
		{
			if(!defeated)
			{
				if(turnActive)
				{	
					// Team based testing
					Creature guyTurn = null;
					Creature guyTarget = null;
					
					// get the current Guy
					guyTurn = teams[curTeam-1][curGuy];
					msg = "It's " + guyTurn.NAME + "'s turn!";
					curSprite = curGuy;
					
					int targetTeam = curTeam == 1 ? 2 : 1;
					
					/*
					 * Directional Key Checks
					 * 
					 * - check if any keys are pressed
					 * 		- if yes, check if the delay has been reached
					 * 			- if yes, update delay and process keys			
					 * 				- if no delay, set to longer delay
					 * 				- if longer delay, set to shorter delay
					 * 				- if shorter delay, leave be
					 * 	 		- if no, reset to no delay
					 */
					boolean up, down;
					//up = keyHandler.keyPressed(keys.UpKey());
					//down = keyHandler.keyPressed(keys.DownKey());
					up = isKeyPressed(getKeys().UpKey());
					down = isKeyPressed(getKeys().DownKey());
					if(up || down)
					{
						if(dirKeyTimer.stopwatch())
						{	
							//perform actions
							if(up)
							{
								//make sure target is selectable
								boolean done = false;
								while(!done)
								{
									curTarget--;
									if(curTarget < 0)
									{
										curTarget = teams[targetTeam-1].length - 1;
									}
									done = teams[targetTeam-1][curTarget].alive;
								}
							}
							else if(down)
							{
								boolean done = false;
								while(!done)
								{
									curTarget++;
									if(curTarget >= teams[targetTeam-1].length)
									{
										curTarget = 0; 
									}
									done = teams[targetTeam-1][curTarget].alive;
									
								}
							}
						}
					}
					else
					{
						// no directional keys pressed, reset the delay
						dirKeyTimer.resetDelay();
						
					}
					/*
					 * End Directional Key Checks
					 */
					
					// check for attack key
					if(isKeyPressed(getKeys().ActionKey()))
					{
						System.out.println("Action Key!");
						// grab the current target
						guyTarget = teams[targetTeam-1][curTarget];
						
						// set attacking flags
						attacking = true;
						//sprite.setCurrentAnimation("Attack");
						sprites[curSprite].setCurrentAnimation("Attack");
						//attackTimer.reset();
						
						msg = guyTurn.NAME + " is attacking!";
						
						// perform attack
						int dmg = guyTurn.Attack(guyTarget);
						msg3 = guyTurn.NAME +" hits " + guyTarget.NAME + " for " + dmg + " damage!";
						
						// check for death
						//if the target dies, check for victory
						guyTarget.checkAlive();						
						if(!guyTarget.alive){
							boolean wipe = true;
							for(int i = 0; i < teams[targetTeam-1].length; i ++)
							{
								if(teams[targetTeam-1][i].alive)
								{
									wipe = false;
									break;
								}
							}
							defeated = wipe;
						}
						
						//if the opposing team still stands, update turn info
						if(!defeated)
						{
							// update turn info
							// Move down the current team.
							// When last person on team goes, switch turns
							boolean foundNextTurn = false;
							while(!foundNextTurn)
							{
								// move down the line
								curGuy++;
								
								// if hit last guy, change team and reset guys
								if(curGuy >= teams[curTeam-1].length)
								{
									//switch team
									int oldTeam = curTeam;
									curTeam = targetTeam;
									targetTeam = oldTeam;
									//reset guy pointers and check for dead guys
									curGuy = 0;
									boolean foundFirstGuy = false;
									while(!foundFirstGuy)
									{
										if(!teams[curTeam-1][curGuy].alive)
										{
											curGuy++;
											System.out.println(2);
										}
										else
										{
											foundFirstGuy = true;
										}
									}
									
									foundNextTurn = true;
									continue;
								}							
								// if next guy is dead, repeat
								foundNextTurn = teams[curTeam-1][curGuy].alive;
							}
							
							//get first target of the target team
							curTarget = 0;
							boolean foundFirstTarget = false;
							while(!foundFirstTarget)
							{
								if(!teams[targetTeam-1][curTarget].alive)
								{
									curTarget++;
								}
								else
								{
									foundFirstTarget = true;
								}
							}
							
							// Reset the turn timer
							//turnTimer.reset();
							turnActive = false;
						}
						
					}
				}
				else
				{
					msg = "Waiting for next turn!";
					turnActive = turnTimer.stopwatch(turnDelay);
					
					// Reset any necessary Key Timers
					dirKeyTimer.resetDelay();
				}
			}
			else
			{
				if(isKeyPressed(getKeys().BackKey()))
				{	
					for(int i = 0; i < teams[0].length; i++)
					{
						teams[0][i].reset();
						teams[1][i].reset();
					}
					
					defeated = false;
					msg2 = "Hit '" + getKeys().ActionName() + "' to attack!";
					System.out.println("New Battle!");
					
					//Reset turn timer
					turnTimer.reset();
					turnActive = false;
				}
			}
		}
		else
		{
			//System.out.println("Before check:" + attacking);
			//attacking = !(attackTimer.stopwatch(attackTime));
			if(animLoopCount == animLoops)
			{
				animLoopCount = 0;
				attacking = false;
				//sprite.setCurrentAnimation("Rest");
				sprites[curSprite].setCurrentAnimation("Rest");
			}
			else
			{
				//increase the loop count if possible
				//int curFrame = sprite.getCurrentAnimFrame();
				//int animLength = sprite.getCurrentAnimation().getLength();
				int curFrame = sprites[curSprite].getCurrentAnimFrame();
				int animLength = sprites[curSprite].getCurrentAnimation().getLength();
				if(curFrame == animLength-1)
				{
					if(!loopRegistered)
					{
						loopRegistered = true;
						animLoopCount++;
						System.out.println(animLoopCount);
					}
				}
				else
					loopRegistered = false;
			}
			//System.out.println("After check: " + attacking);
			
			// once the person is no longer attacking, reset the turn timer.
			if(!attacking)
			{
				msg3 = "";
				turnTimer.reset();
			}
		}
		
		//sprite.animate();
		for(int i = 0; i < sprites.length; i++)
		{
			sprites[i].animate();
		}
	} // end update()

	@Override
	public void render(Graphics g) {

		
		g.drawString(msg, 100, 100);
		g.drawString(msg2, 100, 100 + ((SimpleAnimatedBattlePanel)getParent()).fontInfo.getHeight());
		g.drawString(msg3, 100, 100 + (((SimpleAnimatedBattlePanel)getParent()).fontInfo.getHeight() * 2));
		
		int teamX = 100;
		int teamY = 200;
		int space = ((SimpleAnimatedBattlePanel)getParent()).fontInfo.getHeight();
		//team 1
		g.drawString("Team 1", teamX, teamY-space);
		for(int i = 0; i < teams[0].length; i++)
		{
			Creature guy = teams[0][i];
			String label = guy.NAME + "  HP: " + guy.curHP;
		
			// Set the text color
			// If the guy is dead, write text in red
			// If the guy is targeted, yellow
			// If the guy has the turn, blue
			Color textColor = Color.black;
			if(!guy.alive)
				textColor = Color.red;
			else if(turnActive && curTeam == 2 && curTarget == i)
				textColor = Color.yellow;
			else if(turnActive && curTeam == 1 && curGuy == i)
				textColor = Color.blue;
			g.setColor(textColor);
			
			g.drawString(label, teamX, teamY + (space * i));
		}
		g.setColor(Color.black);
		
		//team 2
		teamX += 200;
		g.drawString("Team 2", teamX, teamY-space);
		for(int i = 0; i < teams[1].length; i++)
		{
			Creature guy = teams[1][i];
			String label = guy.NAME + "  HP: " + guy.curHP;
			
			// Set the text color
			// If the guy is dead, write text in red
			// If the guy is targeted, yellow
			// If the guy has the turn, blue
			Color textColor = Color.black;
			if(!guy.alive)
				textColor = Color.red;
			else if(turnActive && curTeam == 1 && curTarget == i)
				textColor = Color.yellow;
			else if(turnActive && curTeam == 2 && curGuy == i)
				textColor = Color.blue;
			g.setColor(textColor);
			g.drawString(label, teamX, teamY + (space * i));
		}
		g.setColor(Color.black);
		
		//sprite.draw(g);
		//sprite2.draw(g);
		for(int i = 0; i < sprites.length; i++)
		{
			//sprites[i].draw(g);
		}
		
	}

}
