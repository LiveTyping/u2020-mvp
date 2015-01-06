package ru.ltst.u2020mvp.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = UiModule.class)
public class ReleaseUiModule {
    @Provides
    @Singleton
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Provides
    @Singleton
    ActivityHierarchyServer provideActivityHierarchyServer() {
        return ActivityHierarchyServer.NONE;
    }
}
