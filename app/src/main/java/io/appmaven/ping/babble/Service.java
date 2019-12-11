package io.appmaven.ping.babble;

import android.util.Log;

import io.mosaicnetworks.babble.node.BabbleConfig;
import io.mosaicnetworks.babble.node.BabbleService;

import io.appmaven.ping.models.Ball;
import io.appmaven.ping.models.Player;

import io.appmaven.ping.babble.transactions.NewBallTx;
import io.appmaven.ping.babble.transactions.NewPlayerTx;

public class Service extends BabbleService<AppState> {
    private static Service INSTANCE;

    public static Service getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Service();
        }

        return INSTANCE;
    }

    private Service() {
        super(new AppState(), new BabbleConfig.Builder().heartbeat(10).logLevel(BabbleConfig.LogLevel.DEBUG).build());
    }

    public void startGame(Ball b) {
        NewBallTx ballTx = new NewBallTx(b);
        getInstance().submitTx(ballTx);
    }

    public void addPlayer(Player p) {
        NewPlayerTx pTx = new NewPlayerTx(p);
        getInstance().submitTx(pTx);
        Log.i("Adding Player", "Submitted NewPlayerTx");
    }
}
