package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	
	public int tickCount = 0;
	private boolean running = false;
	
	private BufferedImage spriteSheet = null;
	private BufferedImage tank;
	
	private JFrame frame;
	
	//create a window
	public Main(){
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame("NeonTowerDefense credits: Bram en jason");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteSheet = loader.loadImage("/spriteSheet.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldn't load the file");
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		tank = ss.grabImage(2, 1, 32, 32);
	}
	
	//start thread
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	//stop thread
	public synchronized void stop(){
		running = false;
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		//main game loop
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime=now;
			boolean shouldRender = true;
			
			while(delta >=1){
				ticks ++;
				tick();
				delta--;
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
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(ticks + " ticks " + frames + " frames ");
				frames = 0;
				ticks = 0;
			}
			
			
			
		}
	}
	
	//tick method
	public void tick(){
		tickCount++;
	}
	
	//render method
	public void render(){
		//BufferedStrategy
		BufferStrategy bs =  getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.drawImage(tank, 100, 100, null);
		
		g.dispose();
		bs.show();
		
	}
	
	//main method
	public static void main(String[] args){
		new Main().start();
	}
	
}
