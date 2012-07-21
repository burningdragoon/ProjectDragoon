package com.ProjectDragoon;

/**
 * The cleverly named DragonPanel is the main GamePanel for the equally cleverly named Project Dragoon. 
 * @author Alex
 *
 */
public class DragonPanel extends GamePanel {

	private static final long serialVersionUID = 1L;

	private Options options;
	
	public DragonPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
	}
	
	/*
	 * Selectors and Mutators
	 */
	
	public Options getOptions() { return options; }
	
	/* -- End of selectors and mutators -- */
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit() {}
	
	@Override
	public void gameUpdate() {}
	
	@Override
	public void gameRender() {}
	
	/* -- End life cycle methods -- */
	
	
}
