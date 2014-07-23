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
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import com.matisse.world.Game;

public class Renderer {
	private Engine engine;
	private Game game;

	public void create(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}
	
	public void update() {
		clear();
		render3D();
		render2D();		
		Display.sync(engine.getFPS());
		Display.update();
	}
	
	private void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private void render3D() {
		glMatrixMode(GL_PROJECTION); 
		glLoadIdentity(); 
		GLU.gluPerspective(engine.getFOV(), (float) Display.getWidth() / (float) Display.getHeight(), engine.getNear(), engine.getFar());
		glEnable(GL_DEPTH_TEST);
	}
	
	private void render2D() {
		glMatrixMode(GL_PROJECTION); 
		glLoadIdentity(); 
		glOrtho(0, engine.getWidth(), engine.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW); 
		glDisable(GL_DEPTH_TEST);
	}
}
