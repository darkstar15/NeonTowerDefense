package main;

import java.awt.Canvas;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;

	public Main(){
		new Window(WIDTH, HEIGHT, "NeonTowerDefense credits: Bram en Jason", this);
	}
	
	public synchronized void start(){
	
	}
	
	public void run() {
		
	}

	
	public static void main(String[] args) {
		new Main();
	}
}
