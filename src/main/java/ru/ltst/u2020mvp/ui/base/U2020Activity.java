package ru.ltst.u2020mvp.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.ltst.u2020mvp.U2020App;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.ui.AppContainer;
import ru.ltst.u2020mvp.ui.navigation.ActivityScreenSwitcher;

public abstract class U2020Activity<Component> extends FragmentActivity {

    @Inject
    AppContainer appContainer;
    @Inject
    ActivityScreenSwitcher activityScreenSwitcher;

    private Component component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }
        super.onCreate(savedInstanceState);

        U2020App app = U2020App.get(this);
        component = component(app.component());
        doInject(component);

        ViewGroup container = appContainer.get(this);
        getLayoutInflater().inflate(layoutId(), container);

        if (savedInstanceState != null) {
            presenter().onRestore(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityScreenSwitcher.attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityScreenSwitcher.detach();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter().onSave(outState);
    }

    @Override
    protected void onDestroy() {
        component = null;
        super.onDestroy();
    }

    protected void onExtractParams(@NonNull Bundle params) {
        // default no implemetation
    }

    public Component getComponent() {
        return component;
    }

    protected abstract void doInject(Component component);
    protected abstract Component component(U2020Component component);
    protected abstract @LayoutRes int layoutId();
    protected abstract ViewPresenter<? extends View> presenter();
}
