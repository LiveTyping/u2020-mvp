package ru.ltst.u2020mvp.ui.navigation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

public abstract class ActivityScreen implements Screen {

    private static final String BF_TRANSITION_VIEW = "ImgurImageActivity.transitionView";

    @NonNull
    protected final Intent intent(Context context) {
        Intent intent = new Intent(context, activityClass());
        configureIntent(intent);
        return intent;
    }

    protected final Bundle activityOptions(Activity activity) {
        View transitionView = transitionView();
        if (transitionView == null) {
            return null;
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, BF_TRANSITION_VIEW).toBundle();
    }

    @Nullable
    protected View transitionView() {
        return null;
    }

    protected abstract void configureIntent(@NonNull Intent intent);
    protected abstract Class<? extends Activity> activityClass();

    public static void setTransitionView(View view) {
        ViewCompat.setTransitionName(view, BF_TRANSITION_VIEW);
    }

}
