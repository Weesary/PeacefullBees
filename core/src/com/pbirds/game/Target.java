package com.pbirds.game;

import com.badlogic.gdx.graphics.Texture;

public class Target {
	private float posX, posY, health;
	private Texture texture;
	public Target(float posX, float posY, float health, Texture texture) {
		this.posX = posX;
		this.posY = posY;
		this.health = health;
		this.texture = texture;
	}
	public float getPosX() {
		return this.posX;
	}
	public float getPosY() {
		return this.posY;
	}
	public float getHealth() {
		return this.health;
	}
	
}
