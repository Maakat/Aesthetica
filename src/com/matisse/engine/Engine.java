package com.matisse.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.matisse.world.Game;

public class Engine {
	private int width, height, fps;
	private float fov, near, far;
	private boolean running;
	
	private Renderer renderer;
	private Game game;
	private State state;
	
	public void createWindow(String title, int width, int height, int fps, float fov, float near, float far) {
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.fov = fov;
		this.near = near;
		this.far = far;

		game = new Game();
		game.create("null");
		
		renderer = new Renderer();
		renderer.create(this, game);
		
		state = State.LOADING;
		
		running = false;
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);			
			Display.setVSyncEnabled(true);		
			Display.create();
		} catch (LWJGLException e) {
			exitOnError("Could not create display.", 0, e);
		}
		
		running = true;
		state = State.START;
	}
	
	public void run() {
		
		while(running) {
			renderer.update();
		}
		exit(0);
	}
	
	public void exit(int status) {
		System.out.println("Closing under status: " + status + ".");
		Display.destroy();
		System.exit(status);
	}
	
	public void exitOnError(String reason, int status, Exception e) {
		System.err.println(reason);
		System.err.println("Closing under status: " + status + ".");
		Display.destroy();
		e.printStackTrace();
		System.exit(status);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public float getFOV() {
		return fov;
	}
	
	public float getNear() {
		return near;
	}
	
	public float getFar() {
		return far;
	}
}
