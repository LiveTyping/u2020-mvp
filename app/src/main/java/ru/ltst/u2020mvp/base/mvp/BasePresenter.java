package ru.ltst.u2020mvp.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView> {
    private WeakReference<V> view = null;

    /**
     * Load has been called for the current {@link #view}.
     */
    private boolean loaded;

    public final void takeView(V view) {
        if (view == null) throw new NullPointerException("new view must not be null");

        if (this.view != null) dropView(this.view.get());

        this.view = new WeakReference<>(view);
        if (!loaded) {
            loaded = true;
            onLoad();
        }
    }

    public final void dropView(V view) {
        if (view == null) throw new NullPointerException("dropped view must not be null");
        loaded = false;
        this.view = null;
        onDestroy();
    }

    protected final V getView() {
        if (view == null) throw new NullPointerException("getView called when view is null. Ensure takeView(View view) is called first.");
        return view.get();
    }

    protected void onLoad() {
    }

    protected void onDestroy() {
    }

    protected void onRestore(@NonNull Bundle savedInstanceState) {
    }

    protected void onSave(@NonNull Bundle outState) {
    }
}
