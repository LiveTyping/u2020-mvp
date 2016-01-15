package ru.ltst.u2020mvp;

import com.squareup.picasso.Picasso;

import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.data.GalleryDatabase;
import ru.ltst.u2020mvp.data.api.GalleryService;
import ru.ltst.u2020mvp.data.api.ImageService;
import ru.ltst.u2020mvp.ui.ActivityHierarchyServer;
import ru.ltst.u2020mvp.ui.AppContainer;

/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface U2020Graph {
    void inject(U2020App app);
    AppContainer appContainer();
    Picasso picasso();
    ActivityScreenSwitcher activityScreenSwitcher();
    GalleryDatabase galleryDatabase();
    GalleryService galleryService();
    ImageService imageService();
    ActivityHierarchyServer activityHierarchyServer();
}
