package ru.ltst.u2020mvp.ui.image;

import dagger.Component;
import ru.ltst.u2020mvp.U2020Component;

@ImgurImageScope
@Component(
        dependencies = U2020Component.class,
        modules = ImgurImageModule.class
)
public interface ImgurImageComponent extends ImgurImageView.Injector {
    void inject(ImgurImageActivity activity);
}
