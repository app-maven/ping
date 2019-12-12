package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

import io.appmaven.ping.utils.Vector;
import io.mosaicnetworks.babble.node.BabbleTx;

public class MovePlayerTx extends Transaction<MovePlayerTx.Payload> implements BabbleTx {
    public static class Payload {
        public String publicKey;
        public Vector pos;

        public Payload(String pk, Vector pos) {
            this.publicKey = pk;
            this.pos = pos;
        }
    }

    private final static Gson gson = new Gson();

    public MovePlayerTx(MovePlayerTx.Payload newPos) {
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
