package com.ProjectDragoon.physics;

import java.awt.Color;
import java.awt.Graphics;

import com.ProjectDragoon.util.Vector;

public class CollisionPointMap {

	public Vector position;
	public Vector A;
	public Vector B;
	
	public Vector C;
	public Vector D;
	
	public Vector T;
	
	public Vector H;
	public Vector H1;
	public Vector H2;
	
	public CollisionPointMap()
	{
		position = new Vector();
		A = new Vector();
		B = new Vector();
		C = new Vector();
		D = new Vector();
		T = new Vector();
		H = new Vector();
		H1 = new Vector();
		H2 = new Vector();
	}
	
	// Selectors & Mutators
	
	// End S&M
	
	public Vector A()
	{
		Vector v = new Vector(position);
		v.Add(A);
		return v;
	}
	
	public Vector B()
	{
		Vector v = new Vector(position);
		v.Add(B);
		return v;
	}
	
	public Vector C()
	{
		Vector v = new Vector(position);
		v.Add(C);
		return v;
	}
	
	public Vector D()
	{
		Vector v = new Vector(position);
		v.Add(D);
		return v;
	}
	
	public Vector H()
	{
		Vector v = new Vector(position);
		v.Add(H);
		return v;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.red);
		Vector tmp;
		int x;
		int y;
		//A
		tmp = A();
		x = (int)tmp.getX();
		y = (int)tmp.getY();
		g.drawLine(x, y, x, y);
		//B
		tmp = B();
		x = (int)tmp.getX();
		y = (int)tmp.getY();
		g.drawLine(x, y, x, y);
		//C
		tmp = C();
		x = (int)tmp.getX();
		y = (int)tmp.getY();
		g.drawLine(x, y, x, y);
		//D
		tmp = D();
		x = (int)tmp.getX();
		y = (int)tmp.getY();
		g.drawLine(x, y, x, y);
		
		//H
		tmp = H();
		x = (int)tmp.getX();
		y = (int)tmp.getY();
		g.drawLine(x, y, x, y);
	}
}
