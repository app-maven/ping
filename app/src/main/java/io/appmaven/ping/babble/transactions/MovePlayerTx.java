package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

import io.appmaven.ping.utils.Vector;
import io.mosaicnetworks.babble.node.BabbleTx;

public class MovePlayerTx extends Transaction<Vector> implements BabbleTx {
    private final static Gson gson = new Gson();

    public MovePlayerTx(Vector newPos) {
        super(newPos);

        this.type = Type.MOVE_PLAYER;
    }

    public static MovePlayerTx fromJson(String rawTx) {
        return gson.fromJson(rawTx, MovePlayerTx.class);
    }


    @Override
    public byte[] toBytes() {
        return gson.toJson(this).getBytes();
    }
}
