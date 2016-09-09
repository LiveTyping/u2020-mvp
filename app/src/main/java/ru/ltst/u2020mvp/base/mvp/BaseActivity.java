package ru.ltst.u2020mvp.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.UUID;

import javax.inject.Inject;

import ru.ltst.u2020mvp.U2020App;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.network.NetworkReceiver;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String BF_UNIQUE_KEY = BaseActivity.class.getName() + ".unique.key";

    @Inject
    ViewContainer viewContainer;

    private String uniqueKey;

    private NetworkReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(BF_UNIQUE_KEY)) {
            uniqueKey = savedInstanceState.getString(BF_UNIQUE_KEY);
        } else {
            uniqueKey = UUID.randomUUID().toString();
        }

        super.onCreate(savedInstanceState);

        U2020App app = U2020App.get(this);
        onCreateComponent(app.component());
        if (viewContainer == null) {
            throw new IllegalStateException("No injection happened. Add component.inject(this) in onCreateComponent() implementation.");
        }
        Registry.add(this, viewId(), presenter());
        final LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup container = viewContainer.forActivity(this);
        layoutInflater.inflate(layoutId(), container);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BF_UNIQUE_KEY, uniqueKey);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        uniqueKey = savedInstanceState.getString(BF_UNIQUE_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter().onActivityResult(requestCode, resultCode, data);
    }

    protected void onExtractParams(@NonNull Bundle params) {
        // default no implementation
    }

    public String uniqueKey() {
        return uniqueKey;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNetworkReceiver = new NetworkReceiver(isConnected -> {
            presenter().onNetworkConnectionStateChanged(isConnected);
        });
        mNetworkReceiver.register(this);
    }

    @Override
    protected void onStop() {
        mNetworkReceiver.unregister(this);
        mNetworkReceiver = null;
        super.onStop();
    }

    /**
     * Must be implemented by derived activities. Injection must be performed here.
     * Otherwise IllegalStateException will be thrown. Derived activity is
     * responsible to create and store it's component.
     *
     * @param u2020Component application level component
     */
    protected abstract void onCreateComponent(U2020Component u2020Component);

    protected abstract
    @LayoutRes
    int layoutId();

    protected abstract BasePresenter<? extends BaseView> presenter();

    protected abstract
    @IdRes
    int viewId();
}
