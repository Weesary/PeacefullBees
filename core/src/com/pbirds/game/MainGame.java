package com.pbirds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MainGame extends Game {
	
	Texture pigTexture, targetTexture, boulderTexture, tirachinasTexture, bgTexture;
	Sprite pigSprite;
	SpriteBatch batch;
	Target target;
	Pig pig;
	World world;
	Body pigBody;
	
	@Override
	public void create () {
		// Creamos un batch para dibujar en la pantalla
		batch = new SpriteBatch();
				
		// Objetivo al que el cerdito tiene que dar
		target = new Target(950, 52, 100, pigTexture);
		
		// El cerdito que va a ser lanzado
		pig = new Pig(135, 115, 1);
		
		pigTexture = new Texture("pig.png");
		pigSprite = new Sprite(pigTexture);
		pigSprite.setSize(64, 64);
		pigSprite.setPosition(pig.getPosX(), pig.getPosY());		// Volver a cambiar
		
		targetTexture = new Texture("red_target.png");
		tirachinasTexture = new Texture("tirachinas.png");
		bgTexture = new Texture("background.jpg");
		
		// Creamos un mundo para usar Box2d
		world = new World(new Vector2(0, -10), true);
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(pigSprite.getX(), pigSprite.getY());

		// Create our body in the world using our body definition
		pigBody = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		
		
		
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		pigSprite.setPosition(pigBody.getPosition().x, pigBody.getPosition().y);
				
		// Empezamos a dibujar
		batch.begin();
		batch.draw(bgTexture, 0, -150);
//		batch.draw(pigSprite, pig.getPosX(), pig.getPosY(), 64, 64);	// dibujamos al cerdo
		pigSprite.draw(batch);
//		batch.draw(targetTexture, target.getPosX(), target.getPosY(), 64, 64);
		
		
		// Acabamos de dibujar
		batch.end();
		world.step(1/24f, 6, 2);
	}
	
	@Override
	public void dispose () {		// Metodo para limpiar Texturas no usadas y no sobrecargar recursos
		pigTexture.dispose();
		targetTexture.dispose();
		tirachinasTexture.dispose();
		bgTexture.dispose();
		world.dispose();
		
		
	}
}
