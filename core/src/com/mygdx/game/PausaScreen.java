package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class PausaScreen implements Screen {

	private final CookiesFallMenu game;
	private GameScreen juego;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private OrthographicCamera camera;

	public PausaScreen (final CookiesFallMenu game, GameScreen juego) {
		this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(210f / 255, 182f / 255, 244f / 255, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw(batch, "Pausa", 100, 150);
		font.draw(batch, "Click para continuar", 100, 100);
		batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(juego);
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

