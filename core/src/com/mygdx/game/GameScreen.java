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
	private Monstruo mounstro;
	private Caida caida;

	   
	//boolean activo = true;

	public GameScreen(final CookiesFallMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		  // load the images for the droplet and the bucket, 64x64 pixels each 	     
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		  mounstro = new Monstruo(new Texture(Gdx.files.internal("monstruo.png")),hurtSound);
         
	      // load the drop sound effect and the rain background "music" 
         Texture gota = new Texture(Gdx.files.internal("cookie.png"));
         Texture vegetal = new Texture(Gdx.files.internal("vegetal.png"));
         
         Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        
	     Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));
         caida = new Caida(gota, vegetal, dropSound, rainMusic);
	      
	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del mounstro
	      mounstro.crear();
	      
	      // creacion de la lluvia
	      caida.crear();
	}

	@Override
	public void render(float delta) {
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(10, 8, 0.2f, 1);//aquiiiiiiiiiiiiiiiiiiiii
		//actualizar matrices de la cámara
		camera.update();
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//dibujar textos
		font.draw(batch, "Galletas devoradas: " + mounstro.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + mounstro.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);
		
		if (!mounstro.estaHerido()) {
			// movimiento del mounstro desde teclado
	        mounstro.actualizarMovimiento();        
			// caida de la lluvia 
	       if (!caida.actualizarMovimiento(mounstro)) {
	    	  //actualizar HigherScore
	    	  if (game.getHigherScore()<mounstro.getPuntos())
	    		  game.setHigherScore(mounstro.getPuntos());  
	    	  //ir a la ventana de finde juego y destruir la actual
	    	  game.setScreen(new GameOverScreen(game));
	    	  dispose();
	       }
		}
		
		mounstro.dibujar(batch);
		caida.actualizarDibujoLluvia(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
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
      mounstro.destruir();
      caida.destruir();

	}

}
