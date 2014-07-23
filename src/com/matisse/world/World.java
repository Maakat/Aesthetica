package com.matisse.world;

import java.util.ArrayList;
import java.util.List;

public class World {
	public List<Cube> cubes;
	
	public void create() {
		if(cubes == null) cubes = new ArrayList<Cube>();
	}
	
	public void render() {
		
	}
	
	public void addCube() {
		if(cubes == null) cubes = new ArrayList<Cube>();
	}
}
