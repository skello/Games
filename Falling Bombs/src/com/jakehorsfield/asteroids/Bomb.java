package com.jakehorsfield.asteroids;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bomb
{
	private int xPos;
	private int yPos;
	private int yDir;
	private int width;
	private int height;
	private Image image;
	
	public Bomb(int xPos, int yPos, int speed)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		yDir = speed;
		ImageIcon ii = new ImageIcon("res/bomb.gif");
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public void move()
	{
		yPos += yDir;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, xPos, yPos, null);
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
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
