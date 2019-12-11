package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import io.appmaven.ping.utils.Vector;
import io.mosaicnetworks.babble.node.BabbleTx;

public class MovePlayerTx extends Transaction<MovePlayerTx.Move> implements BabbleTx {
    public static class Move {
        @SerializedName("direction")
        public Vector position;

        public Move(Vector position) {
            this.position = position;
        }
    }

    private final static Gson gson = new Gson();

    public MovePlayerTx(Move m) {
        super(m);

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
