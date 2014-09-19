package com.jakewharton.u2020.data.api.transforms;

import com.jakewharton.u2020.data.api.model.response.Gallery;
import com.jakewharton.u2020.data.api.model.response.Image;

import java.util.List;

import rx.functions.Func1;

public final class GalleryToImageList implements Func1<Gallery, List<Image>> {
    @Override
    public List<Image> call(Gallery gallery) {
        return gallery.data;
    }
}
