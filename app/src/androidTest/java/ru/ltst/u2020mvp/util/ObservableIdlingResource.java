package ru.ltst.u2020mvp.util;


import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import rx.Observable;
import rx.functions.Action0;

public final class ObservableIdlingResource<T> implements IdlingResource {

    private final Observable<T> observable;
    @Nullable
    private ResourceCallback callback;
    private boolean isIdle;

    public ObservableIdlingResource(Observable<T> observable) {
        this.observable = observable;
    }

    public Observable<T> observe() {
        isIdle = false;
        return observable.doAfterTerminate(new IdlingAction());
    }

    @Override
    public String getName() {
        return this.getClass().getName() + hashCode();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        callback = resourceCallback;
    }

    private void notifyIdle() {
        if (callback != null) {
            callback.onTransitionToIdle();
        }
    }

    private class IdlingAction implements Action0 {
        @Override
        public void call() {
            isIdle = true;
            notifyIdle();
        }
    }
}
