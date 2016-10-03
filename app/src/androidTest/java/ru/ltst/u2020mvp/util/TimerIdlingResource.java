package ru.ltst.u2020mvp.util;


import android.os.Handler;
import android.os.Looper;
import android.support.test.espresso.IdlingResource;

public class TimerIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean idle = true;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public TimerIdlingResource() {

    }

    public void scheduleTimeout(long timer) {
        idle = false;
        handler.postDelayed(() -> {
            idle = true;
            if (null != resourceCallback) {
                resourceCallback.onTransitionToIdle();
            }
        }, timer);
    }

    @Override
    public String getName() {
        return "CloseKeyboardIdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
