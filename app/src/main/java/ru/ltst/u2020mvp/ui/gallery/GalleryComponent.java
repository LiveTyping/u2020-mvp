package ru.ltst.u2020mvp.ui.gallery;

import dagger.Component;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.ui.gallery.view.GalleryView;

@GalleryScope
@Component(
    dependencies = U2020Component.class,
    modules = GalleryModule.class
)
public interface GalleryComponent extends GalleryView.Injector {
    void inject(GalleryActivity activity);
    GalleryActivity.Presenter presenter();
}
