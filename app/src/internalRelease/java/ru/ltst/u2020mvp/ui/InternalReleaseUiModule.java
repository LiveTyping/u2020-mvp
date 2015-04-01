package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;

@Module(includes = UiModule.class)
public final class InternalReleaseUiModule {
    @Provides
    @ApplicationScope
    AppContainer provideAppContainer(TelescopeAppContainer telescopeAppContainer) {
        return telescopeAppContainer;
    }
}
