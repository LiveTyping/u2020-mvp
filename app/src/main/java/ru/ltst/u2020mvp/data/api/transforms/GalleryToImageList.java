package ru.ltst.u2020mvp.data.api.transforms;

import java.util.List;

import retrofit2.Result;
import ru.ltst.u2020mvp.data.api.model.response.Gallery;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import rx.functions.Func1;

public final class GalleryToImageList implements Func1<Result<Gallery>, List<Image>> {
    @Override
    public List<Image> call(Result<Gallery> result) {
        return result.response().body().data;
    }
}
