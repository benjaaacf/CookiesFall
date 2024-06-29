package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class ElementoCayendoBuilder {
    private Texture textura;
    private float x;
    private float y;
    private boolean esGalleta;

    public ElementoCayendoBuilder setTextura(Texture textura) {
        this.textura = textura;
        return this;
    }

    public ElementoCayendoBuilder setX(float x) {
        this.x = x;
        return this;
    }

    public ElementoCayendoBuilder setY(float y) {
        this.y = y;
        return this;
    }

    public ElementoCayendoBuilder setEsGalleta(boolean esGalleta) {
        this.esGalleta = esGalleta;
        return this;
    }

    public ElementoCayendo build() {
        if (esGalleta) {
            return new Galleta(textura, x, y);
        } else {
            return new Vegetal(textura, x, y);
        }
    }
}
