package com.pbirds.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pbirds.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Peacefull Birds";
	    config.width = 1080;
	    config.height = 720;
		new LwjglApplication(new MainGame(), config);
	}
}
