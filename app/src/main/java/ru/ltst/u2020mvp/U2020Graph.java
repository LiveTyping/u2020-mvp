package ru.ltst.u2020mvp;

import android.app.Application;

import com.squareup.picasso.Picasso;

import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.data.IntentFactory;
import ru.ltst.u2020mvp.data.api.GithubService;
import ru.ltst.u2020mvp.base.mvp.ActivityHierarchyServer;
import ru.ltst.u2020mvp.base.mvp.ViewContainer;

/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface U2020Graph {
    void inject(U2020App app);
    Application application();
    ViewContainer viewContainer();
    Picasso picasso();
    ActivityScreenSwitcher activityScreenSwitcher();
    ActivityHierarchyServer activityHierarchyServer();
    GithubService githubService();
    IntentFactory intentFactory();
}
