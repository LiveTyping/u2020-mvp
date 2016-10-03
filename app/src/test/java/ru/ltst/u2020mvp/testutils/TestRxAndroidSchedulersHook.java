package ru.ltst.u2020mvp.testutils;

import rx.Scheduler;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

public class TestRxAndroidSchedulersHook extends RxAndroidSchedulersHook {
    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
    }
}