package ru.ltst.u2020mvp.data.rx;

import rx.Observer;

/**
 * An {@link Observer} that always informs when it's ended.
 */
public abstract class EndObserver<T> implements Observer<T> {
    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable throwable) {
        onEnd();
    }

    public abstract void onEnd();
}
