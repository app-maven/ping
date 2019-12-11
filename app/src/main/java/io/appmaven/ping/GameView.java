package io.appmaven.ping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.appmaven.ping.scenes.SceneManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SceneManager manager;

    public GameView(Context context) {
        super(context);

        this.thread = new MainThread(getHolder(),this);
        this.manager = new SceneManager(context, getResources());

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

            retry = false;
        }
    }

    public void update() {
        this.manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        this.manager.draw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.manager.receiveTouch(event);

        return true;
    }
}
