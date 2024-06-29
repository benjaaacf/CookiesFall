package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GalletaDorada extends ElementoCayendo {
    public GalletaDorada(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void efecto(Monstruo monstruo) {
        monstruo.aumentarVida();
        monstruo.reproducirSonidoGalletaDorada(); // Reproducir el sonido de galleta dorada
    }
}