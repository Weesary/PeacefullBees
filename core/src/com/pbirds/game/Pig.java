package com.pbirds.game;

public class Pig {
	private float posX, posY, velocity;
	public Pig(float posX, float posY, float velocity) {
		this.posX = posX;
		this.posY = posY;
		this.velocity = velocity;
	}
	
	public float getPosX() {
		return this.posX;
	}
	public float getPosY() {
		return this.posY;
	}
	public float getVelocity() {
		return this.velocity;
	}
}
