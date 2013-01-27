package com.jakehorsfield.blockbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block
{
	private int x;
	private int y;
	private int w;
	private int h;
	private Color color;
	
	public Block(int x, int y, int w, int h, Color color)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	
	public int getX()
	{
		return x;
	}
	
	
	public int getY()
	{
		return y;
	}
	
	public int getW()
	{
		return w;
	}
	
	public int getH()
	{
		return h;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
	}
}
