package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Monstruo {
	   private Rectangle monster;
	   private Texture monsterImage;
	   private Sound sonidoHerido;
           private Sound sonidoComer;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   
	   public Monstruo(Texture tex, Sound ss, Sound sc) {
		   monsterImage = tex;
		   sonidoHerido = ss;
                   sonidoComer = sc;
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return monster;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
                        sonidoComer.play();
		}
		
	
	   public void crear() {
		      monster = new Rectangle();
		      monster.x = 800 / 2 - 64 / 2;
		      monster.y = 20;
		      monster.width = 64;
		      monster.height = 64;
	   }
	   public void danar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   public void dibujar(SpriteBatch batch) {
		 if (!herido)  
		   batch.draw(monsterImage, monster.x, monster.y);
		 else {
		
		   batch.draw(monsterImage, monster.x, monster.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   
		   velx = 400 + puntos * 5; // velocidad del monstruo aumento
                   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) monster.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) monster.x += velx * Gdx.graphics.getDeltaTime();
		   // que no se salga de los bordes izq y der
		   if(monster.x < 0) monster.x = 0;
		   if(monster.x > 800 - 64) monster.x = 800 - 64;
	   }
	    

	public void destruir() {
		    monsterImage.dispose();
	   }
	
   public boolean estaHerido() {
	   return herido;
   }
	   
}
