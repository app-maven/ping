package io.appmaven.ping.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        Player p1 = Service.getInstance().state.getLeftPlayer();
        Player p2 = Service.getInstance().state.getRightPlayer();

        if (p1 == null || p2 == null) {
            new AlertDialog.Builder(this)
                .setTitle("Start Game")
                .setMessage("Are you sure you want start the game without a Player 2?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addDebugPlayerTwo();

                        Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        } else {
            Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
            startActivity(intent);
        }
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

        // To uniquely identify the player
        player.setPublicKey(Service.getInstance().getPublicKey());

        Service.getInstance().addPlayer(player);
    }

    public void addPlayerOne() {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        Vector pos = new Vector(Constants.PADDLE_MARGIN, Constants.screenHeight / 2 - paddle.getHeight()/2);

        this.addPlayer("Player 1", pos);
    }

    public void addPlayerTwo() {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);

        int x = Constants.screenWidth - Constants.PADDLE_MARGIN - paddle.getWidth() - 15;
        int y = Constants.screenHeight / 2 - paddle.getHeight()/2;

        Vector p2Pos = new Vector(x, y);

        this.addPlayer("Player 2", p2Pos);
    }

    public void addDebugPlayerTwo() {
        Bitmap paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);

        int x = Constants.screenWidth - Constants.PADDLE_MARGIN - paddle.getWidth() - 15;
        int y = Constants.screenHeight / 2 - paddle.getHeight()/2;

        Vector p2Pos = new Vector(x, y);
        Player player = new Player(paddle, "Player 2", p2Pos);

        player.setPublicKey("Player2PubKey");

        Service.getInstance().addPlayer(player);
    }

    @Override
    public void stateUpdated() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Player p1 = Service.getInstance().state.getLeftPlayer();
                Player p2 = Service.getInstance().state.getRightPlayer();

                if (p1 != null){
                    ((TextView)findViewById(R.id.txtPlayerOne)).setText(p1.getMoniker());
                }

                if (p2 != null) {
                    ((TextView)findViewById(R.id.txtPlayerTwo)).setText(p2.getMoniker());
                }
            }
        });
    }
}
