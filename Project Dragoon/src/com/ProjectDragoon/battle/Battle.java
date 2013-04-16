package com.ProjectDragoon.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GameComponent;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.entity.Entity;
import com.ProjectDragoon.entity.EntityList;
import com.ProjectDragoon.entity.NumberSprite;
import com.ProjectDragoon.entity.SpriteEntity;
import com.ProjectDragoon.graphics.ImageEntity;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.sprites.Sprite;
import com.ProjectDragoon.testpanels.SimpleAnimatedBattlePanel;
import com.ProjectDragoon.unit.BattleUnit;
import com.ProjectDragoon.util.KeyTimer;
import com.ProjectDragoon.util.Lists;
import com.ProjectDragoon.util.Timer;
import com.ProjectDragoon.util.Vector;
import com.ProjectDragoon.util.data.DataSaver;

public class Battle extends GameComponent {

	private static final long serialVersionUID = 1L;
	
	private final int GOOD = 0;
	private final int BAD = 1;
	
	private State state;
	private TurnState turnstate;
	
	private EntityList battleEntities;
	
	private ArrayList<BattleUnit> party;
	private ArrayList<BattleUnit> opponents;
	private ArrayList<BattleUnit> turnOrder;
	
	private BattleUnit curUnit;
	private BattleUnit curTarget;
	
	private SpriteEntity selectArrow;
	
	private int animLoops;
	private int animLoopCount;
	private boolean loopRegistered;
	private boolean animating;
	
	private Texture numbers;
	
	// Timers
	private KeyTimer keyTimer;
	private KeyTimer selectTimer;
	
	private Timer waitTimer;
	private int waitTime;
	
	private Timer attackTimer;
	private int attackTime;
	private int attackAnimLoop;
	
	private int damageLifetime;
	
	// opponentTimer is for simulating enemy AI
	private Timer opponentTimer;
	private int opponentTime;
	// --
	
	
	// Turn menu sim variables
	String attStr = "ATTACK";
	int attX = 400;
	int attY = 430;
	boolean attSelected = false;
	String waitStr = "WAIT";
	int waitX = 400;
	int waitY = 450;
	boolean waitSelected = false;
	
	
	//grids and junk
	//Texture battleWindowTemp;
	//Texture battleMenuTemp;
	ImageEntity battleWindowTemp;
	ImageEntity battleMenuTemp;
	int battleMenuTempX = 0;
	int battleMenuTempY = 0;
	
	int battleX = 0;
	int battleY = 0;
	int battleW = 793;
	int battleH = 402;
	int menuX = 0;
	int menuY = battleY + battleH + 1;
	int menuW = 793;
	int menuH = 168;
	
	int gridH = 64;
	int gridW = 64;
	
	int partyGridX = (int)((double)gridW * 8.5);
	int partyGridY = (int)((double)gridH * 1.5);
	int partyGridW = 64;
	int partyGridH = 64;
	int partyGridSize = 4;
	
	int windowOffsetX = 8;
	int windowOffsetY = 30;
	
	int nameWidth = 113;
	int nameX = 438 - windowOffsetX;
	int nameY = 469 - windowOffsetY;
	int healthWidth = 99;
	int healthX = 555 - windowOffsetX;
	int healthY = 469 - windowOffsetY;
	int magicWidth = 76;
	int magicX = 657 - windowOffsetX;
	int magicY = 469 - windowOffsetY;
	
	/*
	 * Constructors
	 */
	
	public Battle()
	{
		state = State.INIT;
		turnstate = TurnState.NONE;
		
		battleEntities = new EntityList();
		party = new ArrayList<BattleUnit>();
		opponents = new ArrayList<BattleUnit>();
		turnOrder = new ArrayList<BattleUnit>();
		curUnit = null;
		curTarget = null;
		
		Sprite selectArrowSprite = DataSaver.LoadSprite("res/sprites/selectarrow.sprite");
		selectArrow = new SpriteEntity(selectArrowSprite, new Vector());
		
		animLoops = 0;
		animLoopCount = 0;
		loopRegistered = false;
		animating = true;
		
		numbers = new Texture("/images/sprites/numbers.png");
		
		keyTimer = new KeyTimer(500, 80);
		selectTimer = new KeyTimer(500, 80);
		
		
		waitTimer = new Timer();
		waitTimer.setActive(false);
		waitTime = 1000;
		
		attackTimer = new Timer();
		attackTimer.setActive(false);
		attackTime = 500;
		attackAnimLoop = 2;
		
		//damageTimer = new Timer();
		//damageTimer.setActive(false);
		//damageTime = 500;
		damageLifetime = 1000;
		
		opponentTimer = new Timer();
		opponentTimer.setActive(false);
		opponentTime = 2000;

		//battleWindowTemp = new Texture("/images/battle_menu_temp.png");
		//battleMenuTemp = new Texture("/images/battle_character_menu_temp.png");
		battleWindowTemp = new ImageEntity();
		battleWindowTemp.loadImage("/images/battle_menu_temp.png");
		battleWindowTemp.setPosition(0, menuY);
		
		battleMenuTemp = new ImageEntity();
		battleMenuTemp.loadImage("/images/battle_character_menu_temp.png");
		battleMenuTemp.setPosition(285, menuY);
		
		// Swing component tests
		//JButton battlebutton = new JButton("Battle Button");
		//this.getParent().add(battlebutton);
		
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	/* -- End S&M -- */
	
	/*
	 * Game Component Methods
	 */
	
	@Override
	public void update() {
		// TODO Battle Update Method
		
		for(BattleUnit unit : party)
		{
			if(!animating && unit.equals(curUnit))
				continue;
			
			unit.sprite.animate();
		}
		
		if(selectArrow.isVisible())
			selectArrow.animate();

		switch(state)
		{
			case INIT:
				init();
				break;
				
			case WAITING:
				waitingUpdate();
				break;
				
			case SET_ORDER:
				setOrderUpdate();
				break;
				
			case TURN:
				turnUpdate();
				break;
				
			case ATTACKING:
				attackingUpdate();
				break;
				
			case DAMAGE:
				damageUpdate();
				break;
		}
		
		//update entities
		for(Entity e : battleEntities.list())
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
		// TODO Battle Render Method
		
		for(BattleUnit unit : party)
		{
			unit.sprite.draw(g, unit.getPosition());
		}
		for(BattleUnit unit : opponents)
		{
			unit.sprite.draw(g, unit.getPosition());
		}
		for(Entity entity : battleEntities.list())
		{
			entity.draw(g);
		}
		
		//battleWindowTemp.draw(g, 0, menuY);
		battleWindowTemp.draw(g);
		battleMenuTemp.draw(g);
		
		if(selectArrow.isVisible())
			selectArrow.draw(g);
		
		switch(state)
		{
			case DAMAGE:
			{
				damageDraw(g);
			}
			break;
			
			case TURN:
			{
				turnDraw(g);
				
			}
			break;
		}
		
		String curState = state.toString();
		g.drawString(curState, 400, 50);
		
		// draw grids and junk
		g.setColor(Color.black);
		g.drawRect(battleX, battleY, battleW, battleH);
		g.drawRect(menuX, menuY, menuW, menuH);
		
		for(int i = 0; i < partyGridSize; i++)
		{
			int x = partyGridX;
			int y = partyGridY + (i * partyGridH);
			g.drawRect(x, y, partyGridW, partyGridH);
			g.drawRect(x + partyGridW, y, partyGridW, partyGridH);
			
		}
		
		//names and stuff
		int textHeight = g.getFontMetrics().getHeight();
		int textDY = textHeight + 5;
		for(int i = 0; i < partyGridSize; i++)
		{
			g.drawRect(nameX, nameY + (i*textDY), nameWidth, textHeight);
			g.drawRect(healthX, healthY + (i*textDY), healthWidth, textHeight);
			g.drawRect(magicX, magicY + (i*textDY), magicWidth, textHeight);
		}
		
		/*
		g.setColor(Color.red);
		int size = 64;
		int gridWidth = 800 / size;
		int gridHeight = 600 / size;
		for(int i = 0; i <= gridWidth; i++)
		{
			g.drawLine(i * size, 0, i*size, 600);
		}
		for(int i = 0; i <= gridHeight; i++)
		{
			g.drawLine(0, i*size, 800, i*size);
		}
		*/
		
	}
	
	/* -- End GameComponent -- */
	
	/*
	 * Battle state methods
	 * TODO Battle State Methods
	 */
	
	public void init()
	{
		//swing tests
		//JButton button1 = new JButton("Battle Button");
		//JButton button2 = new JButton("Other Button");
		//JPanel panel = new JPanel();
		//panel.setOpaque(false);
		//panel.setLocation(0, 100);
		//panel.setPreferredSize(new Dimension(200,200));
		//panel.add(button1);
		//panel.add(button2);
		//this.getParent().add(panel);
		//this.getParent().add(battlebutton);
		//--
		
		Sprite sprite = DataSaver.LoadSprite("res/sprites/kain.sprite");
		Sprite badguy = DataSaver.LoadSprite("res/sprites/soldier.sprite");
		
		int x = partyGridX;
		int y = partyGridY;
		int dy = partyGridH;
		for(int i = 0; i < 4; i++)
		{
			BattleUnit unit = new BattleUnit("Kain" + (i+1), 100, 50, 10, 5, 5, 5, 5, 5, 5, 5);
			unit.sprite = sprite.copy();
			unit.sprite.setCurrentAnimation("Rest");
			unit.setPosition(x, y);
			y += dy;
			unit.team = GOOD;
			party.add(unit);
		}
		x = 200;
		y = 200;
		dy = 100;
		for(int i = 0; i < 2; i++)
		{
			BattleUnit unit = new BattleUnit("Soldier" + (i+1), 100, 50, 10, 5, 5, 5, 5, 5, 5, 5);
			unit.sprite = badguy.copy();
			unit.setPosition(x, y);
			y += dy;
			unit.team = BAD;
			opponents.add(unit);
		}
		
		state = State.WAITING;
	}
	
	public void initDraw(Graphics g) {}
	
	public void waitingUpdate()
	{
		if(!waitTimer.isActive())
		{
			waitTimer.activate();
			if(turnOrder.isEmpty())
				state = State.SET_ORDER;
		}
		else if(waitTimer.stopwatch(waitTime))
		{
			waitTimer.deactivate();
			state = State.TURN;
		}
	}
	public void waitingDraw(Graphics g){}
	
	public void setOrderUpdate()
	{
		setTurnOrder();
		printTurnOrder();
		state = State.WAITING;
	}
	public void setOrderDraw(Graphics g){}
	
	public void turnUpdate()
	{
		boolean actionKey = isKeyPressed(getKeys().ActionKey());
		boolean upKey = isKeyPressed(getKeys().UpKey());
		boolean downKey = isKeyPressed(getKeys().DownKey());
		
		if(curUnit == null)
		{
			curUnit = turnOrder.get(0);
		}
		if(curUnit.team == GOOD)
		{
			if(!curUnit.sprite.getCurrentAnimationName().equals("Prep"))
				curUnit.sprite.setCurrentAnimation("Prep");
			
			switch(turnstate)
			{
				case NONE:
					turnstate = TurnState.ACTION;
					break;
					
				case ACTION:
					if(!attSelected && !waitSelected)
						attSelected = true;
					
					selectArrow.setVisible(true);
					//selectArrow.sprite.resetAnimation();
					
					if(attSelected)
					{
						selectArrow.setPosition(attX - selectArrow.sprite.getWidth(), attY - selectArrow.sprite.getHeight());
					}
					else if(waitSelected)
					{
						selectArrow.setPosition(waitX - selectArrow.sprite.getWidth(), waitY - selectArrow.sprite.getHeight());
					}
					
					if(actionKey)
					{
						if(keyTimer.stopwatch())
						{
							if(attSelected)
							{
								turnstate = TurnState.TARGET;
							}
							else if(waitSelected)
							{
								turnstate = TurnState.NONE;
								
								curUnit = null;
								curTarget = null;
								
								state = State.WAITING;
							}
							
							attSelected = false;
							waitSelected = false;
						}
					}
					else
					{
						keyTimer.resetDelay();
					}
					
					if(upKey || downKey)
					{
						if(selectTimer.stopwatch())
						{
							if(attSelected)
							{
								attSelected = false;
								waitSelected = true;
							}
							else if(waitSelected)
							{
								attSelected = true;
								waitSelected = false;
							}
						}
					}
					else
					{
						selectTimer.resetDelay();
					}
					
					break;
					
				case TARGET:
					if(curTarget == null)
					{
						curTarget = opponents.get(0);
						selectTarget(curTarget);
						//selectArrow.setVisible(true);
						//selectArrow.sprite.resetAnimation();
					}
					if(actionKey)
					{
						if(keyTimer.stopwatch())
						{
							curUnit.sprite.setCurrentAnimation("Attack");
							animLoops = attackAnimLoop;
							animLoopCount = 0;
							selectArrow.setVisible(false);
							state = State.ATTACKING;
							turnstate = TurnState.NONE;
						}
					}
					else
					{
						keyTimer.resetDelay();
					}
					
					if(upKey || downKey)
					{
						if(selectTimer.stopwatch())
						{
							int index = opponents.indexOf(curTarget);
							//System.out.println(index);
							int incr = upKey ? -1 : 1;
							index += incr;
							//System.out.println(index);
							if(index < 0)
								index = opponents.size()-1;
							else if(index >= opponents.size())
								index = 0;
							//System.out.println(index);
							curTarget = opponents.get(index);
							selectTarget(curTarget);
						}
					}
					else
					{
						selectTimer.resetDelay();
					}
					break;
			}
		}
		else // Bad team
		{
			//curTarget = party.get(random.nextInt(party.size()));
			if(!opponentTimer.isActive())
			{
				opponentTimer.activate();
				curTarget = party.get(Game.random.nextInt(party.size()));
			}
			else if(opponentTimer.stopwatch(opponentTime))
			{
				state = State.ATTACKING;
				opponentTimer.deactivate();
			}
		}
	}
	
	public void turnDraw(Graphics g)
	{
		if(curUnit != null)
		{
			Sprite s = curUnit.sprite;
			g.setColor(Color.white);
			int x = (int)curUnit.getXPos();
			int y = (int)curUnit.getYPos();
			int w = s.getWidth();
			int h = s.getHeight();
			g.drawRect(x,y,w,h);
			g.setColor(Color.black);
		}
		
		g.drawString(attStr, attX, attY);
		g.drawString(waitStr, waitX, waitY);
	}
	
	public void attackingUpdate()
	{
		if(curUnit.team == GOOD)
		{
			if(!attackTimer.isActive())
			{
				if(animLoopCount == animLoops)
				{
					animating = false;
					attackTimer.activate();
					animLoopCount = 0;
				}
				else
				{
					int curFrame = curUnit.sprite.getCurrentAnimFrame();
					int animLength = curUnit.sprite.getCurrentAnimation().getLength();
					
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
				int dmgVal = curUnit.attack(curTarget);
				//NumberSprite dmg = new NumberSprite(numbers, random.nextInt(500));
				NumberSprite dmg = new NumberSprite(numbers, dmgVal);
				int x = (int)(curTarget.getXPos() + curTarget.sprite.getWidth()/2);
				int y = (int)(curTarget.getYPos() + curTarget.sprite.getHeight());
				dmg.setPosition(x, y);
				dmg.setLifetimer(1000);
				battleEntities.add(dmg);
				
				state = State.DAMAGE;
				animating = true;
				attackTimer.deactivate();
				curUnit.sprite.setCurrentAnimation("Rest");
				//turnOrder.remove(0);
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
				int dmgVal = curUnit.attack(curTarget);
				NumberSprite dmg = new NumberSprite(numbers, dmgVal);
				int x = (int)(curTarget.getXPos() + curTarget.sprite.getWidth()/2);
				int y = (int)(curTarget.getYPos() + curTarget.sprite.getHeight()/2);
				dmg.setPosition(x, y);
				dmg.setLifetimer(damageLifetime);
				battleEntities.add(dmg);
				
				attackTimer.deactivate();
				state = State.DAMAGE;
				//turnOrder.remove(0);
			}
		}
	}
	public void attackingDraw(Graphics g){}
	
	public void damageUpdate()
	{
		if(battleEntities.isEmpty())
		{
			turnOrder.remove(0);
			curUnit = null;
			curTarget = null;
			state = State.WAITING;
		}
	}
	public void damageDraw(Graphics g)
	{
		String msg = curUnit.NAME + " doing damage!";
		g.drawString(msg, 400, 80);
	}
	
	/* -- End Battle state -- */
	
	/*
	 * ...
	 */
	
	public void selectTarget(BattleUnit target)
	{
		int arrowX = (int)(target.getXPos() - selectArrow.sprite.getWidth());
		int arrowY = (int)(target.getYPos() + target.sprite.getHeight()/4 - selectArrow.sprite.getHeight()/2);
		selectArrow.setPosition(arrowX, arrowY);
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return ((GamePanel)this.getParent()).getKeyboard().keyPressed(keyCode);
	}
	
	public KeyValues getKeys()
	{
		return ((SimpleAnimatedBattlePanel)this.getParent()).getKeys();

	}
	
	
	
	private void setTurnOrder()
	{
		System.out.println("Setting order...");
		turnOrder.clear();
		// Set turn order based of each unit's SPEED value and some random variance
		int size = party.size() + opponents.size();
		int[] speeds = new int[size];
		int[] units = new int[size];
		int index = 0;
		for(int i = 0; i < party.size(); i++)
		{
			int speed = party.get(i).SPEED * Game.random.nextInt(255);
			speeds[index] = speed;
			units[index] = index;
			index++;
		}
		for(int i = 0; i < opponents.size(); i++)
		{
			int speed = opponents.get(i).SPEED * Game.random.nextInt(255);
			speeds[index] = speed;
			units[index] = index;
			index++;
		}
		//sort lists based on speed
		Lists.DoubleSort(speeds, units, Lists.SortDirection.DESCENDING);
		
		// arrange the turn order
		for(int i = 0; i < size; i++)
		{
			if(units[i] < party.size())
				turnOrder.add(party.get(units[i]));
			else
			{
				int oppIndex = units[i] - party.size();
				turnOrder.add(opponents.get(oppIndex));
			}
		}	
	}
	
	private void printTurnOrder()
	{
		int count = 0;
		for(BattleUnit unit : turnOrder)
		{
			System.out.println(count++ +  " : " + unit.NAME + " - Speed: " + unit.SPEED);
		}
	}
	
	/* -- End ... -- */
	
public enum State
{
	INIT,
	WAITING,
	SET_ORDER,
	TURN,
	ATTACKING,
	DAMAGE,
}

public enum TurnState
{
	ACTION,
	TARGET,
	NONE
}

}
