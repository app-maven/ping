package io.appmaven.ping.babble;

import android.util.Log;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.mosaicnetworks.babble.node.BabbleState;

import io.appmaven.ping.babble.transactions.Transaction;
import io.appmaven.ping.babble.transactions.NewPlayerTx;


public class AppState implements BabbleState {

    @Override
    public byte[] applyTransactions(byte[][] transactions) {
        for(byte[] bTx : transactions) {
            String rawTx = new String(bTx, UTF_8);
            Transaction tx = Transaction.fromJson(rawTx);

            switch(tx.type) {
                case NEW_PLAYER:
                    Log.i("NewPlayerTx", "Decoding transaction");
                    NewPlayerTx newPlayerTx = NewPlayerTx.fromJson(rawTx);

                    break;
                case INIT_BALL:
                    Log.i("InitBallTx", "Decoding transaction");
                    break;
            }
        }

        return new byte[0];
    }

    @Override
    public void reset() {
    }
}
