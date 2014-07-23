package com.matisse.game;

import com.matisse.engine.Engine;

public class Aesthetica {
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.createWindow("Aesthetica", 800, 600, 60, 0.001f, 10000f, 90);
		engine.run();
	}
}
