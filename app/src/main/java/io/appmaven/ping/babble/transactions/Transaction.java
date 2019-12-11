package io.appmaven.ping.babble.transactions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class Transaction<T> {
    private final static Gson gson = new Gson();

    @SerializedName("type")
    public Type type = Type.NEW_PLAYER;

    @SerializedName("payload")
    public T payload;

    public Transaction(T data) {
        this.payload = data;
    }

    public static Transaction fromJson(String txJson) {
        return gson.fromJson(txJson, Transaction.class);
    }
}

