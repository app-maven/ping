package io.appmaven.ping.babble;

import android.util.Log;

import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.appmaven.ping.babble.transactions.HitBallTx;
import io.appmaven.ping.babble.transactions.MovePlayerTx;
import io.appmaven.ping.babble.transactions.NewBallTx;
import io.appmaven.ping.utils.UnitVector;
import io.mosaicnetworks.babble.node.BabbleState;

import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Player;

import io.appmaven.ping.babble.transactions.Transaction;
import io.appmaven.ping.babble.transactions.NewPlayerTx;


public class AppState implements BabbleState {

    private final Object lock = new Object();

    private HashMap<String, Player> players =  new HashMap<>();

    private Player leftPlayer;
    private Player rightPlayer;

    private Ball ball = null;

    @Override
    public byte[] applyTransactions(byte[][] transactions) {
        for(byte[] bTx : transactions) {
            Log.i("AppState", "Received transaction, decoding...");

            String rawTx = new String(bTx, UTF_8);
            Transaction tx = Transaction.fromJson(rawTx);


            switch(tx.type) {
                case NEW_PLAYER:
                    Log.i("AppState", "Decoding NewPlayerTx transaction");

                    NewPlayerTx newPlayerTx = NewPlayerTx.fromJson(rawTx);

                    // Add player
                    this.addPlayer(newPlayerTx.payload);

                    break;

                case INIT_BALL:
                    Log.i("AppState", "Decoding NewBallTx transaction");

                    NewBallTx newBallTx = NewBallTx.fromJson(rawTx);

                    this.addBall(newBallTx.payload);

                    break;

                case MOVE_PLAYER:
                    Log.i("AppState", "Decoding MovePlayerTx transaction");

                    MovePlayerTx movePlayerTx = MovePlayerTx.fromJson(rawTx);

                    this.movePlayer(movePlayerTx.payload);

                    break;

                case HIT_BALL:
                    Log.i("AppState", "Decoding HitBallTx transaction");
                    HitBallTx hitTx = HitBallTx.fromJson(rawTx);

                    this.hitBall(hitTx.payload);

                    break;
            }
        }

        return new byte[0];
    }

    // State Update methods
    private void addPlayer(Player newPlayer) {
        synchronized (lock) {
            Player player = this.players.get(newPlayer.getPublicKey());

            if (player == null) {
                this.players.put(newPlayer.getPublicKey(), newPlayer);
            }

            if (leftPlayer == null) {
                this.leftPlayer = newPlayer;
            } else if (rightPlayer == null) {
                this.rightPlayer = newPlayer;
            }
        }
    }

    private void addBall(Ball b) {
        synchronized (lock) {
            if (this.ball == null) {
                this.ball = b;
            }
        }
    }

    private void movePlayer(MovePlayerTx.Payload payload) {
        synchronized (lock) {
            Player ctx = this.players.get(payload.publicKey);

            if (ctx != null) {
                ctx.moveTo(payload.pos);
            }
        }
    }

    private void hitBall(UnitVector direction) {
        synchronized (lock) {
            if(this.ball != null) {
                this.ball.setDirection(direction);
            }
        }
    }

    @Override
    public void reset() {
    }

    // Syncronized Getters
    public Ball getBall() {
        synchronized (lock) {
            return this.ball;
        }
    }

    public Player getPlayer(String publicKey) {
        synchronized (lock) {
            return this.players.get(publicKey);
        }
    }

    public HashMap<String, Player> getPlayers() {
        synchronized (lock) {
            return this.players;
        }
    }

    public Player getLeftPlayer() {
        synchronized (lock) {
            return leftPlayer;
        }
    }

    public Player getRightPlayer() {
        synchronized (lock) {
            return rightPlayer;
        }
    }
}
