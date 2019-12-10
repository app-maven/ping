package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

import io.mosaicnetworks.babble.node.BabbleTx;

import io.appmaven.ping.models.Ball;


public class NewBallTx extends Transaction<Ball> implements BabbleTx {
    private final static Gson gson = new Gson();

    public NewBallTx(Ball ball) {
        super(ball);
    }

    public static NewBallTx fromJson(String rawTx) {
        return gson.fromJson(rawTx, NewBallTx.class);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
