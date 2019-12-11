package io.appmaven.ping.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.gson.annotations.SerializedName;

import io.appmaven.ping.utils.Vector;

public class BaseSprite implements Sprite {
    private Bitmap image;

    @SerializedName("pos")
    protected Vector pos;

    public BaseSprite(Bitmap image) {
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

    public void update() {
    }
}
