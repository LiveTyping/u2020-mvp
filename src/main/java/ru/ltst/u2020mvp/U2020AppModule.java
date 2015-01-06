package ru.ltst.u2020mvp;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class U2020AppModule {
    private final U2020App app;

    public U2020AppModule(U2020App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }
}
