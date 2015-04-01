package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;

@Module(includes = UiModule.class)
public class DebugUiModule {
    @Provides
    @ApplicationScope
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }
}
