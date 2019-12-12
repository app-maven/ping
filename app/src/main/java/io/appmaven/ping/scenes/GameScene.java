package io.appmaven.ping.scenes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

        // Get first player in hash map and assign ball
        if (Service.getInstance().state.getBall() == null) {
            Player p1 = Service.getInstance().state.getLeftPlayer();

            Bitmap ball = BitmapFactory.decodeResource(res, R.drawable.ball);
            Vector ballPos = new Vector(p1.getPosition().x + p1.getWidth(), Constants.screenHeight / 2 - ball.getHeight()/2);

            Ball b = new Ball(ball, ballPos, new UnitVector(1, 0));

            Service.getInstance().addBall(b);
        }
    }

    @Override
    public void update() {
        grid.update();

        Ball b = Service.getInstance().state.getBall();
        if(b != null) {
            b.update();
        }

        for (Player p : Service.getInstance().state.getPlayers().values()) {
            if(p != null) {
                p.update();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(canvas != null) {
            grid.draw(canvas);

            Ball b = Service.getInstance().state.getBall();
            if(b != null) {
                b.draw(canvas);
            }

            for (Player p : Service.getInstance().state.getPlayers().values()) {
                if(p != null) {
                    p.draw(canvas);
                }
            }
        }
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            Player activePlayer = Service.getInstance().state.getPlayer(Service.getInstance().getPublicKey());

            Vector draggedPosition = new Vector(activePlayer.getPosition().x, (int)event.getY());
            draggedPosition.y = ((int)event.getY() - (activePlayer.getHeight() / 2));

            MovePlayerTx.Payload payload = new MovePlayerTx.Payload(activePlayer.getPublicKey(), draggedPosition);

            Service.getInstance().movePlayer(payload);
        }
    }

    @Override
    public void terminate() {
        SceneManager.setActiveScene(0);
    }
}
