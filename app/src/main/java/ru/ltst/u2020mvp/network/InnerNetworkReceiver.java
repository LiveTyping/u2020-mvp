package ru.ltst.u2020mvp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.ltst.u2020mvp.util.NetworkUtils;

public class InnerNetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        boolean hasConnection = NetworkUtils.hasConnection(context);
//        if (hasConnection) {
            NetworkReceiver.sendIntent(context);
//        }
    }
}
