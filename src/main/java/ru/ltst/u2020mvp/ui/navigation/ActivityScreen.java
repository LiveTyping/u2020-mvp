package ru.ltst.u2020mvp.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class ActivityScreen implements Screen {

    @NonNull
    protected final Intent intent(Context context) {
        Intent intent = new Intent(context, activityClass());
        configureIntent(intent);
        return intent;
    }

    protected abstract void configureIntent(@NonNull Intent intent);
    protected abstract Class<? extends Activity> activityClass();

}
