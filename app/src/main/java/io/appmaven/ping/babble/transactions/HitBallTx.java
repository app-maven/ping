package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

import io.appmaven.ping.utils.UnitVector;
import io.mosaicnetworks.babble.node.BabbleTx;

public class HitBallTx extends Transaction<UnitVector> implements BabbleTx {
    private final static Gson gson = new Gson();

    public HitBallTx(UnitVector dir) {
        super(dir);

        this.type = Type.HIT_BALL;
    }

    public static HitBallTx fromJson(String rawTx) {
        return gson.fromJson(rawTx, HitBallTx.class);
    }


    @Override
    public byte[] toBytes() {
        return gson.toJson(this).getBytes();
    }
}
