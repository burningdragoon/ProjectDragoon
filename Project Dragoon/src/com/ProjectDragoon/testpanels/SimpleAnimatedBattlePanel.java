package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.battle.Battle;

public class SimpleAnimatedBattlePanel extends GamePanel implements ActionListener {


	private static final long serialVersionUID = 1L;

	//private SimpleAnimatedBattle battle;
	//private SimpleAnimBattle battle;
	private Battle battle;
	
	public Font font;
	public FontMetrics fontInfo;
	private DecimalFormat df = new DecimalFormat("0.##");
	
	protected KeyValues keys;
	
	JButton button;
	
	public SimpleAnimatedBattlePanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
	}

	
	public KeyValues getKeys()
	{
		return keys;
	}
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit()
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		
		keys = new KeyValues();
		
		//battle = new SimpleAnimatedBattle();
		//battle = new SimpleAnimBattle();
		battle = new Battle();
		add(battle);
		//battle.initBattle();
		battle.init();
		
		button = new JButton("Button Button");
		button.setMnemonic(KeyEvent.VK_D);
		button.addActionListener(this);
		//Insets insets = this.getInsets();
        Dimension size = button.getPreferredSize();
        button.setBounds(0, 0, size.width, size.height);
        //this.add(button);
        
        this.revalidate();
	}
	
	@Override
	public void gameUpdate()
	{
		// check if any exit keys have been pressed...
		if(keyHandler.keyPressed(KeyEvent.VK_ESCAPE))
		{
			//exit game.
		}
		
		battle.update();
	}
	
	@Override
	public void gameRender()
	{
		// super class gameRender method sets up the Graphics the context
		// and fills the screen with the empty color.
		super.gameRender();
		
		dbg.setFont(font);
		
		dbg.setColor(Color.black);
		
		battle.render(dbg);
		
		dbg.setColor(Color.white);
		dbg.drawString("FPS: " + df.format(getFPS()), 0, fontInfo.getHeight());
		
		game.getLayeredPane().paintComponents(dbg);
		/*
		int x = button.getX();
		int y = button.getY();
		int w = button.getWidth();
		int h = button.getHeight();
		dbg.setColor(Color.red);
		dbg.drawRect(x, y, w, h);
		*/
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Button Pressed!");
		
	}
	
	/* -- End life cycle methods -- */
	
}
