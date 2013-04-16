package com.ProjectDragoon;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;

import com.ProjectDragoon.testpanels.CoinFlipPanel;
import com.ProjectDragoon.util.NoRepaintManager;

public class Game extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	private static int DEFAULT_FPS = 30;
	//private static final int WIDTH = 800;
	//private static final int HEIGHT = 640;
	private static final int WIDTH = 200;
	private static final int HEIGHT = 240;
	
	// Account for the window frame.
	// will worry about programmatic way to do this later...
	private static final int WINDOW_WIDTH = 6;
	private static final int WINDOW_HEIGHT = 28;
	
	public static final Random random = new Random(1);
	
	private GamePanel gamePanel;
	
	public int i;
	
	public Game(long period)
	{
		super("Project Dragoon");
		
		Container gameContainer = getContentPane();
		//gamePanel = new BattlePanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new SimpleBattlePanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new ImagePanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new SimpleAnimatedBattlePanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new WindowPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new TileMapTestPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new ScrollingTilesPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new PlatformerPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new TopDownPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new TileMovementPanel(this, WIDTH, HEIGHT, period);
		//gamePanel = new StatesPanel(this, WIDTH, HEIGHT, period);
		gamePanel = new CoinFlipPanel(this, WIDTH, HEIGHT, period);
		
		// Absolute Positioning is not the best approach for UI, but whatevs for now.
        gamePanel.setLayout(null);
        
		//gameContainer.setPreferredSize(gamePanel.getPreferredSize());
		//gameContainer.setMinimumSize(gamePanel.getMinimumSize());
		//gameContainer.setMaximumSize(gamePanel.getMaximumSize());
		gameContainer.setPreferredSize(new Dimension(WIDTH + WINDOW_WIDTH, HEIGHT + WINDOW_HEIGHT));
		gameContainer.setMinimumSize(gameContainer.getPreferredSize());
		gameContainer.setMaximumSize(gameContainer.getPreferredSize());
        
		//this.setPreferredSize(gamePanel.getPreferredSize());
		//this.setMinimumSize(gamePanel.getMinimumSize());
		//this.setMaximumSize(gamePanel.getMaximumSize());
        setPreferredSize(gameContainer.getPreferredSize());
        setMinimumSize(gameContainer.getMinimumSize());
        setMaximumSize(gameContainer.getMaximumSize());
		
		gameContainer.add(gamePanel, "Center");
		
		/*
		 * Set the Icon Image of the game (JFrame)
		 */
		try
		{
			Image icon = ImageIO.read(Object.class.getResource("/images/system/icon.png"));
			this.setIconImage(icon);
		} catch (IOException e){}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(this);
		pack();
		setResizable(false);
		setVisible(true);
		
		i = 0;
		
		//Allow the use of Swing components for Active Rendering 
		((JComponent)this.getContentPane()).setOpaque(false);
		gamePanel.setOpaque(false);
		RepaintManager repaintManager = new NoRepaintManager();
		repaintManager.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(repaintManager);
	}
	
	
	/* ---- Overridden WindowListener Methods ---- */
	
	public void windowActivated(WindowEvent arg0) {
		gamePanel.resumeGame();
	}

	public void windowClosing(WindowEvent arg0) {
		gamePanel.stopGame();
	}

	public void windowDeactivated(WindowEvent arg0) {
		gamePanel.pauseGame();
	}

	public void windowDeiconified(WindowEvent arg0) {
		gamePanel.resumeGame();
	}

	public void windowIconified(WindowEvent arg0) {
		gamePanel.pauseGame();
	}

	public void windowOpened(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	
	/* ---- */
	
	public static void main(String[] args)
	{
		long period = (long) 1000.0/DEFAULT_FPS;
		new Game(period*1000000L);
	}

}
