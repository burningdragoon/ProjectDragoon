package com.ProjectDragoon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.ProjectDragoon.states.GameState;
import com.ProjectDragoon.states.GameStateList;
import com.ProjectDragoon.states.State;
import com.ProjectDragoon.util.KeyInputHandler;
import com.ProjectDragoon.util.MouseInputHandler;

public class GamePanel extends JPanel implements Runnable {


	private static final long serialVersionUID = 1L;

	// Number of frames with a delay of 0 ms before the animation thread yields
	// to other running threads
	private static final int NO_DELAYS_PER_YIELD = 16;
	// Number of frames that can be skipped in any one animation loop
	// i.e the game state is updated but not rendered
	private static final int MAX_FRAME_SKIPS = 5;
	
	private static final long MAX_STATS_INTERVAL = 1000000000L; //calculate FPS every 1 second (roughly)
	
	protected Game game;
	
	private int PWIDTH;
	private int PHEIGHT;
	private long period;
	
	private long gameStartTime;
	private long prevStatsTime;
	private long statsInterval = 0L; // in ms
	private double currentFPS = 0;
	private int frameCount = 0;
	
	private Thread animator; // animation thread
	private volatile boolean running = false;
	
	private Color bgColor;
	
	protected Font font;
	protected FontMetrics fontInfo;
	
	protected Graphics dbg;
	protected BufferedImage dbImage = null;
	
	protected KeyInputHandler keyboard;
	protected MouseInputHandler mouse;
	
	protected GameState state;
	protected GameStateList stateList;
	
	public GamePanel(Game game, int width, int height, long period)
	{
		this.game = game;
		
		PWIDTH = width;
		PHEIGHT = height;
		this.period = period;
		
		bgColor = Color.GREEN;
		
		setDoubleBuffered(false);
		//setBackground(Color.black);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		setMaximumSize(new Dimension(PWIDTH, PHEIGHT));
		setMinimumSize(new Dimension(PWIDTH, PHEIGHT));
		
		setFocusable(true);
		requestFocus(); // the JPanel now has focus and receives input events
		
		//default font settings
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		
		//Keyboard handler
		keyboard = new KeyInputHandler();
		addKeyListener(keyboard);
		
		//Mouse handler
		mouse = new MouseInputHandler();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
		state = null;
		stateList = new GameStateList();
		
	}
	
	/*
	 * Selectors and Mutators
	 */
	
	public int getScreenWidth()
	{
		return PWIDTH;
	}
	public int getScreenHeight()
	{
		return PHEIGHT;
	}
	
	public double getFPS()
	{
		return currentFPS;
	}
	
	public KeyInputHandler getKeyboard() { return keyboard; }
	public MouseInputHandler getMouse() { return mouse; }
	
	public GameState getCurrentState() { return state; }
	public void setState(int id)
	{
		state = stateList.getState(id);
	}
	
	/* -- End of Selectors and Mutators -- */
	
	/*
	 * State Handling
	 */
	
	public void initStates() {}
	
	public void addState(GameState state)
	{
		stateList.add(state.getId(), state);
	}
	
	public void enterState(int id)
	{
		if(state != null)
		{
			GameState oldState = state;
			oldState.exit(this);
		}
		state = stateList.getState(id);
		state.enter(this);
		
	}
	
	public void enterState(State s)
	{
		this.enterState(s.convert());
	}
	
	public GameState getState(int id)
	{
		return stateList.getState(id);
	}
	
	/* -- ENd of State Handling -- */
	
	/*
	 * Input Handling 
	 */
	
	public boolean keyPressed(int key)
	{
		return keyboard.keyPressed(key);
	}
	
	public boolean keyReleased(int key)
	{
		return false;
	}
	
	public boolean mousePressed(int button)
	{
		return mouse.isPressed(button);
	}
	
	public void handleInput() {}
	
	/* -- End of Input Handling -- */
	
	
	public void gameOver()
	{
	}
	
	public void addNotify()
	{
		super.addNotify();
		startGame();
	}
	
	public void startGame()
	{
		if(animator == null || !running)
		{
			animator = new Thread(this);
			animator.start();
		}
	}
	
	public void stopGame()
	{
		running = false;
	}
	
	public void pauseGame()
	{	
	}
	
	public void resumeGame()
	{
	}
	
	
	/**
	 * Initializes all variables and what not for the game, not handled in the Constructor.
	 */
	protected void gameInit()
	{
		initStates();
	}

	/**
	 * Implemented run() method.
	 * Everything the method needs to do is set. Any other stuff should go into the 
	 * gameInit() method... assuming it's set up correctly that is.
	 */
	public void run() 
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;
		
		gameStartTime = System.nanoTime();
	    prevStatsTime = gameStartTime;
		beforeTime = System.nanoTime();
		
		if(dbImage == null)
		{
			dbImage = new BufferedImage(PWIDTH, PHEIGHT, BufferedImage.TYPE_INT_ARGB);
			if(dbImage == null)
			{
				System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}
		
		// Initialize the game nonsense
		gameInit();
		
		running = true;
		
		while(running)
		{
			gameUpdate();
			gameRender();
			paintScreen();
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime; //nanoseconds for 1 frame
			sleepTime = (period - timeDiff) - overSleepTime;
			
			if(sleepTime > 0)
			{
				try
				{
					Thread.sleep(sleepTime/1000000L); // nano -> ms
				}
				catch(InterruptedException ex){}
				
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			else
			{
				excess -= sleepTime;
				overSleepTime = 0L;
				
				if(++noDelays >= NO_DELAYS_PER_YIELD)
				{
					Thread.yield();
					noDelays = 0;
				}
			}
			
			beforeTime = System.nanoTime();
			
			/* If frame animation is taking too long, update the game state
			   without rendering it to get the updates/sec nearer to the required FPS */
			int skips = 0;
			while((excess > period) && (skips < MAX_FRAME_SKIPS))
			{
				excess -= period;
				gameUpdate();
				skips++;
			}
			calcFPS();
		}
		System.exit(0);
	} // end of run() method
	
	protected void gameUpdate()
	{	
	}
	
	protected void gameRender()
	{
		if(dbImage == null)
		{
			dbImage = new BufferedImage(PWIDTH, PHEIGHT, BufferedImage.TYPE_INT_ARGB);
			if(dbImage == null)
			{
				System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}
		
		dbg.setColor(bgColor);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		dbg.setFont(font);
	}
	
	
	/**
	 * Use active rendering to put the buffered image on-screen
	 * There is no reason to Override the paintScreen() method.
	 */
	private void paintScreen()
	{
		Graphics g;
		try
		{
			g = this.getGraphics();
			if((g != null) && (dbImage != null))
			{
				g.drawImage(dbImage, 0, 0, null);
			}
			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)
			Toolkit.getDefaultToolkit().sync();
			
			g.dispose();
		}
		catch(Exception e)
		{
			System.err.println("Graphics context error: " + e);
		}
	}
	
	
	public void calcFPS() 
	{
	    statsInterval += period;
	    frameCount++;

	    if (statsInterval >= MAX_STATS_INTERVAL) {     // record stats every MAX_STATS_INTERVAL
	      long timeNow = System.nanoTime();

	      long realElapsedTime = timeNow - prevStatsTime;   // time since last stats collection

	      currentFPS  = ((double)frameCount / realElapsedTime)* 1000000000L;
	      frameCount = 0;

	      prevStatsTime = timeNow;
	      statsInterval = 0L;   // reset
	    }
	}

}
