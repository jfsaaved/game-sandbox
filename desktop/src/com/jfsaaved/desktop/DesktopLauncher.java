package com.jfsaaved.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jfsaaved.Sandbox;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Sandbox.WIDTH;
		config.height = (int) Sandbox.HEIGHT;
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new Sandbox(), config);
	}
}
