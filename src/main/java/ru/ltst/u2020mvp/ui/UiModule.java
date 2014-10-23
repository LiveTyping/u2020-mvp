package ru.ltst.u2020mvp.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.navigation.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;

@Module(
        complete = false,
        library = true
)
public class UiModule {
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

    @Provides
    @Singleton
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @Singleton
    ScreenSwitcher provideScreenSwitcher(ActivityScreenSwitcher activityScreenSwitcher) {
        return activityScreenSwitcher;
    }
}
