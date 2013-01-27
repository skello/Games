package com.jakehorsfield.blockbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener, Runnable
{
	private Thread thread;
	private Paddle paddle;
	private Ball ball;
	private Image lose;
	private Image winImage;
	private Block[][] block = new Block[4][8];
	private Rectangle ballRect;
	private Rectangle paddleRect;
	private int score = 0;
	private int lives = 3;
	private boolean win = false;
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private int missilesFired = 0;
	
	public Panel()
	{	
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	
		initObjects();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void initObjects()
	{
		paddle = new Paddle(400 - (150), 525, 300, 30);
		ball = new Ball(400 - (25 / 2), 300, 25, Color.cyan);
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				block[y][x] = new Block(x * 100, y * 50, 100, 50, randomColor());
			}
		}
	}
	
	public void run()
	{
		while (true)
		{
			moveObjects();
			checkCollisions();
			checkForWin();
			repaint();
			try
			{
				Thread.sleep(20);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void checkCollisions()
	{
		ballRect = ball.getBounds();
		paddleRect = paddle.getBounds();
		if (ballRect.intersects(paddleRect))
		{
			ball.setYDir(-ball.vel);
			for (int y = 0; y < 4; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					block[y][x].setColor(randomColor());
				}
			}
		}
	}
	
	public void moveObjects()
	{
	    ball.move();
		paddle.move();
	}
	
	public void paintComponent(Graphics g)
	{
		Image dbImage = createImage(getWidth(), getHeight());
		g.drawImage(dbImage, 0, 0, null);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				block[y][x].draw(g);
			}
		}
		
		paddle.draw(g);
		ball.draw(g);
		
		if (lives == 3)
			g.setColor(Color.green);
		else if (lives == 2)
			g.setColor(Color.yellow);
		else if (lives == 1)
			g.setColor(Color.red);
		else
			g.setColor(Color.gray);
		g.drawString("Score: " + score, 20, 220);
		g.drawString("Lives: " + lives, 20, 240);
		g.drawString("Missiles Fired: " + missilesFired, 20, 260);
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (block[y][x].getBounds().intersects(ball.getBounds()))
				{
					ball.setYDir(-ball.dy);
					block[y][x].setX(1000);
					block[y][x].setY(1000);
					ball.setColor(block[y][x].getColor());
					score += 1;
				}
			}
		}
		
		if (win)
		{
			g.clearRect(0, 0, getWidth(), getHeight());
			ImageIcon ii = new ImageIcon("res/win.png");
			winImage = ii.getImage();
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(winImage, 0, 225, null);
		}
		
		if (ball.dead && lives <= 0)
		{
			g.clearRect(0, 0, getWidth(), getHeight());
			ImageIcon ii = new ImageIcon("res/lose.png");
			lose = ii.getImage();
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(lose, 0, 225, null);
			thread.stop();
		}
		if (ball.dead && lives > 0)
		{
			try
			{
				thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			ball.dead = false;
			lives -= 1;
			ball.setX(400 - (25 / 2));
			ball.setY(300);
			paddle.setX(250);
			paddle.setY(525);
		}
		
		for (Missile m: missiles)
		{
			m.draw(g);
			m.move();
			
			if (m.getY() == 0 || m.getY() == 600)
			{
				missiles.remove(m);
			}
			
			for (int y = 0; y < 4; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					if (block[y][x].getBounds().intersects(m.getBounds()))
					{
						m.setYDir(-m.dy);
						block[y][x].setX(1000);
						block[y][x].setY(1000);
						score += 1;
					}
				}
			}
		}
	}
	
	public void checkForWin()
	{
		if (score == 32)
		{
			win = true;
		}
	}
	
	public Color randomColor()
	{
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r, g, b);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT)
		{
			paddle.setXDir(-paddle.vel);
		}
		if (key == KeyEvent.VK_RIGHT)
		{
			paddle.setXDir(paddle.vel);
		}
		if (key == KeyEvent.VK_SPACE)
		{
			fire();
		}
	}
	
	public void fire()
	{
		if (missiles.size() > 1)
		{
			return;
		} else
		{
			missiles.add(new Missile((paddle.getX() + (paddle.w / 2)), paddle.y));
			missilesFired += 1;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT)
		{
			paddle.setXDir(0);
		}
		if (key == KeyEvent.VK_RIGHT)
		{
			paddle.setXDir(0);
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
}
