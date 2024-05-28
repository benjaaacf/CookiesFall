package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final CookiesFallMenu game;
        private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Monstruo monstruo;
	private Caida caida;
        private Texture backgroundTexture;


	   
	//boolean activo = true;

	public GameScreen(final CookiesFallMenu game) {
            this.game = game;
            this.batch = game.getBatch();
            this.font = game.getFont();
             
            // Cargar la textura de fondo
            backgroundTexture = new Texture(Gdx.files.internal("perso7.png")); 
            
            // load the images for the droplet and the bucket, 64x64 pixels each 
            Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("sonidoVomito.mp3"));
            Sound eatSound = Gdx.audio.newSound(Gdx.files.internal("comiendo2.mp3"));
            monstruo = new Monstruo(new Texture(Gdx.files.internal("monstruo.png")),hurtSound, eatSound);
         
            // load the drop sound effect and the rain background "music" 
            Texture gota = new Texture(Gdx.files.internal("cookie.png"));
            Texture vegetal = new Texture(Gdx.files.internal("vegetal.png"));

            Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        
	    Music musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musicaFondoGood.mp3"));
            caida = new Caida(gota, vegetal, dropSound, musicaFondo);

            // camara
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            batch = new SpriteBatch();
            
            // creacion del mounstro y caida
            monstruo.crear();
            caida.crear();
	}

	@Override
	public void render(float delta) {
            // Limpiar la pantalla
            ScreenUtils.clear(0, 0, 0.2f, 1);

            // Actualizar la camara
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            // Comenzar el batch
            batch.begin();

            // Dibujar la textura de fondo
            batch.draw(backgroundTexture, 0, 0);

            // Dibujar los textos
            font.draw(batch, "Galletas devoradas: " + monstruo.getPuntos(), 5, 475);
            font.draw(batch, "Vidas : " + monstruo.getVidas(), 670, 475);
            font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

            if (!monstruo.estaHerido()) {
                // Movimiento del monstruo desde teclado
                monstruo.actualizarMovimiento();

                // Caida de objetos
                if (!caida.actualizarMovimiento(monstruo)) {
                    // Actualizar HigherScore
                    if (game.getHigherScore() < monstruo.getPuntos())
                        game.setHigherScore(monstruo.getPuntos());

                    // Ir a la ventana de fin de juego y destruir la actual
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }
            }

            // Dibujar el monstruo y los objetos
            monstruo.dibujar(batch);
            caida.actualizarDibujoLluvia(batch);

            // Terminar el batch
            batch.end();
    }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de fondo
	  caida.continuar();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		caida.pausar();
		game.setScreen(new PausaScreen(game, this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
      monstruo.destruir();
      caida.destruir();

	}

}
