package com.ProjectDragoon.util;

import java.io.Serializable;

public class Vector implements Serializable{

	private static final long serialVersionUID = 1L;

	private double x, y, z;
	
	/*
	 * Constructors
	 */
	
	public Vector()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector(double x, double y, double z)
	{
		set(x,y,z);
	}
	
	public Vector(int x, int y, int z)
	{
		set((double)x, (double)y, (double)z);
	}
	
	public Vector(Vector v)
	{
		set(v);
	}
	
	/* -- End Constructors -- */
	
	/*
	 * Selectors and Mutators
	 */
	
	/* X */
	public void setX(double x)
	{
		this.x = x;
	}
	public void setX(int x)
	{
		this.x = (double)x;
	}
	public double getX()
	{
		return x;
	}
	
	/* Y */
	public void setY(double y)
	{
		this.y = y;
	}
	public void setY(int y)
	{
		this.y = (double)y;
	}
	public double getY()
	{
		return y;
	}
	
	/* Z */
	public void setZ(double z)
	{
		this.z = z;
	}
	public void setZ(int z)
	{
		this.z = (double)z;
	}
	public double getZ()
	{
		return z;
	}
	
	public void set(double x, double y, double z)
	{
		setX(x);
		setY(y);
		setZ(z);
	}
	public void set(int x, int y, int z)
	{
		set((double)x, (double)y, (double)z);
	}
	public void set(Vector v)
	{
		set(v.getX(), v.getY(), v.getZ());
	}
	
	/* -- End Selectors and Mutators -- */
	
	/*
	 * Plain Movement
	 */
	
	public void moveX(double mx)
	{
		x += mx;
	}
	
	public void moveY(double my)
	{
		y += my;
	}
	
	public void moveZ(double mz)
	{
		z += mz;
	}
	
	public void move(double mx, double my, double mz)
	{
		moveX(mx);
		moveY(my);
		moveZ(mz);
	}
	
	/**
	 * 2-dimensional version of move(). Only moves in the x,y, ignoring z
	 * @param mx
	 * @param my
	 */
	public void move(double mx, double my)
	{
		moveX(mx);
		moveY(my);
	}
	
	/* -- End movements -- */
	
	/*
	 * Comparison and other Math methods
	 */
	
	public void Add(Vector v)
	{
		x += v.getX();
		y += v.getY();
		z += v.getZ();
	}
	
	public void Subtract(Vector v)
	{
		x -= v.getX();
		y -= v.getY();
		z -= v.getZ();
	}
	
	public void Multiply(Vector v)
	{
		x *= v.getX();
		y *= v.getY();
		z *= v.getZ();
	}
	
	public void Divide(Vector v)
	{
		x /= v.getX();
		y /= v.getY();
		z /= v.getZ();
	}
	
	public boolean Equals(Vector v)
	{
		return (
				(((v.getX() - 0.0001f) < x) && (x < (v.getX() + 0.0001f))) &&
				(((v.getY() - 0.0001f) < y) && (y < (v.getY() + 0.0001f))) &&
				(((v.getZ() - 0.0001f) < z) && (z < (v.getZ() + 0.0001f)))
				);
	}
	
	/**
	 * Distance only coded for 2D
	 * @param v
	 * @return
	 */
	public double Distance(Vector v)
	{
		double a = (v.getX() - x);
		double b = (v.getY() - y);
		return Math.sqrt(a*a + b*b);
	}
	
	/**
	 * Vector length is the distance from the origin
	 * @return
	 */
	public double Length()
	{
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double DotProduct(Vector v)
	{
		return ((x * v.getX()) + (y * v.getY()) + (z * v.getZ())); 
	}
	
	/**
	 * cross/Vector product is used to calculate the normal
	 * @param v
	 * @return
	 */
	public Vector CrossProduct(Vector v)
	{
		double nx = (y * v.getZ()) - (z * v.getY());
		double ny = (z * v.getY()) - (x * v.getZ());
		double nz = (x * v.getY()) - (y * v.getX());
		
		return (new Vector(nx, ny, nz));
	}
	
	/**
	 * Calculate the normal angle of the Vector
	 * @return
	 */
	public Vector Normal()
	{
		double length;
		if(this.Length() == 0)
			length = 0;
		else
			length = 1 / this.Length();
		
		double nx = x * length;
		double ny = y * length;
		double nz = z * length;
		return (new Vector(nx, ny, nz));
	}
	
	/* -- End Comparison/Math -- */
}
