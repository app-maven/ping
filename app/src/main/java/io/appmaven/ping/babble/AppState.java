package io.appmaven.ping.babble;

import android.util.Log;

import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.appmaven.ping.babble.transactions.HitBallTx;
import io.appmaven.ping.babble.transactions.MovePlayerTx;
import io.appmaven.ping.babble.transactions.NewBallTx;
import io.mosaicnetworks.babble.node.BabbleState;

import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Player;

import io.appmaven.ping.babble.transactions.Transaction;
import io.appmaven.ping.babble.transactions.NewPlayerTx;


public class AppState implements BabbleState {

    private HashMap<String, Player> players = new HashMap<>();

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

                    String publicKey = newPlayerTx.payload.getPublicKey();
                    Player p = this.players.get(publicKey);

                    if (p == null) {
                        this.players.put(publicKey, newPlayerTx.payload);
                    }

                    if (leftPlayer == null) {
                        this.leftPlayer = newPlayerTx.payload;
                    } else if (rightPlayer == null) {
                        this.rightPlayer = newPlayerTx.payload;
                    }

                    break;

                case INIT_BALL:
                    Log.i("AppState", "Decoding NewBallTx transaction");

                    NewBallTx newBallTx = NewBallTx.fromJson(rawTx);

                    if (this.ball == null) {
                        this.ball = newBallTx.payload;
                    }

                    break;

                case MOVE_PLAYER:
                    Log.i("AppState", "Decoding MovePlayerTx transaction");

                    MovePlayerTx movePlayerTx = MovePlayerTx.fromJson(rawTx);

                    Player ctx = this.players.get(movePlayerTx.payload.publicKey);

                    if (ctx != null) {
                        ctx.moveTo(movePlayerTx.payload.pos);
                    }

                    break;

                case HIT_BALL:
                    Log.i("AppState", "Decoding HitBallTx transaction");
                    HitBallTx hitTx = HitBallTx.fromJson(rawTx);

                    if(this.ball != null) {
                        this.ball.setDirection(hitTx.payload);
                    }

                    break;
            }
        }

        return new byte[0];
    }

    @Override
    public void reset() {
    }

    // Getters
    public Ball getBall() {
        return this.ball;
    }

    public Player getPlayer(String publicKey) {
        return this.players.get(publicKey);
    }

    public HashMap<String, Player> getPlayers() {
        return this.players;
    }

    public Player getLeftPlayer() {
        return leftPlayer;
    }

    public Player getRightPlayer() {
        return rightPlayer;
    }
}
