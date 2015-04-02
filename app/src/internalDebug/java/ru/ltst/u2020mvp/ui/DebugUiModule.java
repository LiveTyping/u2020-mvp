package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.misc.DebugAppContainer;

@Module(includes = UiModule.class)
public class DebugUiModule {
    @Provides
    @ApplicationScope
    AppContainer provideAppContainer(DebugAppContainer appContainer) {
        return appContainer;
    }
}
