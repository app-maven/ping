package io.appmaven.ping.models;

import android.graphics.Bitmap;

import io.appmaven.ping.sprites.Sprite;
import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class Ball extends Sprite {

    private UnitVector direction;

    private int velocity = 10;

    public Ball(Bitmap image, Vector pos, UnitVector direction) {
        super(image);

        this.pos = pos;
        this.direction = direction;
    }

    public Vector getDirection() {
        return this.direction;
    }
}
