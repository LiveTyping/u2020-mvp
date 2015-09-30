package ru.ltst.u2020mvp.data.api.transforms;

import retrofit.Result;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;
import rx.functions.Func1;

public class ImageResponseToImage implements Func1<Result<ImageResponse>, Image> {
    @Override
    public Image call(Result<ImageResponse> result) {
        return result.response().body().data;
    }
}
