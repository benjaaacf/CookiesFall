package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Vegetal extends ElementoCayendo {
    public Vegetal(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void efecto(Monstruo monstruo) {
        monstruo.danar();
    }
}
