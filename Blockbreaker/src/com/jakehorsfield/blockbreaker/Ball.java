package com.jakehorsfield.blockbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball
{
	private int x;
	private int y;
	private int d;
	private int dx;
	public int dy;
	public int vel;
	public boolean dead = false;
	private Color color;
	
	public Ball(int x, int y, int d, Color color)
	{
		this.x = x;
		this.y = y;
		this.d = d;
		this.color = color;
		vel = 6;
		dx = vel;
		dy = vel;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setVel(int vel)
	{
		this.vel = vel;
	}
	
	public void move()
	{
		x += dx;
		y += dy;
		checkCollision();
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, d, d);
	}
	
	public void setXDir(int dx)
	{
		this.dx = dx;
	}
	
	public void setYDir(int dy)
	{
		this.dy = dy;
	}
	
	public void checkCollision()
	{
		if (x <= 0)
		{
			dx = -dx;
		}
		if (x >= (800 - d))
		{
			dx = -dx;
		}
		if (y >= (600 - d))
		{
			dead = true;
		}
		if (y <= 0)
		{
			dy = -dy;
		}
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x, y, d, d);
		g.setColor(Color.gray);
		g.drawOval(x, y, d, d);	
	}
}
