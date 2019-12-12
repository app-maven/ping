package io.appmaven.ping.babble;

import android.util.Log;

import io.appmaven.ping.babble.transactions.HitBallTx;
import io.appmaven.ping.babble.transactions.MovePlayerTx;
import io.appmaven.ping.utils.UnitVector;
import io.appmaven.ping.utils.Vector;
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
        super(new AppState(), new BabbleConfig.Builder().heartbeat(50).logLevel(BabbleConfig.LogLevel.ERROR).build());
    }

    public void addBall(Ball b) {
        NewBallTx ballTx = new NewBallTx(b);
        getInstance().submitTx(ballTx);

        Log.i("Service", "Submitted NewBallTx");
    }

    public void addPlayer(Player p) {
        NewPlayerTx tx = new NewPlayerTx(p);
        getInstance().submitTx(tx);

        Log.i("Service", "Submitted NewPlayerTx");
    }

    public void movePlayer(MovePlayerTx.Payload payload) {
        MovePlayerTx tx = new MovePlayerTx(payload);
        getInstance().submitTx(tx);

        Log.i("Service", "Submitted MovePlayerTx");
    }

    public void hitBall(UnitVector newDir) {
        HitBallTx tx = new HitBallTx(newDir);
        getInstance().submitTx(tx);

        Log.i("Service", "Submitted HitBallTx");
    }
}
