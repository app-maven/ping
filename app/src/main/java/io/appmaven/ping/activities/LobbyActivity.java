package io.appmaven.ping.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.appmaven.ping.models.Player;
import io.appmaven.ping.utils.Vector;
import io.mosaicnetworks.babble.utils.Utils;
import io.mosaicnetworks.babble.node.BabbleService;
import io.mosaicnetworks.babble.node.ServiceObserver;

import io.appmaven.ping.R;
import io.appmaven.ping.Constants;
import io.appmaven.ping.babble.Service;

public class LobbyActivity extends Activity implements ServiceObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int type = intent.getIntExtra(Constants.EXTRA_TYPE, 0);
        Log.i("Hey", "Can you see this?");

        switch(type) {
            case 0:
                this.newNode("Player 1");
                this.addPlayerOne();

                break;
            case 1:
                this.joinNode("Player 2");
                this.addPlayerTwo();

                break;
            default:
                break;
        }

        setContentView(R.layout.activity_lobby);
    }

    public void startGame(View view) {
        this.addPlayerTwo();

        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }

    public void newNode(String moniker) {
        Service.getInstance().configureNew(moniker, Utils.getIPAddr(this));

        // Start instance
        Service.getInstance().start();
        Service.getInstance().registerObserver(this);

        if (Service.getInstance().getState() != BabbleService.State.RUNNING_WITH_DISCOVERY) {
            Log.e("startNew", "Unable to advertise peers");
        }
    }

    public void joinNode(String moniker) {
        String ip = Utils.getIPAddr(this);

        // TODO
    }

    // Adds player to game
    public void addPlayer(String moniker, Vector pos) {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        Player player = new Player(paddle, moniker, pos);

        Service.getInstance().addPlayer(player);
    }

    public void addPlayerOne() {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        Vector pos = new Vector(Constants.PADDLE_MARGIN, Constants.screenHeight / 2 - paddle.getHeight()/2);

        this.addPlayer("Player 1", pos);
    }

    public void addPlayerTwo() {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        Player p1 = Service.getInstance().state.getPlayerOne();

        int x = Constants.screenWidth - Constants.PADDLE_MARGIN - paddle.getWidth() - 15;
        int y = p1.getPosition().y;

        Vector p2Pos = new Vector(x, y);

        this.addPlayer("Player 2", p2Pos);
    }

    @Override
    public void stateUpdated() {
    }
}
