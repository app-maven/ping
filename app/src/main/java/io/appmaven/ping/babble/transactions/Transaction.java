package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;

public class Transaction<Data> {
    private final static Gson gson = new Gson();

    public static enum Type {
        NEW_PLAYER,
        INIT_BALL,
        HIT_BALL,
        MOVE_PLAYER
    }

    public Type type = Type.MOVE_PLAYER;
    public Data payload;

    public Transaction(Data data) {
        this.payload = data;
    }

    public static Transaction fromJson(String txJson) {
        return gson.fromJson(txJson, Transaction.class);
    }
}

