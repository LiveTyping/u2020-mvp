package ru.ltst.u2020mvp.ui.navigation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import java.security.InvalidParameterException;

public class ActivityScreenSwitcher implements ScreenSwitcher {

    @Nullable
    private Activity activity;

    // cause nextActivity.onStart() is called before prevActivity.onStop()
    // we need to explicitly store nextActivity
    @Nullable
    private Activity nextActivity;

    @Override
    public void open(Screen screen) {
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
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    public void attach(@NonNull Activity activity) {
        if (this.activity != null) {
            nextActivity = activity;
            return;
        }
        this.activity = activity;
    }

    public void detach() {
        if (nextActivity != null) {
            activity = nextActivity;
            nextActivity = null;
        } else {
            activity = null;
            nextActivity = null;
        }
    }
}
