package io.appmaven.ping.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.appmaven.ping.utils.Vector;

public class Sprite {
    private Bitmap image;

    protected Vector pos;

    public Sprite(Bitmap image) {
        this.image = image;
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    public int getHeight() {
        return this.image.getHeight();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.image, this.pos.x, this.pos.y, null);
    }

    public void update() {
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Vector getPosition() {
        return this.pos;
    }

    public void setPosition(Vector vector) {
        this.pos = vector;
    }
}
