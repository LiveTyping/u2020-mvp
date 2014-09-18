package com.jakewharton.u2020.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.jakewharton.u2020.Injector;
import com.jakewharton.u2020.U2020App;
import com.jakewharton.u2020.U2020InjectionService;
import com.jakewharton.u2020.ui.AppContainer;

import javax.inject.Inject;

import dagger.ObjectGraph;

public abstract class U2020Activity extends Activity implements U2020InjectionService {

    private ObjectGraph activityGraph;

    @Inject
    AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        U2020App app = U2020App.get(this);
        activityGraph = app.plus(modules());
        activityGraph.inject(this);

        ViewGroup container = appContainer.get(this);
        getLayoutInflater().inflate(layoutId(), container);
        if (savedInstanceState != null) {
            presenter().onRestore(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter().onSave(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
    }

    @Override
    public <T> T inject(T object) {
        return activityGraph.inject(object);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.isValidSystemService(name))
            return this;
        return super.getSystemService(name);
    }

    protected abstract Object[] modules();
    protected abstract @LayoutRes int layoutId();
    protected abstract ActivityPresenter<? extends Activity> presenter();
}
