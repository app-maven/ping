package io.appmaven.ping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;

import io.appmaven.ping.babble.Service;
import io.appmaven.ping.models.Player;
import io.appmaven.ping.scenes.SceneManager;
import io.mosaicnetworks.babble.node.ServiceObserver;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, ServiceObserver {

    public HashMap<String, Player> players = new HashMap<>();

    private GameThread thread;
    private SceneManager manager;

    public GameView(Context context) {
        super(context);

        this.thread = new GameThread(getHolder(),this);
        this.manager = new SceneManager(context, getResources());

        Service.getInstance().registerObserver(this);
        Service.getInstance().registerObserver(this.thread);

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
        Log.i("surfaceDestroy", "destroyed");
        boolean retry = true;

        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                Log.e("GameView", e.getLocalizedMessage());
            }

            retry = false;
        }
    }

    public void update() {
        this.manager.update();

        for (Player p : this.players.values()) {
            if(p != null) {
                p.update();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.manager.draw(canvas);

        for (Player p : this.players.values()) {
            if(p != null) {
                p.draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.manager.receiveTouch(event);

        return true;
    }

    @Override
    public void stateUpdated() {
        HashMap<String, Player> babblePlayers = Service.getInstance().state.getPlayers();

        for(Player state : babblePlayers.values() ) {
            try {
                Player p = this.players.get(state.getPublicKey());
                if(p == null) {
                    Player newPlayer = new Player(state.getImage(), state.getMoniker(), state.getPosition());
                    newPlayer.moveTo(state.getNewPosition());

                    this.players.put(state.getPublicKey(), newPlayer);
                } else {
                    p.moveTo(state.getNewPosition());
                }

            } catch (Exception e) {
                Log.i("Exception:", e.getMessage());
            }
        }
    }
}
