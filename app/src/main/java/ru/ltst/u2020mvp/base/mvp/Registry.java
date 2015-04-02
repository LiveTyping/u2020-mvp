package ru.ltst.u2020mvp.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;

import java.util.HashMap;

import butterknife.ButterKnife;
import ru.ltst.u2020mvp.ui.ActivityHierarchyServer;

public class Registry {
    private static final HashMap<String, Entry> registers = new HashMap<>();
    public static final ActivityHierarchyServer.Empty SERVER = new ActivityHierarchyServer.Empty() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            final Entry entry = registers.get(getKey(activity));
            if (entry != null && savedInstanceState != null) {
                entry.presenter.onRestore(savedInstanceState);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onActivityStarted(Activity activity) {
            final Entry entry = registers.get(getKey(activity));
            if (entry != null) {
                final BaseView view = ButterKnife.findById(activity, entry.viewId);
                entry.presenter.takeView(view);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onActivityStopped(Activity activity) {
            final Entry entry = registers.get(getKey(activity));
            if (entry != null) {
                final BaseView view = ButterKnife.findById(activity, entry.viewId);
                entry.presenter.dropView(view);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            final Entry entry = registers.get(getKey(activity));
            if (entry != null) {
                entry.presenter.onSave(outState);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            registers.remove(getKey(activity));
        }
    };

    public static <V extends BaseView> void add(Activity activity, @IdRes int viewId, BasePresenter<V> presenter) {
        registers.put(getKey(activity), new Entry<>(viewId, presenter));
    }

    private static String getKey(Activity activity) {
        return activity.getClass().getName();
    }

    private static class Entry<V extends BaseView> {
        @IdRes
        public final int viewId;
        public final BasePresenter<V> presenter;

        public Entry(int viewId, BasePresenter<V> presenter) {
            this.viewId = viewId;
            this.presenter = presenter;
        }
    }
}
