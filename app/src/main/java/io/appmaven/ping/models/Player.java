package io.appmaven.ping.models;

import android.graphics.Bitmap;
import android.util.Log;

import io.appmaven.ping.sprites.BaseSprite;

import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class Player extends BaseSprite {
    private String moniker;

    private Vector newPosition;

    private int velocity = 50;

    public Player(Bitmap image, String moniker, Vector pos) {
        super(image);

        this.pos = pos;
        this.newPosition = pos;
        this.moniker = moniker;
    }

    public String getMoniker() {
        return this.moniker;
    }

    public Vector getNewPosition() {
        return this.newPosition;
    }

    @Override
    public void update() {
        if (this.pos.y != this.newPosition.y) {
            UnitVector direction = null;

            if (this.getPosition().y + this.velocity < this.newPosition.y) {
                direction = new UnitVector(0, 1);
            } else if (this.getPosition().y - this.velocity > this.newPosition.y) {
                direction = new UnitVector(0, -1);
            }

            if (direction != null) {
                this.setPosition(this.getPosition().add(direction.mult(this.velocity)));
            }
        }

    }

    public void moveTo(Vector vector) {
        this.newPosition = vector;
    }
}
