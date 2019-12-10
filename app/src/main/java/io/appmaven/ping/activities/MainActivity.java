package io.appmaven.ping.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.appmaven.ping.Constants;
import io.appmaven.ping.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, LobbyActivity.class);
        intent.putExtra(Constants.EXTRA_TYPE, 0);

        startActivity(intent);
    }

    public void joinGame(View view) {
        Intent intent = new Intent(this, LobbyActivity.class);
        intent.putExtra(Constants.EXTRA_TYPE, 1);

        startActivity(intent);
    }
}
