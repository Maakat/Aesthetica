package com.matisse.world;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<World> worlds;
	
	public void create(String path) {
		if(worlds == null) worlds = new ArrayList<World>();
		//TODO This would load the game from a file.
	}
	
	public void addWorld(World world) {
		if(worlds == null) worlds = new ArrayList<World>();
		worlds.add(world);
	}
}
