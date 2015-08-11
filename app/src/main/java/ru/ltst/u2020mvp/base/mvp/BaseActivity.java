package ru.ltst.u2020mvp.base.mvp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.ltst.u2020mvp.U2020App;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.ui.AppContainer;

public abstract class BaseActivity extends ActionBarActivity {

    @Inject
    AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }
        super.onCreate(savedInstanceState);

        U2020App app = U2020App.get(this);
        onCreateComponent(app.component());
        if (appContainer == null) {
            throw new IllegalStateException("No injection happened. Add component.inject(this) in onCreateComponent() implementation.");
        }
        Registry.add(this, viewId(), presenter());
        final LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup container = appContainer.get(this);
        layoutInflater.inflate(layoutId(), container);
    }

    protected void onExtractParams(@NonNull Bundle params) {
        // default no implemetation
    }

    /**
     * Must be implemented by derived activities. Injection must be performed here.
     * Otherwise IllegalStateException will be thrown. Derived activity is
     * responsible to create and store it's component.
     * @param u2020Component application level component
     */
    protected abstract void onCreateComponent(U2020Component u2020Component);
    protected abstract @LayoutRes int layoutId();
    protected abstract BasePresenter<? extends BaseView> presenter();
    protected abstract @IdRes int viewId();
}
