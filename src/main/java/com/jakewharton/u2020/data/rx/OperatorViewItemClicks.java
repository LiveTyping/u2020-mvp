package com.jakewharton.u2020.data.rx;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.observables.Assertions;
import rx.android.subscriptions.AndroidSubscriptions;
import rx.functions.Action0;

public class OperatorViewItemClicks<L extends AbsListView> implements Observable.OnSubscribe<OperatorViewItemClicks.ItemClick> {

    private final L list;

    public OperatorViewItemClicks(final L list) {
        this.list = list;
    }

    @Override
    public void call(final Subscriber<? super ItemClick> observer) {
        Assertions.assertUiThread();
        final CompositeOnClickListener composite = CachedListeners.getFromViewOrCreate(list);

        final AbsListView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                observer.onNext(new ItemClick(parent, view, position, id));
            }
        };

        final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
            @Override
            public void call() {
                composite.removeOnClickListener(listener);
            }
        });

        composite.addOnClickListener(listener);
        observer.add(subscription);
    }

    private static class CompositeOnClickListener implements AbsListView.OnItemClickListener {
        private final List<AbsListView.OnItemClickListener> listeners = new ArrayList<>();

        public boolean addOnClickListener(final AbsListView.OnItemClickListener listener) {
            return listeners.add(listener);
        }

        public boolean removeOnClickListener(final AbsListView.OnItemClickListener listener) {
            return listeners.remove(listener);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for (AdapterView.OnItemClickListener listener : listeners) {
                listener.onItemClick(parent, view, position, id);
            }
        }
    }

    private static class CachedListeners {
        private static final Map<AbsListView, CompositeOnClickListener> sCachedListeners = new WeakHashMap<>();

        public static CompositeOnClickListener getFromViewOrCreate(final AbsListView view) {
            final CompositeOnClickListener cached = sCachedListeners.get(view);

            if (cached != null) {
                return cached;
            }

            final CompositeOnClickListener listener = new CompositeOnClickListener();

            sCachedListeners.put(view, listener);
            view.setOnItemClickListener(listener);

            return listener;
        }
    }

    public static class ItemClick {
        public final AdapterView<?> parent;
        public final View view;
        public final int position;
        public final long id;

        public ItemClick(AdapterView<?> parent, View view, int position, long id) {
            this.parent = parent;
            this.view = view;
            this.position = position;
            this.id = id;
        }
    }
}
