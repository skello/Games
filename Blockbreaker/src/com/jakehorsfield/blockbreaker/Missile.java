package com.jakehorsfield.blockbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile
{
	private int x;
	private int y;
	public int dy;
	private int vel;
	private int w;
	private int h;
	
	public Missile(int x, int y)
	{
		this.x = x;
		this.y = y;
		vel = -5;
		dy = vel;
		w = 10;
		h = 20;
	}
	
	public void setYDir(int dy)
	{
		this.dy = dy;
	}
	
	public void move()
	{
		y += dy;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);
	}
}
