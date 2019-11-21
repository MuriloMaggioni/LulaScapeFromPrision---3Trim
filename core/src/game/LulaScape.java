package game;

import Telas.Menu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LulaScape extends Game {
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 480;
	public static final float PPM = 100;

	//Box2D Bits de Colisão
	public static final short NADA_BIT = 0;
	public static final short TERRENO_BIT = 1;
	public static final short PERSO_BIT = 2;
	public static final short TIJOLO_BIT = 4;
	public static final short MOEDA_BIT = 8;
	public static final short QUEBRADO_BIT = 16;
	public static final short OBJETO_BIT = 32;
	public static final short INIMIGO_BIT = 64;
	public static final short CABECA_INIMIGO_BIT = 128;
	public static final short POWER_UP_BIT = 256;
	public static final short POWER_UP_BLOCO_BIT = 512;
	public static final short PERSO_CABECA_BIT = 1024;

	public SpriteBatch batch;

	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		//Seta os sons!
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/powerup.wav", Sound.class);
		manager.load("audio/sounds/powerdown.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.finishLoading();

		setScreen(new Menu(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

}
