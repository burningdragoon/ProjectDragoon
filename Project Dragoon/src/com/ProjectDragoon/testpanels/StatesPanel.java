package com.ProjectDragoon.testpanels;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.states.Menu;
import com.ProjectDragoon.states.Play;
import com.ProjectDragoon.states.State;
import com.ProjectDragoon.states.Test;

public class StatesPanel extends GamePanel {

	public static final int MENU = State.MENU.convert();
	public static final int PLAY = State.PLAY.convert();
	public static final int TEST = State.TEST.convert();
	
	public String message = "This is a message.";
	
	public StatesPanel(Game game, int width, int height, long period) 
	{
		super(game, width, height, period);

		this.addState(new Test(TEST));
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
		
		
	}
	
	@Override
	public void initStates()
	{
		this.getState(TEST).init(this);
		this.getState(MENU).init(this);
		this.getState(PLAY).init(this);
		
		this.enterState(MENU);
	}
	
	/*
	 * Game Life-Cycle Methods
	 * 
	 */
	
	@Override
	public void gameInit()
	{
		super.gameInit();
	}
	
	@Override
	public void gameUpdate()
	{
		state.update(this);
	}
	
	@Override
	public void gameRender()
	{
		super.gameRender();
		state.render(this, dbg);
		
		//String msg = "Current State: " + State.values()[0].convert(state.getId());
		String msg = "Current State: " + state.getName();
		dbg.drawString(msg, 0, fontInfo.getHeight());
	}
	
	/* -- End of Game Life-Cycle methods -- */

}
