package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;

@Module(includes = UiModule.class)
public class ReleaseUiModule {
    @Provides
    @ApplicationScope
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }
}
