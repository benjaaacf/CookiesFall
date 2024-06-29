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
    private Texture texturaGalletaDorada;
    private Texture texturaVegetal;
    private Music rainMusic;
    private float velocidadCaida;
    private int puntosAcumulados;

    public Caida(Texture texturaGalleta, Texture texturaGalletaDorada, Texture texturaVegetal, Music rainMusic) {
        this.texturaGalleta = texturaGalleta;
        this.texturaGalletaDorada = texturaGalletaDorada;
        this.texturaVegetal = texturaVegetal;
        this.rainMusic = rainMusic;
        this.velocidadCaida = 300;
        this.puntosAcumulados = 0;
    }

    public void crear() {
        elementos = new Array<>();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    public boolean actualizarMovimiento(Monstruo monstruo) {
        // Ajustar la frecuencia de aparicion de nuevas galletas en funcion de los puntos
        long intervaloCaida = Math.max(200000000L, 1000000000 - monstruo.getPuntos() * 800000000L); // Ajustar este valor segun sea necesario


        if (TimeUtils.nanoTime() - lastDropTime > intervaloCaida) {
            crearCaida();
        }

        // Aumentar la velocidad de caida conforme avanzan los puntos
        velocidadCaida = 300 + monstruo.getPuntos() * 2; // Incrementa la velocidad proporcionalmente a los puntos


        for (int i = 0; i < elementos.size; i++) {
            ElementoCayendo elemento = elementos.get(i);
            elemento.getArea().y -= velocidadCaida * Gdx.graphics.getDeltaTime();

            if (elemento.getArea().y + 64 < 0) {
                elementos.removeIndex(i);
                continue;
            }

            if (elemento instanceof Galleta) {
                if (elemento.getArea().overlaps(monstruo.getArea())) {
                    ((Galleta) elemento).efecto(monstruo);
                    elementos.removeIndex(i);
                }
            } else if (elemento instanceof GalletaDorada) {
                if (elemento.getArea().overlaps(monstruo.getArea())) {
                    ((GalletaDorada) elemento).efecto(monstruo);
                    elementos.removeIndex(i);
                }
            } else if (elemento instanceof Vegetal) {
                if (elemento.getArea().overlaps(monstruo.getArea())) {
                    ((Vegetal) elemento).efecto(monstruo);
                    elementos.removeIndex(i);
                    if (monstruo.getVidas() <= 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (ElementoCayendo elemento : elementos) {
            batch.draw(elemento.getTextura(), elemento.getArea().x, elemento.getArea().y);
        }
    }

    public void crearCaida() {
        ElementoCayendo elemento;
        if (MathUtils.randomBoolean(0.01f)) {
            elemento = new GalletaDorada(texturaGalletaDorada, MathUtils.random(0, 800 - 64), 480);
        } else if (MathUtils.randomBoolean()) {
            elemento = new Galleta(texturaGalleta, MathUtils.random(0, 800 - 64), 480);
        } else {
            elemento = new Vegetal(texturaVegetal, MathUtils.random(0, 800 - 64), 480);
        }
        elementos.add(elemento);
        lastDropTime = TimeUtils.nanoTime();
        
    }

    public void destruir() {
        texturaGalleta.dispose();
        texturaGalletaDorada.dispose();
        texturaVegetal.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
