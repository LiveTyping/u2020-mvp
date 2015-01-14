package ru.ltst.u2020mvp;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import ru.ltst.u2020mvp.ui.ActivityHierarchyServer;
import timber.log.Timber;

import static timber.log.Timber.DebugTree;

public class U2020App extends Application {
    private U2020Component component;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildComponentAndInject() {
        component = U2020Component.Initializer.init(this);
        component.inject(this);
    }

    public U2020Component component() {
        return component;
    }

    public static U2020App get(Context context) {
        return (U2020App) context.getApplicationContext();
    }
}
