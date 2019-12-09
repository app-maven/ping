package io.appmaven.ping.models;

import android.graphics.Bitmap;

import io.appmaven.ping.sprites.Sprite;

import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class Player extends Sprite {

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

    @Override
    public void update(){
        if (this.getPosition().y + this.velocity < this.newPosition.y) {
            UnitVector direction = new UnitVector(0, 1);
            this.getPosition().add(direction.mult(velocity));
        } else if (this.getPosition().y - this.velocity > this.newPosition.y) {
            UnitVector direction = new UnitVector(0, -1);
            this.getPosition().add(direction.mult(velocity));
        }
    }

    public void moveTo(Vector vector) {
        this.newPosition = vector;
    }
}
