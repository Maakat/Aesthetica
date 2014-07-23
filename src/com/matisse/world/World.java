package com.matisse.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.matisse.engine.Engine;

public class World {
	private List<Cube> cubes;
	private float[][] height;
	private float width, length;
	
	public void create(Engine engine, String name, String mapLocation) {
		//engine.addWorld(this);
		
		if(cubes == null)
			cubes = new ArrayList<Cube>();
		
		try {
			BufferedImage image = ImageIO.read(new File(mapLocation));
			height = new float[image.getWidth()][image.getHeight()];
			width = image.getWidth()*0.25f;
			length = image.getHeight()*0.25f;
			
			for(int x=0;x<image.getWidth();x++)
				for(int y=0;y<image.getHeight();y++)
					height[x][y] = (new Color(image.getRGB(x, y)).getRed())/255f;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		for(int x=0;x<height.length;x++){
			for(int y=0;y<height[x].length;y++){
				float heightmapExaggeration = 16;
				Random random = new Random();
				glBegin(GL_QUADS);
				glColor3f(0, random.nextInt(), 0);
				glVertex3f(x*0.25f, getHeightAt(x, y)*heightmapExaggeration, y*0.25f);
				glVertex3f((x+1)*0.25f, getHeightAt(x+1, y)*heightmapExaggeration, y*0.25f);
				glVertex3f((x+1)*0.25f, getHeightAt(x+1, y+1)*heightmapExaggeration, (y+1)*0.25f);
				glVertex3f(x*0.25f, getHeightAt(x, y+1)*heightmapExaggeration, (y+1)*0.25f);
				glEnd();
			}
		}
	}
	
	public float calculateHeight(float x, float z) {
		float x1, x2, y1, y2, Q11, Q12, Q21, Q22;
		x1 = (int) Math.floor(x);
		x2 = (int) Math.floor(x+1);
		y1 = (int) Math.floor(z);
		y2 = (int) Math.floor(z+1);
		
		Q11 = getHeightAt((int) x1, (int) y1);
		Q12 = getHeightAt((int) x1, (int) y2);
		Q21 = getHeightAt((int) x2, (int) y1);
		Q22 = getHeightAt((int) x2, (int) y2);

		return (1f/( (x2-x1)*(y2-y1) ))*( Q11*(x2-x)*(y2-z)+Q21*(x-x1)*(y2-z)+Q12*(x2-x)*(z-y1)+Q22*(x-x1)*(z-y1));
	}

	public float getHeightAt(float x, float y){
		try{
			return height[(int) x][(int) y];
		}catch (Exception e) {
			return 0;
		}	
	}
	
	public void addCube(Cube cube) {
		if(cubes == null) cubes = new ArrayList<Cube>();
		cubes.add(cube);
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getLength() {
		return length;
	}
}
