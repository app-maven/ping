package io.appmaven.ping.scenes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import io.appmaven.ping.Constants;
import io.appmaven.ping.R;
import io.appmaven.ping.babble.Service;
import io.appmaven.ping.babble.transactions.MovePlayerTx;
import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Grid;
import io.appmaven.ping.models.Player;
import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class GameScene implements Scene {

    private Grid grid;

    public GameScene(Context ctx, Resources res) {
        this.grid = new Grid(BitmapFactory.decodeResource(res, R.drawable.tile));
    }

    @Override
    public void update() {
        grid.update();

        Ball b = Service.getInstance().state.getBall();
        if(b != null) {
            b.update();
        }

//        for (Player p : Service.getInstance().state.getPlayers().values()) {
//            if(p != null) {
//                p.update();
//            }
//        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(canvas != null) {
            grid.draw(canvas);

            Ball b = Service.getInstance().state.getBall();
            if(b != null) {
                b.draw(canvas);
            }

//            for (Player p : Service.getInstance().state.getPlayers().values()) {
//                if(p != null) {
//                    p.draw(canvas);
//                }
//            }
        }
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.i("hello", "lol you clieked!");
            Player activePlayer = Service.getInstance().state.getPlayer(Service.getInstance().getPublicKey());

            Vector draggedPosition = new Vector(activePlayer.getPosition().x, (int)event.getY());
            draggedPosition.y = ((int)event.getY() - (activePlayer.getHeight() / 2));

            if (Math.abs(draggedPosition.y - activePlayer.getPosition().y) >= activePlayer.getVelocity()) {
                MovePlayerTx.Payload payload = new MovePlayerTx.Payload(activePlayer.getPublicKey(), draggedPosition);

                Service.getInstance().movePlayer(payload);
            }
        }
    }

    @Override
    public void terminate() {
        SceneManager.setActiveScene(0);
    }
}
