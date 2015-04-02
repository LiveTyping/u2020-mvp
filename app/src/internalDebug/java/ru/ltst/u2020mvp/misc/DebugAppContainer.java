package ru.ltst.u2020mvp.misc;

import android.app.Activity;
import android.app.Application;
import android.os.PowerManager;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.ltst.u2020mvp.ui.AppContainer;
import ru.ltst.u2020mvp.ui.ApplicationScope;

import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static butterknife.ButterKnife.findById;

@ApplicationScope
public final class DebugAppContainer implements AppContainer {
    private final Application app;

    @Inject
    public DebugAppContainer(Application app) {
        this.app = app;
    }

    @Override
    public ViewGroup get(final Activity activity) {
        riseAndShine(activity);
        return findById(activity, android.R.id.content);
    }

    /**
     * Show the activity over the lock-screen and wake up the device. If you launched the app manually
     * both of these conditions are already true. If you deployed from the IDE, however, this will
     * save you from hundreds of power button presses and pattern swiping per day!
     */
    public static void riseAndShine(Activity activity) {
        activity.getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager power = (PowerManager) activity.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock lock =
                power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, "wakeup!");
        lock.acquire();
        lock.release();
    }
}
