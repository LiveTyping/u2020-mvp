package ru.ltst.u2020mvp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;


import java.util.Arrays;
import java.util.List;

import ru.ltst.u2020mvp.util.NetworkUtils;

public class NetworkReceiver extends BroadcastReceiver {

    public static final String ACTION = NetworkReceiver.class.getName() + ".ACTION";

    private final List<Action> mActions;

    public NetworkReceiver(Action... actions) {
        this.mActions = Arrays.asList(actions);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean hasConnection = NetworkUtils.hasConnection(context);
        for (Action action : mActions) {
            action.call(hasConnection);
        }
    }

    public void register(Context context) {
        IntentFilter intent = new IntentFilter(ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(this, intent);
    }

    public void unregister(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
    }

    public static void sendIntent(Context context) {
        Intent intent = new Intent(ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public interface Action {
        void call(boolean isConnected);
    }
}
