package ru.ltst.u2020mvp.ui.screen.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import javax.inject.Inject;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.base.HasComponent;
import ru.ltst.u2020mvp.base.mvp.BaseActivity;
import ru.ltst.u2020mvp.base.mvp.BasePresenter;
import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.data.api.oauth.OauthService;
import ru.ltst.u2020mvp.ui.screen.main.MainScope.MainComponent;

public final class MainActivity extends BaseActivity implements HasComponent<MainComponent> {
    @Inject
    MainPresenter presenter;
    @Inject
    ActivityScreenSwitcher activityScreenSwitcher;

    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Remove the status bar color. The DrawerLayout is responsible for drawing it from now on.
            setStatusBarColor(getWindow());
        }
    }

    @Override
    protected void onCreateComponent(U2020Component u2020Component) {
        mainComponent = DaggerMainScope_MainComponent.builder()
                .u2020Component(u2020Component)
                .mainActivityModule(new MainScope.MainActivityModule(this))
                .build();
        mainComponent.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityScreenSwitcher.attach(this);
    }

    @Override
    protected void onStop() {
        activityScreenSwitcher.detach(this);
        super.onStop();
    }

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected BasePresenter<? extends BaseView> presenter() {
        return presenter;
    }

    @Override
    protected int viewId() {
        return R.id.main_drawer_layout;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri data = intent.getData();
        if (data == null) return;
        if ("u2020".equals(data.getScheme())) {
            Intent serviceIntent = new Intent(this, OauthService.class);
            serviceIntent.setData(data);
            startService(serviceIntent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarColor(Window window) {
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }
}
