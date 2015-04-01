package ru.ltst.u2020mvp.ui;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.ToolbarPresenter;

@Module
public class UiModule {

    @Provides
    @ApplicationScope
    ToolbarPresenter proviceToolbarPresenter() {
        return new ToolbarPresenter();
    }

    @Provides
    @ApplicationScope
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @ApplicationScope
    ScreenSwitcher provideScreenSwitcher(ActivityScreenSwitcher activityScreenSwitcher) {
        return activityScreenSwitcher;
    }
}
