package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

import io.mosaicnetworks.babble.node.BabbleTx;

import io.appmaven.ping.models.Player;

public class NewPlayerTx extends Transaction<Player> implements BabbleTx {
    private final static Gson gson = new Gson();

    public NewPlayerTx(Player player) {
        super(player);
    }

    public static NewPlayerTx fromJson(String rawTx) {
        return gson.fromJson(rawTx, NewPlayerTx.class);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
