package com.matisse.menu;

import com.matisse.vec.Vector3;

public class ImageBox extends Box {
	String imageLocations[];
	public ImageBox(Vector3 position, Vector3 size, Vector3 color, String[] imageLocations) {
		super(position, size, color);
		this.imageLocations = imageLocations;
	}
}
