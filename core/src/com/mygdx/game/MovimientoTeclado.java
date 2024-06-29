package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MovimientoTeclado implements MovimientoStrategy {

    @Override
    public void mover(Monstruo monstruo) {
        int velx = 400 + monstruo.getPuntos() * 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) monstruo.getArea().x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) monstruo.getArea().x += velx * Gdx.graphics.getDeltaTime();
        if (monstruo.getArea().x < 0) monstruo.getArea().x = 0;
        if (monstruo.getArea().x > 800 - 64) monstruo.getArea().x = 800 - 64;
    }
}
