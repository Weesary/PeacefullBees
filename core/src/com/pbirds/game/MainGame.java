package com.pbirds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class MainGame extends Game implements InputProcessor {
	
	Texture beeTexture, winnieTexture, tirachinasTexture, bgTexture;
	Sprite beeSprite, winnieSprite;
	SpriteBatch batch;
	World world;
	OrthographicCamera cam;
	Body beeBody, winnieBody, floorBody;
	Vector2 gravedad;
	Vector2 throwVector;
	Vector2 initialPosBee;
	
	MouseJointDef jointDef;
	
	final float PTM = 75f;
	
	@Override
	public void create () {
		// Establecemos la gravedad
		gravedad = new Vector2(0, -9.8f);
		
		// Creamos un batch para dibujar en la pantalla
		batch = new SpriteBatch();
		// La camara es necesaria, en la documentacion de libgdx hay cosas interesantes
		cam = new OrthographicCamera(1080, 720);
		cam.setToOrtho(false, 1080*1.7f, 720*1.7f);
		
		jointDef = new MouseJointDef();
		jointDef.bodyA = floorBody;
		jointDef.collideConnected = true;
		jointDef.maxForce = 500;
		
		
		// Creamos a la abeja
		beeTexture = new Texture("pchela.png");
		beeSprite = new Sprite(beeTexture);
		
		// Creamos al osito
		winnieTexture = new Texture("winnie.png");
		winnieSprite = new Sprite(winnieTexture);
		
		beeSprite.setSize(64, 64);
		beeSprite.setPosition(115, 115);
		
		
		
		winnieSprite.setSize(128, 128);
		winnieSprite.setPosition(800, 115);
		
		tirachinasTexture = new Texture("tirachinas.png");
		bgTexture = new Texture("background.jpg");
		
		// Creamos un mundo de box2d
		world = new World(new Vector2(0, 0), true);
		
				
		// Primero tenemos que definir los cuerpos
		BodyDef beeBodyDef = new BodyDef();
		BodyDef winnieBodyDef = new BodyDef();
		BodyDef floorBodyDef = new BodyDef();
		
		// El tipo de cuerpo - dinamico, para el suelo haremos estatico
		beeBodyDef.type = BodyType.DynamicBody;
		winnieBodyDef.type = BodyType.DynamicBody;
		floorBodyDef.type = BodyType.StaticBody;
		
		// Establecemos posicion inicial en el mundo para nuestros cuerpos
		beeBodyDef.position.set((beeSprite.getX() + beeSprite.getWidth()) / PTM, (beeSprite.getY() + beeSprite.getHeight()) / PTM);
		// Recordamos la posicion inicial de la abeja para luego calcular la fuerza de tension del tirachinas
		initialPosBee = new Vector2(beeBodyDef.position);
		
		winnieBodyDef.position.set((winnieSprite.getX() + winnieSprite.getWidth()) / PTM, (winnieSprite.getY() + winnieSprite.getHeight()) / PTM);
		floorBodyDef.position.set(0, 0.3f);

		// Creamos los cuerpos partiendo de las definiciones de cuerpo
		beeBody = world.createBody(beeBodyDef);
		winnieBody = world.createBody(winnieBodyDef);
		winnieBody.setSleepingAllowed(false);
		floorBody = world.createBody(floorBodyDef);
		
		// Creamos formas para nuestras texturas
		// Circulo estaria bien para empezar
		CircleShape beeShape = new CircleShape();
		CircleShape winnieShape = new CircleShape();
		PolygonShape floorShape = new PolygonShape();
		// Del radio depende la masa del objeto
		beeShape.setRadius(0.4f);
		winnieShape.setRadius(0.3f);
		floorShape.setAsBox(Gdx.graphics.getWidth()*2f, 0f);
      
		FixtureDef beeFixtureDef = new FixtureDef();
		FixtureDef winnieFixtureDef = new FixtureDef();
		FixtureDef floorFixtureDef = new FixtureDef();
		
		// FixtureDef.density influye en el peso del cuerpo
        beeFixtureDef.shape = beeShape;
        beeFixtureDef.density = 5f;
        beeFixtureDef.friction = 1f;
        beeBody.createFixture(beeFixtureDef);
        
        winnieFixtureDef.shape = winnieShape;
        winnieFixtureDef.density = 2f;
        winnieFixtureDef.friction = 1f;
        winnieBody.createFixture(winnieFixtureDef);
        
        floorFixtureDef.shape = floorShape;
        floorFixtureDef.density = 1f;
        floorFixtureDef.friction = 1f;
        floorBody.createFixture(floorFixtureDef);
        
        beeShape.dispose();
        winnieShape.dispose();
        floorShape.dispose();
		
		Gdx.input.setInputProcessor(this);
		
		
	}
	
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		beeSprite.setPosition((beeBody.getPosition().x) * PTM, (beeBody.getPosition().y) * PTM);
		winnieSprite.setPosition((winnieBody.getPosition().x) * PTM, (winnieBody.getPosition().y) * PTM);
				
		// Empezamos a dibujar
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		batch.draw(bgTexture, 0, -150);
		batch.draw(tirachinasTexture, 155, 55, 64, 128);
		beeSprite.draw(batch);
		winnieSprite.draw(batch);
		winnieBody.setLinearVelocity(winnieBody.getLinearVelocity().x*0.99f, winnieBody.getLinearVelocity().y);
		beeBody.setLinearVelocity(beeBody.getLinearVelocity().x*0.99f, beeBody.getLinearVelocity().y);
		
		// Acabamos de dibujar
		batch.end();
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {		// Metodo para limpiar Texturas no usadas y no sobrecargar recursos
		beeTexture.dispose();
		winnieTexture.dispose();
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
			world.setGravity(gravedad);
			beeBody.applyLinearImpulse(23.0f, 20.0f, beeBody.getPosition().x, beeBody.getPosition().y, true);
			
		}
		if(keycode == Input.Keys.F) {
			beeBody.setLinearVelocity(0, 0);
			beeBody.setAngularVelocity(0);
			beeBody.setTransform(initialPosBee, 0);
		}
		return true;
	}
	
	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		Vector3 mousePos = new Vector3(screenX, screenY, 0);
		cam.unproject(mousePos);
		throwVector = new Vector2(-(mousePos.x - beeBody.getPosition().x*PTM)*15, -(mousePos.y - beeBody.getPosition().y*PTM)*15);
		if (throwVector.x < 0 || throwVector.y < 0) return true;
		Gdx.app.log("Mouse: ", mousePos.x + ", " + mousePos.y);
		Gdx.app.log("Body: ", beeBody.getPosition().x + ", " + beeBody.getPosition().y);
//		Vector2 zeroVector = new Vector2(0, 0);
		beeBody.applyForceToCenter(throwVector, true);
		beeBody.setAngularVelocity(0);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return true;
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
