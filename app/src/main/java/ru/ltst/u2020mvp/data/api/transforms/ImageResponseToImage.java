package ru.ltst.u2020mvp.data.api.transforms;

import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;

import rx.functions.Func1;

public class ImageResponseToImage implements Func1<ImageResponse, Image> {
    @Override
    public Image call(ImageResponse imageResponse) {
        return imageResponse.data;
    }
}
