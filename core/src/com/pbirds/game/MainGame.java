package com.pbirds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MainGame extends Game implements InputProcessor {
	
	Texture pigTexture, targetTexture, tirachinasTexture, bgTexture;
	Sprite pigSprite;
	SpriteBatch batch;
	Target target;
	Pig pig;
	World world;
	Body pigBody;
	Vector2 gravedad;
	
	final float PIXELS_TO_METERS = 100f;
	
	@Override
	public void create () {
		// Establecemos la gravedad
		gravedad = new Vector2(0, -9.8f);
		
		// Creamos un batch para dibujar en la pantalla
		batch = new SpriteBatch();
				
		// Objetivo al que el cerdito tiene que dar
		target = new Target(870, 52, 100, pigTexture);
		
		// El cerdito que va a ser lanzado
		pig = new Pig(135, 115, 1);
		
		pigTexture = new Texture("pchela.png");
		pigSprite = new Sprite(pigTexture);
		pigSprite.setSize(64, 64);
		pigSprite.setPosition(pig.getPosX(), pig.getPosY());		// Volver a cambiar
		
		targetTexture = new Texture("winnie.png");
		tirachinasTexture = new Texture("tirachinas.png");
		bgTexture = new Texture("background.jpg");
		
		world = new World(new Vector2(0, 0), true);
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		
		bodyDef.position.set((pigSprite.getX() + pigSprite.getWidth()/2) / PIXELS_TO_METERS, (pigSprite.getY() + pigSprite.getHeight()/2) / PIXELS_TO_METERS);

		// Create our body in the world using our body definition
		pigBody = world.createBody(bodyDef);
		
		CircleShape pigShape = new CircleShape();
		pigShape.setRadius(3.2f);
       
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = pigShape;
        fixtureDef.density = 0.1f;
        pigBody.createFixture(fixtureDef);
        pigShape.dispose();		
		
		Gdx.input.setInputProcessor(this);
		
		

		// Create a circle shape and set its radius to 6
		
		
		
		
	}
	
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		pigSprite.setPosition((pigBody.getPosition().x) * PIXELS_TO_METERS, (pigBody.getPosition().y) * PIXELS_TO_METERS);
				
		// Empezamos a dibujar
		batch.begin();
		batch.draw(bgTexture, 0, -150);
		batch.draw(targetTexture, target.getPosX(), target.getPosY(), 128, 128);
//		batch.draw(pigSprite, pig.getPosX(), pig.getPosY(), 64, 64);	// dibujamos al cerdo
		pigSprite.draw(batch);
		
		
		// Acabamos de dibujar
		batch.end();
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {		// Metodo para limpiar Texturas no usadas y no sobrecargar recursos
		pigTexture.dispose();
		targetTexture.dispose();
		tirachinasTexture.dispose();
		bgTexture.dispose();
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.SPACE) {
			pigBody.applyLinearImpulse(17.0f, 20.0f, pigBody.getPosition().x, pigBody.getPosition().y, true);
			world.setGravity(gravedad);
		}
		return true;
	}
	
	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
