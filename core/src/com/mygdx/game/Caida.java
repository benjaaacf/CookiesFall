package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Caida {
    private Array<ElementoCayendo> elementos;
    private long lastDropTime;
    private Texture texturaGalleta;
    private Texture texturaVegetal;
    private Sound dropSound;
    private Music rainMusic;
    private float velocidadCaida;

    public Caida(Texture texturaGalleta, Texture texturaVegetal, Sound dropSound, Music rainMusic) {
        this.texturaGalleta = texturaGalleta;
        this.texturaVegetal = texturaVegetal;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
        this.velocidadCaida = 300; // Velocidad inicial
    }

    public void crear() {
        elementos = new Array<>();
        crearCaida();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearCaida() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;

        ElementoCayendo elemento;
        if (MathUtils.random(1, 10) < 5) {
            elemento = new Vegetal(texturaVegetal, x, y);
        } else {
            elemento = new Galleta(texturaGalleta, x, y);
        }

        elementos.add(elemento);
        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Monstruo monstruo) {
        // Ajustar la frecuencia de aparicion de nuevas gotas en funcion de los puntos
        long intervaloCaida = Math.max(200000000L, 1000000000 - monstruo.getPuntos() * 800000000L); // Ajustar este valor segun sea necesario

        if (TimeUtils.nanoTime() - lastDropTime > intervaloCaida) {
            crearCaida();
        }

        // Aumentar la velocidad de caida conforme avanzan los puntos
        velocidadCaida = 300 + monstruo.getPuntos() * 3; // Incrementa la velocidad proporcionalmente a los puntos

        for (int i = 0; i < elementos.size; i++) {
            ElementoCayendo elemento = elementos.get(i);
            elemento.getArea().y -= velocidadCaida * Gdx.graphics.getDeltaTime();

            if (elemento.getArea().y + 64 < 0) {
                elementos.removeIndex(i);
                continue;
            }

            if (elemento.getArea().overlaps(monstruo.getArea())) {
                ((Consumible) elemento).consumir(monstruo);
                elementos.removeIndex(i);

                if (monstruo.getVidas() <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ElementoCayendo elemento : elementos) {
            batch.draw(elemento.getTextura(), elemento.getArea().x, elemento.getArea().y);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
