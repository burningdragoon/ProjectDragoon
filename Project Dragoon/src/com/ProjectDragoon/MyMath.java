package com.ProjectDragoon;

import com.ProjectDragoon.util.Vector;

public final class MyMath {

	private static double PI = 3.145926535;
	private static double PI_over_180 = PI / 180.0f;
	private static double PI_under_180 = 180.0f / PI;
	
	public static double toDegrees(double radians)
	{
		return radians * PI_under_180;
	}
	
	public static double toRadians(double degrees)
	{
		return degrees * PI_over_180;
	}
	
	public static double wrapAngleDegrees(double degrees)
	{
		double result = degrees % 360.0f;
		if(result < 0)
			result += 360.0f;
		return result;
	}
	
	public static double wrapAngleRadians(double radians)
	{
		double result = radians % PI;
		if(result < 0)
			result += PI;
		return result;
	}
	
	public static double LinearVelocityX(double angle)
	{
		angle -= 90;
		if (angle < 0)
			angle = 360 + angle;
		return Math.cos(toRadians(angle));
	}
	
	public static double LinearVelocityY(double angle)
	{
		angle -= 90;
		if(angle < 0)
			angle = 360 + angle;
		return Math.sin(toRadians(angle));
	}
	
	public static Vector LinearVelocity(double angle)
	{
		double vx = LinearVelocityX(angle);
		double vy = LinearVelocityY(angle);
		return new Vector(vx, vy, 0.0f);
	}
	
	public static double AngleToTarget(double x1, double y1, double x2, double y2)
	{
		double deltaX = (x2 - x1);
		double deltaY = (y2 - y1);
		return Math.atan2(deltaY, deltaX);
	}
	
	public static double AngleToTarget(Vector source, Vector target)
	{
		return AngleToTarget(source.getX(), source.getY(), target.getX(), target.getY());
	}
	
	public static double Distance(double x1, double y1, double x2, double y2)
	{
		double deltaX = (x2 - x1);
		double deltaY = (y2 - y1);
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}
	
	public static double Distance(Vector v1, Vector v2)
	{
		return Distance(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}
	
	public static double Length(double x, double y, double z)
	{
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public static double Length(Vector v)
	{
		return Length(v.getX(), v.getY(), v.getZ());
	}
	
	public static double DotProduct(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return (x1*x2 + y1*y2 + z1*z2);
	}
	
	public static double DotProduct(Vector v1, Vector v2)
	{
		return DotProduct(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(), v2.getZ());
	}
	
	public static Vector CrossProduct(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double nx = (y1 * z2) - (z1 * y2);
		double ny = (z1 * y2) - (x1 * z2);
		double nz = (z1 * y2) - (y1 * x2);
		return (new Vector(nx, ny, nz));
	}
	
	public static Vector CrossProduct(Vector v1, Vector v2)
	{
		return CrossProduct(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(), v2.getZ());
	}

	public static Vector Normal(double x, double y, double z)
	{
		double length = Length(x,y,z);
		if(length != 0)
			length = 1 / length;
		double nx = x*length;
		double ny = y*length;
		double nz = z*length;
		return (new Vector(nx, ny, nz));
	}
	
	/**
	 * Convert value from nanoseconds to milliseconds
	 * @param ns the time value in nanoseconds
	 * @return
	 */
	public static long nanoToMilli(long ns)
	{
		return ns / 1000000l;
	}
	
	/**
	 * Converts value from milliseconds to nanoseconds
	 * @param ms the time value in milliseconds
	 * @return
	 */
	public static long milliToNano(long ms)
	{
		return ms * 1000000l;
	}
}
