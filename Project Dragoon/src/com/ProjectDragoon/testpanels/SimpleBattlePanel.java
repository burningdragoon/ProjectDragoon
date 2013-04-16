package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.battle.SimpleBattle;

public class SimpleBattlePanel extends GamePanel {

	private static final long serialVersionUID = 1L;

	private SimpleBattle battle;
	
	public Font font;
	public FontMetrics fontInfo;
	
	protected KeyValues keys;
	
	
	public SimpleBattlePanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		//keys = new KeyValues();
	}
	
	public KeyValues getKeys()
	{
		return keys;
	}
	
	@Override
	public void gameInit()
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		
		keys = new KeyValues();
		
		battle = new SimpleBattle();
		add(battle);
		battle.initBattle();
		
	}
	
	@Override
	public void gameUpdate()
	{
		// check if any exit keys have been pressed...
		if(keyboard.keyPressed(KeyEvent.VK_ESCAPE))
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
		
	}

}
