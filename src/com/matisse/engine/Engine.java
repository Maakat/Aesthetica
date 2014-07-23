package com.matisse.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import com.matisse.controller.Camera;
import com.matisse.vec.Vector3;
import com.matisse.world.World;

public class Engine {
	private int width, height, fps;
	private float fov, near, far;
	private State state;
	private World world;
	private Camera camera;
	
	public void create(String title, int width, int height, int fps, float fov, float near, float far) {
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.fov = fov;
		this.near = near;
		this.far = far;

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);			
			Display.setVSyncEnabled(true);		
			Display.create();
		} catch (LWJGLException e) {
			exitOnError("Could not create display.", 0, e);
		}
		
		state = State.START;
	}
	
	public void run() {
		while(state != State.EXITING) {
			input();
			update();
			render();
		}
		exit(0);
	}
	
	private void input() {
		camera.input();
		camera.setPosition(new Vector3(camera.getPosition().x, world.calculateHeight(camera.getPosition().x * 2, camera.getPosition().z * 2) * -16, camera.getPosition().z));
		System.out.println(camera.getPosition().y);
	}
	
	private void update() {
		
	}
	
	private void render() {
		clear();
		render3D();
		render2D();
		Display.sync(fps);
		Display.update();
	}
	
	private void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.5f, 0.5f, 1, 1);
	}
	
	private void render3D() {
		glMatrixMode(GL_PROJECTION); 
		glLoadIdentity(); 
		GLU.gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), near, far);
		glEnable(GL_DEPTH_TEST);
	
		camera.translate();
		world.render();
	}
	
	private void render2D() {
		glMatrixMode(GL_PROJECTION); 
		glLoadIdentity(); 
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW); 
		glDisable(GL_DEPTH_TEST);
	}

	private void exit(int status) {
		System.out.println("Closing under status: " + status + ".");
		Display.destroy();
		System.exit(status);
	}
	
	private void exitOnError(String reason, int status, Exception e) {
		System.err.println(reason);
		System.err.println("Closing under status: " + status + ".");
		Display.destroy();
		e.printStackTrace();
		System.exit(status);
	}
	
	public void loadGame() {
		state = State.LOADING;
		world = new World();
		world.create(this, "Aesthetica", "resources/textures/height.jpg");
		camera = new Camera();
		camera.create(new Vector3(world.getWidth() / 2, -10, world.getLength() / 2), new Vector3(0, 0, 0));							
	}
}
