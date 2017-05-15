package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;
	
	public int tickCount = 0;
	private Thread thread;
	private boolean running = false;

	//main constructor
	public Main(){
		new Window(WIDTH, HEIGHT, "NeonTowerDefense credits: Bram en Jason", this);
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
	}
	
	//run method
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		//main game loop
		while(running = true){
			long now = System.nanoTime();
			delta += (now- lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >=1){
				ticks++;
				tick();
				delta--;
				shouldRender = true;
			}
			
			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			if(shouldRender){
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >=1000){
				lastTimer += 1000;
				System.out.println(ticks + "ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
			
			
		}
		
	}
	
	//tick method
	private void tick(){
		tickCount++;
	}
	
	//Render method
	private void render(){
		//BufferStrategy
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		bs.show();
		
		
	}

	//main method
	public static void main(String[] args) {
		new Main().start();
	}
}
