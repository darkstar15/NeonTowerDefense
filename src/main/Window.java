package main;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	private static final long serialVersionUID = 1L;

	public Window(int width, int height, String title, Main main){
		JFrame frame = new JFrame(title);
		
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		main.start();
	}
}
