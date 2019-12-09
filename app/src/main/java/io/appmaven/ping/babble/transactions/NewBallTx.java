package io.appmaven.ping.babble.transactions;

import io.mosaicnetworks.babble.node.BabbleTx;

import io.appmaven.ping.models.Ball;


public class NewBallTx extends Transaction<Ball> implements BabbleTx {

    public NewBallTx(Ball ball) {
        super(ball);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
