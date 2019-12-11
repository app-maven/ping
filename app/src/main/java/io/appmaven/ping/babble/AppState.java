package io.appmaven.ping.babble;

import android.util.Log;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.appmaven.ping.babble.transactions.NewBallTx;
import io.mosaicnetworks.babble.node.BabbleState;

import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Player;

import io.appmaven.ping.babble.transactions.Transaction;
import io.appmaven.ping.babble.transactions.NewPlayerTx;


public class AppState implements BabbleState {

    private Player active = null;

    private Player player1 = null;
    private Player player2 = null;

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

                    if(this.player1 == null) {
                        this.player1 = newPlayerTx.payload;
                        this.active = this.player1;
                    }  else if(this.player2 == null) {
                        this.player2 = newPlayerTx.payload;
                        this.active = this.player2;
                    }

                    break;

                case INIT_BALL:
                    Log.i("AppState", "Decoding NewBallTx transaction");

                    NewBallTx newBallTx = NewBallTx.fromJson(rawTx);

                    if(this.ball == null) {
                        this.ball = newBallTx.payload;
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
    public Player getPlayerOne() {
        return this.player1;
    }

    public Player getPlayerTwo() {
        return this.player1;
    }

    public Player getActivePlayer() {
        return this.active;
    }
}
