package com.jakehorsfield.asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener
{
	private Thread thread;
	private boolean running = false;
	private Image dbImage;
	private Image bgImage;
	private Image loseImage;
	private Ship ship;
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private int amountOfBombs = 0;
	private int missilesFired = 0;
	private int score = 1;
	private int lives = 3;
	private boolean dead = false;
	
	public Panel()
	{
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		
		ship = new Ship(400, 550);
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public void run()
	{
		while (running)
		{
			moveAllObjects();
			createBombs();
			checkCollisions();
			repaint();
			
			try { Thread.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	public void moveAllObjects()
	{
		ship.move(); // Move ship
		for (Missile m: missiles) // Move Missiles
		{
			m.move();
		}
		for (Bomb b: bombs) // Moves bombs
		{
			b.move();
		}
	}
	
	public void createBombs()
	{
		amountOfBombs = bombs.size();
		
		if (amountOfBombs < 10)
		{
			for (int i = amountOfBombs; i < 10; i++)
			{
				bombs.add(new Bomb(randomX(), 0, randomSpeed()));
			}
		}
	}
	
	public void checkCollisions()
	{
		for (Iterator<Missile> it = missiles.iterator(); it.hasNext(); ) // If missile hits top of screen
		{
			if (it.next().getY() <= 0)
				it.remove();
		}
		for (Iterator<Bomb> it = bombs.iterator(); it.hasNext(); ) // If bombs hits bottom of screen
		{
			if (it.next().getY() > 580)
				it.remove();
			if (it.next().getBounds().intersects(ship.getBounds()))
			{
				if (lives > 0)
				{
					lives -= 1;
					it.remove();
				}
				if (lives < 1)
				{
					dead = true;
				}
			}
		}
		
		for (int mi = 0; mi < missiles.size(); mi++)
		{
			for (int bo = 0; bo < bombs.size(); bo++)
			{
				if (bombs.get(bo).getBounds().intersects(missiles.get(mi).getBounds()))
				{
					bombs.remove(bo);
					score += 1;
				}
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		dbImage = createImage(getWidth(), getHeight());
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.BLACK);
		ImageIcon ii = new ImageIcon("res/background.jpg");
		bgImage = ii.getImage();
		g.drawImage(bgImage, 0, 0, null);
		
		{
		switch (lives) // Set text color according to amount of lives left
			{
			case 3:
				g.setColor(Color.green);
				break;
			case 2:
				g.setColor(Color.yellow);
				break;
			case 1:
				g.setColor(Color.red);
				break;
			case 0:
				g.setColor(Color.red);
				break;
			default:
				g.setColor(Color.white);
				break;
			}
			g.setFont(new Font("Hervana", Font.BOLD, 14)); // Draw player stats
			g.drawString("Score: " + score, 20, 30);
			g.drawString("Missiles fired: " + missilesFired, 20, 50);
		
			g.drawString("Lives: " + lives, 20, 70);
			
			ship.draw(g); // Draw ship
			
			for (Missile m: missiles) // Draw missiles
			{
				m.draw(g);
			}
			for (Bomb b: bombs) // Draw bombs
			{
				b.draw(g);
			}
		}
		
		if (dead)
		{
			ImageIcon ij = new ImageIcon("res/you_lose.jpg");
			loseImage = ij.getImage();
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(loseImage, 0, 0, null);
			thread.stop();
		}
		
		g.dispose();
	}
	
	public void fireMissile()
	{
		if (missiles.size() < 3)
		{
			missiles.add(new Missile(ship.getX() + (ship.getWidth() / 2), ship.getY() - (ship.getHeight() / 2)));
			missilesFired += 1;
		} else
		{
			return;
		}
	}
	
	public int randomX()
	{
		Random random = new Random();
		
		return random.nextInt(780);
	}
	
	public int randomSpeed()
	{
		Random random = new Random();
		return random.nextInt(score / 8 + 1) + 1;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP)
			ship.setYDir(-ship.speed);
		if (key == KeyEvent.VK_DOWN)
			ship.setYDir(ship.speed);
		if (key == KeyEvent.VK_LEFT)
			ship.setXDir(-ship.speed);
		if (key == KeyEvent.VK_RIGHT)
			ship.setXDir(ship.speed);
		if (key == KeyEvent.VK_SPACE)
			fireMissile();
		if (key == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP)
			ship.setYDir(0);
		if (key == KeyEvent.VK_DOWN)
			ship.setYDir(0);
		if (key == KeyEvent.VK_LEFT)
			ship.setXDir(0);
		if (key == KeyEvent.VK_RIGHT)
			ship.setXDir(0);
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
}
