package com.jakehorsfield.asteroids;

import javax.swing.JFrame;

public class Frame extends JFrame
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Falling Bombs!";
	
	public Frame()
	{
		add(new Panel());
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Frame();
	}
}
