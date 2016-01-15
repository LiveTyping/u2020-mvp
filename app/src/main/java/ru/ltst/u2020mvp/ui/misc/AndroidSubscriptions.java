package ru.ltst.u2020mvp.ui.misc;

import android.os.Looper;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class AndroidSubscriptions {

    private AndroidSubscriptions() {
        throw new AssertionError("No instances");
    }

    /**
     * Create a {@link Subscription} that always runs the specified {@code unsubscribe} on the
     * UI thread.
     */

    public static Subscription unsubscribeInUiThread(final Action0 unsubscribe) {
        return Subscriptions.create(() -> {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                unsubscribe.call();
            } else {
                final Scheduler.Worker inner = AndroidSchedulers.mainThread().createWorker();
                inner.schedule(() -> {
                    unsubscribe.call();
                    inner.unsubscribe();
                });
            }
        });
    }
}
