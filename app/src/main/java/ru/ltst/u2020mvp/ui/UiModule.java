package ru.ltst.u2020mvp.ui;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.base.mvp.ActivityHierarchyServer;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.base.mvp.annotation.ActivityScreenSwitcherServer;

@Module
public class UiModule {

    @Provides
    @ApplicationScope
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @ApplicationScope
    @ActivityScreenSwitcherServer
    ActivityHierarchyServer provideActivityScreenSwitcherServer(final ActivityScreenSwitcher screenSwitcher) {
        return new ActivityHierarchyServer.Empty() {
            @Override
            public void onActivityStarted(Activity activity) {
                screenSwitcher.attach(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                screenSwitcher.detach(activity);
            }
        };
    }
}
