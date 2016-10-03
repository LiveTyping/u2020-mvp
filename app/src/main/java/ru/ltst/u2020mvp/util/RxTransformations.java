package ru.ltst.u2020mvp.util;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxTransformations {
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyDelay() {
        return observable -> Observable.timer(1000, TimeUnit.MILLISECONDS)
                .flatMap(aLong -> observable);
    }
}
