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

        Player p1 = Service.getInstance().state.getPlayerOne();

        Bitmap ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        Vector ballPos = new Vector(p1.getPosition().x + p1.getWidth(), Constants.screenHeight / 2 - ball.getHeight()/2);

        Ball b = new Ball(ball, ballPos, new UnitVector(1, 0));

        Service.getInstance().startGame(b);
    }

    @Override
    public void update() {
        grid.update();

        Player p1 = Service.getInstance().state.getPlayerOne();
        Player p2 = Service.getInstance().state.getPlayerTwo();

        Ball b = Service.getInstance().state.getBall();

        if(b != null) {
            b.update();
        }

        if(p1 != null && p2 != null) {
            p1.update();
            p2.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(canvas != null) {
            grid.draw(canvas);

            Player p1 = Service.getInstance().state.getPlayerOne();
            Player p2 = Service.getInstance().state.getPlayerTwo();

            Ball b = Service.getInstance().state.getBall();

            if(b != null) {
                b.draw(canvas);
            }

            if(p1 != null && p2 != null) {
                p1.draw(canvas);
                p2.draw(canvas);
            }
        }
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            Player activePlayer = Service.getInstance().state.getActivePlayer();

            Vector draggedPosition = new Vector(activePlayer.getPosition().x, (int)event.getY());
            draggedPosition.y = ((int)event.getY() - (activePlayer.getHeight() / 2));

            Service.getInstance().movePlayer(draggedPosition);
        }
    }

    @Override
    public void terminate() {
        SceneManager.setActiveScene(0);
    }
}
