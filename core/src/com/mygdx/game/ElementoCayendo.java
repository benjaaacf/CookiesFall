package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class ElementoCayendo implements Consumible {
    protected Texture textura;
    protected Rectangle area;

    public ElementoCayendo(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 64, 64);
    }

    public Rectangle getArea() {
        return area;
    }

    public Texture getTextura() {
        return textura;
    }

    public abstract void efecto(Monstruo monstruo);

    @Override
    public void consumir(Monstruo monstruo) {
        efecto(monstruo);
    }
}
