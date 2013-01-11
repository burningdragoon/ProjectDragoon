package com.ProjectDragoon.testpanels;

import java.awt.Font;
import java.awt.FontMetrics;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.KeyValues;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.maps.Map;
import com.ProjectDragoon.maps.TileSet;
import com.ProjectDragoon.sprites.SpriteEntity;
import com.ProjectDragoon.util.Camera;

public class TopDownPanel extends GamePanel {

	private Font font;
	private FontMetrics fontInfo;
	private KeyValues keys;
	
	private Texture background;
	
	private Map map;
	private Camera camera;
	
	private SpriteEntity player;
	
	public TopDownPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
		// TODO Auto-generated constructor stub
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
		
		TileSet tileset = new TileSet(10,10,32,32);
		tileset.loadTiles("/images/test_tiles.png");
		map = new Map(tileset, 30, 30);
		map.readMap("res/maps/testmap4.txt");
	}
	
	@Override
	public void gameUpdate() {}
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		
		map.draw(dbg);
	}
	
	/* -- End life cycle methods -- */

}
