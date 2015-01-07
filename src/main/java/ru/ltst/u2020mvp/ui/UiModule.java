package ru.ltst.u2020mvp.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.navigation.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ToolbarPresenter;

@Module
public class UiModule {

    @Provides
    @Singleton
    ToolbarPresenter proviceToolbarPresenter() {
        return new ToolbarPresenter();
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
