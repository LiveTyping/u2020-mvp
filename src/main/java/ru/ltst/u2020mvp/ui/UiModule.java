package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.navigation.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ToolbarPresenter;

@Module
public class UiModule {

    @Provides
    ToolbarPresenter proviceToolbarPresenter() {
        return new ToolbarPresenter();
    }

    @Provides
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    ScreenSwitcher provideScreenSwitcher(ActivityScreenSwitcher activityScreenSwitcher) {
        return activityScreenSwitcher;
    }
}
