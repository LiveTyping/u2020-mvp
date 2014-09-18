package com.jakewharton.u2020;

import android.app.Application;
import android.content.Context;

import com.jakewharton.u2020.ui.ActivityHierarchyServer;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

import static timber.log.Timber.DebugTree;

public class U2020App extends Application implements U2020InjectionService {
    private ObjectGraph objectGraph;

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

        buildObjectGraphAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildObjectGraphAndInject() {
        objectGraph = ObjectGraph.create(Modules.list(this));
        objectGraph.inject(this);
    }

    public ObjectGraph plus(Object... modules) {
        return objectGraph.plus(modules);
    }

    public <T> T inject(T o) {
        return objectGraph.inject(o);
    }

    public static U2020App get(Context context) {
        return (U2020App) context.getApplicationContext();
    }

    @Override
    public Object getSystemService(String name) {
        if (Injector.isValidSystemService(name))
            return this;
        return super.getSystemService(name);
    }
}
