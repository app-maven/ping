package io.appmaven.ping.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import io.appmaven.ping.Constants;
import io.appmaven.ping.GameView;
import io.appmaven.ping.R;
import io.appmaven.ping.babble.Service;
import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Player;
import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Player p1 = Service.getInstance().state.getLeftPlayer();
        Player p2 = Service.getInstance().state.getRightPlayer();

        if (p1 != null && p2 != null) {
            if (Service.getInstance().state.getBall() == null) {
                Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
                Vector ballPos = new Vector(p1.getPosition().x + p1.getWidth(), Constants.screenHeight / 2 - ballBitmap.getHeight()/2);

                Ball b = new Ball(ballBitmap, ballPos, new UnitVector(1, 0));

                Service.getInstance().addBall(b);
            }
        }

        setContentView(new GameView(this));
    }
}
