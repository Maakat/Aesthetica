package com.matisse.controller;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.matisse.vec.Vector3;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
	private Vector3 position, rotation;
	private float tempSpeed = 0.1f;

	public void create(Vector3 position, Vector3 rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public void input() {
		rotation.y += Mouse.getDX();
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.x += Math.sin(rotation.y*Math.PI/180)*tempSpeed;
			position.z += -Math.cos(rotation.y*Math.PI/180)*tempSpeed;
		}
	}
	
	public void translate() {
		input();
		glRotatef(rotation.y, 0, 1, 0);
		glTranslatef(-position.x, position.y, -position.z);
	}
	
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getRotation() {
		return rotation;
	}
}
