package ru.ltst.u2020mvp.data.api.transforms;

import ru.ltst.u2020mvp.data.api.model.response.Gallery;
import ru.ltst.u2020mvp.data.api.model.response.Image;

import java.util.List;

import rx.functions.Func1;

public final class GalleryToImageList implements Func1<Gallery, List<Image>> {
    @Override
    public List<Image> call(Gallery gallery) {
        return gallery.data;
    }
}
