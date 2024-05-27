package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Galleta extends ElementoCayendo {
    public Galleta(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void efecto(Monstruo monstruo) {
        monstruo.sumarPuntos(1);
        monstruo.sumarPuntos(1);
    }
}

