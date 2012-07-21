package com.ProjectDragoon.testpanels;

import java.awt.Component;


public class SimpleComponent extends Component{

	private static final long serialVersionUID = 1L;
	
	public String s;

	public SimpleComponent()
	{
		super();
		s = "";
	}
	
	public void printParentName()
	{
		String s = ((BattlePanel)this.getParent()).NAME;
		System.out.println(s);
	}
	
	public void printString()
	{
		System.out.println(s);
	}
	
}
