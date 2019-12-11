package io.appmaven.ping.models;

import android.graphics.Bitmap;

import io.appmaven.ping.babble.Service;
import io.appmaven.ping.sprites.BaseSprite;
import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class Ball extends BaseSprite {

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

    public void setDirection(UnitVector dir) {
        this.direction = dir;
    }

    @Override
    public void update() {
        Player p1 = Service.getInstance().state.getPlayerOne();
        Player p2 = Service.getInstance().state.getPlayerTwo();

        if (this.direction.x > 0) {
            if (this.pos.x + this.getImage().getWidth() < p2.getPosition().x) {
            } else if (this.pos.x + this.getImage().getWidth() >= p2.getPosition().x) {
                Service.getInstance().hitBall(this.direction.reflectX());
            }
            // Headed towards Player 1
        } else if(this.direction.x < 0) {
            if (this.pos.x > p1.getPosition().x + p1.getWidth()) {
            } else if (this.pos.x <= p1.getPosition().x + p1.getWidth() && this.pos.y <= p1.getPosition().y + p1.getHeight() && this.pos.y >= p1.getPosition().y) {
                Service.getInstance().hitBall(this.direction.reflectX());
            }
        }

        this.setPosition(this.pos.add(this.direction.mult(this.velocity)));
    }
}
