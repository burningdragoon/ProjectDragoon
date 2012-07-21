package com.ProjectDragoon.testpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;

import com.ProjectDragoon.Game;
import com.ProjectDragoon.GamePanel;
import com.ProjectDragoon.graphics.Texture;
import com.ProjectDragoon.gui.Window;
import com.ProjectDragoon.util.MouseInputHandler;

public class WindowPanel extends GamePanel {

	private static final long serialVersionUID = 1L;

	public Font font;
	public FontMetrics fontInfo;
	private DecimalFormat df = new DecimalFormat("0.##");
	
	private Texture frameTexture;
	private Texture patternTexture;
	
	private Window window;
	private Window window2;
	private Window window3;
	private Window window4;
	private Window window5;
	private Window window6;
	int width = 600;
	int height = 150;
	
	Color bgColor;
	
	public WindowPanel(Game game, int width, int height, long period) {
		super(game, width, height, period);
	}
	
	/*
	 * Main game life cycle methods
	 */
	
	@Override
	public void gameInit() 
	{
		font = new Font("Courier", Font.BOLD, 14);
		fontInfo = this.getFontMetrics(font);
		
		frameTexture = new Texture();
		patternTexture = new Texture();
		frameTexture.loadImage("/images/basic_square_window_frame.png");
		patternTexture.loadImage("/images/brick_window_pattern.png");
		
		window = new Window();
		window.setWindowWidth(800);
		window.setWindowHeight(600);
		//window.loadWindowFrame("/images/basic_window_frame.png");
		//window.loadWindowPattern("/images/basic_window_pattern.png");
		window.setWindowFrame(frameTexture);
		window.setWindowPattern(patternTexture);
		window.configureWindowInfo();
		window.setPatternOffsetX(6);
		window.setPatternOffsetY(6);
		
		window2 = new Window();
		window2.setWindowWidth(width);
		window2.setWindowHeight(height);
		window2.setWindowFrame(frameTexture);
		window2.setWindowPattern(patternTexture);
		window2.configureWindowInfo();
		window2.setPatternOffsetX(6);
		window2.setPatternOffsetY(6);
		
		window3 = new Window();
		window3.setWindowWidth(width);
		window3.setWindowHeight(height);
		window3.setWindowFrame(frameTexture);
		window3.setWindowPattern(patternTexture);
		window3.configureWindowInfo();
		window3.setPatternOffsetX(6);
		window3.setPatternOffsetY(6);
		window3.setPosition(0, height);
		
		window4 = new Window();
		window4.setWindowWidth(width);
		window4.setWindowHeight(height);
		window4.setWindowFrame(frameTexture);
		window4.setWindowPattern(patternTexture);
		window4.configureWindowInfo();
		window4.setPatternOffsetX(6);
		window4.setPatternOffsetY(6);
		window4.setPosition(0, height*2);
		
		window5 = new Window();
		window5.setWindowWidth(width);
		window5.setWindowHeight(height);
		window5.setWindowFrame(frameTexture);
		window5.setWindowPattern(patternTexture);
		window5.configureWindowInfo();
		window5.setPatternOffsetX(6);
		window5.setPatternOffsetY(6);
		window5.setPosition(0, height*3);
		
		window6 = new Window();
		window6.setWindowWidth(800-width);
		window6.setWindowHeight(600);
		window6.setWindowFrame(frameTexture);
		window6.setWindowPattern(patternTexture);
		window6.configureWindowInfo();
		window6.setPatternOffsetX(6);
		window6.setPatternOffsetY(6);
		window6.setPosition(width, 0);
		
		bgColor = Color.black;
	}
	
	@Override
	public void gameUpdate() 
	{
		// change background color every mouse click.
		if(mouseHandler.isClicked(MouseInputHandler.MOUSE_BUTTON_1))
		{
			int x = mouseHandler.getX();
			int y = mouseHandler.getY();
			String sX = "";
			String sY = "";
			
			if(x < 100)
				sX += "0";
			if(x < 10)
				sX += "0";
			sX += x;
			System.out.println("X: " + x + ", " + sX);
			
			if(y < 100)
				sY += "0";
			if(y < 10)
				sY += "0";
			sY += y;
			System.out.println("Y: " + y + ", " + sY);
			
			String hex = "#" + sX + sY;
			System.out.println(hex);
			
			bgColor = Color.decode(hex);
			
			//bgColor = Color.red;
		}
	}
	
	@Override
	public void gameRender() 
	{
		super.gameRender();
		dbg.setFont(font);
		dbg.setColor(bgColor);
		dbg.fillRect(0, 0, 800, 600);
		
		//window.draw(dbg);
		//window2.draw(dbg);
		//window3.draw(dbg);
		//window4.draw(dbg);
		//window5.draw(dbg);
		//window6.draw(dbg);
		//dbg.setColor(Color.black);
		//dbg.drawRect(0, 0, width, height);
		
		dbg.setColor(Color.white);
		dbg.drawString("FPS: " + df.format(getFPS()), 0, fontInfo.getHeight());
		dbg.drawString("Mouse: " + mouseHandler.getX() + "," + mouseHandler.getY(), 0, fontInfo.getHeight()*2);
		
	}
	
	/* -- End life cycle methods -- */
	
}
