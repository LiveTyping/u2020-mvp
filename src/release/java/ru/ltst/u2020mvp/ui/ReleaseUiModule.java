package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;

@Module(includes = UiModule.class)
public class ReleaseUiModule {
    @Provides
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Provides
    ActivityHierarchyServer provideActivityHierarchyServer() {
        return ActivityHierarchyServer.NONE;
    }
}
