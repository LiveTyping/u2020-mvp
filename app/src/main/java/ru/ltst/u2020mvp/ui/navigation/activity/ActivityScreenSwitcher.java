package ru.ltst.u2020mvp.ui.navigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import java.security.InvalidParameterException;

import ru.ltst.u2020mvp.ui.navigation.Screen;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;

public class ActivityScreenSwitcher extends ActivityConnector<Activity> implements ScreenSwitcher {

    @Override
    public void open(Screen screen) {
        final Activity activity = getAttachedObject();
        if (activity == null) {
            return;
        }
        if (screen instanceof ActivityScreen) {
            ActivityScreen activityScreen = ((ActivityScreen) screen);
            Intent intent = activityScreen.intent(activity);
            ActivityCompat.startActivity(activity, intent, activityScreen.activityOptions(activity));
        } else {
            throw new InvalidParameterException("Only ActivityScreen objects allowed");
        }
    }

    @Override
    public void goBack() {
        final Activity activity = getAttachedObject();
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
