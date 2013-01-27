package com.jakehorsfield.blockbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle
{
	private int x;
	public int y;
	public int w;
	private int h;
	private int dx;
	public int vel;
	
	public Paddle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		vel = 6;
		dx = 0;
	}
	
	public void move()
	{
		x += dx;
		checkCollision();
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, w, h);
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void decreaseWidth()
	{
		w -= 5;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setXDir(int dx)
	{
		this.dx = dx;
	}
	
	public void setWidth(int w)
	{
		this.w = w;
	}
	
	public void checkCollision()
	{
		if (x <= 0)
		{
			x = 0;
		}
		if (x >= 600 - (w / 2 - 48))
		{
			x = 600 - (w / 2 - 48);
		}
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		g.setColor(Color.cyan);
		g.drawRect(x, y, w, h);
	}
}
