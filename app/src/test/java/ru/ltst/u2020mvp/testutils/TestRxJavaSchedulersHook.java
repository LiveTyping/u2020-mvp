package ru.ltst.u2020mvp.testutils;

import rx.Scheduler;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

public class TestRxJavaSchedulersHook extends RxJavaSchedulersHook {
    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getNewThreadScheduler() {
        return Schedulers.immediate();
    }
}