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
           private Sound sonidoGalletaDorada; // sonido para galleta dorada
	   private int vidas = 3;
	   private int puntos = 0;
	   private boolean herido = false;
	   private int tiempoHeridoMax = 50;
	   private int tiempoHerido;
           private MovimientoStrategy movimientoStrategy;
	   
	   
	   
           
           public Monstruo(Texture tex, Sound ss, Sound sc, Sound sg) {
		   monsterImage = tex;
		   sonidoHerido = ss;
                   sonidoComer = sc;
                   sonidoGalletaDorada = sg;
                   this.movimientoStrategy = new MovimientoTeclado(); // Estrategia por defecto
                }
                
                public void reproducirSonidoGalletaDorada(){
                    sonidoGalletaDorada.play();
                }
                
                public void setMovimientoStrategy(MovimientoStrategy strategy) {
                    this.movimientoStrategy = strategy;
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
                movimientoStrategy.mover(this);
            }
	    

	public void destruir() {
            monsterImage.dispose();
        }
	
   public boolean estaHerido() {
        return herido;
        
   }
   
   public void aumentarVida() {
        vidas++;
    }
}
	   

