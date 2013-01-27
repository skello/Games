package com.jakehorsfield.asteroids;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Ship
{
	private int xPos;
	private int yPos;
	private int xDir;
	private int yDir;
	private int width;
	private int height;
	private Image image;
	public int speed;
	
	public Ship(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		xDir = 0;
		yDir = 0;
		ImageIcon ii = new ImageIcon("res/ship.png");
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		speed = 5;
	}
	
	public void move()
	{
		xPos += xDir;
		yPos += yDir;
		
		checkCollisions();
	}
	
	public void checkCollisions()
	{
		if (xPos <= 0)
			xPos = 0;
		if (xPos >= (800 - width))
			xPos = (800 - width);
		if (yPos <= 0)
			yPos = 0;
		if (yPos >= (600 - (height + (height / 2))))
			yPos = (600 - (height + (height / 2)));
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, xPos, yPos, null);
	}
	
	public void setXDir(int xDir)
	{
		this.xDir = xDir;
	}
	
	public void setYDir(int yDir)
	{
		this.yDir = yDir;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public int getXDir()
	{
		return xDir;
	}
	
	public int getYDir()
	{
		return yDir;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(xPos, yPos, width, height);
	}
}
