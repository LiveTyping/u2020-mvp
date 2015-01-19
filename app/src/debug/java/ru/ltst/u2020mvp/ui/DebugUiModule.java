package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.debug.DebugAppContainer;
import ru.ltst.u2020mvp.ui.debug.SocketActivityHierarchyServer;

@Module(includes = UiModule.class)
public class DebugUiModule {

    @Provides
    @ApplicationScope
    AppContainer provideAppContainer(DebugAppContainer debugAppContainer) {
        return debugAppContainer;
    }

    @Provides
    @ApplicationScope
    ActivityHierarchyServer provideActivityHierarchyServer() {
        return new SocketActivityHierarchyServer();
    }
}
