package io.appmaven.ping.scenes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import io.appmaven.ping.R;
import io.appmaven.ping.models.Grid;

public class GameScene implements Scene {

    private Grid grid;

    public GameScene(Context ctx, Resources res) {
        this.grid = new Grid(BitmapFactory.decodeResource(res, R.drawable.tile));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }

    @Override
    public void terminate() {

    }
}
